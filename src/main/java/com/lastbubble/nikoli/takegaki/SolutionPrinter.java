package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionPrinter {

  private static final char DOT = '\u00B7';

  private final Grid<Integer> grid;
  private final Set<Edge> pathEdges;

  private final StringBuilder buf = new StringBuilder();

  public SolutionPrinter(Grid<Integer> grid, Stream<Edge> pathEdges) {

    this.grid = grid;
    this.pathEdges = pathEdges.collect(Collectors.toSet());
  }

  public void print() {

    // first line
    buf.append(DOT);
    IntStream.range(0, grid.width()).forEach(x ->
      buf.append(pathEdges.contains(Edge.at(x, grid.height(), Edge.H)) ? '-' : ' ').append(DOT)
    );
    printLine();

    for (int y = grid.height() - 1; y >= 0; y--) {

      final int row = y;

      IntStream.range(0, grid.width()).forEach(x -> {
        buf
          .append(charForEdge(Edge.at(x, row, Edge.V)))
          .append(charForCell(Cell.at(x, row)));
      });
      buf.append(charForEdge(Edge.at(grid.width(), y, Edge.V)));
      printLine();

      buf.append(DOT);
      IntStream.range(0, grid.width()).forEach(x ->
        buf.append(charForEdge(Edge.at(x, row, Edge.H))).append(DOT)
      );
      printLine();
    }
  }

  private char charForEdge(Edge edge) {

    return pathEdges.contains(edge) ? (edge.orientation() == Edge.H ? '-' : '|') : ' ';
  }

  private char charForCell(Cell cell) {

    return grid.valueAt(cell).map(n -> (char) ('0' + n)).orElse(' ');
  }

  private void printLine() { System.out.println(buf); buf.setLength(0); }
}
