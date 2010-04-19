package com.compactcode.strategy;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * A transformation strategy that delegates to {@link Iterables#transform(Iterable, Function)} and
 * copies the result into a new list.
 */
public class ImmediateTransformationStrategy implements TransformationStrategy {
	public <T, O> List<O> transform(List<T> fromList, Function<? super T, O> function) {
		return Lists.newArrayList(Iterables.transform(fromList, function));
	}
}
