package com.lastbubble.nikoli;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid<V> {

  private final Cell maxCell;
  private final Map<Cell, V> values;

  public Grid(Builder<V> builder) {

    this.maxCell = builder.values.keySet().stream().reduce(
      (builder.maxCell != null) ? builder.maxCell : Cell.at(0, 0),
      (a, b) -> Cell.at(Integer.max(a.x(), b.x()), Integer.max(a.y(), b.y()))
    );
    this.values = Collections.unmodifiableMap(builder.values);
  }

  public int width() { return maxCell.x() + 1; }
  public int height() { return maxCell.y() + 1; }

  public Stream<Cell> cells() {

    return Stream.iterate(
        Cell.at(0, 0),
        c -> (c.x() + 1) < width() ? Cell.at(c.x() + 1, c.y()) : Cell.at(0, c.y() + 1)
      )
      .limit(width() * height());
  }

  public Stream<Cell> rowContaining(Cell c) {
    return IntStream.range(0, width()).mapToObj(x -> Cell.at(x, c.y()));
  }

  public Stream<Cell> columnContaining(Cell c) {
    return IntStream.range(0, height()).mapToObj(y -> Cell.at(c.x(), y));
  }

  public Stream<Cell> filledCells() { return values.keySet().stream(); }

  public boolean isCompletelyFilled() { return (width() * height()) == values.size(); }

  public Optional<V> valueAt(Cell cell) { return Optional.ofNullable(values.get(cell)); }

  public boolean valueFoundIn(Stream<Cell> group, V value) {

    return group.anyMatch(c -> Objects.equals(values.get(c), value));
  }

  public Builder<V> toBuilder() {

    Builder<V> builder = builder();
    values.forEach((c, v) -> builder.assign(c, v));
    return builder;
  }

  public static <V> Builder<V> builder() { return new Builder<V>(); }

  public static class Builder<V> {

    private Cell maxCell;
    private Map<Cell, V> values = new HashMap<>();

    public Builder<V> withMaxCell(Cell c) { maxCell = c; return this; }

    public Builder<V> assign(Cell c, V v) { values.put(c, v); return this; }

    public Builder<V> fillUsing(Stream<FilledCell<V>> filledCells) {
      filledCells.forEach(c -> assign(c.cell(), c.value())); return this;
    }

    public Grid<V> build() {

      Grid<V> grid = new Grid<V>(this);
      values = new HashMap<>();
      return grid;
    }
  }
}
