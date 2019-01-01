package com.lastbubble.nikoli.hashiwokakero;

import static com.lastbubble.nikoli.RasterMatcher.matchesLines;
import static com.lastbubble.nikoli.ReaderHelper.readerFrom;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import com.lastbubble.nikoli.Cell;

import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.Test;

public class HashiwokakeroSolutionTest {

  private final HashiwokakeroPuzzle puzzle = new Hashiwokakero().read(readerFrom(
      "4, ,4,1",
      " , , ,1",
      " , ,3,4",
      "2,1, ,2"
    )
  );

  private final Set<Bridge> bridges = Stream.of(
      Bridge.connecting(Cell.at(0, 0), Cell.at(2, 0)).incrementWeight(),
      Bridge.connecting(Cell.at(0, 0), Cell.at(0, 3)).incrementWeight(),
      Bridge.connecting(Cell.at(2, 0), Cell.at(3, 0)),
      Bridge.connecting(Cell.at(2, 0), Cell.at(2, 2)),
      Bridge.connecting(Cell.at(3, 1), Cell.at(3, 2)),
      Bridge.connecting(Cell.at(2, 2), Cell.at(3, 2)).incrementWeight(),
      Bridge.connecting(Cell.at(3, 2), Cell.at(3, 3)),
      Bridge.connecting(Cell.at(1, 3), Cell.at(3, 3))
    ).collect(Collectors.toSet());

    private final HashiwokakeroSolution solution = new HashiwokakeroSolution(puzzle, bridges);

  @Test public void puzzle() {

    assertThat(solution.puzzle(), sameInstance(puzzle));
  }

  @Test public void elements() {

    assertThat(solution.elements(), sameInstance(bridges));
  }

  @Test public void toRaster() {

    assertThat(solution.toRaster(), matchesLines(
        "4═══4─1",
        "║   │  ",
        "║   │ 1",
        "║   │ │",
        "║   3═4",
        "║     │",
        "2 1───2"
      )
    );
  }
}