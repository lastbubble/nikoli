package com.lastbubble.nikoli.sudoku;

import static com.lastbubble.nikoli.CharRaster.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;

class SudokuPuzzle implements Puzzle<Value> {

  private final Grid<Value> grid;

  SudokuPuzzle(Grid<Value> grid) { this.grid = grid; }

  @Override public Grid<Value> grid() { return grid; }

  public Stream<Cell> rowContaining(Cell c) {
    return IntStream.range(0, 9).mapToObj(x -> Cell.at(x, c.y()));
  }

  public Stream<Cell> columnContaining(Cell c) {
    return IntStream.range(0, 9).mapToObj(y -> Cell.at(c.x(), y));
  }

  public Stream<Cell> regionContaining(Cell c) {

    int x = (c.x() / 3) * 3;
    int y = (c.y() / 3) * 3;

    return Stream.of(
      Cell.at(x,     y), Cell.at(x + 1,     y), Cell.at(x + 2,     y),
      Cell.at(x, y + 1), Cell.at(x + 1, y + 1), Cell.at(x + 2, y + 1),
      Cell.at(x, y + 2), Cell.at(x + 1, y + 2), Cell.at(x + 2, y + 2)
    );
  }

  @Override public CharRaster toRaster() {

    int width = 2 * grid.width() + 1, height = 2 * grid.height() + 1;

    CharRaster raster = new CharRaster(width, height);

    for (int y = 0; y < height; y++) {

      for (int x = 0; x < width; x++) {

        if (y % 2 == 0) {

          if (x % 2 == 0) {

            if (y == 0) {

              raster.set(x, y,
                (x == 0) ?
                  DOUBLE_DOWN_RIGHT :
                  ((x == (width - 1)) ? DOUBLE_DOWN_LEFT :
                    ((x % 3 == 0) ? DOUBLE_DOWN_HORIZONTAL : SINGLE_DOWN_DOUBLE_HORIZONTAL))
              );

            } else if (y == (height - 1)) {

              raster.set(x, y,
                (x == 0) ?
                  DOUBLE_UP_RIGHT :
                  ((x == (width - 1)) ? DOUBLE_UP_LEFT :
                    ((x % 3 == 0) ? DOUBLE_UP_HORIZONTAL : SINGLE_UP_DOUBLE_HORIZONTAL))
              );

            } else {

              if (y % 3 == 0) {

                raster.set(x, y,
                  (x == 0) ?
                    DOUBLE_VERTICAL_RIGHT :
                    ((x == (width - 1)) ? DOUBLE_VERTICAL_LEFT :
                      ((x % 3 == 0) ? DOUBLE_VERTICAL_HORIZONTAL : SINGLE_VERTICAL_DOUBLE_HORIZONTAL))
                );

              } else {

                raster.set(x, y,
                  (x == 0) ?
                    DOUBLE_VERTICAL_SINGLE_RIGHT :
                    ((x == (width - 1)) ? DOUBLE_VERTICAL_SINGLE_LEFT :
                      ((x % 3 == 0) ? DOUBLE_VERTICAL_SINGLE_HORIZONTAL : SINGLE_VERTICAL_HORIZONTAL))
                );
              }
            }

          } else {

            raster.set(x, y, (y % 3 == 0) ? DOUBLE_HORIZONTAL : SINGLE_HORIZONTAL);
          }

        } else {

          if (x % 2 == 0) { raster.set(x, y, (x % 3 == 0) ? DOUBLE_VERTICAL : SINGLE_VERTICAL); }
        }
      }
    }

    grid.filledCells().forEach(c ->
      raster.set(2 * c.x() + 1, 2 * c.y() + 1, grid.valueAt(c).get().describe())
    );

    return raster;
  }
}
