package com.lastbubble.nikoli;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderHelper {

  public static BufferedReader readerFrom(String... lines) {

    return new BufferedReader(
      new StringReader(Stream.of(lines).collect(Collectors.joining("\n")))
    );
  }
}
