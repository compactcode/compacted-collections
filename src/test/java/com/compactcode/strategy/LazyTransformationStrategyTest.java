package com.compactcode.strategy;

import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;


public class LazyTransformationStrategyTest {

	@Test
	public void canLazilyTransformAList() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("immediate");
			}
		};
		FluentList.fluent("1", "2").lazy().transform(throwException);
	}
	
}
