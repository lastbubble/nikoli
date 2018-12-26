package com.lastbubble.nikoli;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CharRasterTest {

  private CharRaster raster;

  @Test public void print_whenEmpty() {

    raster = new CharRaster(3, 2);

    assertThat(raster, matchesLines("   ", "   "));
  }

  @Test public void print_whenNonEmpty() {

    raster = new CharRaster(3, 2);
    raster.set(0, 0, 'a');
    raster.set(2, 0, 'b');
    raster.set(0, 1, 'c');
    raster.set(1, 1, 'd');

    assertThat(raster, matchesLines("a b", "cd "));
  }

  @Test public void transform() {

    raster = new CharRaster(3, 2);
    raster.set(0, 0, 'a');
    raster.set(2, 0, 'b');
    raster.set(0, 1, 'c');
    raster.set(1, 1, 'd');

    assertThat(raster.transform(Character::toUpperCase), matchesLines("A B", "CD "));
  }
}
