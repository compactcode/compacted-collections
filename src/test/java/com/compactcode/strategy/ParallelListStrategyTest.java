package com.compactcode.strategy;

import static com.compactcode.FluentList.fluent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;


public class ParallelListStrategyTest {

	private static final int SLEEP_TIME = 100;
	
	private Predicate<Integer> slowMatcher = new Predicate<Integer>() {
		public boolean apply(Integer value) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				
			}
			return true;
		}
	};
	private Function<Integer, Boolean> slowTransformation = Functions.forPredicate(slowMatcher);

	@Test
	public void canTransformAInParallel() {
		assertEquals(Lists.newArrayList("1", "2", "3"), fluent(1, 2, 3).parallel(2).transform(Functions.toStringFunction()));
	}
	
	@Test
	public void canFilterAInParallel() {
		assertEquals(Lists.newArrayList(2), fluent(1, 2, 3).parallel(2).filter(IsEqual.equalTo(2)));
	}
	
	@Test
	public void transformAInParallelPropgatesTransformationErrors() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("propogated");
			}
		};
		try {
			fluent("1").parallel(2).transform(throwException);
			fail();
		} catch (Exception e) {
			assertEquals(true, e.getMessage().contains("propogated"));
		}
	}
	
	@Test
	public void transformingInParallelIsFasterThanImmediateForSlowTransformations() {
		final FluentList<Integer> source = fluent(1, 2, 3, 4, 5, 6);
		Runnable immediate = new Runnable() {
			public void run() {
				source.immediate().transform(slowTransformation);
			}
		};
		Runnable parallel = new Runnable() {
			public void run() {
				source.parallel(6).transform(slowTransformation);
			}
		};
		assertSecondRunnableIsFasterBy(immediate, parallel, 150);
	}
	
	@Test
	public void filteringInParallelIsFasterThanImmediateForSlowMatchers() {
		final FluentList<Integer> source = fluent(1, 2, 3, 4, 5, 6);
		Runnable immediate = new Runnable() {
			public void run() {
				source.immediate().filter(slowMatcher);
			}
		};
		Runnable parallel = new Runnable() {
			public void run() {
				source.parallel(6).filter(slowMatcher);
			}
		};
		assertSecondRunnableIsFasterBy(immediate, parallel, 150);
	}

	private void assertSecondRunnableIsFasterBy(Runnable slower, Runnable faster, int tolerance) {
		long slowTime = measureExecutionTime(slower);
		long fastTime = measureExecutionTime(faster);
		
		assertTrue(fastTime < slowTime - tolerance);
	}
	
	private long measureExecutionTime(Runnable task) {
		long start = System.currentTimeMillis();
		task.run();
		long end = System.currentTimeMillis();
		return end - start;
	}
}
