package com.lastbubble.nikoli;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

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
    assertThat(grid.cells().filter(c -> c.x() >= grid.width() || c.y() >= grid.height()).count(), is(0L));
  }

  @Test public void expandToIncludeAssignedCells() {

    Cell maxCell = cell;

    Cell assignedCell = Cell.at(maxCell.x() + positiveNumber(), maxCell.y() + positiveNumber());

    grid = builder.withMaxCell(maxCell).assign(assignedCell, "a").build();

    assertThat(grid.width(), is(assignedCell.x() + 1));
    assertThat(grid.height(), is(assignedCell.y() + 1));
  }

  @Test public void assignValueToCell() {

    grid = builder.assign(cell, a).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    int cellsWithoutValues = (int) grid.cells().filter(c -> grid.valueAt(c).isPresent() == false).count();
    assertThat(cellsWithoutValues + 1, is(grid.width() * grid.height()));
  }

  @Test public void reuseBuilder() {

    grid = builder.assign(cell, a).build();

    Grid<String> otherGrid = builder.assign(cell, null).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    assertThat(otherGrid.valueAt(cell).isPresent(), is(false));
  }

  @Test public void assignMultipleValuesToCell() {

    grid = builder.assign(cell, a).assign(cell, b).build();

    assertThat(grid.valueAt(cell).orElse(null), is(b));
  }

  @Test public void assignNullValueToCell() {

    grid = builder.assign(cell, a).assign(cell, null).build();

    assertThat(grid.valueAt(cell).isPresent(), is(false));
  }

  @Test public void assignMultipleValuesToMultipleCells() {

    grid = builder.assign(cell, a).assign(otherCell, b).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    assertThat(grid.valueAt(otherCell).orElse(null), is(b));
  }

  @Test public void createBuilderFrom() {

    grid = builder.assign(cell, a).build();

    Grid<String> otherGrid = grid.toBuilder().assign(otherCell, b).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    assertThat(otherGrid.valueAt(cell).orElse(null), is(a));

    assertThat(grid.valueAt(otherCell).isPresent(), is(false));
    assertThat(otherGrid.valueAt(otherCell).orElse(null), is(b));
  }

  @Test public void createBuilderFromAndReplaceCellValue() {

    grid = builder.assign(cell, a).build();

    Grid<String> otherGrid = grid.toBuilder().assign(cell, b).build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    assertThat(otherGrid.valueAt(cell).orElse(null), is(b));
  }

  @Test public void buildUsingFilledCells() {

    grid = builder
      .fillUsing(Stream.of(
          FilledCell.using(cell, a),
          FilledCell.using(otherCell, b)
        )
      )
      .build();

    assertThat(grid.valueAt(cell).orElse(null), is(a));
    assertThat(grid.valueAt(otherCell).orElse(null), is(b));
  }

  @Test public void isCompletelyFilled() {

    builder.withMaxCell(Cell.at(1,1));

    builder.assign(Cell.at(1,0), a);
    assertThat(builder.build().isCompletelyFilled(), is(false));

    builder.assign(Cell.at(1,0), a).assign(Cell.at(1,1), a);
    assertThat(builder.build().isCompletelyFilled(), is(false));

    builder.assign(Cell.at(1,0), a).assign(Cell.at(1,1), a).assign(Cell.at(0,0), a);
    assertThat(builder.build().isCompletelyFilled(), is(false));

    builder.assign(Cell.at(1,0), a).assign(Cell.at(1,1), a).assign(Cell.at(0,0), a).assign(Cell.at(0,1), a);
    assertThat(builder.build().isCompletelyFilled(), is(true));
  }

  @Test public void rowContaining() {

    grid = builder.withMaxCell(Cell.at(2,2)).build();

    assertThatCellsAre(grid.rowContaining(Cell.at(0,0)),
      Cell.at(0,0), Cell.at(1,0), Cell.at(2,0)
    );

    assertThatCellsAre(grid.rowContaining(Cell.at(1,0)),
      Cell.at(0,0), Cell.at(1,0), Cell.at(2,0)
    );

    assertThatCellsAre(grid.rowContaining(Cell.at(1,1)),
      Cell.at(0,1), Cell.at(1,1), Cell.at(2,1)
    );

    assertThatCellsAre(grid.rowContaining(Cell.at(2,2)),
      Cell.at(0,2), Cell.at(1,2), Cell.at(2,2)
    );
  }

  @Test public void columnContaining() {

    grid = builder.withMaxCell(Cell.at(2,2)).build();

    assertThatCellsAre(grid.columnContaining(Cell.at(0,0)),
      Cell.at(0,0), Cell.at(0,1), Cell.at(0,2)
    );

    assertThatCellsAre(grid.columnContaining(Cell.at(0,1)),
      Cell.at(0,0), Cell.at(0,1), Cell.at(0,2)
    );

    assertThatCellsAre(grid.columnContaining(Cell.at(1,1)),
      Cell.at(1,0), Cell.at(1,1), Cell.at(1,2)
    );

    assertThatCellsAre(grid.columnContaining(Cell.at(2,2)),
      Cell.at(2,0), Cell.at(2,1), Cell.at(2,2)
    );
  }

  private void assertThatCellsAre(Stream<Cell> stream, Cell... expected) {
    assertThat(stream.collect(toSet()), containsInAnyOrder(expected));
  }

  @Test public void anyCellsContain() {

    grid = builder.assign(Cell.at(1,0), a).assign(Cell.at(0,1), b).build();

    assertThat(grid.valueFoundIn(grid.rowContaining(Cell.at(0,0)), a), is(true));
    assertThat(grid.valueFoundIn(grid.rowContaining(Cell.at(0,1)), a), is(false));

    assertThat(grid.valueFoundIn(grid.rowContaining(Cell.at(0,0)), b), is(false));
    assertThat(grid.valueFoundIn(grid.rowContaining(Cell.at(0,1)), b), is(true));
  }

  private static final String a = "a";
  private static final String b = "b";

  private static final Cell cell = randomCell();
  private static final Cell otherCell =
    Cell.at(naturalNumberOtherThan(cell.x()), naturalNumberOtherThan(cell.y()));

  private static Cell randomCell() {

    int x = naturalNumber();
    return Cell.at(x, naturalNumberOtherThan(x));
  }
}
