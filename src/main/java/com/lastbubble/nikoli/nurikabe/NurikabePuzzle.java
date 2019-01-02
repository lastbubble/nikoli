package com.lastbubble.nikoli.nurikabe;

import static com.lastbubble.nikoli.CharRaster.*;

import com.lastbubble.nikoli.CharRaster;
import com.lastbubble.nikoli.Grid;
import com.lastbubble.nikoli.Puzzle;

class NurikabePuzzle implements Puzzle<Integer> {

  private final Grid<Integer> grid;

  NurikabePuzzle(Grid<Integer> grid) { this.grid = grid; }

  @Override public Grid<Integer> grid() { return grid; }

  @Override public CharRaster toRaster() {

    int width = 2 * grid.width() + 1, height = 2 * grid.height() + 1;

    CharRaster raster = new CharRaster(width, height);

    for (int y = 0; y < height; y++) {

      for (int x = 0; x < width; x++) {

        if (y % 2 == 0) {

          if (x % 2 == 0) {

            if (y == 0) {

              raster.set(x, y,
                (x == 0) ? SINGLE_DOWN_RIGHT : ((x == (width - 1)) ? SINGLE_DOWN_LEFT : SINGLE_DOWN_HORIZONTAL)
              );

            } else if (y == (height - 1)) {

              raster.set(x, y,
                (x == 0) ? SINGLE_UP_RIGHT : ((x == (width - 1)) ? SINGLE_UP_LEFT : SINGLE_UP_HORIZONTAL)
              );

            } else {

              raster.set(x, y,
                (x == 0) ? SINGLE_VERTICAL_RIGHT : ((x == (width - 1)) ? SINGLE_VERTICAL_LEFT : SINGLE_VERTICAL_HORIZONTAL)
              );
            }

          } else {

            raster.set(x, y, SINGLE_HORIZONTAL);
          }

        } else {

          if (x % 2 == 0) { raster.set(x, y, SINGLE_VERTICAL); }
        }
      }
    }

    grid.filledCells().forEach(c ->
      raster.set(2 * c.x() + 1, 2 * c.y() + 1, (char) ('0' + grid.valueAt(c).get()))
    );

    return raster;
  }
}
