package com.compactcode;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

public class FluentSet<T> extends ForwardingSet<T> {

	public static <T> FluentSet<T> fluent(Iterable<T> delegate) {
		return new FluentSet<T>(Sets.newHashSet(delegate));
	}
	
	public static <T> FluentSet<T> fluent(T... elements) {
		return new FluentSet<T>(Sets.newHashSet(elements));
	}
	
	private final Set<T> delegate;

	public FluentSet(Set<T> delegate) {
		this.delegate = delegate;
	}
	
	/**
	 * Convert each element of this set into a new one.
	 * 
	 * Naturally, duplicates will be removed so the size of the resulting set may be smaller than this set.
	 */
	public <O> FluentSet<O> transform(Function<T, O> mapper) {
		return fluent(Iterables.transform(this, mapper));
	}

	/**
	 * Synonym for transform.
	 */
	public <O> FluentSet<O> map(Function<T, O> mapper) {
		return transform(mapper);
	}
	
	/**
	 * Convert this set into a single value.
	 */
	public <O> O reduce(Function<Collection<T>, O> mapper) {
		return mapper.apply(this);
	}
	
	/**
	 * Find all matching elements in this set.
	 */
	public FluentSet<T> filter(Predicate<? super T> predicate) {
		return fluent(Sets.filter(this, predicate));
	}
	
	/**
	 * Find all matching elements in this set.
	 * 
	 * Composes the given predicate and mapper.
	 */
	public <O> FluentSet<T> filter(Function<? super T, O> mapper, Predicate<? super O> predicate) {
		return filter(Predicates.compose(predicate, mapper));
	}
	
	protected Set<T> delegate() {
		return delegate;
	}

	public FluentList<T> toList() {
		return FluentList.fluent(this);
	}

}
