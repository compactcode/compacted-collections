package com.compactcode;

import java.util.Collection;
import java.util.List;

import com.compactcode.strategy.ImmediateTransformationStrategy;
import com.compactcode.strategy.LazyTransformationStrategy;
import com.compactcode.strategy.TransformationStrategy;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class FluentList<T> extends ForwardingList<T> {

	public static <T> FluentList<T> fluent(List<T> delegate) {
		return new FluentList<T>(delegate);
	}
	
	public static <T> FluentList<T> fluent(Iterable<T> delegate) {
		return new FluentList<T>(Lists.newArrayList(delegate));
	}
	
	public static <T> FluentList<T> fluent(T... elements) {
		return fluent(Lists.newArrayList(elements));
	}
	
	private final List<T> delegate;
	private final TransformationStrategy strategy;
	
	public FluentList(List<T> delegate) {
		this(delegate, new LazyTransformationStrategy());
	}

	public FluentList(List<T> delegate, TransformationStrategy strategy) {
		this.delegate = delegate;
		this.strategy = strategy;
	}

	/**
	 * Convert each element of this list into a new one.
	 */
	public <O> FluentList<O> transform(Function<? super T, O> mapper) {
		return fluent(strategy.transform(delegate, mapper));
	}
	
	/**
	 * Synonym for transform.
	 */
	public <O> FluentList<O> map(Function<? super T, O> mapper) {
		return transform(mapper);
	}
	
	/**
	 * Convert this list into a single value.
	 */
	public <O> O reduce(Function<Collection<T>, O> reducer) {
		return reducer.apply(this);
	}
	
	/**
	 * Convert each element of this list into a list, then concatenate those lists.
	 */
	public <O> FluentList<O> expand(Function<T, List<O>> expander) {
		List<O> flat = Lists.newArrayList();
		for (List<O> sublist : map(expander)) {
			flat.addAll(sublist);
		}
		return fluent(flat);
	}
	
	/**
	 * Find all matching elements in this list.
	 */
	public FluentList<T> filter(Predicate<? super T> predicate) {
		return fluent(Iterables.filter(this, predicate));
	}
	
	/**
	 * Find all matching elements in this list.
	 * 
	 * Composes the given predicate and mapper.
	 */
	public <O> FluentList<T> filter(Function<? super T, O> mapper, Predicate<? super O> predicate) {
		return filter(Predicates.compose(predicate, mapper));
	}
	
	/**
	 * Find the first matching element in this list, or return null.
	 */
	public T find(Predicate<? super T> predicate) {
		return filter(predicate).first();
	}
	
	/**
	 * Find the first matching element in this list, or return null.
	 * 
	 * Composes the given predicate and mapper.
	 */
	public <O> T find(Function<? super T, O> mapper, Predicate<? super O> predicate) {
		return find(Predicates.compose(predicate, mapper));
	}
	
	/**
	 * Return a sorted copy of this list using natural ordering of each element as a string.
	 */
	public <O> FluentList<T> sort() {
		return sort(Ordering.usingToString());
	}
	
	/**
	 * Return a sorted copy of this list using natural ordering.
	 */
	public <O> FluentList<T> sort(Function<T, ? extends Comparable<O>> mapper) {
		return sort(mapper, Ordering.natural());
	}
	
	/**
	 * Return a sorted copy of this list using the given ordering.
	 */
	public FluentList<T> sort(Ordering<? super T> order) {
		return fluent(order.sortedCopy(this));
	}
	
	/**
	 * Return a sorted copy of this list using the given ordering.
	 */
	public <O> FluentList<T> sort(Function<? super T, O> mapper, Ordering<? super O> order) {
		return sort(order.onResultOf(mapper));
	}
	
	/**
	 * Return a copy of this list in reverse order.
	 */
	public FluentList<T> reverse() {
		return fluent(Iterables.reverse(this));
	}
	
	/**
	 * Find the first element in this list, or return null if no elements exist.
	 */
	public T first() {
		if(size() == 0) {
			return null;
		}
		return get(0);
	}
	
	/**
	 * Find the last element in this list, or return null if no elements exist.
	 */
	public T last() {
		if(size() == 0) {
			return null;
		}
		return get(size() - 1);
	}
	
	/**
	 * Return a string created by concatenating each element seperated by the given seperator.
	 */
	public String join(String seperator) {
		return Joiner.on(seperator).join(this);
	}
	
	/**
	 * Return a copy of this list with duplicates removed.
	 */
	public FluentList<T> unique() {
		return toSet().toList();
	}
	
	/**
	 * Remove all null elements from this list.
	 */
	public FluentList<T> compact() {
		return filter(Predicates.notNull());
	}
	
	/**
	 * Return a copy of this list as a set.
	 */
	public FluentSet<T> toSet() {
		return FluentSet.fluent(this);
	}
	
	protected List<T> delegate() {
		return delegate;
	}

	/**
	 * Subsequent transformations (map, transform) will be applied immediately.
	 */
	public FluentList<T> immediate() {
		return new FluentList<T>(this, new ImmediateTransformationStrategy());
	}

	/**
	 * Subsequent transformations (map, transform) will be applied lazily.
	 */
	public FluentList<T> lazy() {
		return new FluentList<T>(this, new LazyTransformationStrategy());
	}

}
