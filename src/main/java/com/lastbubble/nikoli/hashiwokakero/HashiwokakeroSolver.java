package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.logic.Formula.*;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.logic.Formula;
import com.lastbubble.nikoli.solver.Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashiwokakeroSolver extends Solver<Bridge> {

  private final Grid<Integer> grid;

  public HashiwokakeroSolver(Grid<Integer> grid) {

    this.grid = grid;

    Set<Bridge> allBridges = allBridges();

    Map<Cell, List<Bridge>> bridgesForCell = new HashMap<>();

    for (Bridge bridge : allBridges) {

      varFor(bridge);

      bridgesForCell.computeIfAbsent(bridge.oneEnd(), k -> new ArrayList<>()).add(bridge);

      bridgesForCell.computeIfAbsent(bridge.otherEnd(), k -> new ArrayList<>()).add(bridge);

      if (bridge.weight() > 1) { add(implies(varFor(bridge), varFor(bridge.withWeight(1)))); }

      Stream<Bridge> crossingBridges;

      if (bridge.oneEnd().y() == bridge.otherEnd().y()) {

        crossingBridges = allBridges.stream()
          .filter(x ->
            x.oneEnd().y() < bridge.oneEnd().y() &&
            x.otherEnd().y() > bridge.oneEnd().y() &&
            x.oneEnd().x() > bridge.oneEnd().x() &&
            x.oneEnd().x() < bridge.otherEnd().x()
        );

      } else {

        crossingBridges = allBridges.stream()
          .filter(x ->
            x.oneEnd().x() < bridge.oneEnd().x() &&
            x.otherEnd().x() > bridge.oneEnd().x() &&
            x.oneEnd().y() > bridge.oneEnd().y() &&
            x.oneEnd().y() < bridge.otherEnd().y()
          );
      }

      crossingBridges.forEach(x -> add(implies(varFor(bridge), not(varFor(x)))));
    }

    grid.filledCells().forEach(cell -> {
      addExactly(grid.valueAt(cell).orElse(0), bridgesForCell.getOrDefault(cell, new ArrayList<Bridge>()).stream());
    });
  }

  Set<Bridge> allBridges() {

    Set<Bridge> bridges = new HashSet<Bridge>();

    grid.filledCells().sorted().forEach(oneEnd -> {

      int oneValue = grid.valueAt(oneEnd).orElse(0);

      Consumer<? super Cell> addBridges = otherEnd -> {
        int otherValue = grid.valueAt(otherEnd).orElse(0);
        if (oneValue != 1 || otherValue != 1) {
          bridges.add(Bridge.connecting(oneEnd, otherEnd));
        }
        if (oneValue != 1 && otherValue != 1) {
          bridges.add(Bridge.connecting(oneEnd, otherEnd).incrementWeight());
        }
      };

      grid.filledCells()
        .filter(c -> oneEnd.y() == c.y() && oneEnd.x() < c.x())
        .sorted()
        .findFirst()
        .ifPresent(addBridges);

      grid.filledCells()
        .filter(c -> oneEnd.x() == c.x() && oneEnd.y() < c.y())
        .sorted()
        .findFirst()
        .ifPresent(addBridges);
    });

    return bridges;
  }

  @Override protected Set<Bridge> canonicalize(Set<Bridge> bridges) {

    return bridges.stream()
      .filter(x -> !(x.weight() == 1 && bridges.contains(x.withWeight(2))))
      .collect(Collectors.toSet());
  }
}
