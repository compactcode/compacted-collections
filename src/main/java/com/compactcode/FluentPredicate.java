package com.compactcode;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class FluentPredicate<T> implements Predicate<T> {

	public static final <T> FluentPredicate<T> predicate(Predicate<T> predicate) {
		return new FluentPredicate<T>(predicate);
	}
	
	private final Predicate<T> delegate;
	
	public FluentPredicate(Predicate<T> delegate) {
		this.delegate = delegate;
	}

	public FluentPredicate<T> and(Predicate<T> other) {
		return predicate(Predicates.<T>and(other, delegate));
	}
	
	public FluentPredicate<T> andNot(Predicate<T> other) {
		return and(Predicates.not(other));
	}
	
	public final boolean apply(T input) {
		return delegate.apply(input);
	}

}
