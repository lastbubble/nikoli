package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Cell;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Edge {

  public enum Orientation { HORIZONTAL, VERTICAL; }
  public static final Orientation H = Orientation.HORIZONTAL;
  public static final Orientation V = Orientation.VERTICAL;

  public static Orientation oppositeOf(Orientation o) { return (o != null) ? ((o == H) ? V : H) : null; }

  public static Edge at(int x, int y, Orientation o) {

    checkArgument(x >= 0, "x cannot be negative");
    checkArgument(y >= 0, "y cannot be negative");
    checkArgument(o != null, "orientation must be specified");

    return new Edge(x, y, o);
  }

  private static void checkArgument(boolean b, String reason) {

    if (!b) { throw new IllegalArgumentException(reason); }
  }

  public static Edge topOf(Cell c) { return Edge.at(c.x(), c.y() + 1, H); }

  public static Edge leftOf(Cell c) { return Edge.at(c.x(), c.y(), V); }

  public static Edge bottomOf(Cell c) { return Edge.at(c.x(), c.y(), H); }

  public static Edge rightOf(Cell c) { return Edge.at(c.x() + 1, c.y(), V); }

  public static Stream<Edge> edgesOf(Cell c) {

    return Stream.of(topOf(c), leftOf(c), bottomOf(c), rightOf(c));
  }

  private final int x;
  private final int y;
  private final Orientation o;

  private Edge(int x, int y, Orientation o) {

    this.x = x;
    this.y = y;
    this.o = o;
  }

  public int x() { return x; }
  public int y() { return y; }
  public Orientation orientation() { return o; }

  public Stream<Edge> neighbors(boolean forward) {

    Cell cell = Cell.at(x(), y());

    Stream<Optional<Edge>> neighbors;

    if (orientation() == H) {

      if (forward) {

        neighbors = Stream.of(
          Optional.of(rightOf(cell)),
          cell.toRight().map(c -> bottomOf(c)),
          cell.below().map(c -> rightOf(c))
        );

      } else {

        neighbors = Stream.of(
          Optional.of(leftOf(cell)),
          cell.toLeft().map(c -> bottomOf(c)),
          cell.below().map(c -> leftOf(c))
        );
      }

    } else {

      if (forward) {

        neighbors = Stream.of(
          Optional.of(topOf(cell)),
          cell.toLeft().map(c -> topOf(c)),
          cell.above().map(c -> leftOf(c))
        );

      } else {

        neighbors = Stream.of(
          Optional.of(bottomOf(cell)),
          cell.toLeft().map(c -> bottomOf(c)),
          cell.below().map(c -> leftOf(c))
        );
      }
    }

    return neighbors.filter(Optional::isPresent).map(Optional::get);
  }

  public boolean isConnected(Edge that) {

    if (this.o == that.o) {

      return
        Math.abs(this.x - that.x) == (this.o == Edge.H ? 1 : 0) &&
        Math.abs(this.y - that.y) == (this.o == Edge.V ? 1 : 0);

    } else {

      int adjacentX = this.x + (this.o == Edge.H ? 1 : -1);
      int adjacentY = this.y + (this.o == Edge.V ? 1 : -1);

      return
        (this.x == that.x || adjacentX == that.x) &&
        (this.y == that.y || adjacentY == that.y);
    }
  }

  @Override public int hashCode() { return Objects.hash(x, y, o); }

  @Override public boolean equals(Object obj) {

    if (obj == this) {

      return true;

    } else if (obj instanceof Edge) {

      Edge that = (Edge) obj;
      return (this.x == that.x && this.y == that.y && this.o == that.o);

    } else {

      return false;
    }
  }

  @Override public String toString() {

    return String.format("Edge(%d,%d,%s)", x(), y(), orientation().name().charAt(0));
  }
}
