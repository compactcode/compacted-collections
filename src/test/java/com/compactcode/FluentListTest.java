package com.compactcode;

import static com.compactcode.FluentList.fluent;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.sum;
import static com.google.common.base.Functions.*;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.in;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;


public class FluentListTest {
	
	@Test
	public void canCreateFluentListFromElements() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b"));
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingToString() {
		assertEquals(newArrayList("a", "b").toString(), fluent(newArrayList("a", "b")).toString());
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingEquals() {
		assertEquals(newArrayList("a", "b"), fluent(newArrayList("a", "b")));
	}
	
	@Test
	public void canSumListUsingToIntegerFunction() {
		assertEquals(3, fluent(newArrayList("1", "2")).map(stringToInt()).reduce(sum()));
	}
	
	@Test
	public void canFilterListUsingPredicate() {
		assertEquals(newArrayList("b", "c"), fluent(newArrayList("a", "b", "c")).filter(in(newArrayList("b", "c"))));
	}
	
	@Test
	public void canFilterMatchingElementUsingPredicateAndFunction() {
		assertEquals(newArrayList(2), fluent(newArrayList(1, 2)).filter(equalTo("2"), toStringFunction()));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicate() {
		assertEquals("b", fluent(newArrayList("a", "b", "b")).find(equalTo("b")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicateAndFunction() {
		assertEquals(2, fluent(newArrayList(1, 2)).find(equalTo("2"), toStringFunction()));
	}
	
	@Test
	public void canFindNoMatchingElementUsingPredicate() {
		assertEquals(null, fluent(newArrayList("a", "b", "b")).find(equalTo("c")));
	}
	
	@Test
	public void canExpandListIntoASingleList() {
		Function<Integer, List<Integer>> expander = new Function<Integer, List<Integer>>() {
			public List<Integer> apply(Integer source) {
				return newArrayList(source, source);
			}
		};
		assertEquals(newArrayList(1, 1, 2, 2), fluent(newArrayList(1, 2)).expand(expander));
	}
	
}
