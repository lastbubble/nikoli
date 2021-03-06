package com.lastbubble.nikoli.hashiwokakero;

import com.lastbubble.nikoli.Cell;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Bridge {

  public static Bridge connecting(Cell end1, Cell end2) {

    boolean sameX = end1.x() == end2.x();
    boolean sameY = end1.y() == end2.y();

    if (sameX && sameY || (!sameX && !sameY)) {
      throw new IllegalArgumentException("invalid endpoints");
    }

    return new Bridge(end1, end2, 1);
  }

  private final Cell end1;
  private final Cell end2;
  private final int weight;

  private Bridge(Cell end1, Cell end2, int weight) {

    this.end1 = end1;
    this.end2 = end2;
    this.weight = weight;
  }

  public Stream<Cell> span() {

    Stream<Cell> cells;

    if (end1.x() == end2.x()) {

      int y1 = end1.y(), y2 = end2.y();
      cells = IntStream.rangeClosed(y1 < y2 ? y1 : y2, y1 < y2 ? y2 : y1).mapToObj(y -> Cell.at(end1.x(), y));

    } else {

      int x1 = end1.x(), x2 = end2.x();
      cells = IntStream.rangeClosed(x1 < x2 ? x1 : x2, x1 < x2 ? x2 : x1).mapToObj(x -> Cell.at(x, end1.y()));
    }

    return cells;
  }

  public Cell oneEnd() { return end1.compareTo(end2) < 0 ? end1 : end2; }

  public Cell otherEnd() { return end1.compareTo(end2) > 0 ? end1 : end2; }

  public Cell oppositeFrom(Cell end) {

    if (end.equals(end1)) { return end2; }
    else if (end.equals(end2)) { return end1; }

    throw new IllegalArgumentException(end + " is not an endpoint of " + this);
  }

  public boolean crosses(Bridge that) {

    return (end1.y() == end2.y()) ?
      (
        that.oneEnd().y() < this.oneEnd().y() &&
        that.otherEnd().y() > this.oneEnd().y() &&
        that.oneEnd().x() > this.oneEnd().x() &&
        that.oneEnd().x() < this.otherEnd().x()
      ) :
      (
        that.oneEnd().x() < this.oneEnd().x() &&
        that.otherEnd().x() > this.oneEnd().x() &&
        that.oneEnd().y() > this.oneEnd().y() &&
        that.oneEnd().y() < this.otherEnd().y()
      );
  }

  public int weight() { return weight; }

  public Bridge withWeight(int n) { return new Bridge(end1, end2, n); }

  public Bridge incrementWeight() { return withWeight(weight() + 1); }

  @Override public int hashCode() { return Objects.hash(oneEnd(), otherEnd(), weight); }

  @Override public boolean equals(Object obj) {

    if (obj == this) { return true; }

    if (obj instanceof Bridge) {

      Bridge that = (Bridge) obj;

      return (
        this.oneEnd().equals(that.oneEnd()) &&
        this.otherEnd().equals(that.otherEnd()) &&
        this.weight() == that.weight()
      );
    }

    return false;
  }

  @Override public String toString() {

    return String.format("Bridge[(%d,%d)%s(%d,%d)]",
      oneEnd().x(), oneEnd().y(), (weight > 1) ? '=' : '-', otherEnd().x(), otherEnd().y()
    );
  }
}
