package com.lastbubble.nikoli;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class GridTest {

  private Grid.Builder<String> builder = Grid.<String>builder();

  private Grid<String> grid;

  @Test public void defaultGrid() {

    grid = builder.build();

    assertThat(grid.width(), is(1));
    assertThat(grid.height(), is(1));
  }

  @Test public void assignMaxCell() {

    Cell cell = randomCell();

    grid = builder.withMaxCell(cell).build();

    assertThat(grid.width(), is(cell.x() + 1));
    assertThat(grid.height(), is(cell.y() + 1));
  }

  @Test public void assignMaxCell_whenNull() {

    grid = builder.withMaxCell(null).build();

    assertThat(grid.width(), is(1));
    assertThat(grid.height(), is(1));
  }

  @Test public void streamOfCells() {

    grid = builder.withMaxCell(randomCell()).build();

    int numberOfCells = grid.width() * grid.height();
    assertThat(grid.cells().distinct().count(), is((long) numberOfCells));
  }

  @Test public void expandToIncludeAssignedCells() {

    Cell maxCell = randomCell();

    Cell assignedCell = Cell.at(maxCell.x() + positiveNumber(), maxCell.y() + positiveNumber());

    grid = builder.withMaxCell(maxCell).assign(assignedCell, "a").build();

    assertThat(grid.width(), is(assignedCell.x() + 1));
    assertThat(grid.height(), is(assignedCell.y() + 1));
  }

  @Test public void assignValueToCell() {

    Cell cell = randomCell();
    String a = "a";

    grid = builder.assign(cell, a).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    int cellsWithoutValues = (int) grid.cells().filter(c -> grid.valueAt(c).isPresent() == false).count();
    assertThat(cellsWithoutValues + 1, is(grid.width() * grid.height()));
  }

  @Test public void reuseBuilder() {

    Cell cell = randomCell();
    String a = "a";

    grid = builder.assign(cell, a).build();

    Grid<String> otherGrid = builder.assign(cell, null).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    assertThat(otherGrid.valueAt(cell).isPresent(), is(false));
  }

  @Test public void assignMultipleValuesToCell() {

    Cell cell = randomCell();
    String a = "a";
    String b = "b";

    grid = builder.assign(cell, a).assign(cell, b).build();

    assertThat(grid.valueAt(cell).orElse(null), is(b));
  }

  @Test public void assignNullValueToCell() {

    Cell cell = randomCell();
    String a = "a";

    grid = builder.assign(cell, a).assign(cell, null).build();

    assertThat(grid.valueAt(cell).isPresent(), is(false));
  }

  @Test public void assignMultipleValuesToMultipleCells() {

    Cell cell1 = randomCell();
    String a = "a";

    Cell cell2 = Cell.at(naturalNumberOtherThan(cell1.x()), naturalNumberOtherThan(cell1.y()));
    String b = "b";

    grid = builder.assign(cell1, a).assign(cell2, b).build();

    assertThat(grid.valueAt(cell1).orElse(null), is(a));
    assertThat(grid.valueAt(cell2).orElse(null), is(b));
  }

  private static Cell randomCell() {

    int x = naturalNumber();
    return Cell.at(x, naturalNumberOtherThan(x));
  }
}
