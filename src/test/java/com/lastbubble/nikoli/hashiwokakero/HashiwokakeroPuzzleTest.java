package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;

import org.junit.Test;

public class HashiwokakeroPuzzleTest {

  private final Grid<Integer> grid = Grid.<Integer>builder()
    .assign(Cell.at(0, 0), 4)
    .assign(Cell.at(2, 0), 4)
    .assign(Cell.at(3, 0), 1)
    .assign(Cell.at(3, 1), 1)
    .assign(Cell.at(2, 2), 3)
    .assign(Cell.at(3, 2), 4)
    .assign(Cell.at(0, 3), 2)
    .assign(Cell.at(1, 3), 1)
    .assign(Cell.at(3, 3), 2)
    .build();

  private final HashiwokakeroPuzzle puzzle = new HashiwokakeroPuzzle(grid);

  @Test public void grid() {

    assertThat(puzzle.grid(), sameInstance(grid));
  }

  @Test public void toRaster() {

    assertThat(puzzle.toRaster(), matchesLines(
        "4   4 1",
        "       ",
        "      1",
        "       ",
        "    3 4",
        "       ",
        "2 1   2"
      )
    );
  }
}