package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;
import com.lastbubble.nikoli.Solution;

import org.junit.Test;

public class HashiwokakeroSolverTest {

  @Test public void allBridges() {

    whenPuzzleIs(
      "4, ,4,1",
      " , , ,1",
      " , ,3,4",
      "2,1, ,2"
    );

    HashiwokakeroSolver solver = new HashiwokakeroSolver(puzzle.grid());

    assertThat(solver.allBridges(), containsInAnyOrder(
        Bridge.connecting(Cell.at(0, 0), Cell.at(2, 0)),
        Bridge.connecting(Cell.at(0, 0), Cell.at(2, 0)).incrementWeight(),
        Bridge.connecting(Cell.at(0, 0), Cell.at(0, 3)),
        Bridge.connecting(Cell.at(0, 0), Cell.at(0, 3)).incrementWeight(),
        Bridge.connecting(Cell.at(2, 0), Cell.at(3, 0)),
        Bridge.connecting(Cell.at(2, 0), Cell.at(2, 2)),
        Bridge.connecting(Cell.at(2, 0), Cell.at(2, 2)).incrementWeight(),
        Bridge.connecting(Cell.at(3, 1), Cell.at(3, 2)),
        Bridge.connecting(Cell.at(2, 2), Cell.at(3, 2)),
        Bridge.connecting(Cell.at(2, 2), Cell.at(3, 2)).incrementWeight(),
        Bridge.connecting(Cell.at(3, 2), Cell.at(3, 3)),
        Bridge.connecting(Cell.at(3, 2), Cell.at(3, 3)).incrementWeight(),
        Bridge.connecting(Cell.at(0, 3), Cell.at(1, 3)),
        Bridge.connecting(Cell.at(1, 3), Cell.at(3, 3))
      )
    );
  }

  @Test public void solve() {

    whenPuzzleIs(
      "4, ,4,1",
      " , , ,1",
      " , ,3,4",
      "2,1, ,2"
    );

    for (Solution<Integer, Bridge> solution : hashiwokakero.solve(puzzle)) {

      assertThat(solution.elements(), containsInAnyOrder(
          Bridge.connecting(Cell.at(0, 0), Cell.at(2, 0)).incrementWeight(),
          Bridge.connecting(Cell.at(0, 0), Cell.at(0, 3)).incrementWeight(),
          Bridge.connecting(Cell.at(2, 0), Cell.at(3, 0)),
          Bridge.connecting(Cell.at(2, 0), Cell.at(2, 2)),
          Bridge.connecting(Cell.at(3, 1), Cell.at(3, 2)),
          Bridge.connecting(Cell.at(2, 2), Cell.at(3, 2)).incrementWeight(),
          Bridge.connecting(Cell.at(3, 2), Cell.at(3, 3)),
          Bridge.connecting(Cell.at(1, 3), Cell.at(3, 3))
        )
      );
    }
  }

  @Test public void rejectCrossingBridges() {

    whenPuzzleIs(
      " ,2,3",
      "1, ,3",
      " ,2,3"
    );

    for (Solution<Integer, Bridge> solution : hashiwokakero.solve(puzzle)) {

      assertThat(solution.elements(), containsInAnyOrder(
          Bridge.connecting(Cell.at(1, 0), Cell.at(2, 0)).incrementWeight(),
          Bridge.connecting(Cell.at(2, 0), Cell.at(2, 1)),
          Bridge.connecting(Cell.at(0, 1), Cell.at(2, 1)),
          Bridge.connecting(Cell.at(2, 1), Cell.at(2, 2)),
          Bridge.connecting(Cell.at(1, 2), Cell.at(2, 2)).incrementWeight()
        )
      );
    }
  }

  @Test public void rejectMultipleLinks() {

    whenPuzzleIs(
      "2,2",
      "2,2"
    );

    for (Solution<Integer, Bridge> solution : hashiwokakero.solve(puzzle)) {

      assertThat(solution.elements(), containsInAnyOrder(
          Bridge.connecting(Cell.at(0, 0), Cell.at(1, 0)),
          Bridge.connecting(Cell.at(0, 0), Cell.at(0, 1)),
          Bridge.connecting(Cell.at(1, 0), Cell.at(1, 1)),
          Bridge.connecting(Cell.at(0, 1), Cell.at(1, 1))
        )
      );
    }
  }

  private void whenPuzzleIs(String... lines) {

    puzzle = hashiwokakero.read(readerFrom(lines));
  }

  private HashiwokakeroPuzzle puzzle;

  private static final Hashiwokakero hashiwokakero = new Hashiwokakero();
}
