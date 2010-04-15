package com.compactcode;

import static com.compactcode.FluentList.fluent;
import static com.compactcode.FluentPredicate.predicate;
import static com.compactcode.Predicates.greaterThan;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PredicatesTest {

	@Test
	public void canFilterItemsGreaterThanValue() {
		assertEquals(newArrayList(4, 5), fluent(newArrayList(1, 2, 3, 4, 5)).filter(greaterThan(3)));
	}
	
	@Test
	public void canComposePredicatesFluently() {
		assertEquals(true, predicate(greaterThan(2)).andNot(greaterThan(5)).apply(3));
		assertEquals(false, predicate(greaterThan(6)).andNot(greaterThan(5)).apply(3));
	}
	
}
