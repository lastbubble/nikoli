package com.lastbubble.nikoli;

import static org.hamcrest.Matchers.arrayContaining;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.function.Function;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RasterMatcher extends TypeSafeMatcher<CharRaster> {

  public static Matcher<? super CharRaster> matchesLines(String... lines) {

    return new RasterMatcher(lines);
  }

  private final String[] lines;

  private RasterMatcher(String[] lines) { this.lines = lines; }

  @Override protected boolean matchesSafely(CharRaster raster) {

    return arrayContaining(lines).matches(toLines(raster));
  }

  private String[] toLines(CharRaster raster) {

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    PrintWriter writer = new PrintWriter(out);
    raster.transform(RASTER_TRANSFORM).printTo(writer);
    writer.close();

    return out.toString().split("\n");
  }

  private static final Function<Character, Character> RASTER_TRANSFORM = c -> {
    switch (c) {
      case '\u00B7': return '.'; // dot
      case '\u2500': return '-'; // single horizontal
      case '\u2502': return '|'; // single vertical
      case '\u2550': return '='; // double horizontal
      case '\u2551': return 'H'; // double vertical
      case '\u2554': return 'r'; // down and right
      case '\u2557': return '7'; // down and left
      case '\u255A': return 'L'; // up and right
      case '\u255D': return ']'; // up and left
      default: break;
    }
    return c;
  };

  @Override public void describeTo(Description description) {

    description.appendText("matching lines");

    for (String line : lines) {
      description.appendText("\n\"" + line + "\"");
    }
  }

  @Override protected void describeMismatchSafely(CharRaster raster, Description description) {

    description.appendText("was");

    for (String line : toLines(raster)) {
      description.appendText("\n\"" + line + "\"");
    }
  }
}
