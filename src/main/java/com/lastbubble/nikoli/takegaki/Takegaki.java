package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.PuzzleFactory;
import com.lastbubble.nikoli.Solution;
import com.lastbubble.nikoli.solver.Solver;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Takegaki implements PuzzleFactory<Integer, Edge> {

  @Override public TakegakiPuzzle read(BufferedReader reader) {

    Grid.Builder<Integer> builder = Grid.<Integer>builder();

    List<String> lines = reader.lines().collect(Collectors.toList());

    for (int y = 0; y < lines.size(); y++) {

      String[] tokens = lines.get(y).split(",");

      for (int x = 0; x < tokens.length; x++) {

        String token = tokens[x];

        if (CELL_PATTERN.matcher(token).matches()) {

          builder.assign(Cell.at(x, y), Integer.parseInt(token));
        }
      }
    }

    return new TakegakiPuzzle(builder.build());
  }

  @Override public Iterable<? extends Solution<Integer, Edge>> solve(Puzzle<Integer> puzzle) {

    List<TakegakiSolution> solutions = new ArrayList<TakegakiSolution>();

    Solver<Edge> solver = new TakegakiSolver(puzzle.grid());

    solver.solve(pathEdges -> {
      solutions.add( new TakegakiSolution(puzzle, pathEdges));
    });

    return solutions;
  }

  private static final Pattern CELL_PATTERN = Pattern.compile("0|1|2|3");
}
