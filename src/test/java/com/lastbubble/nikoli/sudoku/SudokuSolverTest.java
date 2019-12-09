package com.lastbubble.nikoli.sudoku;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SudokuSolverTest {

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

  private final SudokuSolver solver = new SudokuSolver(puzzle);

  @Test public void solve() {

    SudokuSolution solution = solver.solve();

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
