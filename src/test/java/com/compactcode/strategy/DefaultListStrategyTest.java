package com.compactcode.strategy;

import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Predicates.alwaysTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class DefaultListStrategyTest {

	Function<String, String> throwException = new Function<String, String>() {
		public String apply(String value) {
			throw new RuntimeException("immediate");
		}
	};
	
	@Test
	public void canImmediatelyTransformAList() {
		FluentList.fluent(1, 2, null).compact();
		try {
			FluentList.fluent("1", "2").serial().transform(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}
	}
	
	@Test
	public void canImmediatelyTransformAListTwice() {
		try {
			FluentList.fluent("1", "2").serial().map(toStringFunction()).map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}

	@Test
	public void canImmediatelyExpandList() {
		Function<String, List<String>> throwException = new Function<String, List<String>>() {
			public List<String> apply(String value) {
				throw new RuntimeException("immediate");
			}
		};
		try {
			FluentList.fluent("1", "2").serial().expand(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canExpandAndImmediatelyTransformAList() {
		Function<String, List<String>> expander = new Function<String, List<String>>() {
			public List<String> apply(String value) {
				return Lists.newArrayList(value);
			}
		};
		try {
			FluentList.fluent("1", "2").serial().expand(expander).map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canFilterAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").serial().filter(alwaysTrue()).map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canSortAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").serial().sort().map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canReverseAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").serial().reverse().map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canCompactAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2", null).serial().unique().map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canImmediatelyFilterAList() {
		Predicate<String> throwException = new Predicate<String>() {
			public boolean apply(String value) {
				throw new RuntimeException("immediate");
			}
		};
		try {
			FluentList.fluent("1", "2").serial().filter(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}
	}
}
