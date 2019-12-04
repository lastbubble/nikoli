package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.logic.Formula.*;

import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.logic.Formula;
import com.lastbubble.nikoli.solver.Solver;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TakegakiSolver extends Solver<Edge> {

  private final Grid<Integer> grid;

  public TakegakiSolver(Grid<Integer> grid) {

    this.grid = grid;

    grid.cells().forEach(cell ->
      Edge.edgesOf(cell).forEach(edge ->
        Stream.of(true, false).forEach(up -> {
          add(edgeConnectionsFor(edge, up));
          grid.valueAt(cell).ifPresent(x -> addExactly(x, Edge.edgesOf(cell)));
        })
      )
    );
  }

  private Formula edgeConnectionsFor(Edge edge, boolean forward) {

    List<Var<Edge>> neighborVars = edge.neighbors(forward)
      .filter(validEdge())
      .map(this::varFor)
      .collect(Collectors.toList());

    Var<Edge> a = varFor(edge);

    if (neighborVars.size() == 1) {

      Var<Edge> b = neighborVars.get(0);

      // (a => b) & (b => a)
      return allOf(
        anyOf(not(a), b),
        anyOf(a, not(b))
      );

    } else if (neighborVars.size() == 2) {

      Var<Edge> b = neighborVars.get(0);
      Var<Edge> c = neighborVars.get(1);

      // a => ((b & ~c) | (~b & c))
      return allOf(
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), b, c)
      );

    } else {

      Var<Edge> b = neighborVars.get(0);
      Var<Edge> c = neighborVars.get(1);
      Var<Edge> d = neighborVars.get(2);

      // a => ((b & ~c & ~d) | (~b & c & ~d) | (~b & ~c & d))
      return allOf(
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), not(b), not(d)),
        anyOf(not(a), not(c), not(d)),
        anyOf(not(a), b, c, d)
      );
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

  @Override protected boolean acceptable(Stream<Edge> solution) {

    Set<Edge> edgeSet = solution.collect(Collectors.toSet());

    List<List<Edge>> loops = Loops.findLoopsIn(edgeSet.stream());

    if (loops.size() == 1) { return true; }

    for (List<Edge> invalidLoop : loops) {

      add(anyOf(invalidLoop.stream().map(this::varFor).map(Formula::not)));
    }

    return false;
  }
}
