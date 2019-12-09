package com.lastbubble.nikoli;

import java.util.Objects;
import java.util.Optional;

public class Cell implements Comparable<Cell> {

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

  public Optional<Cell> above() { return validCell(x, y + 1); }
  public Optional<Cell> toLeft() { return validCell(x - 1, y); }
  public Optional<Cell> below() { return validCell(x, y - 1); }
  public Optional<Cell> toRight() { return validCell(x + 1, y); }

  private static Optional<Cell> validCell(int x, int y) {

    return Optional.ofNullable((x >= 0 && y >= 0) ? Cell.at(x, y) : null);
  }

  @Override public int compareTo(Cell that) {

    int delta = this.y() - that.y();

    if (delta == 0) { delta = this.x() - that.x(); }

    return delta;
  }

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

  @Override public String toString() {
    return String.format("Cell[%s,%s]", x, y);
  }
}
