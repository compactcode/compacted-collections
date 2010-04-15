package com.compactcode.strategy;

import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Predicates.alwaysTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;

public class ImmediateTransformationStrategyTest {

	Function<String, String> throwException = new Function<String, String>() {
		public String apply(String value) {
			throw new RuntimeException("immediate");
		}
	};
	
	@Test
	public void canImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").immediate().transform(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}
	}
	
	@Test
	public void canImmediatelyTransformAListTwice() {
		try {
			FluentList.fluent("1", "2").immediate().map(toStringFunction()).map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}

	@Test
	public void canFilterAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").immediate().filter(alwaysTrue()).map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canSortAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").immediate().sort().map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canReverseAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2").immediate().reverse().map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
	@Test
	public void canCompactAndImmediatelyTransformAList() {
		try {
			FluentList.fluent("1", "2", null).immediate().unique().map(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}		
	}
	
}
