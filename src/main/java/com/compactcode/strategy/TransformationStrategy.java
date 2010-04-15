package com.compactcode.strategy;

import java.util.List;

import com.google.common.base.Function;

public interface TransformationStrategy {
	<T, O>List<O> transform(List<T> source, Function<? super T, O> mapper);
}
