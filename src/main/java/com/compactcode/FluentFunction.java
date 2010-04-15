package com.compactcode;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class FluentFunction<A, B> implements Function<A, B>{
	
	public static final <A, B> FluentFunction<A, B> function(Function<A, B> function) {
		return new FluentFunction<A, B>(function);
	}
	
	private final Function<A, B> delegate;
	
	public FluentFunction(Function<A, B> delegate) {
		this.delegate = delegate;
	}

	public <C> FluentFunction<A, C> map(Function<? super B, C> next) {
		return function(Functions.compose(next, delegate));
	}
	
	public FluentPredicate<A> check(Predicate<B> next) {
		return FluentPredicate.predicate(Predicates.compose(next, this));
	}

	public final B apply(A input) {
		return delegate.apply(input);
	}
	
}