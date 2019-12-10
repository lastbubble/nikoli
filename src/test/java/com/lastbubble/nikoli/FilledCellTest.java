package com.lastbubble.nikoli;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class FilledCellTest {

  private FilledCell<String> filledCell;

  @Test public void assign() {

    Cell cell = randomCell();
    String value = String.valueOf(naturalNumber());

    filledCell = FilledCell.using(cell, value);

    assertThat(filledCell.cell(), is(cell));
    assertThat(filledCell.value(), is(value));
  }

  @Test public void hashCodeImplemented() {

    filledCell = FilledCell.using(randomCell(), String.valueOf(naturalNumber()));

    assertThat(filledCell.hashCode(), is(FilledCell.using(filledCell.cell(), filledCell.value()).hashCode()));
  }

  @Test public void equalsImplemented() {

    Cell cell = randomCell();
    Cell otherCell = Cell.at(naturalNumberOtherThan(cell.x()), naturalNumberOtherThan(cell.y()));

    filledCell = FilledCell.using(cell, String.valueOf(naturalNumber()));

    assertThat(filledCell, equalTo(filledCell));
    assertThat(filledCell, not(equalTo( new Object())));
    assertThat(filledCell, not(equalTo(FilledCell.using(otherCell, filledCell.value()))));
    assertThat(filledCell, not(equalTo(FilledCell.using(cell, "not_a_number"))));
    assertThat(filledCell, equalTo(FilledCell.using(filledCell.cell(), filledCell.value())));
  }

  private static Cell randomCell() { return Cell.at(naturalNumber(), naturalNumber()); }
}
