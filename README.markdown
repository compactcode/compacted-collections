# A sexier way to use google collections in your Java projects.

I started with [Google Collections](http://code.google.com/p/google-collections/), got some inspiration from [Ruby](http://ruby-doc.org/core/classes/Array.html) and then whipped out the [Fluent Interface](http://en.wikipedia.org/wiki/Fluent_interface).

This project has been designed to be used in conjunction with google collections api, not as a replacement.

## Usage

### Overview

	listOf("1", "2", "3", "4", "4").map(toInt()).filter(greaterThan(1)).reverse();
	
### Transformation

	listOf("1", "2").map(toInt());
	listOf("1", "2").map(toInt()).reduce(sum());
	
By default transformations are performed lazily using google collections [Lists.transform](http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/Lists.html#transform(java.util.List,%20com.google.common.base.Function).

However, you can execute transformations immediately, or in parallel using multiple threads.

	listOf("1", "2").immediate().map(toInt());
	listOf("1", "2").parallel(2).map(toInt());
	
### Filtering

A standard library of matchers are provided by the [Hamcrest](http://code.google.com/p/hamcrest/) project.

	listOf(1, 2, 3).filter(greaterThan(1));
	listOf(1, 2).find(equalTo(1)); 
	listOf(1, 2).first(); 
	listOf(1, 2).last(); 
	
### Sorting

	listOf(1, 3, 2).sort(natural())
	listOf(1, 2, 3).reverse()
	
### Other

	listOf("1", "2", "2").unique();
	listOf("1", "2", null).compact();

## Installation + Setup

This project is built using [Apache Maven](http://maven.apache.org/).

You can configure an eclipse project using Maven like so:

	mvn eclipse:eclipse

## Resources

[JavaDocs](http://compactcode.github.com/compacted-collections/apidocs/2.2)