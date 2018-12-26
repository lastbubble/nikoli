package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.CharRaster;

import java.util.function.Function;

class TakegakiTestBase {

  protected void assertRasterLinesAre(CharRaster raster, String ... lines) {

    assertThat(raster.transform(RASTER_TRANSFORM), matchesLines(lines));
  }

  private static final Function<Character, Character> RASTER_TRANSFORM = c -> {
    switch (c) {
      case '\u00B7': return 'x'; // dot
      case '\u2550': return '-'; // double horizontal
      case '\u2551': return '|'; // double vertical
      case '\u2554': return 'r'; // down and right
      case '\u2557': return '7'; // down and left
      case '\u255A': return 'L'; // up and right
      case '\u255D': return ']'; // up and left
      default: break;
    }
    return c;
  };
}
