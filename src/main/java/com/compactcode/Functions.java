package com.compactcode;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public final class Functions {

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
	
	public static final Function<List<Integer>, Integer> avg() {
		return new Function<List<Integer>, Integer>() {
			public Integer apply(List<Integer> values) {
				return sum().apply(values) / values.size();
			}
		};
	}
	
	public static final <T, O> Function<List<T>, List<O>> all(final Function<T, O> mapper) {
		return new Function<List<T>, List<O>>() {
			public List<O> apply(List<T> source) {
				return Lists.transform(source, mapper);
			}
		};
	}

}
