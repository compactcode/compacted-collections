package com.compactcode;

import static com.compactcode.FluentList.fluent;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.hamcrest.beans.HasPropertyWithValue;

import com.google.common.base.Function;

/**
 * A collection of useful functions.
 */
public final class Functions {

	/**
	 * Use {@link Functions#toPropertyValue(String)} instead.
	 */
	@Deprecated
	public static final <T, O> Function<T, O> propertyValue(final String name) {
		return toPropertyValue(name);
	}

	/**
	 * A non-typesafe way to extract property values using reflection.
	 * 
	 * See also {@link HasPropertyWithValue#hasProperty(String, org.hamcrest.Matcher)} 
	 */
	public static <T, O> Function<T, O> toPropertyValue(final String name) {
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
	
	/**
	 * Shortcut for {@link #stringToInt()}
	 */
	public static final Function<String, Integer> toInt() {
		return stringToInt();
	}
	
	/**
	 * Converts a string to an {@link Integer} using {@link Integer#valueOf(String)}
	 */
	public static final Function<String, Integer> stringToInt() {
		return new Function<String, Integer>() {
			public Integer apply(String value) {
				return Integer.valueOf(value);
			}
		};
	}
	
	/**
	 * Shortcut for {@link #stringToLong()}
	 */
	public static final Function<String, Long> toLong() {
		return stringToLong();
	}
	
	/**
	 * Converts a string to a {@link Long} using {@link Long#valueOf(String)}
	 */
	public static final Function<String, Long> stringToLong() {
		return new Function<String, Long>() {
			public Long apply(String value) {
				return Long.valueOf(value);
			}
		};
	}
	
	/**
	 * Calculates the sum for a collection of integers.
	 */
	public static final Function<Collection<Integer>, Integer> sum() {
		return new Function<Collection<Integer>, Integer>() {
			public Integer apply(Collection<Integer> values) {
				int total = 0;
				for (Integer value : values) {
					total += value;
				}
				return total;
			}
		};
	}
	
	/**
	 * Allows a mapper to be used on a list of lists.
	 */
	public static final <T, O> Function<List<T>, FluentList<O>> each(final Function<T, O> mapper) {
		return new Function<List<T>, FluentList<O>>() {
			public FluentList<O> apply(List<T> source) {
				return fluent(source).map(mapper);
			}
		};
	}
	
}
