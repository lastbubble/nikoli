package com.lastbubble.nikoli.hashiwokakero;

import com.lastbubble.nikoli.Cell;

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

  public int weight() { return weight; }

  public Bridge incrementWeight() {

    return new Bridge(end1, end2, weight() + 1);
  }
}
