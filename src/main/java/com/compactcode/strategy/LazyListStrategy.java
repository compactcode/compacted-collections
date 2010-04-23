package com.compactcode.strategy;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * Delegates to {@link Lists#transform(List, Function)} for transformation.
 * <p />
 * Delegates to {@link ImmediateListStrategy} for filtering.
 */
public class LazyListStrategy implements ListStrategy {
	private final ListStrategy fallback = new ImmediateListStrategy();
	public <T, O> List<O> transform(List<T> fromList, Function<? super T, O> function) {
		return Lists.transform(fromList, function);
	}
	public <T> List<T> filter(List<T> fromList, Predicate<? super T> predicate) {
		return fallback.filter(fromList, predicate);
	}
}
