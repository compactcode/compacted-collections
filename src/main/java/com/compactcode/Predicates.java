package com.compactcode;

import com.google.common.base.Predicate;

public final class Predicates {

	public static final Predicate<Integer> greaterThan(final int target) {
		return new Predicate<Integer>() {
			public boolean apply(Integer value) {
				return value > target;
			}
		};
	}
	
}
