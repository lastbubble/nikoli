package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.logic.Formula.*;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.logic.Formula;
import com.lastbubble.nikoli.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TakegakiSolver extends Solver {

  private final Grid<Integer> grid;

  public TakegakiSolver(Grid<Integer> grid) {

    this.grid = grid;

    grid.cells().forEach(cell ->
      Edge.edgesOf(cell).forEach(edge ->
        Stream.of(true, false).forEach(up -> {
          add(edgeConnectionsFor(edge, up));
          if (grid.valueAt(cell).isPresent()) {
            add(pathEdgesForCell(cell, grid.valueAt(cell).get()));
          }
        })
      )
    );
  }

  private Formula edgeConnectionsFor(Edge edge, boolean forward) {

    List<Var> neighborVars = edge.neighbors(forward)
      .filter(validEdge())
      .map(e -> vars.add(e))
      .collect(Collectors.toList());

    Var a = vars.add(edge);

    if (neighborVars.size() == 1) {

      Var b = neighborVars.get(0);

      // (a => b) & (b => a)
      return allOf(
        anyOf(not(a), b),
        anyOf(a, not(b))
      );

    } else if (neighborVars.size() == 2) {

      Var b = neighborVars.get(0);
      Var c = neighborVars.get(1);

      // a => ((b & ~c) | (~b & c))
      return allOf(
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), b, c)
      );

    } else {

      Var b = neighborVars.get(0);
      Var c = neighborVars.get(1);
      Var d = neighborVars.get(2);

      // a => ((b & ~c & ~d) | (~b & c & ~d) | (~b & ~c & d))
      return allOf(
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), not(b), not(d)),
        anyOf(not(a), not(c), not(d)),
        anyOf(not(a), b, c, d)
      );
    }
  }

  private Formula pathEdgesForCell(Cell cell, int count) {

    List<Var> edgeVars = Edge.edgesOf(cell).map(e -> vars.add(e)).collect(Collectors.toList());
    Var a = edgeVars.get(0);
    Var b = edgeVars.get(1);
    Var c = edgeVars.get(2);
    Var d = edgeVars.get(3);

    switch (count) {

      case 0:
        return allOf(
          not(a),
          not(b),
          not(c),
          not(d)
        );

      case 1:
        // (a | b | c | d) & (a => ~b & ~c ~d) & (b => ~a & ~c & ~d) & (c => ~a & ~b & ~d) & (d => ~a & ~b & ~c)
        return allOf(
          anyOf(a, b, c, d),
          anyOf(not(a), not(b)),
          anyOf(not(a), not(c)),
          anyOf(not(a), not(d)),
          anyOf(not(b), not(c)),
          anyOf(not(b), not(d)),
          anyOf(not(c), not(d))
        );

      case 2:
        // (a | b | c | d) &
        //   a => (b | c | d) &
        //   b => (a | c | d) &
        //   c => (a | b | d) &
        //   d => (a | b | c) &
        //   (a & b) => (~c & ~d) &
        //   (a & c) => (~b & ~d) &
        //   (a & d) => (~b & ~c) &
        //   (b & c) => (~a & ~d) &
        //   (b & d) => (~a & ~c) &
        //   (c & d) => (~a & ~b)
        return allOf(
          anyOf(a, b, c, d),
          anyOf(not(a), b, c, d),
          anyOf(a, not(b), c, d),
          anyOf(a, b, not(c), d),
          anyOf(a, b, c, not(d)),
          anyOf(not(a), not(b), not(c)),
          anyOf(not(a), not(b), not(d)),
          anyOf(not(a), not(c), not(d)),
          anyOf(not(b), not(c), not(d))
        );

      case 3:
        // (~a | ~b | ~c | ~d) & (~a => b & c d) & (~b => a & c & d) & (~c => a & b & d) & (~d => a & b & c)
        return allOf(
          anyOf(not(a), not(b), not(c), not(d)),
          anyOf(a, b),
          anyOf(a, c),
          anyOf(a, d),
          anyOf(b, c),
          anyOf(b, d),
          anyOf(c, d)
        );

      default:
        throw new IllegalArgumentException("Invalid cell value: " + count);
    }
  }

  private Predicate<Edge> validEdge() {
    return e -> {
      int x = e.x();
      int y = e.y();
      Edge.Orientation o = e.orientation();
      int xMax = (o == Edge.V) ? grid.width() : grid.width() - 1;
      int yMax = (o == Edge.H) ? grid.height() : grid.height() - 1;
      return (x >= 0 && x <= xMax && y >= 0 && y <= yMax);
    };
  }

  @Override protected boolean acceptable(Set<Var> solutionVars) {

    Set<Edge> pathEdges = solutionVars.stream().map(var -> (Edge) var.data()).collect(Collectors.toSet());

    Edge firstEdge = pathEdges.stream().findFirst().orElse(null);

    if (firstEdge != null) {

      List<Edge> path = new ArrayList<>();

      Edge currentEdge = firstEdge;

      pathEdges.remove(currentEdge);

      while (pathEdges.size() > 0) {

        path.add(currentEdge);

        Edge nextEdge = findConnectedEdge(currentEdge, pathEdges);

        if (nextEdge == null) {

          add(anyOf(path.stream().map(vars::add).map(v -> not(v))));
          return false;
        }

        pathEdges.remove(nextEdge);

        if (pathEdges.isEmpty()) { return nextEdge.isConnected(firstEdge); }

        currentEdge = nextEdge;
      }
    }

    return false;
  }

  private Edge findConnectedEdge(Edge edge, Set<Edge> edges) {

    for (Edge e : edges) { if (edge.isConnected(e)) { return e; } }

    return null;
  }
}
