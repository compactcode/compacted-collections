package com.compactcode.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.compactcode.FluentList;
import com.google.common.base.Function;

public class ImmediateTransformationStrategyTest {

	@Test
	public void canImmediatelyTransformAList() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("immediate");
			}
		};
		
		try {
			FluentList.fluent("1", "2").immediate().transform(throwException);
			fail("not immediate");
		} catch (RuntimeException e) {
			assertEquals("immediate", e.getMessage());
		}
	}
	
}
