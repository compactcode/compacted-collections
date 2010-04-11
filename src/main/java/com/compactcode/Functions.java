package com.compactcode;

import java.util.List;

import com.google.common.base.Function;

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

}
