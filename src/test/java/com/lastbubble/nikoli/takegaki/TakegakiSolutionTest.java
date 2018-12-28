package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TakegakiSolutionTest {

  private final Puzzle<Integer> puzzle = new TakegakiPuzzle(
    Grid.<Integer>builder()
      .assign(Cell.at(0, 0), 2)
      .assign(Cell.at(1, 0), 3)
      .assign(Cell.at(1, 1), 3)
      .assign(Cell.at(0, 2), 2)
      .assign(Cell.at(2, 2), 3)
      .build()
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
        "r===7 .",
        "H2 3H  ",
        "H r=] .",
        "H H3   ",
        "H L===7",
        "H2   3H",
        "L=====]"
      )
    );
  }
}
