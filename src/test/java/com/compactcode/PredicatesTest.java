package com.compactcode;

import static com.compactcode.FluentPredicate.predicate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Predicates;

public class PredicatesTest {

	@Test
	public void canAndPredicatesFluently() {
		assertEquals(false, predicate(Predicates.alwaysTrue()).and(Predicates.alwaysFalse()).apply(3));
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
