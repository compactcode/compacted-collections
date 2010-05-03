package com.compactcode.strategy;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Delegates to {@link Iterables#transform(Iterable, Function)} for transformation.
 * <p />
 * Delegates to {@link Iterables#filter(Iterable, Predicate)} for filtering.
 */
public class DefaultListStrategy implements ListStrategy {
	public <T, O> List<O> transform(List<T> fromList, Function<? super T, O> function) {
		return Lists.newArrayList(Iterables.transform(fromList, function));
	}
	public <T> List<T> filter(List<T> fromList, Predicate<? super T> predicate) {
		return Lists.newArrayList(Iterables.filter(fromList, predicate));
	}
}
