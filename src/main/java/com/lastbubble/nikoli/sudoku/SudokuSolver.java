package com.lastbubble.nikoli.sudoku;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.FilledCell;
import com.lastbubble.nikoli.Grid;

public class SudokuSolver {

  private final SudokuPuzzle puzzle;

  public SudokuSolver(SudokuPuzzle puzzle) { this.puzzle = puzzle; }

  public SudokuSolution solve() {

    Set<FilledCell<Value>> filledCells = new HashSet<>();

    Grid<Value> grid = puzzle.grid();

    while (true) {

      NaiveSolver naiveSolver = new NaiveSolver(grid);

      Set<FilledCell<Value>> naivelyFilledCells = naiveSolver.solve();

      if (naivelyFilledCells.isEmpty()) { break; }

      filledCells.addAll(naivelyFilledCells);

      grid = grid.toBuilder().fillUsing(naivelyFilledCells.stream()).build();
    }

    return new SudokuSolution(puzzle, filledCells);
  }

  private class NaiveSolver {

    private final Grid<Value> grid;
    private final Set<FilledCell<Value>> filledCells = new HashSet<>();

    private NaiveSolver(Grid<Value> grid) { this.grid = grid; }

    public Set<FilledCell<Value>> solve() {

      Multimap<Cell, Value> possibilities = HashMultimap.create();

      grid.cells().forEach(c -> {
        if (!grid.valueAt(c).isPresent()) {
          for (Value v : Value.values()) {
            if (!
              (
                areaContains(puzzle.rowContaining(c), v) ||
                areaContains(puzzle.columnContaining(c), v) ||
                areaContains(puzzle.regionContaining(c), v)
              )
            ) {
              possibilities.put(c, v);
            }
          }
        }
      });

      possibilities.keySet().forEach(c -> {

        Collection<Value> values = possibilities.get(c);

        if (values.size() == 1) {

          filledCells.add(FilledCell.using(c, values.iterator().next()));

        } else {

          values.stream().forEach(v -> {
            if (
              puzzle.rowContaining(c)
                .filter(cc -> !cc.equals(c))
                .allMatch(cc -> !possibilities.containsEntry(cc, v))
            ) {

              filledCells.add(FilledCell.using(c, v));
            }
          });
        }
      });

      return filledCells;
    }

    private boolean areaContains(Stream<Cell> cells, Value value) {
      return cells.anyMatch(c -> grid.valueAt(c).orElse(null) == value);
    }
  }
}
