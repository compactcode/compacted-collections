package com.compactcode;

import static com.compactcode.FluentList.fluent;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.base.Function;

public final class Functions {

	/**
	 * A non-typesafe way to extract property values using reflection.
	 */
	public static final <T, O> Function<T, O> propertyValue(final String name) {
		return new Function<T, O>() {
			@SuppressWarnings("unchecked")
			public O apply(T source) {
				try {
					Field field = source.getClass().getDeclaredField(name);
					field.setAccessible(true);
					return (O) field.get(source);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
	
	public static final Function<String, Integer> stringToInt() {
		return new Function<String, Integer>() {
			public Integer apply(String value) {
				return Integer.valueOf(value);
			}
		};
	}
	
	public static final Function<List<Integer>, Integer> sum() {
		return new Function<List<Integer>, Integer>() {
			public Integer apply(List<Integer> values) {
				int total = 0;
				for (Integer value : values) {
					total += value;
				}
				return total;
			}
		};
	}
	
	public static final <T, O> Function<List<T>, FluentList<O>> each(final Function<T, O> mapper) {
		return new Function<List<T>, FluentList<O>>() {
			public FluentList<O> apply(List<T> source) {
				return fluent(source).map(mapper);
			}
		};
	}
	
}
