package com.compactcode;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matcher;

import com.compactcode.strategy.ImmediateListStrategy;
import com.compactcode.strategy.LazyListStrategy;
import com.compactcode.strategy.ListStrategy;
import com.compactcode.strategy.ParallelListStrategy;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * A list that provides a convenient, fluent way of interacting with the google collections api.
 */
public class FluentList<T> extends ForwardingList<T> {
	
	/**
	 * Create a new fluent list from the given elements.
	 */
	public static <T> FluentList<T> fluent(T... elements) {
		return listOf(elements);
	}

	/**
	 * Create a new fluent list from the given elements.
	 */
	public static <T> FluentList<T> listOf(T... elements) {
		return fluent(Lists.newArrayList(elements));
	}

	/**
	 * Create a new fluent list that wraps the given list.
	 */
	public static <T> FluentList<T> fluent(List<T> delegate) {
		return fluent(delegate, new ImmediateListStrategy());
	}
	
	/**
	 * Create a new fluent list from the given iterable.
	 */
	public static <T> FluentList<T> fluent(Iterable<T> delegate) {
		return fluent(Lists.newArrayList(delegate));
	}
	
	private static <T> FluentList<T> fluent(List<T> delegate, ListStrategy strategy) {
		return new FluentList<T>(delegate, strategy);
	}
	
	private static <T> FluentList<T> fluent(Iterable<T> delegate, ListStrategy strategy) {
		return fluent(Lists.newArrayList(delegate), strategy);
	}
	
	private final List<T> delegate;
	private final ListStrategy strategy;
	
	private FluentList(List<T> delegate, ListStrategy strategy) {
		this.delegate = delegate;
		this.strategy = strategy;
	}

	/**
	 * Convert each element of this list into a new one.
	 */
	public <O> FluentList<O> transform(Function<? super T, O> mapper) {
		return fluent(strategy.transform(delegate, mapper), strategy);
	}
	
	/**
	 * Synonym for #{@link FluentList#transform(Function)}.
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
	 * 
	 * Lazy transformation is not supported during expand, defaults to immediate in this case.
	 */
	public <O> FluentList<O> expand(Function<T, List<O>> expander) {
		List<O> flat = Lists.newArrayList();
		for (List<O> sublist : map(expander)) {
			flat.addAll(sublist);
		}
		return fluent(flat, strategy);
	}
	
	/**
	 * Find all matching elements in this list.
	 */
	public FluentList<T> filter(Predicate<? super T> predicate) {
		return fluent(strategy.filter(this, predicate), strategy);
	}
	
	/**
	 * Find all matching elements in this list.
	 */
	public FluentList<T> filter(final Matcher<? super T> matcher) {
		return filter(asPredicate(matcher));
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
	 * Find all matching elements in this list.
	 * 
	 * Composes the given predicate and mapper.
	 */
	public <O> FluentList<T> filter(Function<? super T, O> mapper, Matcher<? super O> matcher) {
		return filter(mapper, asPredicate(matcher));
	}
	
	/**
	 * Find the first matching element in this list, or return null.
	 */
	public T find(Predicate<? super T> predicate) {
		return filter(predicate).first();
	}
	
	/**
	 * Find the first matching element in this list, or return null.
	 */
	public T find(Matcher<? super T> predicate) {
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
	 * Find the first matching element in this list, or return null.
	 * 
	 * Composes the given predicate and mapper.
	 */
	public <O> T find(Function<? super T, O> mapper, Matcher<? super O> matcher) {
		return find(mapper, asPredicate(matcher));
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
		return fluent(order.sortedCopy(this), strategy);
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
		return fluent(Iterables.reverse(this), strategy);
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
	 * Find the first n elements in this list, or return an empty list if no elements exist.
	 */
	public FluentList<T> first(int n) {
		return first(n, iterator());
	}

	/**
	 * Find the last n elements in this list, or return an empty list if no elements exist.
	 */
	public FluentList<T> last(int n) {
		return first(n, Iterables.reverse(this).iterator()).reverse();
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
		return fluent(Sets.newHashSet(this), strategy);
	}
	
	/**
	 * Remove all null elements from this list.
	 */
	public FluentList<T> compact() {
		return filter(Predicates.notNull());
	}

	/**
	 * Subsequent transformations (map, transform) will be applied using a {@link ImmediateListStrategy}.
	 * 
	 * This is the default behaviour.
	 */
	public FluentList<T> immediate() {
		return new FluentList<T>(this, new ImmediateListStrategy());
	}

	/**
	 * Subsequent transformations (map, transform) will be applied using a {@link LazyListStrategy}.
	 */
	public FluentList<T> lazy() {
		return new FluentList<T>(this, new LazyListStrategy());
	}

	/**
	 * Subsequent transformations (map, transform) will be applied using a {@link ParallelListStrategy}.
	 */
	public FluentList<T> parallel(int threads) {
		return new FluentList<T>(this, new ParallelListStrategy(threads));
	}

	protected List<T> delegate() {
		return delegate;
	}
	
	private FluentList<T> first(int n, Iterator<T> iterator) {
		List<T> matched = Lists.newArrayList();
		while (iterator.hasNext() && matched.size() < Math.abs(n)) {
			matched.add(iterator.next());
		}
		return fluent(matched, strategy);
	}
	
	private <O >Predicate<O> asPredicate(final Matcher<? super O> matcher) {
		return new Predicate<O>() {
			public boolean apply(O element) {
				return matcher.matches(element);
			}
		};
	}
	
}
