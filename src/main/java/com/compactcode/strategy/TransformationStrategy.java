package com.compactcode.strategy;

import java.util.List;

import com.compactcode.FluentList;
import com.google.common.base.Function;

/**
 * Represents a strategy used to transform/map a {@link FluentList}.  
 */
public interface TransformationStrategy {
	<T, O>List<O> transform(List<T> fromList, Function<? super T, O> function);
}
