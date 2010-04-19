package com.compactcode;

import org.hamcrest.Matcher;
import org.hamcrest.number.OrderingComparisons;

import com.google.common.base.Predicate;

public final class Predicates {

	public static final Predicate<Integer> greaterThan(final int target) {
		return fromMatcher(OrderingComparisons.greaterThan(target));
	}
	
	public static final <T> Predicate<T> fromMatcher(final Matcher<T> matcher) {
		return new Predicate<T>() {
			public boolean apply(T element) {
				return matcher.matches(element);
			}
		};
	}
	
}
