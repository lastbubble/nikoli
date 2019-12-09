package com.lastbubble.nikoli;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class FilledCellTest {

  @Test public void assign() {

    Cell cell = Cell.at(naturalNumber(), naturalNumber());
    int value = naturalNumber();

    FilledCell<Integer> filledCell = FilledCell.using(cell, value);

    assertThat(filledCell.cell(), is(cell));
    assertThat(filledCell.value(), is(value));
  }
}
