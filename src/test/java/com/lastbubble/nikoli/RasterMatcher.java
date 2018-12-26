package com.lastbubble.nikoli;

import static org.hamcrest.Matchers.arrayContaining;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RasterMatcher extends TypeSafeMatcher<CharRaster> {

  public static Matcher<? super CharRaster> matchesLines(String... lines) {

    return new RasterMatcher(arrayContaining(lines));
  }

  private final Matcher<? super String[]> linesMatcher;

  private RasterMatcher(Matcher<? super String[]> linesMatcher) { this.linesMatcher = linesMatcher; }

  @Override protected boolean matchesSafely(CharRaster raster) {

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    PrintWriter writer = new PrintWriter(out);
    raster.printTo(writer);
    writer.close();

    String[] lines = out.toString().split("\n");
    return linesMatcher.matches(lines);
  }

  @Override public void describeTo(Description description) {

    description.appendText("matching lines");
  }
}
