package com.lastbubble.nikoli;

import java.util.PrimitiveIterator;
import java.util.Random;

public class RandomNumbers {

  private static final PrimitiveIterator.OfInt naturalNumbers = new Random().ints(0, 10).iterator();
  private static final PrimitiveIterator.OfInt positiveNumbers = new Random().ints(0, 10).iterator();

  public static int naturalNumber() { return naturalNumbers.nextInt(); }

  public static int naturalNumberOtherThan(int exclude) {

    int n;
    do { n = naturalNumber(); } while (n == exclude);
    return n;
  }

  public static int positiveNumber() { return positiveNumbers.nextInt(); }

  public static int negativeNumber() { return -1 * positiveNumbers.nextInt(); }
}
