package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;

import org.junit.Test;

public class TakegakiPuzzleTest {

  private final TakegakiPuzzle puzzle = new TakegakiPuzzle(
    Grid.<Integer>builder()
      .assign(Cell.at(0, 0), 0)
      .assign(Cell.at(2, 0), 1)
      .assign(Cell.at(1, 1), 2)
      .assign(Cell.at(2, 2), 3)
      .build()
  );

  @Test public void toRaster() {

    assertThat(puzzle.toRaster(), matchesLines(
        ". . . .",
        " 0   1 ",
        ". . . .",
        "   2   ",
        ". . . .",
        "     3 ",
        ". . . ."
      )
    );
  }
}
