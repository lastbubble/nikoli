package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.lastbubble.nikoli.Cell;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BridgeTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  private Bridge bridge;

  private static final Cell end = Cell.at(naturalNumber(), naturalNumber());

  @Test public void invalid_sameCell() {

    thrown.expect(IllegalArgumentException.class);

    whenConnectingTo(Cell.at(end.x(), end.y()));
  }

  @Test public void invalid_diagonal() {

    thrown.expect(IllegalArgumentException.class);

    whenConnectingTo(Cell.at(naturalNumberOtherThan(end.x()), naturalNumberOtherThan(end.y())));
  }

  @Test public void initialWeight() {

    whenConnectingTo(Cell.at(end.x(), naturalNumberOtherThan(end.y())));

    assertThat(bridge.weight(), is(1));
  }

  @Test public void incrementWeight() {

    whenConnectingTo(Cell.at(end.x(), naturalNumberOtherThan(end.y())));

    Bridge otherBridge = bridge.incrementWeight();

    assertThat(otherBridge.weight(), is(bridge.weight() + 1));
  }

  @Test public void span_whenVertical() {

    Cell otherEnd = Cell.at(end.x(), naturalNumberOtherThan(end.y()));

    whenConnectingTo(otherEnd);

    assertTrue(bridge.span().allMatch(c -> c.x() == end.x()));

    List<Cell> spannedCells = bridge.span().collect(Collectors.toList());
    assertThat(spannedCells.size(), is(Math.abs(end.y() - otherEnd.y()) + 1));
    for (int y = spannedCells.get(0).y(), i = 1; i < spannedCells.size(); i++) {
      assertThat(spannedCells.get(i).y(), is(y + i));
    }

    Bridge reverseBridge = Bridge.connecting(otherEnd, end);
    assertThat(spannedCells, is(reverseBridge.span().collect(Collectors.toList())));
  }

  @Test public void span_whenHorizontal() {

    Cell otherEnd = Cell.at(naturalNumberOtherThan(end.x()), end.y());

    whenConnectingTo(otherEnd);

    assertTrue(bridge.span().allMatch(c -> c.y() == end.y()));

    List<Cell> spannedCells = bridge.span().collect(Collectors.toList());
    assertThat(spannedCells.size(), is(Math.abs(end.x() - otherEnd.x()) + 1));
    for (int x = spannedCells.get(0).x(), i = 1; i < spannedCells.size(); i++) {
      assertThat(spannedCells.get(i).x(), is(x + i));
    }

    Bridge reverseBridge = Bridge.connecting(otherEnd, end);
    assertThat(spannedCells, is(reverseBridge.span().collect(Collectors.toList())));
  }

  private void whenConnectingTo(Cell otherEnd) {

    bridge = Bridge.connecting(end, otherEnd);
  }
}
