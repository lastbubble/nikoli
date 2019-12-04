package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Solution;

import org.junit.Test;

public class TakegakiSolverTest {

  @Test public void solve() {

    whenPuzzleIs(
      "2,3, ,0",
      " ,3,1, ",
      "2, ,3, "
    );

    for (Solution<Integer, Edge> solution : takegaki.solve(puzzle)) {

      assertThat(solution.elements(), containsInAnyOrder(
          Edge.at(0, 0, Edge.H),
          Edge.at(1, 0, Edge.H),
          Edge.at(0, 0, Edge.V),
          Edge.at(2, 0, Edge.V),
          Edge.at(1, 1, Edge.H),
          Edge.at(0, 1, Edge.V),
          Edge.at(1, 1, Edge.V),
          Edge.at(1, 2, Edge.H),
          Edge.at(2, 2, Edge.H),
          Edge.at(0, 2, Edge.V),
          Edge.at(3, 2, Edge.V),
          Edge.at(0, 3, Edge.H),
          Edge.at(1, 3, Edge.H),
          Edge.at(2, 3, Edge.H)
        )
      );
    }
  }

  @Test public void rejectLoop() {

    whenPuzzleIs(
      "3, ,3",
      " , ,3",
      "3, , "
    );

    for (Solution<Integer, Edge> solution : takegaki.solve(puzzle)) {

      assertThat(solution.elements(), containsInAnyOrder(
          Edge.at(0, 0, Edge.H),
          Edge.at(1, 0, Edge.H),
          Edge.at(2, 0, Edge.H),
          Edge.at(0, 0, Edge.V),
          Edge.at(3, 0, Edge.V),
          Edge.at(0, 1, Edge.H),
          Edge.at(2, 1, Edge.H),
          Edge.at(1, 1, Edge.V),
          Edge.at(2, 1, Edge.V),
          Edge.at(0, 2, Edge.H),
          Edge.at(2, 2, Edge.H),
          Edge.at(0, 2, Edge.V),
          Edge.at(3, 2, Edge.V),
          Edge.at(0, 3, Edge.H),
          Edge.at(1, 3, Edge.H),
          Edge.at(2, 3, Edge.H)
        )
      );
    }
  }

  private void whenPuzzleIs(String... lines) {

    puzzle = takegaki.read(readerFrom(lines));
  }

  private TakegakiPuzzle puzzle;

  private static final Takegaki takegaki = new Takegaki();
}
