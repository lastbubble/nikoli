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
    printDotLine(grid.height());

    for (int y = grid.height() - 1; y >= 0; y--) {

      final int row = y;

      IntStream.range(0, grid.width()).forEach(x -> {
        buf
          .append(charForEdge(Edge.at(x, row, Edge.V)))
          .append(charForCell(Cell.at(x, row)));
      });
      buf.append(charForEdge(Edge.at(grid.width(), y, Edge.V)));
      printLine();

      printDotLine(row);
    }
  }

  private void printDotLine(int y) {

    buf.append(charForDot(0, y));
    IntStream.range(0, grid.width()).forEach(x ->
      buf
        .append(charForEdge(Edge.at(x, y, Edge.H)))
        .append(charForDot(x + 1, y))
    );
    printLine();
  }

  private char charForDot(int x, int y) {

    boolean topInPath = pathEdges.contains(Edge.at(x, y, Edge.V));
    boolean leftInPath = (x > 0) && pathEdges.contains(Edge.at(x - 1, y, Edge.H));
    boolean bottomInPath = (y > 0) && pathEdges.contains(Edge.at(x, y - 1, Edge.V));
    boolean rightInPath = pathEdges.contains(Edge.at(x, y, Edge.H));

    if (topInPath) {

      if (leftInPath) { return '\u255D'; }
      else if (bottomInPath) { return '\u2551'; }
      else if (rightInPath) { return '\u255A'; }

    } else if (leftInPath) {

      if (bottomInPath) { return '\u2557'; }
      else if (rightInPath) { return '\u2550'; }

    } else if (bottomInPath && rightInPath) {

      return '\u2554';
    }

    return DOT;
  }

  private char charForEdge(Edge edge) {

    return pathEdges.contains(edge) ? (edge.orientation() == Edge.H ? '\u2550' : '\u2551') : ' ';
  }

  private char charForCell(Cell cell) {

    return grid.valueAt(cell).map(n -> (char) ('0' + n)).orElse(' ');
  }

  private void printLine() { System.out.println(buf); buf.setLength(0); }
}
