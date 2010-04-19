package com.compactcode.strategy;

import static com.compactcode.FluentList.fluent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.common.base.Function;

/**
 * A transformation strategy that uses a {@link ThreadPoolExecutor} to transform items in parallel 
 * using a given number of threads. 
 */
public class ParallelTransformationStrategy implements TransformationStrategy {

	private final int numThreads;
	
	public ParallelTransformationStrategy(int numThreads) {
		this.numThreads = numThreads;
	}

	public <T, O> List<O> transform(List<T> fromList, Function<? super T, O> transform) {
		ExecutorService executors = Executors.newFixedThreadPool(numThreads);
		try {
			Function<T, Callable<O>> toCallable = toCallable(transform);
			Function<Future<O>, O> fromFuture = fromFuture();
			return fluent(executors.invokeAll(fluent(fromList).map(toCallable))).immediate().map(fromFuture);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			executors.shutdown();
		}
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
