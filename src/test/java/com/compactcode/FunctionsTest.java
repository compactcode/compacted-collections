package com.compactcode;

import static com.compactcode.FluentList.fluent;
import static com.compactcode.Functions.each;
import static com.compactcode.Functions.max;
import static com.compactcode.Functions.min;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.stringToLong;
import static com.compactcode.Functions.sum;
import static com.compactcode.Functions.toInt;
import static com.compactcode.Functions.toLong;
import static com.compactcode.Functions.toPropertyValue;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import com.google.common.base.Function;

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
	public void canConvertStringsToIntegersUsingShortcut() {
		assertEquals(newArrayList(1, 2), fluent(newArrayList("1", "2")).map(toInt()));
	}
	
	@Test
	public void canConvertStringsToLongs() {
		assertEquals(newArrayList(1L, 2L), fluent(newArrayList("1", "2")).map(stringToLong()));
	}

	@Test
	public void canConvertStringsToLongsUsingShortcut() {
		assertEquals(newArrayList(1L, 2L), fluent(newArrayList("1", "2")).map(toLong()));
	}
	
	@Test
	public void canMapObjectsToPropertyValues() {
		Function<Customer, String> toName = toPropertyValue("name");
		assertEquals(newArrayList("a", "b"), fluent(new Customer("a"), new Customer("b")).map(toName));
	}
	
	@Test
	public void cannotMapObjectsToPropertyValuesWhenPropertyNameIsIncorrect() {
		try {
			fluent(new Customer("1")).map(toPropertyValue("notAProperty")).hashCode();
			fail();
		}catch (Exception e) {
			assertEquals("java.lang.NoSuchFieldException: notAProperty", e.getMessage());
		}
	}
	
	@Test
	public void canSumIntegers() {
		assertEquals(Integer.valueOf(6), fluent(newArrayList(1, 2, 3)).reduce(sum()));
	}
	
	@Test
	public void canFindMinInteger() {
		assertEquals(Integer.valueOf(1), fluent(newArrayList(1, 2, 3)).reduce(min()));
	}
	
	@Test
	public void canFindMinIntegerWithNulls() {
		assertEquals(Integer.valueOf(1), fluent(newArrayList(1, 2, null, 3)).reduce(min()));
	}
	
	@Test
	public void canFindMinIntegerReturnsNullForEmptyList() {
		ArrayList<Integer> source = newArrayList();
		assertEquals(null, fluent(source).reduce(min()));
	}
	
	@Test
	public void canFindMaxInteger() {
		assertEquals(Integer.valueOf(3), fluent(newArrayList(1, 2, 3)).reduce(max()));
	}
	
	@Test
	public void canFindMaxIntegerWithNulls() {
		assertEquals(Integer.valueOf(3), fluent(newArrayList(1, 2, null, 3)).reduce(max()));
	}
	
	@Test
	public void canFindMaxIntegerReturnsNullForEmptyList() {
		ArrayList<Integer> source = newArrayList();
		assertEquals(null, fluent(source).reduce(max()));
	}
	
	@Test
	public void canMapListsUsingMapAllFunction() {
		assertEquals(newArrayList(1, 2), each(stringToInt()).apply(newArrayList("1", "2")));
	}
	
}
