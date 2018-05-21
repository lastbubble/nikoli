package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.solver.Solver;

import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws Exception {

    Grid<Integer> grid = new TakegakiReader().read(Paths.get(args[0]));

    Solver<Edge> solver = new TakegakiSolver(grid);

    System.out.println("\nPUZZLE\n======");
    new SolutionPrinter(grid, Stream.<Edge>of()).print();

    solver.solve(pathEdges -> {
      System.out.println("\nSOLUTION\n========");
      new SolutionPrinter(grid, pathEdges.stream()).print();
    });
  }
}
