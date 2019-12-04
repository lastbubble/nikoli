package com.lastbubble.nikoli.nurikabe;

import static com.lastbubble.nikoli.logic.Formula.*;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.solver.Solver;

import java.util.Optional;
import java.util.stream.Stream;

public class NurikabeSolver extends Solver<Cell> {

  public NurikabeSolver(Grid<Integer> grid) {

    // for each non-filled cell, if it's on the path, at least one of its (non-filled) neighbors is on the path too
    grid.cells().filter(c -> !grid.valueAt(c).isPresent())
      .forEach(c ->
        add(anyOf(
          Stream.concat(
            Stream.of(not(varFor(c))),
            Stream.of(c.above(), c.toLeft(), c.below(), c.toRight())
              .filter(Optional::isPresent)
              .map(Optional::get)
              .filter(neighbor -> !grid.valueAt(neighbor).isPresent())
              .map(this::varFor)
            )
          )
        )
      );

      // for each block of four (non-filled) cells, no more than three are on the path

      // the number of cells on the path equals the number of cells in the grid
      // minus the sum of filled cells' values
      int totalCells = grid.width() * grid.height();
      int pathLength = totalCells - grid.filledCells().mapToInt(c -> grid.valueAt(c).get()).sum();
      addExactly(pathLength, grid.cells().filter(c -> !grid.valueAt(c).isPresent()));
  }
}
