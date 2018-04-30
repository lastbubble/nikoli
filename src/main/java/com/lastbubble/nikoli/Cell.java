package com.lastbubble.nikoli;

import java.util.Objects;

public class Cell {

  public static Cell at(int x, int y) {

    checkArgument(x >= 0, "x cannot be negative");
    checkArgument(y >= 0, "y cannot be negative");

    return new Cell(x, y);
  }

  private static void checkArgument(boolean b, String reason) {

    if (!b) { throw new IllegalArgumentException(reason); }
  }

  private final int x;
  private final int y;

  private Cell(int x, int y) {

    this.x = x;
    this.y = y;
  }

  public int x() { return x; }
  public int y() { return y; }

  @Override public int hashCode() { return Objects.hash(x, y); }

  @Override public boolean equals(Object o) {

    if (o == this) {

      return true;

    } else if (o instanceof Cell) {

      Cell that = (Cell) o;
      return (this.x == that.x && this.y == that.y);

    } else {

      return false;
    }
  }
}
