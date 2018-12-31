package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Puzzle;
import com.lastbubble.nikoli.Solution;

import java.util.Set;

class TakegakiSolution implements Solution<Integer, Edge> {
  private final Puzzle<Integer> puzzle;
  private final Set<Edge> edges;
  TakegakiSolution(Puzzle<Integer> puzzle, Set<Edge> edges) {
    this.puzzle = puzzle;
    this.edges = edges;
  }
  @Override public Puzzle<Integer> puzzle() { return puzzle; }
  @Override public Set<Edge> elements() { return edges; }
  @Override public CharRaster toRaster() { return new Rasterizer(puzzle.grid(), edges).toRaster(); }
}
