package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TakegakiSolutionTest {

  private final TakegakiPuzzle puzzle = new Takegaki().read(readerFrom(
      "2,3, ",
      " ,3, ",
      "2, ,3"
    )
  );

  private final Set<Edge> elements = Stream.of(
      Edge.at(0, 0, Edge.H),
      Edge.at(1, 0, Edge.H),
      Edge.at(0, 0, Edge.V),
      Edge.at(2, 0, Edge.V),
      Edge.at(1, 1, Edge.H),
      Edge.at(0, 1, Edge.V),
      Edge.at(1, 1, Edge.V),
      Edge.at(1, 2, Edge.H),
      Edge.at(2, 2, Edge.H),
      Edge.at(0, 2, Edge.V),
      Edge.at(3, 2, Edge.V),
      Edge.at(0, 3, Edge.H),
      Edge.at(1, 3, Edge.H),
      Edge.at(2, 3, Edge.H)
    )
    .collect(Collectors.toSet());

  private final TakegakiSolution solution = new TakegakiSolution(puzzle, elements);

  @Test public void properties() {

    assertThat(solution.puzzle(), sameInstance(puzzle));
    assertThat(solution.elements(), sameInstance(elements));
  }

  @Test public void toRaster() {

    assertThat(solution.toRaster(), matchesLines(
        "╔═══╗ •",
        "║2 3║  ",
        "║ ╔═╝ •",
        "║ ║3   ",
        "║ ╚═══╗",
        "║2   3║",
        "╚═════╝"
      )
    );
  }
}
