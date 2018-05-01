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
