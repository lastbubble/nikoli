package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import com.lastbubble.nikoli.Cell;

import co.unruly.matchers.StreamMatchers;
import org.junit.Test;

public class HashiwokakeroSolverTest {

  private final HashiwokakeroPuzzle puzzle = new Hashiwokakero().read(readerFrom(
        "4, ,4,1",
        " , , ,1",
        " , ,3,4",
        "2,1, ,2"
      )
    );

  private final HashiwokakeroSolver solver = new HashiwokakeroSolver(puzzle.grid());

  @Test public void allBridges() {

    assertThat(solver.allBridges(), StreamMatchers.contains(
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

    solver.solve(bridges -> {
      assertThat(bridges, containsInAnyOrder(
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
    });
  }
}
