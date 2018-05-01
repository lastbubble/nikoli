package com.lastbubble.nikoli.takegaki;

import java.util.Objects;

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
}
