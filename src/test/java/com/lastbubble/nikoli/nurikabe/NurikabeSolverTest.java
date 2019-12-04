package com.lastbubble.nikoli.nurikabe;

import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Solution;

import org.junit.Test;

public class NurikabeSolverTest {

  @Test public void solve() {

    whenPuzzleIs(
      "1, , , ",
      " , ,3, ",
      " , , , ",
      "4, , , "
    );

    for (Solution<Integer, Cell> solution : nurikabe.solve(puzzle)) {
      assertThat(solution, is(not(nullValue())));
      /*assertThat(solution.elements(), containsInAnyOrder(
          Cell.at(1, 0),
          Cell.at(2, 0),
          Cell.at(0, 1),
          Cell.at(1, 1),
          Cell.at(2, 1),
          Cell.at(2, 2),
          Cell.at(2, 3)
        )
      );*/
    }
  }

  private void whenPuzzleIs(String... lines) {

    puzzle = nurikabe.read(readerFrom(lines));
  }

  private NurikabePuzzle puzzle;

  private static final Nurikabe nurikabe = new Nurikabe();
}
