package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.logic.Formula.*;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.solver.Solver;

import java.util.ArrayList;
import java.util.HashMap;
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

    Map<Cell, List<Bridge>> bridgesForCell = new HashMap<>();

    allBridges().forEach(bridge -> {
      varFor(bridge);
      bridgesForCell.computeIfAbsent(bridge.oneEnd(), k -> new ArrayList<>()).add(bridge);
      bridgesForCell.computeIfAbsent(bridge.otherEnd(), k -> new ArrayList<>()).add(bridge);
      if (bridge.weight() > 1) {
        add(anyOf(not(varFor(bridge)), varFor(Bridge.connecting(bridge.oneEnd(), bridge.otherEnd()))));
      }
    });

    grid.filledCells().forEach(cell -> {
      addExactly(grid.valueAt(cell).orElse(0), bridgesForCell.getOrDefault(cell, new ArrayList<Bridge>()).stream());
    });
  }

  Stream<Bridge> allBridges() {

    List<Bridge> bridges = new ArrayList<Bridge>();

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

    return bridges.stream();
  }

  @Override protected Set<Bridge> canonicalize(Set<Bridge> bridges) {

    return bridges.stream().filter(bridge -> {
        return !(
          bridge.weight() == 1 &&
          bridges.contains(Bridge.connecting(bridge.oneEnd(), bridge.otherEnd()).incrementWeight())
        );
      }).collect(Collectors.toSet());
  }
}
