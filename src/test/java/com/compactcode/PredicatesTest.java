package com.compactcode;

import static com.compactcode.FluentList.fluent;
import static com.compactcode.Predicates.greaterThan;
import static com.compactcode.Predicates.sizeGreaterThan;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class PredicatesTest {

	@Test
	public void canFilterItemsGreaterThanValue() {
		assertEquals(newArrayList(4, 5), fluent(newArrayList(1, 2, 3, 4, 5)).filter(greaterThan(3)));
	}
	
	@Test
	public void canFilterCollectionSizeGreaterThanValue() {
		List<List<String>> source = newArrayList();
		source.add(newArrayList("a"));
		source.add(newArrayList("b", "c"));
		
		List<List<String>> expected = newArrayList();
		expected.add(newArrayList("b", "c"));
		
		assertEquals(expected, fluent(source).filter(sizeGreaterThan(1)));
	}

}
