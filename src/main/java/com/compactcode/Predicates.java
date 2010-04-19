package com.compactcode;

import org.hamcrest.Matcher;
import org.hamcrest.number.OrderingComparisons;

import com.google.common.base.Predicate;

/**
 * Use the {@link Matcher} library provided by hamcrest instead.
 */
@Deprecated
public final class Predicates {

	/**
	 * Use {@link OrderingComparisons} instead.
	 */
	@Deprecated
	public static final Predicate<Integer> greaterThan(final int target) {
		return fromMatcher(OrderingComparisons.greaterThan(target));
	}
	
	private static final <T> Predicate<T> fromMatcher(final Matcher<T> matcher) {
		return new Predicate<T>() {
			public boolean apply(T element) {
				return matcher.matches(element);
			}
		};
	}
	
}
