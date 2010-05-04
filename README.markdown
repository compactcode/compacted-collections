# A sexier way to use google collections in your Java projects.

compacted-collections brings the full power of object-oriented programming to your Java collections.

Modelled closely on the standard [Ruby](http://ruby-doc.org/core/classes/Array.html) library.

Features:

* [Fluent Interface](http://en.wikipedia.org/wiki/Fluent_interface)
* [Facade Pattern](http://en.wikipedia.org/wiki/Facade_pattern)

Built on top of well known, high quality libraries:

* [Google Collections](http://code.google.com/p/google-collections/)
* [Hamcrest](http://code.google.com/p/hamcrest/)

## Usage

### Overview

	listOf("1", "2", "3", "4", "4").map(toInt()).filter(greaterThan(1)).reverse();
	
### Transformation

	listOf("1", "2").map(toInt());
	listOf("1", "2").map(toInt()).reduce(sum());
	
### Filtering

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
			<version>2.5</version>
		</dependency>
	</dependencies>
	...
	
## Examples

If you'd like to see some compacted collections in action, check out the [example project](http://github.com/compactcode/compacted-examples).