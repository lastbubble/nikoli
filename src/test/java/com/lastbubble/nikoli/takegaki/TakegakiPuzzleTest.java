package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TakegakiPuzzleTest {

  private final TakegakiPuzzle puzzle = new Takegaki().read(readerFrom(
      "0, ,1",
      " ,2, ",
      " , ,3"
    )
  );

  @Test public void toRaster() {

    assertThat(puzzle.toRaster(), matchesLines(
        "• • • •",
        " 0   1 ",
        "• • • •",
        "   2   ",
        "• • • •",
        "     3 ",
        "• • • •"
      )
    );
  }
}
