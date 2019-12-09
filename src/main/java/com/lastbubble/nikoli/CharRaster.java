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

  private CharRaster transform(Function<Character, Character> f) {

    for (char[] row : cells) {

      for (int i = 0; i < row.length; i++) { row[i] = f.apply(row[i]); }
    }

    return this;
  }

  public void printTo(PrintWriter writer) {

    for (char[] row : cells) { writer.println( new String(row)); }
  }

  public static final char DOT = '\u2022';
  public static final char SINGLE_HORIZONTAL = '\u2500';
  public static final char SINGLE_VERTICAL = '\u2502';
  public static final char SINGLE_DOWN_RIGHT = '\u250C';
  public static final char SINGLE_DOWN_LEFT = '\u2510';
  public static final char SINGLE_UP_RIGHT = '\u2514';
  public static final char SINGLE_UP_LEFT = '\u2518';
  public static final char SINGLE_VERTICAL_RIGHT = '\u251c';
  public static final char SINGLE_VERTICAL_LEFT = '\u2524';
  public static final char SINGLE_DOWN_HORIZONTAL = '\u252c';
  public static final char SINGLE_UP_HORIZONTAL = '\u2534';
  public static final char SINGLE_VERTICAL_HORIZONTAL = '\u253c';
  public static final char DOUBLE_HORIZONTAL = '\u2550';
  public static final char DOUBLE_VERTICAL = '\u2551';
  public static final char DOUBLE_DOWN_RIGHT = '\u2554';
  public static final char DOUBLE_DOWN_LEFT = '\u2557';
  public static final char DOUBLE_UP_RIGHT = '\u255A';
  public static final char DOUBLE_UP_LEFT = '\u255D';
  public static final char DOUBLE_VERTICAL_SINGLE_RIGHT = '\u255F';
  public static final char DOUBLE_VERTICAL_RIGHT = '\u2560';
  public static final char DOUBLE_VERTICAL_SINGLE_LEFT = '\u2562';
  public static final char DOUBLE_VERTICAL_LEFT = '\u2563';
  public static final char DOUBLE_DOWN_HORIZONTAL = '\u2566';
  public static final char DOUBLE_UP_HORIZONTAL = '\u2569';
  public static final char SINGLE_VERTICAL_DOUBLE_HORIZONTAL = '\u256A';
  public static final char DOUBLE_VERTICAL_SINGLE_HORIZONTAL = '\u256B';
  public static final char DOUBLE_VERTICAL_HORIZONTAL = '\u256C';
  public static final char FULL_BLOCK = '\u2588';
  public static final char SINGLE_DOWN_DOUBLE_HORIZONTAL = '\u2564';
  public static final char SINGLE_UP_DOUBLE_HORIZONTAL = '\u2567';
}
