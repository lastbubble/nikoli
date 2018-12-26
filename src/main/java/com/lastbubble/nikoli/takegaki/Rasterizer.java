package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Grid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Rasterizer {

  private final Grid<Integer> grid;
  private final Set<Edge> pathEdges;

  public Rasterizer(Grid<Integer> grid) { this(grid, new HashSet<Edge>()); }

  public Rasterizer(Grid<Integer> grid, Set<Edge> pathEdges) {

    this.grid = grid;
    this.pathEdges = pathEdges;
  }

  public CharRaster toRaster() {

    final CharRaster raster = new CharRaster(2 * grid.width() + 1, 2 * grid.height() + 1);

    IntStream.rangeClosed(0, grid.width()).forEach(x ->
      IntStream.rangeClosed(0, grid.height()).forEach(y -> 
        raster.set(2 * x, 2 * y, '\u00B7')
      )
    );

    grid.filledCells().forEach(c ->
      raster.set(2 * c.x() + 1, 2 * c.y() + 1, (char) ('0' + grid.valueAt(c).get()))
    );

    pathEdges.forEach(e -> {
        if (e.orientation() == Edge.H) {

          int y = 2 * e.y();

          raster.set(2 * e.x() + 1, y, '\u2550');

          if (pathEdges.contains(Edge.at(e.x() + 1, e.y(), Edge.H))) {
            raster.set(2 * e.x() + 1 + 1, y, '\u2550');
          }

          if (pathEdges.contains(Edge.at(e.x(), e.y(), Edge.V))) {
            raster.set(2 * e.x(), y, '\u2554');
          }

          if (pathEdges.contains(Edge.at(e.x() + 1, e.y(), Edge.V))) {
            raster.set(2 * e.x() + 1 + 1, y, '\u2557');
          }

          if (e.y() > 0 && pathEdges.contains(Edge.at(e.x(), e.y() - 1, Edge.V))) {
            raster.set(2 * e.x(), y, '\u255A');
          }

          if (e.y() > 0 && pathEdges.contains(Edge.at(e.x() + 1, e.y() - 1, Edge.V))) {
            raster.set(2 * e.x() + 1 + 1, y, '\u255D');
          }

        } else {

          int x = 2 * e.x();

          raster.set(x, 2 * e.y() + 1, '\u2551');

          if (pathEdges.contains(Edge.at(e.x(), e.y() + 1, Edge.V))) {
            raster.set(x, 2 * e.y() + 1 + 1, '\u2551');
          }
        }
      }
    );

    return raster;
  }
}
