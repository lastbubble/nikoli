package com.lastbubble.nikoli;

import java.util.Objects;

public class FilledCell<V> {

  public static <V> FilledCell<V> using(Cell cell, V value) {

    return new FilledCell<V>(cell, value);
  }

  private final Cell cell;
  private final V value;

  private FilledCell(Cell cell, V value) {

    this.cell = cell;
    this.value = value;
  }

  public Cell cell() { return cell; }
  public V value() { return value; }

  @Override public int hashCode() { return Objects.hash(cell, value); }

  @Override public boolean equals(Object o) {

    if (o == this) {

      return true;

    } else if (o instanceof FilledCell) {

      FilledCell<?> that = (FilledCell<?>) o;
      return (
        Objects.equals(this.cell(), that.cell()) &&
        Objects.equals(this.value(), that.value())
      );

    } else {

      return false;
    }
  }

  @Override public String toString() {
    return String.format("%s={%s}", cell, value);
  }
}
