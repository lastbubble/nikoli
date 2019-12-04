package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.CharRaster.*;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.Solution;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class HashiwokakeroSolution implements Solution<Integer, Bridge> {

  private final Puzzle<Integer> puzzle;
  private final Set<Bridge> bridges;

  HashiwokakeroSolution(Puzzle<Integer> puzzle, Set<Bridge> bridges) {
    this.puzzle = puzzle;
    this.bridges = bridges;
  }

  @Override public Puzzle<Integer> puzzle() { return puzzle; }

  @Override public Set<Bridge> elements() { return bridges; }

  @Override public CharRaster toRaster() {

    CharRaster raster = puzzle.toRaster();

    for (Bridge bridge : elements()) {

      List<Cell> cells = bridge.span().collect(Collectors.toList());

      Cell firstCell = cells.get(0);
      Cell lastCell = cells.get(cells.size() - 1);

      Stream<Cell> rasterCells;
      char bridgeChar;

      if (firstCell.y() == cells.get(1).y()) {

        rasterCells = IntStream.range(2 * firstCell.x() + 1, 2 * lastCell.x())
          .mapToObj(x -> Cell.at(x, 2 * firstCell.y()));
        bridgeChar = bridge.weight() > 1 ? DOUBLE_HORIZONTAL : SINGLE_HORIZONTAL;

      } else {

        rasterCells = IntStream.range(2 * firstCell.y() + 1, 2 * lastCell.y())
          .mapToObj(y -> Cell.at(2 * firstCell.x(), y));
        bridgeChar = bridge.weight() > 1 ? DOUBLE_VERTICAL : SINGLE_VERTICAL;
      }

      rasterCells.forEach(c -> raster.set(c.x(), c.y(), bridgeChar));
    }

    return raster;
  }
}
