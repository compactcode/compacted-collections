package com.compactcode;

import static com.compactcode.FluentList.fluent;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.sum;
import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.in;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Ordering.natural;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;


public class FluentListTest {
	
	@Test
	public void canCreateFluentListFromElements() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b"));
	}
	
	@Test
	public void canTransformElementsUsingMapper() {
		assertEquals(newArrayList(1, 2), fluent("1", "2").transform(stringToInt()));
	}
	
	@Test
	public void canUseMapAsSynonymToTransform() {
		assertEquals(newArrayList(1, 2), fluent("1", "2").map(stringToInt()));
	}
	
	@Test
	public void transformIsLazy() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		fluent("1", "2").transform(throwException);
	}
	
	@Test
	public void mapIsLazy() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		fluent("1", "2").map(throwException);
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingToString() {
		assertEquals(newArrayList("a", "b").toString(), fluent(newArrayList("a", "b")).toString());
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingEquals() {
		assertEquals(newArrayList("a", "b"), fluent(newArrayList("a", "b")));
	}
	
	@Test
	public void canSumListUsingReduce() {
		assertEquals(3, fluent(newArrayList(1, 2)).reduce(sum()));
	}
	
	@Test
	public void canFilterUsingPredicate() {
		assertEquals(newArrayList("b", "c"), fluent(newArrayList("a", "b", "c")).filter(in(newArrayList("b", "c"))));
	}
	
	@Test
	public void canFilterListUsingNegatedPredicate() {
		assertEquals(newArrayList("a", "c"), fluent(newArrayList("a", "b", "c")).filterNot(equalTo("b")));
	}
	
	@Test
	public void canFilterMatchingElementUsingPredicateAndFunction() {
		assertEquals(newArrayList(2), fluent(newArrayList(1, 2)).filter(toStringFunction(), equalTo("2")));
	}
	
	@Test
	public void canFilterMatchingElementUsingNegatedPredicateAndFunction() {
		assertEquals(newArrayList(1), fluent(newArrayList(1, 2)).filterNot(toStringFunction(), equalTo("2")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicate() {
		assertEquals("b", fluent(newArrayList("a", "b", "b")).find(equalTo("b")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicateAndFunction() {
		assertEquals(2, fluent(newArrayList(1, 2)).find(toStringFunction(), equalTo("2")));
	}
	
	@Test
	public void canFindNoMatchingElementUsingPredicate() {
		assertEquals(null, fluent(newArrayList("a", "b", "b")).find(equalTo("c")));
	}
	
	@Test
	public void canRemoveAllNullsUsingCompact() {
		assertEquals(newArrayList("a", "b"), fluent(newArrayList("a", null, "b")).compact());
	}
	
	@Test
	public void canSortListUsingGivenOrdering() {
		assertEquals(newArrayList("a", "b", "c"), fluent(newArrayList("c", "a", "b")).sort(natural()));
	}

	@Test
	public void canSortListUsingGivenOrderingAndFunction() {
		assertEquals(newArrayList("11", "12", "21"), fluent(newArrayList("21", "12", "11")).sort(stringToInt(), natural()));
	}
	
	@Test
	public void canReverseList() {
		assertEquals(newArrayList("a", "b", "c"), fluent(newArrayList("c", "b", "a")).reverse());
	}
	
	@Test
	public void canConvertListToSet() {
		assertEquals(newHashSet("a", "b"), fluent(newArrayList("b", "b", "a")).toSet());
	}
	
	@Test
	public void canExpandListIntoASingleList() {
		Function<Integer, List<Integer>> expander = new Function<Integer, List<Integer>>() {
			public List<Integer> apply(Integer source) {
				return newArrayList(source, source);
			}
		};
		assertEquals(newArrayList(1, 1, 2, 2), fluent(1, 2).expand(expander));
	}
	
	@Test
	public void canGetTheFirstElementFromAList() {
		assertEquals("a", fluent("a", "b").first());
	}
	
	@Test
	public void canGetTheFirstElementFromAnEmptyList() {
		assertEquals(null, fluent().first());
	}
	
	@Test
	public void canGetTheLastElementFromAList() {
		assertEquals("b", fluent("a", "b").last());
	}
	
	@Test
	public void canGetTheLastElementFromAnEmptyList() {
		assertEquals(null, fluent().last());
	}
	
}
