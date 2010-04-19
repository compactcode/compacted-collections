package com.compactcode;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Experimental.
 * 
 * Designed to make it easier to compose and chain predicates together.
 */
public class FluentPredicate<T> implements Predicate<T> {

	/**
	 * Wrap the given predicate with fluent behaviour.
	 */
	public static final <T> FluentPredicate<T> predicate(Predicate<T> predicate) {
		return new FluentPredicate<T>(predicate);
	}
	
	private final Predicate<T> delegate;
	
	public FluentPredicate(Predicate<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * And this predicate with the given predicate using {@link Predicates#and(Predicate, Predicate)}.
	 */
	public FluentPredicate<T> and(Predicate<T> other) {
		return predicate(Predicates.<T>and(delegate, other));
	}
	
	/**
	 * And this predicate with the negation of the given predicate using {@link Predicates#and(Predicate, Predicate)}.
	 */
	public FluentPredicate<T> andNot(Predicate<T> other) {
		return and(Predicates.not(other));
	}
	
	/**
	 * Or this predicate with the given predicate using {@link Predicates#or(Predicate, Predicate)}.
	 */
	public FluentPredicate<T> or(Predicate<T> other) {
		return predicate(Predicates.<T>or(delegate, other));
	}
	
	/**
	 * Or this predicate with the negation of the given predicate using {@link Predicates#or(Predicate, Predicate)}.
	 */
	public FluentPredicate<T> orNot(Predicate<T> other) {
		return or(Predicates.not(other));
	}
	
	public final boolean apply(T input) {
		return delegate.apply(input);
	}

}
