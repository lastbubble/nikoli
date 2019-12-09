package com.lastbubble.nikoli.sudoku;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.FilledCell;

import static com.lastbubble.nikoli.sudoku.Value.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class SudokuSolutionTest {

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

  private final Set<FilledCell<Value>> filledCells = Stream.of(
      FilledCell.using(Cell.at(0,0), SIX),
      FilledCell.using(Cell.at(1,0), EIGHT),
      FilledCell.using(Cell.at(4,0), NINE),
      FilledCell.using(Cell.at(7,0), FIVE),
      FilledCell.using(Cell.at(8,0), FOUR),
      FilledCell.using(Cell.at(0,1), NINE),
      FilledCell.using(Cell.at(1,1), FOUR),
      FilledCell.using(Cell.at(2,1), SEVEN),
      FilledCell.using(Cell.at(3,1), ONE),
      FilledCell.using(Cell.at(5,1), EIGHT),
      FilledCell.using(Cell.at(6,1), TWO),
      FilledCell.using(Cell.at(8,1), THREE),
      FilledCell.using(Cell.at(2,2), FIVE),
      FilledCell.using(Cell.at(3,2), FOUR),
      FilledCell.using(Cell.at(4,2), SIX),
      FilledCell.using(Cell.at(5,2), THREE),
      FilledCell.using(Cell.at(6,2), SEVEN),
      FilledCell.using(Cell.at(7,2), EIGHT),
      FilledCell.using(Cell.at(1,3), FIVE),
      FilledCell.using(Cell.at(4,3), ONE),
      FilledCell.using(Cell.at(5,3), FOUR),
      FilledCell.using(Cell.at(6,3), SIX),
      FilledCell.using(Cell.at(0,4), ONE),
      FilledCell.using(Cell.at(3,4), SEVEN),
      FilledCell.using(Cell.at(4,4), TWO),
      FilledCell.using(Cell.at(5,4), NINE),
      FilledCell.using(Cell.at(8,4), EIGHT),
      FilledCell.using(Cell.at(2,5), TWO),
      FilledCell.using(Cell.at(3,5), FIVE),
      FilledCell.using(Cell.at(4,5), EIGHT),
      FilledCell.using(Cell.at(7,5), THREE),
      FilledCell.using(Cell.at(1,6), SIX),
      FilledCell.using(Cell.at(2,6), ONE),
      FilledCell.using(Cell.at(3,6), EIGHT),
      FilledCell.using(Cell.at(4,6), THREE),
      FilledCell.using(Cell.at(5,6), TWO),
      FilledCell.using(Cell.at(6,6), FOUR),
      FilledCell.using(Cell.at(0,7), SEVEN),
      FilledCell.using(Cell.at(2,7), EIGHT),
      FilledCell.using(Cell.at(3,7), NINE),
      FilledCell.using(Cell.at(5,7), FIVE),
      FilledCell.using(Cell.at(6,7), THREE),
      FilledCell.using(Cell.at(7,7), ONE),
      FilledCell.using(Cell.at(8,7), SIX),
      FilledCell.using(Cell.at(0,8), THREE),
      FilledCell.using(Cell.at(1,8), NINE),
      FilledCell.using(Cell.at(4,8), SEVEN),
      FilledCell.using(Cell.at(7,8), TWO),
      FilledCell.using(Cell.at(8,8), FIVE)
    ).collect(Collectors.toSet());

  private final SudokuSolution solution = new SudokuSolution(puzzle, filledCells);

  @Test public void puzzle() {

    assertThat(solution.puzzle(), sameInstance(puzzle));
  }

  @Test public void elements() {

    assertThat(solution.elements(), sameInstance(filledCells));
  }

  @Test public void toRaster() {

    assertThat(solution.toRaster(), matchesLines(
        "╔═╤═╤═╦═╤═╤═╦═╤═╤═╗",
        "║6│8│3║2│9│7║1│5│4║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║9│4│7║1│5│8║2│6│3║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║2│1│5║4│6│3║7│8│9║",
        "╠═╪═╪═╬═╪═╪═╬═╪═╪═╣",
        "║8│5│9║3│1│4║6│7│2║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║1│3│6║7│2│9║5│4│8║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║4│7│2║5│8│6║9│3│1║",
        "╠═╪═╪═╬═╪═╪═╬═╪═╪═╣",
        "║5│6│1║8│3│2║4│9│7║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║7│2│8║9│4│5║3│1│6║",
        "╟─┼─┼─╫─┼─┼─╫─┼─┼─╢",
        "║3│9│4║6│7│1║8│2│5║",
        "╚═╧═╧═╩═╧═╧═╩═╧═╧═╝"
      )
    );
  }
}