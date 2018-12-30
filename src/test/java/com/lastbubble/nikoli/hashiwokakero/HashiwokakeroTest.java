package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.PuzzleFactory;

import org.junit.Test;

public class HashiwokakeroTest {

  private final PuzzleFactory<Integer, Bridge> puzzleFactory = new Hashiwokakero();

  @Test public void read() {

    Puzzle<Integer> puzzle = puzzleFactory.read(readerFrom(
        "4, ,4,1",
        " , , ,1",
        " , ,3,4",
        "2,1, ,2"
      )
    );

    Grid<Integer> grid = puzzle.grid();

    assertThat(grid.filledCells().count(), is(9L));
    assertThat(grid.valueAt(Cell.at(0, 0)).orElse(null), is(4));
    assertThat(grid.valueAt(Cell.at(2, 0)).orElse(null), is(4));
    assertThat(grid.valueAt(Cell.at(3, 0)).orElse(null), is(1));
    assertThat(grid.valueAt(Cell.at(3, 1)).orElse(null), is(1));
    assertThat(grid.valueAt(Cell.at(2, 2)).orElse(null), is(3));
    assertThat(grid.valueAt(Cell.at(3, 2)).orElse(null), is(4));
    assertThat(grid.valueAt(Cell.at(0, 3)).orElse(null), is(2));
    assertThat(grid.valueAt(Cell.at(1, 3)).orElse(null), is(1));
    assertThat(grid.valueAt(Cell.at(3, 3)).orElse(null), is(2));
  }
}
