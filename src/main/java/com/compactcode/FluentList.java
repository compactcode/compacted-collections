package com.compactcode;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class FluentList<T> extends ForwardingList<T> {
	
	public static <T> FluentList<T> fluent(Iterable<T> delegate) {
		return new FluentList<T>(Lists.newArrayList(delegate));
	}
	
	public static <T> FluentList<T> fluent(T... elements) {
		return fluent(Lists.newArrayList(elements));
	}
	
	private final List<T> delegate;
	
	public FluentList(List<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Convert each element of this list into another type.
	 */
	public <O> FluentList<O> transform(Function<? super T, O> mapper) {
		return fluent(Iterables.transform(delegate, mapper));
	}
	
	/**
	 * Synonym for transform.
	 */
	public <O> FluentList<O> map(Function<? super T, O> mapper) {
		return fluent(Iterables.transform(delegate, mapper));
	}
	
	/**
	 * Convert this list into a single value.
	 */
	public <O> O reduce(Function<List<T>, O> reducer) {
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
	 */
	public <O> FluentList<T> filterNot(Predicate<? super T> predicate) {
		return filter(Predicates.not(predicate));
	}
	
	/**
	 * Find all matching elements in this list.
	 * 
	 * Composes the given predicate and mapper.
	 */
	public <O> FluentList<T> filterNot(Function<? super T, O> mapper, Predicate<? super O> predicate) {
		return filterNot(Predicates.compose(predicate, mapper));
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
		return filter(predicate).head();
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
	 * Remove all null elements from this list.
	 */
	public FluentList<T> compact() {
		return filter(Predicates.notNull());
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
	 * Return a copy of this list with reverse ordering.
	 */
	public FluentList<T> reverse() {
		return sort(Ordering.explicit(this).reverse());
	}
	
	/**
	 * Find the first element in this list, or return null if no elements exist.
	 */
	public T head() {
		if(size() == 0) {
			return null;
		}
		return get(0);
	}
	
	protected List<T> delegate() {
		return delegate;
	}


}
