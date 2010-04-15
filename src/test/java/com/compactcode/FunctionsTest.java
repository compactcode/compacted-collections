package com.compactcode;

import static com.compactcode.FluentFunction.function;
import static com.compactcode.FluentList.fluent;
import static com.compactcode.Functions.each;
import static com.compactcode.Functions.propertyValue;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.stringToLong;
import static com.compactcode.Functions.sum;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class FunctionsTest {
	
	public class Customer {
		private String name;
		public Customer(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		};
	}
	
	@Test
	public void canConvertStringsToIntegers() {
		assertEquals(newArrayList(1, 2), fluent(newArrayList("1", "2")).map(stringToInt()));
	}
	
	@Test
	public void canConvertStringsToLongs() {
		assertEquals(newArrayList(1L, 2L), fluent(newArrayList("1", "2")).map(stringToLong()));
	}

	@Test
	public void canMapObjectsToPropertyValues() {
		Function<Customer, String> toName = propertyValue("name");
		assertEquals(newArrayList("a", "b"), fluent(new Customer("a"), new Customer("b")).map(toName));
	}
	
	@Test
	public void cannotMapObjectsToPropertyValuesWhenPropertyNameIsIncorrect() {
		try {
			fluent(new Customer("1")).map(propertyValue("notAProperty")).hashCode();
			fail();
		}catch (Exception e) {
			assertEquals("java.lang.NoSuchFieldException: notAProperty", e.getMessage());
		}
	}
	
	@Test
	public void canSumIntegers() {
		assertEquals(6, fluent(newArrayList(1, 2, 3)).reduce(sum()));
	}
	
	@Test
	public void canMapListsUsingMapAllFunction() {
		assertEquals(newArrayList(1, 2), each(stringToInt()).apply(newArrayList("1", "2")));
	}
	
	@Test
	public void canComposeFunctionsFluently() {
		assertEquals(3, function(Functions.toStringFunction()).map(stringToInt()).apply(3));
	}
	
	@Test
	public void canComposePredicatesFromFunctionsFluently() {
		assertEquals(true, function(stringToInt()).check(Predicates.greaterThan(2)).apply("3"));
	}
	
}
