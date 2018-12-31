package com.lastbubble.nikoli.hashiwokakero;

import com.lastbubble.nikoli.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LinksFinder {

  private final Set<Bridge> bridges;

  private final List<Set<Bridge>> links = new ArrayList<>();

  private final Set<Cell> cellsToTravel = new HashSet<>();

  private final Set<Cell> traveledCells = new HashSet<>();

  private Set<Bridge> link;

  LinksFinder(Stream<Bridge> bridges) { this.bridges = bridges.collect(Collectors.toSet()); }

  List<Set<Bridge>> find() {

    while (bridges.size() > 0) {

      cellsToTravel.clear();
      traveledCells.clear();

      link = new HashSet<Bridge>();
      links.add(link);

      cellsToTravel.add(bridges.iterator().next().oneEnd());

      while (cellsToTravel.size() > 0) { travel(cellsToTravel.iterator().next()); }
    }

    return links;
  }

  private void travel(Cell cell) {

    for (Bridge bridge : bridgesFor(cell)) {

      link.add(bridge);

      bridges.remove(bridge);

      cellsToTravel.add(bridge.oppositeFrom(cell));
    }

    cellsToTravel.remove(cell);
    traveledCells.add(cell);
  }

  private Set<Bridge> bridgesFor(Cell cell) {

    return bridges.stream()
      .filter(x -> cell.equals(x.oneEnd()) || cell.equals(x.otherEnd()))
      .collect(Collectors.toSet());
  }
}
