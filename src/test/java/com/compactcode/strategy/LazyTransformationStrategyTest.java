package com.compactcode.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;
import com.google.common.base.Predicate;


public class LazyTransformationStrategyTest {

	@Test
	public void canLazilyTransformAList() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		FluentList.fluent("1", "2").lazy().transform(throwException);
	}
	
	@Test
	public void cannotLazilyFilterAList() {
		Predicate<String> throwException = new Predicate<String>() {
			public boolean apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		try {
			FluentList.fluent("1", "2").lazy().filter(throwException);
			fail("lazy when not expected");
		} catch (RuntimeException e) {
			assertEquals("not lazy", e.getMessage());
		}
	}
	
	@Test
	public void cannotLazilyExpandList() {
		Function<String, List<String>> throwException = new Function<String, List<String>>() {
			public List<String> apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		try {
			FluentList.fluent("1", "2").lazy().expand(throwException);
			fail("lazy when not expected");
		} catch (RuntimeException e) {
			assertEquals("not lazy", e.getMessage());
		}
	}
	
}
