package com.compactcode.strategy;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class LazyTransformationStrategy implements TransformationStrategy {

	public <T, O> List<O> transform(List<T> fromList, Function<? super T, O> function) {
		return Lists.transform(fromList, function);
	}

}
