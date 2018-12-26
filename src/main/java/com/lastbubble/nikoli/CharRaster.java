package com.lastbubble.nikoli;

import java.io.PrintWriter;
import java.util.function.Function;

public class CharRaster {

  private final char[][] cells;

  public CharRaster(int width, int height) {

    cells = new char[height][width];

    transform(c -> ' ');
  }

  public void set(int x, int y, char c) { cells[y][x] = c; }

  public CharRaster transform(Function<Character, Character> f) {

    for (char[] row : cells) {

      for (int i = 0; i < row.length; i++) { row[i] = f.apply(row[i]); }
    }

    return this;
  }

  public void printTo(PrintWriter writer) {

    for (char[] row : cells) { writer.println( new String(row)); }
  }
}
