package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HashiwokakeroPuzzleTest {

  private final HashiwokakeroPuzzle puzzle = new Hashiwokakero().read(readerFrom(
      "4, ,4,1",
      " , , ,1",
      " , ,3,4",
      "2,1, ,2"
    )
  );

  @Test public void toRaster() {

    assertThat(puzzle.toRaster(), matchesLines(
        "4   4 1",
        "       ",
        "      1",
        "       ",
        "    3 4",
        "       ",
        "2 1   2"
      )
    );
  }
}