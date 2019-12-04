package com.lastbubble.nikoli.nurikabe;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class NurikabeSolutionTest {

  private final NurikabePuzzle puzzle = new Nurikabe().read(readerFrom(
      "1, , ,2",
      " , , , ",
      " , , , ",
      "2, , ,3"
    )
  );

  private final Set<Cell> pathCells = Stream.of(
      Cell.at(1, 0),
      Cell.at(0, 1),
      Cell.at(1, 1),
      Cell.at(2, 1),
      Cell.at(3, 1),
      Cell.at(1, 2),
      Cell.at(3, 2),
      Cell.at(1, 3)
    ).collect(Collectors.toSet());

  private final NurikabeSolution solution = new NurikabeSolution(puzzle, pathCells);

  @Test public void puzzle() {

    assertThat(solution.puzzle(), sameInstance(puzzle));
  }

  @Test public void elements() {

    assertThat(solution.elements(), sameInstance(pathCells));
  }

  @Test public void toRaster() {

    assertThat(solution.toRaster(), matchesLines(
        "┌─┬─┬─┬─┐",
        "│1│█│ │2│",
        "├─┼─┼─┼─┤",
        "│█│█│█│█│",
        "├─┼─┼─┼─┤",
        "│ │█│ │█│",
        "├─┼─┼─┼─┤",
        "│2│█│ │3│",
        "└─┴─┴─┴─┘"
      )
    );
  }
}