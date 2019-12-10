package com.lastbubble.nikoli.sudoku;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.lastbubble.nikoli.Cell;

import org.junit.Test;

public class SudokuPuzzleTest {

  private final SudokuPuzzle puzzle = new Sudoku().read(readerFrom(
      " , ,3,2, ,7,1, , ",
      " , , , ,5, , ,6, ",
      "2,1, , , , , , ,9",
      "8, ,9,3, , , ,7,2",
      " ,3,6, , , ,5,4, ",
      "4,7, , , ,6,9, ,1",
      "5, , , , , , ,9,7",
      " ,2, , ,4, , , , ",
      " , ,4,6, ,1,8, , "
    )
  );

  @Test public void regionContaining() {

    Stream<List<Cell>> regions = Stream.of(
      Arrays.asList(
        Cell.at(0,0), Cell.at(1,0), Cell.at(2,0),
        Cell.at(0,1), Cell.at(1,1), Cell.at(2,1),
        Cell.at(0,2), Cell.at(1,2), Cell.at(2,2)
      ),
      Arrays.asList(
        Cell.at(3,0), Cell.at(4,0), Cell.at(5,0),
        Cell.at(3,1), Cell.at(4,1), Cell.at(5,1),
        Cell.at(3,2), Cell.at(4,2), Cell.at(5,2)
      ),
      Arrays.asList(
        Cell.at(6,0), Cell.at(7,0), Cell.at(8,0),
        Cell.at(6,1), Cell.at(7,1), Cell.at(8,1),
        Cell.at(6,2), Cell.at(7,2), Cell.at(8,2)
      ),
      Arrays.asList(
        Cell.at(0,3), Cell.at(1,3), Cell.at(2,3),
        Cell.at(0,4), Cell.at(1,4), Cell.at(2,4),
        Cell.at(0,5), Cell.at(1,5), Cell.at(2,5)
      ),
      Arrays.asList(
        Cell.at(3,3), Cell.at(4,3), Cell.at(5,3),
        Cell.at(3,4), Cell.at(4,4), Cell.at(5,4),
        Cell.at(3,5), Cell.at(4,5), Cell.at(5,5)
      ),
      Arrays.asList(
        Cell.at(6,3), Cell.at(7,3), Cell.at(8,3),
        Cell.at(6,4), Cell.at(7,4), Cell.at(8,4),
        Cell.at(6,5), Cell.at(7,5), Cell.at(8,5)
      ),
      Arrays.asList(
        Cell.at(0,6), Cell.at(1,6), Cell.at(2,6),
        Cell.at(0,7), Cell.at(1,7), Cell.at(2,7),
        Cell.at(0,8), Cell.at(1,8), Cell.at(2,8)
      ),
      Arrays.asList(
        Cell.at(3,6), Cell.at(4,6), Cell.at(5,6),
        Cell.at(3,7), Cell.at(4,7), Cell.at(5,7),
        Cell.at(3,8), Cell.at(4,8), Cell.at(5,8)
      ),
      Arrays.asList(
        Cell.at(6,6), Cell.at(7,6), Cell.at(8,6),
        Cell.at(6,7), Cell.at(7,7), Cell.at(8,7),
        Cell.at(6,8), Cell.at(7,8), Cell.at(8,8)
      )
    );

    regions.forEach(region -> {
      for (Cell cell : region) {
        assertThatCellsAre(puzzle.regionContaining(cell), region.stream());
      }
    });
  }

  private void assertThatCellsAre(Stream<Cell> stream, Stream<Cell> expected) {
    assertThat(stream.collect(toSet()), is(expected.collect(toSet())));
  }

  @Test public void toRaster() {

    assertThat(puzzle.toRaster(), matchesLines(
        "╔═╤═╤═╦═╤═╤═╦═╤═╤═╗",
        "║ │ │3║2│ │7║1│ │ ║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║ │ │ ║ │5│ ║ │6│ ║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║2│1│ ║ │ │ ║ │ │9║",
        "╠═╪═╪═╬═╪═╪═╬═╪═╪═╣",
        "║8│ │9║3│ │ ║ │7│2║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║ │3│6║ │ │ ║5│4│ ║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║4│7│ ║ │ │6║9│ │1║",
        "╠═╪═╪═╬═╪═╪═╬═╪═╪═╣",
        "║5│ │ ║ │ │ ║ │9│7║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║ │2│ ║ │4│ ║ │ │ ║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║ │ │4║6│ │1║8│ │ ║",
        "╚═╧═╧═╩═╧═╧═╩═╧═╧═╝"
      )
    );
  }
}