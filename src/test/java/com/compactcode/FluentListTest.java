package com.compactcode;

import static com.compactcode.FluentList.*;
import static com.compactcode.Functions.stringToInt;
import static com.compactcode.Functions.sum;
import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.in;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Ordering.natural;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.google.common.base.Function;

public class FluentListTest {
	
	@Test
	public void canCreateFluentListFromElements() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b"));
	}

	@Test
	public void canCreateFluentListFromElementsUsingListOf() {
		assertEquals(newArrayList("a", "b"), listOf("a", "b"));
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
	public void transformIsImmediateByDefault() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		try {
			fluent("1", "2").transform(throwException);
			fail();
		} catch (Exception e) {
			assertEquals("not lazy", e.getMessage());
		}
	}
	
	@Test
	public void mapIsImmediateByDefault() {
		Function<String, String> throwException = new Function<String, String>() {
			public String apply(String value) {
				throw new RuntimeException("not lazy");
			}
		};
		try {
			fluent("1", "2").map(throwException);
			fail();
		} catch (Exception e) {
			assertEquals("not lazy", e.getMessage());
		}
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingToString() {
		assertEquals(newArrayList("a", "b").toString(), fluent("a", "b").toString());
	}
	
	@Test
	public void canCompareFluentToNonFluentListUsingEquals() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b"));
	}
	
	@Test
	public void canSumListUsingReduce() {
		assertEquals(Integer.valueOf(3), fluent(1, 2).reduce(sum()));
	}
	
	@Test
	public void canFilterUsingPredicate() {
		assertEquals(newArrayList("b", "c"), fluent("a", "b", "c").filter(in(newArrayList("b", "c"))));
	}
	
	@Test
	public void canFilterUsingMatcher() {
		assertEquals(newArrayList("b", "c"), fluent("a", "b", "c").filter(isIn(newArrayList("b", "c"))));
	}
	
	@Test
	public void canFilterMatchingElementUsingPredicateAndFunction() {
		assertEquals(newArrayList(2), fluent(1, 2).filter(toStringFunction(), equalTo("2")));
	}
	
	@Test
	public void canFilterMatchingElementUsingMatcherAndFunction() {
		assertEquals(newArrayList(2), fluent(1, 2).filter(toStringFunction(), IsEqual.equalTo("2")));
	}
	
	@Test
	public void canRejectUsingPredicate() {
		assertEquals(newArrayList("b", "c"), fluent("a", "b", "c").reject(equalTo("a")));
	}
	
	@Test
	public void canRejectUsingMatcher() {
		assertEquals(newArrayList("b", "c"), fluent("a", "b", "c").reject(IsEqual.equalTo("a")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicate() {
		assertEquals("b", fluent("a", "b", "b").find(equalTo("b")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingMatcher() {
		assertEquals("b", fluent("a", "b", "b").find(IsEqual.equalTo("b")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicateAndFunction() {
		assertEquals(Integer.valueOf(2), fluent(newArrayList(1, 2)).find(toStringFunction(), equalTo("2")));
	}
	
	@Test
	public void canFindFirstMatchingElementUsingPredicateAndMatcher() {
		assertEquals(Integer.valueOf(2), fluent(newArrayList(1, 2)).find(toStringFunction(), IsEqual.equalTo("2")));
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
		assertEquals(newArrayList("a", "b", "c"), fluent("c", "a", "b").sort(natural()));
	}

	@Test
	public void canSortListUsingGivenOrderingAndFunction() {
		assertEquals(newArrayList("11", "12", "21"), fluent("21", "12", "11").sort(stringToInt(), natural()));
	}
	
	@Test
	public void canSortListNaturalOrderingAndToComparableFunction() {
		assertEquals(newArrayList("11", "12", "21"), fluent("21", "12", "11").sort(stringToInt()));
	}
	
	@Test
	public void canReverseList() {
		assertEquals(newArrayList("a", "b", "c"), fluent(newArrayList("c", "b", "a")).reverse());
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
	public void canGetTheFirstNegativeTwoElementsFromAList() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b", "c").first(-2));
	}
	
	@Test
	public void canGetTheFirstTwoElementsFromAList() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b", "c").first(2));
	}
	
	@Test
	public void canGetTheLaztTwoElementsFromAnEmptyList() {
		assertEquals(newArrayList(), fluent().last(2));
	}
	
	@Test
	public void canGetTheLastTwoElementsFromAListOfTwoElements() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b").last(2));
	}
	
	@Test
	public void canGetTheLastNegativeTwoElementsFromAList() {
		assertEquals(newArrayList("b", "c"), fluent("a", "b", "c").last(-2));
	}
	
	@Test
	public void canGetTheLastTwoElementsFromAList() {
		assertEquals(newArrayList("b", "c"), fluent("a", "b", "c").last(2));
	}
	
	@Test
	public void canGetTheFirstTwoElementsFromAnEmptyList() {
		assertEquals(newArrayList(), fluent().first(2));
	}
	
	@Test
	public void canGetTheFirstTwoElementsFromAListOfTwoElements() {
		assertEquals(newArrayList("a", "b"), fluent("a", "b").first(2));
	}
	
	@Test
	public void canGetTheLastElementFromAList() {
		assertEquals("b", fluent("a", "b").last());
	}
	
	@Test
	public void canGetTheLastElementFromAnEmptyList() {
		assertEquals(null, fluent().last());
	}
	
	@Test
	public void canRemoveDuplicatesFromAList() {
		assertEquals(newArrayList(11, 22), fluent(11, 22, 22, 11).unique().sort());
	}
	
	@Test
	public void canJoinAList() {
		assertEquals("1, 2, 3", fluent(1, 2, 3).join(", "));
	}
	
}
