package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;

import org.junit.Test;

public class TakegakiPuzzleTest extends TakegakiTestBase {

  private final TakegakiPuzzle puzzle = new TakegakiPuzzle(
    Grid.<Integer>builder()
      .assign(Cell.at(0, 0), 0)
      .assign(Cell.at(2, 0), 1)
      .assign(Cell.at(1, 1), 2)
      .assign(Cell.at(2, 2), 3)
      .build()
  );

  @Test public void toRaster() {

    assertRasterLinesAre(puzzle.toRaster(),
      "x x x x",
      " 0   1 ",
      "x x x x",
      "   2   ",
      "x x x x",
      "     3 ",
      "x x x x"
    );
  }
}
