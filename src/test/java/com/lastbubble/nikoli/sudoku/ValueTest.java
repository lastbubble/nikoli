package com.lastbubble.nikoli.sudoku;

import static com.lastbubble.nikoli.sudoku.Value.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ValueTest {

  @Test public void describe() {

    assertThatDigitIs(ONE, '1');
    assertThatDigitIs(TWO, '2');
    assertThatDigitIs(THREE, '3');
    assertThatDigitIs(FOUR, '4');
    assertThatDigitIs(FIVE, '5');
    assertThatDigitIs(SIX, '6');
    assertThatDigitIs(SEVEN, '7');
    assertThatDigitIs(EIGHT, '8');
    assertThatDigitIs(NINE, '9');
  }

  private void assertThatDigitIs(Value value, char digit) {
    assertThat(value.describe(), is(digit));
  }
}
