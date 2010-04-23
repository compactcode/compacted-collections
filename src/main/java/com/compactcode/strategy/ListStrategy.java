package com.compactcode.strategy;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Represents a strategy for transforming and filtering a {@link List}.  
 */
public interface ListStrategy {
	<T> List<T> filter(List<T> fromList, Predicate<? super T> predicate);
	<T, O>List<O> transform(List<T> fromList, Function<? super T, O> function);
}
