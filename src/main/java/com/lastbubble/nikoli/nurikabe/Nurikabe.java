package com.lastbubble.nikoli.nurikabe;

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

public class Nurikabe implements PuzzleFactory<Integer, Cell> {

  @Override public NurikabePuzzle read(BufferedReader reader) {

    Grid.Builder<Integer> builder = Grid.<Integer>builder();

    List<String> lines = reader.lines().collect(Collectors.toList());

    int maxWidth = 0;

    for (int y = 0; y < lines.size(); y++) {

      String[] tokens = lines.get(y).split(",");

      maxWidth = Math.max(maxWidth, tokens.length);

      for (int x = 0; x < tokens.length; x++) {

        String token = tokens[x];

        if (CELL_PATTERN.matcher(token).matches()) {

          builder.assign(Cell.at(x, y), Integer.parseInt(token));
        }
      }
    }

    builder.withMaxCell(Cell.at(maxWidth - 1, lines.size() - 1));

    return new NurikabePuzzle(builder.build());
  }

  @Override public Iterable<? extends Solution<Integer, Cell>> solve(Puzzle<Integer> puzzle) {

    List<NurikabeSolution> solutions = new ArrayList<NurikabeSolution>();

    Solver<Cell> solver = new NurikabeSolver(puzzle.grid());

    solver.solve(pathCells -> {
      solutions.add( new NurikabeSolution(puzzle, pathCells));
    });

    return solutions;
  }

  private static final Pattern CELL_PATTERN = Pattern.compile("0|1|2|3|4|5|6|7|8|9");
}
