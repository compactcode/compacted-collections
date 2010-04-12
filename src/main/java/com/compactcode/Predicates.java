package com.compactcode;

import java.util.Collection;

import com.google.common.base.Predicate;

public final class Predicates {

	public static final Predicate<Integer> greaterThan(final int target) {
		return new Predicate<Integer>() {
			public boolean apply(Integer value) {
				return value > target;
			}
		};
	}
	
	public static final Predicate<Collection<?>> sizeGreaterThan(final int target) {
		return com.google.common.base.Predicates.compose(greaterThan(target), Functions.collectionSize());
	}
	
}
