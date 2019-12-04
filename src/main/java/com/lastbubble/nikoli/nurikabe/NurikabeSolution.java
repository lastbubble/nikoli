package com.lastbubble.nikoli.nurikabe;

import static com.lastbubble.nikoli.CharRaster.*;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.Solution;

import java.util.Set;

class NurikabeSolution implements Solution<Integer, Cell> {

  private final Puzzle<Integer> puzzle;
  private final Set<Cell> pathCells;

  NurikabeSolution(Puzzle<Integer> puzzle, Set<Cell> pathCells) {
    this.puzzle = puzzle;
    this.pathCells = pathCells;
  }

  @Override public Puzzle<Integer> puzzle() { return puzzle; }

  @Override public Set<Cell> elements() { return pathCells; }

  @Override public CharRaster toRaster() {

    CharRaster raster = puzzle.toRaster();

    for (Cell c : pathCells) { raster.set(2 * c.x() + 1, 2 * c.y() + 1, FULL_BLOCK); }

    return raster;
  }
}
