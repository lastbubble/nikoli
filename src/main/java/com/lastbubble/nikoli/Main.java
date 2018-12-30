package com.lastbubble.nikoli;

import com.lastbubble.nikoli.hashiwokakero.Bridge;
import com.lastbubble.nikoli.hashiwokakero.Hashiwokakero;
import com.lastbubble.nikoli.takegaki.Edge;
import com.lastbubble.nikoli.takegaki.Takegaki;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws Exception {

    PrintWriter out = new PrintWriter(System.out, true);

    String type = args[0];
    BufferedReader reader = Files.newBufferedReader(Paths.get(args[1]));

    if (type.equals("takegaki")) {

      PuzzleFactory<Integer, Edge> takegaki = new Takegaki();

      Puzzle<Integer> puzzle = takegaki.read(reader);

      out.println("\nPUZZLE\n======");
      puzzle.toRaster().printTo(out);

      Iterable<? extends Solution<Integer, Edge>> solutions = takegaki.solve(puzzle);

      out.println("\nSOLUTION\n========");
      for (Solution<Integer, Edge> solution : solutions) {
        solution.toRaster().printTo(out);
      }

    } else if (type.equals("hashiwokakero")) {

      PuzzleFactory<Integer, Bridge> hashiwokakero = new Hashiwokakero();

      Puzzle<Integer> puzzle = hashiwokakero.read(reader);

      out.println("\nPUZZLE\n======");
      puzzle.toRaster().printTo(out);

      Iterable<? extends Solution<Integer, Bridge>> solutions = hashiwokakero.solve(puzzle);

      out.println("\nSOLUTION\n========");
      for (Solution<Integer, Bridge> solution : solutions) {
        solution.toRaster().printTo(out);
      }
    }
  }
}
