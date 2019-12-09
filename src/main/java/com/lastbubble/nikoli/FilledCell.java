package com.lastbubble.nikoli;

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

  @Override public String toString() {
    return String.format("%s={%s}", cell, value);
  }
}
