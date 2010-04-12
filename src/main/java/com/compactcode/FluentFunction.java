package com.compactcode;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class FluentFunction<A, B> implements Function<A, B>{
	
	public static final <A, B> FluentFunction<A, B> fluent(Function<A, B> function) {
		return new FluentFunction<A, B>(function);
	}
	
	private final Function<A, B> current;
	
	public FluentFunction(Function<A, B> function) {
		this.current = function;
	}

	public <C> FluentFunction<A, C> compose(Function<? super B, C> next) {
		return fluent(Functions.compose(next, current));
	}

	public B apply(A input) {
		return current.apply(input);
	}

	
}