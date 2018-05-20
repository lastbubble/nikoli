package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.solver.Solver;

import java.util.stream.Stream;

import org.junit.Test;

public class TakegakiSolverTest {

  private final Grid<Integer> grid = Grid.<Integer>builder()
    .assign(Cell.at(0, 0), 1)
    .assign(Cell.at(1, 0), 3)
    .assign(Cell.at(4, 0), 3)
    .assign(Cell.at(8, 0), 3)
    .assign(Cell.at(9, 0), 3)
    .assign(Cell.at(0, 1), 0)
    .assign(Cell.at(4, 1), 0)
    .assign(Cell.at(6, 1), 1)
    .assign(Cell.at(7, 1), 0)
    .assign(Cell.at(8, 1), 1)
    .assign(Cell.at(2, 2), 1)
    .assign(Cell.at(4, 2), 3)
    .assign(Cell.at(5, 2), 3)
    .assign(Cell.at(0, 3), 3)
    .assign(Cell.at(1, 3), 0)
    .assign(Cell.at(5, 3), 1)
    .assign(Cell.at(7, 3), 3)
    .assign(Cell.at(9, 3), 2)
    .assign(Cell.at(3, 4), 2)
    .assign(Cell.at(4, 4), 3)
    .assign(Cell.at(8, 4), 3)
    .assign(Cell.at(9, 4), 3)
    .assign(Cell.at(0, 5), 0)
    .assign(Cell.at(1, 5), 3)
    .assign(Cell.at(5, 5), 0)
    .assign(Cell.at(6, 5), 3)
    .assign(Cell.at(0, 6), 1)
    .assign(Cell.at(2, 6), 2)
    .assign(Cell.at(4, 6), 1)
    .assign(Cell.at(8, 6), 3)
    .assign(Cell.at(9, 6), 2)
    .assign(Cell.at(4, 7), 3)
    .assign(Cell.at(5, 7), 2)
    .assign(Cell.at(7, 7), 1)
    .assign(Cell.at(1, 8), 0)
    .assign(Cell.at(2, 8), 2)
    .assign(Cell.at(3, 8), 1)
    .assign(Cell.at(5, 8), 3)
    .assign(Cell.at(9, 8), 2)
    .assign(Cell.at(0, 9), 2)
    .assign(Cell.at(1, 9), 3)
    .assign(Cell.at(5, 9), 1)
    .assign(Cell.at(8, 9), 2)
    .assign(Cell.at(9, 9), 0)
    .build();

  @Test public void solveExample() throws Exception {

    Solver solver = new TakegakiSolver(grid);

    System.out.println("\nPUZZLE\n======");
    new SolutionPrinter(grid, Stream.<Edge>of()).print();

    solver.solve(solutionVars -> {
      System.out.println("\nSOLUTION\n========");
      new SolutionPrinter(grid, solutionVars.stream().map(var -> (Edge) var.data())).print();
    });
  }
}
