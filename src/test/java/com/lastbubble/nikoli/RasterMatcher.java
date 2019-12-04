package com.lastbubble.nikoli;

import static org.hamcrest.Matchers.arrayContaining;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
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
    raster.printTo(writer);
    writer.close();

    return out.toString().split("\n");
  }

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
