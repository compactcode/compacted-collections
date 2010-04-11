package com.compactcode;

import static com.compactcode.FluentFunction.all;
import static com.compactcode.FluentFunction.fluent;
import static com.compactcode.FluentList.fluent;
import static com.compactcode.Functions.avg;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.sum;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Functions;

public class FunctionsTest {
	
	@Test
	public void canConvertStringsToIntegers() {
		assertEquals(newArrayList(1, 2), fluent(newArrayList("1", "2")).map(stringToInt()));
	}
	
	@Test
	public void canSumIntegers() {
		assertEquals(6, fluent(newArrayList(1, 2, 3)).reduce(sum()));
	}
	
	@Test
	public void canAverageIntegers() {
		assertEquals(2, fluent(newArrayList(1, 2, 3)).reduce(avg()));
		assertEquals(1, fluent(newArrayList(1, 2)).reduce(avg()));
	}
	
	@Test
	public void canMapListsUsingMapAllFunction() {
		assertEquals(newArrayList(1, 2), all(stringToInt()).apply(newArrayList("1", "2")));
	}
	
	@Test
	public void canComposeFunctionsFluently() {
		assertEquals(3, fluent(Functions.toStringFunction()).compose(stringToInt()).apply(3));
	}
	
}
