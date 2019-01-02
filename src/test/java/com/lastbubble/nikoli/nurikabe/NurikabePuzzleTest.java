package com.lastbubble.nikoli.nurikabe;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NurikabePuzzleTest {

  private final NurikabePuzzle puzzle = new Nurikabe().read(readerFrom(
      "1, , , ",
      " , , , ",
      " , , , ",
      " , , , "
    )
  );

  @Test public void toRaster() {

    assertThat(puzzle.toRaster(), matchesLines(
        "┌─┬─┬─┬─┐",
        "│1│ │ │ │",
        "├─┼─┼─┼─┤",
        "│ │ │ │ │",
        "├─┼─┼─┼─┤",
        "│ │ │ │ │",
        "├─┼─┼─┼─┤",
        "│ │ │ │ │",
        "└─┴─┴─┴─┘"
      )
    );
  }
}