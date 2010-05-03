# A sexier way to use google collections in your Java projects.

I started with [Google Collections](http://code.google.com/p/google-collections/), got some inspiration from [Ruby](http://ruby-doc.org/core/classes/Array.html) and then whipped out the [Fluent Interface](http://en.wikipedia.org/wiki/Fluent_interface).

This project has been designed to be used in conjunction with the google collections api, not as a replacement.

## Usage

### Overview

	listOf("1", "2", "3", "4", "4").map(toInt()).filter(greaterThan(1)).reverse();
	
### Transformation

	listOf("1", "2").map(toInt());
	listOf("1", "2").map(toInt()).reduce(sum());
	
### Filtering

A standard library of matchers are provided by the [Hamcrest](http://code.google.com/p/hamcrest/) project.

	listOf(1, 2, 3).filter(greaterThan(1));
	listOf(1, 2, 3).reject(lessThan(3));
	listOf(1, 2).find(equalTo(1)); 
	listOf(1, 2).first();
	listOf(1, 2, 3).first(2); 
	listOf(1, 2).last();
	listOf(1, 2, 3).last(2); 
	
### Sorting

	listOf(1, 3, 2).sort(natural());
	listOf(1, 2, 3).reverse();
	
### Other

    listOf("1", "2", "3").join(", ");
	listOf("1", "2", "2").unique();
	listOf("1", "2", null).compact();

## Installation + Setup

This project is built using [Apache Maven](http://maven.apache.org/).

You can easily add it to any maven enabled project like so:

	...
	<repositories>
		<repository>
			<id>compacted-collections</id>
			<url>http://compactcode.github.com/compacted-collections/mavenrepo</url>
		</repository>
	</repositories>
	...
	<dependencies>
		<dependency>
			<groupId>com.compactcode</groupId>
			<artifactId>compacted-collections</artifactId>
			<version>2.4</version>
		</dependency>
	</dependencies>
	...

## Resources

[JavaDocs](http://compactcode.github.com/compacted-collections/apidocs/2.4)