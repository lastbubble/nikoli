package com.lastbubble.nikoli.takegaki;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TakegakiReader {

  public Grid<Integer> read(Path path) throws IOException {

    Grid.Builder<Integer> builder = Grid.<Integer>builder();

    List<String> lines = Files.lines(path).collect(Collectors.toList());

    Collections.reverse(lines);

    for (int y = 0; y < lines.size(); y++) {

      String[] tokens = lines.get(y).split(",");

      for (int x = 0; x < tokens.length; x++) {

        String token = tokens[x];

        if (CELL_PATTERN.matcher(token).matches()) {

          builder.assign(Cell.at(x, y), Integer.parseInt(token));
        }
      }
    }

    return builder.build();
  }

  private static final Pattern CELL_PATTERN = Pattern.compile("0|1|2|3");
}
