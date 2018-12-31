package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.hamcrest.Matchers.*;
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

  @Test public void invalid_sameCell() {

    thrown.expect(IllegalArgumentException.class);

    whenConnectingTo(Cell.at(end.x(), end.y()));
  }

  @Test public void invalid_diagonal() {

    thrown.expect(IllegalArgumentException.class);

    whenConnectingTo(Cell.at(naturalNumberOtherThan(end.x()), naturalNumberOtherThan(end.y())));
  }

  @Test public void initialWeight() {

    whenConnectingTo(cellInSameColumn());

    assertThat(bridge.weight(), is(1));
  }

  @Test public void withWeight() {

    whenConnectingTo(cellInSameColumn());

    int weight = 2;
    Bridge weightedBridge = bridge.withWeight(weight);

    assertThat(bridge.weight(), is(1));
    assertThat(weightedBridge.weight(), is(weight));
  }

  @Test public void incrementWeight() {

    whenConnectingTo(cellInSameColumn());

    Bridge otherBridge = bridge.incrementWeight();

    assertThat(otherBridge.weight(), is(bridge.weight() + 1));
  }

  @Test public void span_whenVertical() {

    Cell otherEnd = cellInSameColumn();

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

    Cell otherEnd = cellInSameRow();

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

  @Test public void oppositeFrom() {

    Cell otherEnd = cellInSameRow();

    whenConnectingTo(otherEnd);

    assertThat(bridge.oppositeFrom(end), is(otherEnd));
    assertThat(bridge.oppositeFrom(otherEnd), is(end));
  }

  @Test public void oppositeFrom_notEndpoint() {

    Cell otherEnd = cellInSameRow();

    whenConnectingTo(otherEnd);

    thrown.expect(IllegalArgumentException.class);

    bridge.oppositeFrom(Cell.at(end.x() + 1, end.y() + 1));
  }

  @Test public void crosses() {

    bridge = Bridge.connecting(Cell.at(2, 2), Cell.at(2, 4));

    assertBridgeCrosses(false, Cell.at(0, 0), Cell.at(4, 0));
    assertBridgeCrosses(false, Cell.at(0, 3), Cell.at(1, 3));
    assertBridgeCrosses(false, Cell.at(0, 4), Cell.at(4, 4));
    assertBridgeCrosses(true, Cell.at(1, 3), Cell.at(3, 3));
  }

  private void assertBridgeCrosses(boolean expected, Cell end1, Cell end2) {

    Bridge otherBridge = Bridge.connecting(end1, end2);

    assertThat(bridge.crosses(otherBridge), is(expected));
    assertThat(otherBridge.crosses(bridge), is(expected));
  }

  @Test public void hashCodeImplemented() {

    Cell otherEnd = cellInSameRow();

    whenConnectingTo(otherEnd);

    assertThat(bridge.hashCode(), is(Bridge.connecting(end, otherEnd).hashCode()));
  }

  @Test public void equalsImplemented() {

    Cell otherEnd = cellInSameRow();
    Cell anotherEndInSameRow = Cell.at(end.x() + otherEnd.x() + 1, end.y());

    whenConnectingTo(otherEnd);

    assertThat(bridge, equalTo(bridge));
    assertThat(bridge, not(equalTo( new Object())));
    assertThat(bridge, not(equalTo(Bridge.connecting(end, cellInSameColumn()))));
    assertThat(bridge, not(equalTo(Bridge.connecting(end, anotherEndInSameRow))));
    assertThat(bridge, not(equalTo(bridge.incrementWeight())));
    assertThat(bridge, equalTo(Bridge.connecting(end, otherEnd)));
    assertThat(bridge, equalTo(Bridge.connecting(otherEnd, end)));
  }

  @Test public void toStringImplemented() {

    bridge = Bridge.connecting(Cell.at(0, 1), Cell.at(0, 3));

    assertThat(bridge.toString(), is("Bridge[(0,1)-(0,3)]"));
    assertThat(bridge.incrementWeight().toString(), is("Bridge[(0,1)=(0,3)]"));
  }

  private void whenConnectingTo(Cell otherEnd) {

    bridge = Bridge.connecting(end, otherEnd);
  }

  private static final Cell end = Cell.at(naturalNumber(), naturalNumber());

  private static Cell cellInSameRow() { return Cell.at(naturalNumberOtherThan(end.x()), end.y()); }

  private static Cell cellInSameColumn() { return Cell.at(end.x(), naturalNumberOtherThan(end.y())); }
}
