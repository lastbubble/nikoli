Overview
========
Implementations of solvers for logic puzzles like the ones published in [Nikoli](http://www.nikoli.com/en/) magazine.
The puzzle types are taken from Alex Bellos' [_Puzzle Ninja_](https://www.faber.co.uk/9781783351367-puzzle-ninja.html) book.

Where possible, the puzzles are translated to a [SAT](https://en.wikipedia.org/wiki/Boolean_satisfiability_problem)
problem and solved with the [sat4j](http://www.sat4j.org/) library. 

### References
* [CNF generator](http://www.wolframalpha.com/widget/widgetPopup.jsp)
* [sat4j Javadoc](http://www.sat4j.org/maven23/org.sat4j.core/apidocs/index.html)

Takegaki
--------

The rules for Takegaki (more commonly known as Slitherlink) can be found [here](https://en.wikipedia.org/wiki/Slitherlink).

```java -jar Nikoli.jar puzzle_file```

where `puzzle_file` is a text file encoding a Takegaki puzzle. Each row of the puzzle is represented by a comma-delimited
line of cell values: 0, 1, 2, 3 or space (unspecified).

### References
* David Westreicher. Slitherlink Reloaded, 2011. https://david-westreicher.github.io/static/papers/ba-thesis.pdf
