package com.lastbubble.nikoli.sudoku;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.FilledCell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.solver.Solver;

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

    if (grid.isCompletelyFilled() == false) {

      new SatSolver(possibilitiesFor(grid)).solve(cs -> filledCells.addAll(cs));
    }

    return new SudokuSolution(puzzle, filledCells);
  }

  private Multimap<Cell, Value> possibilitiesFor(Grid<Value> grid) {

    Multimap<Cell, Value> possibilities = HashMultimap.create();

    grid.cells().forEach(c -> {
      if (!grid.valueAt(c).isPresent()) {
        for (Value v : Value.values()) {
          if (!
            (
              grid.valueFoundIn(grid.rowContaining(c), v) ||
              grid.valueFoundIn(grid.columnContaining(c), v) ||
              grid.valueFoundIn(puzzle.regionContaining(c), v)
            )
          ) {
            possibilities.put(c, v);
          }
        }
      }
    });

    Set<Cell> keys = new HashSet<>(possibilities.keySet());

    keys.forEach(cell -> {
      Collection<Value> values = possibilities.get(cell);
      if (values.size() != 2) { return; }
      grid.rowContaining(cell).filter(c -> !cell.equals(c))
        .forEach(sibling -> {
          if (values.equals(possibilities.get(sibling))) {
            grid.rowContaining(cell).filter(c -> !cell.equals(c) && !sibling.equals(c))
              .forEach(c -> values.forEach(v -> possibilities.remove(c, v)));
          }
        });

      grid.columnContaining(cell).filter(c -> !cell.equals(c))
        .forEach(sibling -> {
          if (values.equals(possibilities.get(sibling))) {
            grid.columnContaining(cell).filter(c -> !cell.equals(c) && !sibling.equals(c))
              .forEach(c -> values.forEach(v -> possibilities.remove(c, v)));
          }
        });

      puzzle.regionContaining(cell).filter(c -> !cell.equals(c))
        .forEach(sibling -> {
          if (values.equals(possibilities.get(sibling))) {
            puzzle.regionContaining(cell).filter(c -> !cell.equals(c) && !sibling.equals(c))
              .forEach(c -> values.forEach(v -> possibilities.remove(c, v)));
          }
        });
    });

    return possibilities;
  }

  private class NaiveSolver {

    private final Grid<Value> grid;
    private final Set<FilledCell<Value>> filledCells = new HashSet<>();

    private NaiveSolver(Grid<Value> grid) { this.grid = grid; }

    public Set<FilledCell<Value>> solve() {

      Multimap<Cell, Value> possibilities = possibilitiesFor(grid);

      possibilities.keySet().forEach(c -> {

        Collection<Value> values = possibilities.get(c);

        if (values.size() == 1) {

          filledCells.add(FilledCell.using(c, values.iterator().next()));

        } else {

          values.stream().forEach(v -> {
            if (
              grid.rowContaining(c)
                .filter(cc -> !cc.equals(c))
                .allMatch(cc -> !possibilities.containsEntry(cc, v))
                ||
              grid.columnContaining(c)
                .filter(cc -> !cc.equals(c))
                .allMatch(cc -> !possibilities.containsEntry(cc, v))
                ||
              puzzle.regionContaining(c)
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
  }

  private class SatSolver extends Solver<FilledCell<Value>> {

    private SatSolver(Multimap<Cell, Value> possibilities) {

      Grid<Value> grid = puzzle.grid();

      ;

      IntStream.range(0, 8).forEach(n -> {

        Set<FilledCell<Value>> filledCells;

        for (Value value : Value.values()) {

          filledCells = grid.rowContaining(Cell.at(0,n))
            .filter(c -> possibilities.containsEntry(c, value))
            .map(c -> FilledCell.using(c, value))
            .collect(toSet());

          if (!filledCells.isEmpty()) { addExactly(1, filledCells.stream()); }

          filledCells = grid.columnContaining(Cell.at(n,0))
          .filter(c -> possibilities.containsEntry(c, value))
          .map(c -> FilledCell.using(c, value))
          .collect(toSet());

          if (!filledCells.isEmpty()) { addExactly(1, filledCells.stream()); }
        }
      });

      Stream.of(
          Cell.at(0,0), Cell.at(2,0), Cell.at(5,0),
          Cell.at(0,3), Cell.at(2,3), Cell.at(5,3),
          Cell.at(0,6), Cell.at(2,6), Cell.at(5,6)
        ).forEach(cell -> {
        Set<FilledCell<Value>> filledCells;

        for (Value value : Value.values()) {

          filledCells = puzzle.regionContaining(cell)
            .filter(c -> possibilities.containsEntry(c, value))
            .map(c -> FilledCell.using(c, value))
            .collect(toSet());

          if (!filledCells.isEmpty()) { addExactly(1, filledCells.stream()); }
        }
      });

      possibilities.keySet().forEach(c -> {
        addExactly(1, possibilities.get(c).stream().map(v -> FilledCell.using(c, v)));
      });
    }
  }
}
