# A sexier way to use google collections in your Java projects.

Its simple...

I started with [Google Collections](http://code.google.com/p/google-collections/), got some inspiration from [Ruby](http://ruby-doc.org/core/classes/Array.html) and then whipped out the [Fluent Interface](http://en.wikipedia.org/wiki/Fluent_interface).

## Usage

### Overview

	fluent("1", "2", "3", "4", "4").map(toInt()).filter(greaterThan(1)).reverse();
	
### Transformation

	fluent("1", "2").map(toInt());
	fluent("1", "2").map(toInt()).reduce(sum());
	
By default transformations are performed lazily using google collections [Lists.transform](http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/Lists.html#transform(java.util.List,%20com.google.common.base.Function).

However, you can execute transformations immediately, or in parallel using multiple threads.

	fluent("1", "2").immediate().map(toInt());
	fluent("1", "2").parallel(2).map(toInt());
	
### Filtering

	fluent(1, 2, 3).filter(greaterThan(1));
	fluent(1, 2).find(equalTo(1)); 
	fluent(1, 2).first(); 
	fluent(1, 2).last(); 
	
### Sorting

	fluent(1, 3, 2).sort(natural())
	fluent(1, 2, 3).reverse()
	
### Other

	fluent("1", "2", "2").unique();
	fluent("1", "2", null).compact();
