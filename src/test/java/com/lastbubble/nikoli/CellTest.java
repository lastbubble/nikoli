package com.lastbubble.nikoli;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class CellTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test public void negativeXnotAllowed() {

    thrown.expect(IllegalArgumentException.class);

    Cell.at(negativeNumber(), naturalNumber());
  }

  @Test public void negativeYnotAllowed() {

    thrown.expect(IllegalArgumentException.class);

    Cell.at(naturalNumber(), negativeNumber());
  }

  @Test public void assignXandY() {

    int x = naturalNumber();
    int y = naturalNumberOtherThan(x);

    Cell cell = Cell.at(x, y);

    assertThat(cell.x(), is(x));
    assertThat(cell.y(), is(y));
  }

  @Test public void neighbors_whenCorner() {

    Cell cell = Cell.at(0, 0);

    assertThat(cell.above().get(), is(Cell.at(0, 1)));
    assertThat(cell.toLeft().isPresent(), is(false));
    assertThat(cell.below().isPresent(), is(false));
    assertThat(cell.toRight().get(), is(Cell.at(1, 0)));
  }

  @Test public void neighbors_whenLeftEdge() {

    int y = positiveNumber();

    Cell cell = Cell.at(0, y);

    assertThat(cell.above().get(), is(Cell.at(0, y + 1)));
    assertThat(cell.toLeft().isPresent(), is(false));
    assertThat(cell.below().get(), is(Cell.at(0, y - 1)));
    assertThat(cell.toRight().get(), is(Cell.at(1, y)));
  }

  @Test public void neighbors_whenBottomEdge() {

    int x = positiveNumber();

    Cell cell = Cell.at(x, 0);

    assertThat(cell.above().get(), is(Cell.at(x, 1)));
    assertThat(cell.toLeft().get(), is(Cell.at(x - 1, 0)));
    assertThat(cell.below().isPresent(), is(false));
    assertThat(cell.toRight().get(), is(Cell.at(x + 1, 0)));
  }

  @Test public void neighbors_whenMiddle() {

    int x = positiveNumber();
    int y = x + 1;

    Cell cell = Cell.at(x, y);

    assertThat(cell.above().get(), is(Cell.at(x, y + 1)));
    assertThat(cell.toLeft().get(), is(Cell.at(x - 1, y)));
    assertThat(cell.below().get(), is(Cell.at(x, y - 1)));
    assertThat(cell.toRight().get(), is(Cell.at(x + 1, y)));
  }

  @Test public void hashCodeImplemented() {

    Cell cell = randomCell();
    assertThat(cell.hashCode(), is(Cell.at(cell.x(), cell.y()).hashCode()));
  }

  @Test public void equalsImplemented() {

    Cell cell = randomCell();

    assertThat(cell, equalTo(cell));
    assertThat(cell, not(equalTo( new Object())));
    assertThat(cell, not(equalTo(Cell.at(cell.x(), naturalNumberOtherThan(cell.y())))));
    assertThat(cell, not(equalTo(Cell.at(naturalNumberOtherThan(cell.x()), cell.y()))));
    assertThat(cell, equalTo(Cell.at(cell.x(), cell.y())));
  }

  private Cell randomCell() { return Cell.at(naturalNumber(), naturalNumber()); }
}
