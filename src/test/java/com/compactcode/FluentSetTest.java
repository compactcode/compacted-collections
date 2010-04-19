package com.compactcode;

import static com.compactcode.FluentSet.fluent;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.sum;
import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.in;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class FluentSetTest {

	@Test
	public void canCreateFluentSetFromElements() {
		assertEquals(newHashSet("a", "b"), fluent("a", "b"));
	}
	
	@Test
	public void canTransformElementsUsingMapper() {
		assertEquals(newHashSet(1, 2), fluent("1", "2", "2").transform(stringToInt()));
	}
	
	@Test
	public void canUseMapAsSynonymToTransform() {
		assertEquals(newHashSet(1, 2), fluent("1", "2", "2").map(stringToInt()));
	}
	
	@Test
	public void canCompareFluentToNonFluentSetUsingToString() {
		assertEquals(newHashSet("a", "b").toString(), fluent("a", "b").toString());
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingEquals() {
		assertEquals(newHashSet("a", "b"), fluent("a", "b"));
	}
	
	@Test
	public void canSumListUsingReduce() {
		assertEquals(Integer.valueOf(3), fluent(1, 2).reduce(sum()));
	}
	
	@Test
	public void canFilterUsingPredicate() {
		assertEquals(newHashSet("b", "c"), fluent("a", "b", "c").filter(in(newHashSet("b", "c"))));
	}
	
	@Test
	public void canFilterMatchingElementUsingPredicateAndFunction() {
		assertEquals(newHashSet(2), fluent(1, 2).filter(toStringFunction(), equalTo("2")));
	}
	
}
