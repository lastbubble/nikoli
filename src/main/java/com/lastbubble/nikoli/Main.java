package com.lastbubble.nikoli;

import com.lastbubble.nikoli.hashiwokakero.Hashiwokakero;
import com.lastbubble.nikoli.sudoku.Sudoku;
import com.lastbubble.nikoli.takegaki.Takegaki;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws Exception {

    PrintWriter out = new PrintWriter(System.out, true);

    PuzzleFactory<?, ?> puzzleFactory = null;

    String type = args[0];
    if      (type.equals("takegaki"))      { puzzleFactory = new Takegaki(); }
    else if (type.equals("hashiwokakero")) { puzzleFactory = new Hashiwokakero(); }
    else if (type.equals("sudoku"))        { puzzleFactory = new Sudoku(); }

    BufferedReader reader = Files.newBufferedReader(Paths.get(args[1]));

    if (puzzleFactory != null) { printAndSolve(out, puzzleFactory, reader); }
  }

  private static <C, E> void printAndSolve(
    PrintWriter out, PuzzleFactory<C, E> puzzleFactory, BufferedReader reader
  ) {

    Puzzle<C> puzzle = puzzleFactory.read(reader);

    out.println("\nPUZZLE\n======");
    puzzle.toRaster().printTo(out);

    Iterable<? extends Solution<C, E>> solutions = puzzleFactory.solve(puzzle);

    out.println("\nSOLUTION\n========");
    for (Solution<C, E> solution : solutions) {
      solution.toRaster().printTo(out);
    }
  }
}
