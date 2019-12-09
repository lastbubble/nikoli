package com.lastbubble.nikoli.sudoku;

import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.FilledCell;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.Solution;

import java.util.Set;

class SudokuSolution implements Solution<Value, FilledCell<Value>> {

  private final Puzzle<Value> puzzle;
  private final Set<FilledCell<Value>> filledCells;

  SudokuSolution(Puzzle<Value> puzzle, Set<FilledCell<Value>> filledCells) {
    this.puzzle = puzzle;
    this.filledCells = filledCells;
  }

  @Override public Puzzle<Value> puzzle() { return puzzle; }

  @Override public Set<FilledCell<Value>> elements() { return filledCells; }

  @Override public CharRaster toRaster() {

    CharRaster raster = puzzle.toRaster();

    for (FilledCell<Value> c : filledCells) {
      raster.set(2 * c.cell().x() + 1, 2 * c.cell().y() + 1, c.value().describe());
    }

    return raster;
  }
}
