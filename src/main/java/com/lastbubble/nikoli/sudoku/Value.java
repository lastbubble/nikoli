package com.lastbubble.nikoli.sudoku;

public enum Value {
  ONE('1'),
  TWO('2'),
  THREE('3'),
  FOUR('4'),
  FIVE('5'),
  SIX('6'),
  SEVEN('7'),
  EIGHT('8'),
  NINE('9');

  private final char digit;

  private Value(char digit) { this.digit = digit; }

  public char describe() { return digit; }
}
