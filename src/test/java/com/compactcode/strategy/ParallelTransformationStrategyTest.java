package com.compactcode.strategy;

import static com.compactcode.FluentList.fluent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;


public class ParallelTransformationStrategyTest {

	private static final int SLEEP_TIME = 100;
	
	private Function<Integer, Integer> slowTransformation = new Function<Integer, Integer>() {
		public Integer apply(Integer value) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				
			}
			return value;
		}
	};

	@Test
	public void canTransformAInParallel() {
		assertEquals(Lists.newArrayList("1", "2", "3"), fluent(1, 2, 3).parallel(2).transform(Functions.toStringFunction()));
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
		
		long normalTime = measureExecutionTime(immediate);
		long parallelTime = measureExecutionTime(parallel);
		
		System.out.println("normal time: " + normalTime + " ms");
		System.out.println("parallel time: " + parallelTime + " ms");
		
		// Just check that there is a conservative, consistent improvment.
		assertTrue(parallelTime < normalTime - 150);
	}
	
	private long measureExecutionTime(Runnable task) {
		long start = System.currentTimeMillis();
		task.run();
		long end = System.currentTimeMillis();
		return end - start;
	}
}
