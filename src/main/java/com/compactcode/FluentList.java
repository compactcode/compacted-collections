package com.compactcode;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class FluentList<T> extends ForwardingList<T> {
	
	public static <T> FluentList<T> fluent(Iterable<T> delegate) {
		return new FluentList<T>(ImmutableList.copyOf(delegate));
	}
	
	public static <T> FluentList<T> fluent(T... elements) {
		return fluent(Lists.newArrayList(elements));
	}
	
	private final List<T> delegate;
	
	public FluentList(List<T> delegate) {
		this.delegate = delegate;
	}

	public <O> FluentList<O> map(Function<? super T, O> mapper) {
		return fluent(Iterables.transform(delegate, mapper));
	}
	
	public <O> O reduce(Function<List<T>, O> reducer) {
		return reducer.apply(this);
	}
	
	public <O> FluentList<O> expand(Function<T, List<O>> expander) {
		List<O> flat = Lists.newArrayList();
		for (List<O> sublist : map(expander)) {
			flat.addAll(sublist);
		}
		return fluent(flat);
	}
	
	public FluentList<T> filter(Predicate<? super T> predicate) {
		return fluent(Iterables.filter(this, predicate));
	}
	
	public <O> FluentList<T> filter(Predicate<? super O> predicate, Function<? super T, O> mapper) {
		return filter(Predicates.compose(predicate, mapper));
	}
	
	public T find(Predicate<? super T> predicate) {
		return filter(predicate).head();
	}
	
	public <O> T find(Predicate<? super O> predicate, Function<? super T, O> mapper) {
		return find(Predicates.compose(predicate, mapper));
	}
	
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
