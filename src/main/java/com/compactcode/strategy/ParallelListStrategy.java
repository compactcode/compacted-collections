package com.compactcode.strategy;

import static com.compactcode.FluentList.fluent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * A list strategy that uses a {@link ThreadPoolExecutor} to transform/filter items in parallel 
 * using a given number of threads. 
 */
public class ParallelListStrategy implements ListStrategy {

	private final int numThreads;
	
	public ParallelListStrategy(int numThreads) {
		this.numThreads = numThreads;
	}

	public <T, O> List<O> transform(List<T> fromList, Function<? super T, O> transform) {
		ExecutorService executors = Executors.newFixedThreadPool(numThreads);
		try {
			Function<T, Callable<O>> toCallable = toCallable(transform);
			Function<Future<O>, O> fromFuture = fromFuture();
			return fluent(executors.invokeAll(fluent(fromList).map(toCallable))).map(fromFuture);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			executors.shutdown();
		}
	}
	
	public <T> List<T> filter(List<T> fromList, Predicate<? super T> predicate) {
		List<Boolean> shouldInclude = transform(fromList, Functions.forPredicate(predicate));
		List<T> filtered = Lists.newArrayList();
		for (int i = 0; i < fromList.size(); i++) {
			if(shouldInclude.get(i)) {
				filtered.add(fromList.get(i));
			}
		}
		return filtered;
	}
	
	private <T, O> Function<T, Callable<O>> toCallable(final Function<? super T, O> transform) {
		return new Function<T, Callable<O>>() {
			public Callable<O> apply(final T element) {
				return new Callable<O> () {
					public O call() throws Exception {
						return transform.apply(element);
					}
				};
			}
		};
	};
	
	private <O> Function<Future<O>, O> fromFuture() {
		return new Function<Future<O>, O>() {
			public O apply(Future<O> result) {
				try {
					return result.get();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

}
