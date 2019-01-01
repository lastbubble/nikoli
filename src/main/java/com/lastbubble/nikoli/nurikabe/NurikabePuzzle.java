package com.lastbubble.nikoli.nurikabe;

import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;

class NurikabePuzzle implements Puzzle<Integer> {

  private final Grid<Integer> grid;

  NurikabePuzzle(Grid<Integer> grid) { this.grid = grid; }

  @Override public Grid<Integer> grid() { return grid; }

  @Override public CharRaster toRaster() {

    final CharRaster raster = new CharRaster(2 * grid.width() - 1, 2 * grid.height() - 1);

    grid.filledCells().forEach(c ->
      raster.set(2 * c.x(), 2 * c.y(), (char) ('0' + grid.valueAt(c).get()))
    );

    return raster;
  }
}
