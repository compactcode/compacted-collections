package com.compactcode;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;

public class FluentFunction<A, B> implements Function<A, B>{

	public static final <T, O> Function<List<T>, List<O>> all(final Function<T, O> mapper) {
		return new Function<List<T>, List<O>>() {
			public List<O> apply(List<T> source) {
				return Lists.transform(source, mapper);
			}
		};
	}
	
	public static final <A, B> FluentFunction<A, B> fluent(Function<A, B> function) {
		return new FluentFunction<A, B>(function);
	}
	
	private final Function<A, B> current;
	
	public FluentFunction(Function<A, B> function) {
		this.current = function;
	}

	public <C> FluentFunction<A, C> compose(Function<B, C> next) {
		return fluent(Functions.compose(next, current));
	}

	public B apply(A input) {
		return current.apply(input);
	}

	
}