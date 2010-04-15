package com.compactcode;

import static com.compactcode.FluentList.fluent;
import static com.compactcode.FluentPredicate.predicate;
import static com.compactcode.Predicates.greaterThan;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Predicates;

public class PredicatesTest {

	@Test
	public void canFilterItemsGreaterThanValue() {
		assertEquals(newArrayList(4, 5), fluent(newArrayList(1, 2, 3, 4, 5)).filter(greaterThan(3)));
	}

	@Test
	public void canAndPredicatesFluently() {
		assertEquals(false, predicate(greaterThan(2)).and(Predicates.<Integer>alwaysFalse()).apply(3));
	}
	
	@Test
	public void canAndNotPredicatesFluently() {
		assertEquals(true, predicate(Predicates.alwaysTrue()).andNot(Predicates.alwaysFalse()).apply(3));
	}
	
	@Test
	public void canOrPredicatesFluently() {
		assertEquals(true, predicate(Predicates.alwaysFalse()).or(Predicates.alwaysTrue()).apply(3));
	}
	
	@Test
	public void canOrNotPredicatesFluently() {
		assertEquals(true, predicate(Predicates.alwaysFalse()).orNot(Predicates.alwaysFalse()).apply(3));
	}
	
}
