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

      if (link.contains(bridge) == false) {

        link.add(bridge);
        bridges.remove(bridge);

        Cell oppositeEnd = oppositeEnd(bridge, cell);

        if (traveledCells.contains(oppositeEnd) == false) {

          cellsToTravel.add(oppositeEnd);
        }
      }
    }

    cellsToTravel.remove(cell);
    traveledCells.add(cell);
  }

  private Set<Bridge> bridgesFor(Cell cell) {

    return bridges.stream()
      .filter(x -> cell.equals(x.oneEnd()) || cell.equals(x.otherEnd()))
      .collect(Collectors.toSet());
  }

  private static Cell oppositeEnd(Bridge bridge, Cell cell) {

    if (cell.equals(bridge.oneEnd())) { return bridge.otherEnd(); }
    else if (cell.equals(bridge.otherEnd())) { return bridge.oneEnd(); }

    throw new IllegalArgumentException("cell " + cell + " is not endpoint of " + bridge);
  }
}
