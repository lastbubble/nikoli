package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;

class TakegakiPuzzle implements Puzzle<Integer> {

  private final Grid<Integer> grid;

  TakegakiPuzzle(Grid<Integer> grid) { this.grid = grid; }

  @Override public Grid<Integer> grid() { return grid; }

  @Override public CharRaster toRaster() { return new Rasterizer(grid()).toRaster(); }
}
