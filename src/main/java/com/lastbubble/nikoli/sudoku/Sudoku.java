package com.lastbubble.nikoli.sudoku;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.FilledCell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.PuzzleFactory;
import com.lastbubble.nikoli.Solution;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Sudoku implements PuzzleFactory<Value, FilledCell<Value>> {

  @Override public SudokuPuzzle read(BufferedReader reader) {

    Grid.Builder<Value> builder = Grid.<Value>builder();

    List<String> lines = reader.lines().collect(Collectors.toList());

    int maxWidth = 0;

    for (int y = 0; y < lines.size(); y++) {

      String[] tokens = lines.get(y).split(",");

      maxWidth = Math.max(maxWidth, tokens.length);

      for (int x = 0; x < tokens.length; x++) {

        String token = tokens[x];

        if (CELL_PATTERN.matcher(token).matches()) {

          builder.assign(Cell.at(x, y), valueFor(token));
        }
      }
    }

    builder.withMaxCell(Cell.at(maxWidth - 1, lines.size() - 1));

    return new SudokuPuzzle(builder.build());
  }

  private static Value valueFor(String s) {

    for (Value v : Value.values()) { if (s.charAt(0) == v.describe()) { return v; } }

    return null;
  }

  @Override public Iterable<? extends Solution<Value, FilledCell<Value>>> solve(Puzzle<Value> puzzle) {

    List<SudokuSolution> solutions = new ArrayList<SudokuSolution>();

    if (puzzle instanceof SudokuPuzzle) {

      solutions.add( new SudokuSolver((SudokuPuzzle) puzzle).solve());
    }

    return solutions;
  }

  private static final Pattern CELL_PATTERN = Pattern.compile("0|1|2|3|4|5|6|7|8|9");
}
