package com.lastbubble.nikoli.takegaki;

import static com.lastbubble.nikoli.RandomNumbers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EdgeTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test public void oppositeOrientation() {

    assertThat(Edge.oppositeOf(Edge.H), is(Edge.V));
    assertThat(Edge.oppositeOf(Edge.V), is(Edge.H));
    assertThat(Edge.oppositeOf(null), is(nullValue()));
  }

  @Test public void negativeXnotAllowed() {

    thrown.expect(IllegalArgumentException.class);

    Edge.at(negativeNumber(), naturalNumber(), Edge.H);
  }

  @Test public void negativeYnotAllowed() {

    thrown.expect(IllegalArgumentException.class);

    Edge.at(naturalNumber(), negativeNumber(), Edge.V);
  }

  @Test public void orientationRequired() {

    thrown.expect(IllegalArgumentException.class);

    Edge.at(naturalNumber(), naturalNumber(), null);
  }

  @Test public void assignXYandOrientation() {

    int x = naturalNumber();
    int y = naturalNumberOtherThan(x);

    Edge edge = Edge.at(x, y, Edge.H);

    assertThat(edge.x(), is(x));
    assertThat(edge.y(), is(y));
    assertThat(edge.orientation(), is(Edge.Orientation.HORIZONTAL));

    Edge otherEdge = Edge.at(y, x, Edge.V);
    assertThat(otherEdge.x(), is(y));
    assertThat(otherEdge.y(), is(x));
    assertThat(otherEdge.orientation(), is(Edge.Orientation.VERTICAL));
  }

  @Test public void hashCodeImplemented() {

    Edge edge = randomEdge();
    Edge otherEdge = Edge.at(edge.x(), edge.y(), edge.orientation());

    assertThat(edge.hashCode(), is(otherEdge.hashCode()));
  }

  @Test public void equalsImplemented() {

    Edge edge = randomEdge();

    assertThat(edge, equalTo(edge));
    assertThat(edge, not(equalTo( new Object())));
    assertThat(edge, not(equalTo(Edge.at(edge.x(), naturalNumberOtherThan(edge.y()), edge.orientation()))));
    assertThat(edge, not(equalTo(Edge.at(naturalNumberOtherThan(edge.x()), edge.y(), edge.orientation()))));
    assertThat(edge, not(equalTo(Edge.at(edge.x(), edge.y(), edge.orientation() == Edge.H ? Edge.V : Edge.H))));
    assertThat(edge, equalTo(Edge.at(edge.x(), edge.y(), edge.orientation())));
  }

  @Test public void isConnected() {

    assertEdgesConnected(0, 0, Edge.H,
      Edge.at(0, 0, Edge.V),
      Edge.at(1, 0, Edge.H),
      Edge.at(1, 0, Edge.V)
    );

    assertEdgesConnected(0, 0, Edge.V,
      Edge.at(0, 0, Edge.H),
      Edge.at(0, 1, Edge.H),
      Edge.at(0, 1, Edge.V)
    );

    assertEdgesConnected(1, 1, Edge.H,
      Edge.at(0, 1, Edge.H),
      Edge.at(1, 0, Edge.V),
      Edge.at(1, 1, Edge.V),
      Edge.at(2, 0, Edge.V),
      Edge.at(2, 1, Edge.H),
      Edge.at(2, 1, Edge.V)
    );

    assertEdgesConnected(1, 1, Edge.V,
      Edge.at(0, 1, Edge.H),
      Edge.at(0, 2, Edge.H),
      Edge.at(1, 0, Edge.V),
      Edge.at(1, 1, Edge.H),
      Edge.at(1, 2, Edge.H),
      Edge.at(1, 2, Edge.V)
    );
  }

  private static void assertEdgesConnected(int x, int y, Edge.Orientation o, Edge... expected) {

    Edge edge = Edge.at(x, y, o);

    Set<Edge> expectedConnectedEdges = Stream.of(expected).collect(Collectors.toSet());

    for (Edge e : expected) { assertThat(edge.isConnected(e), is(true)); }

    Stream<Edge> unexpectedConnectedEdges = allEdgesInGrid(3, 3)
      .filter(e -> !expectedConnectedEdges.contains(e) && edge.isConnected(e));

      assertThat(unexpectedConnectedEdges.count(), is(0L));
  }

  private static Edge randomEdge() {

    return Edge.at(naturalNumber(), naturalNumber(), naturalNumber() % 2 == 0 ? Edge.H : Edge.V);
  }

  private static Stream<Edge> allEdgesInGrid(int width, int height) {

    return Stream.iterate(
        Edge.at(0, 0, Edge.H),
        e -> {
          int x = e.x() + 1;
          int y = e.y();
          Edge.Orientation o = e.orientation();

          if (x > ((o == Edge.H) ? (width - 1) : width)) {

            x = 0;
            y++;

            if (y > ((o == Edge.V) ? (height - 1) : height)) {
              x = 0; y = 0; o = Edge.oppositeOf(o);
            }
          }

          return Edge.at(x, y, o);
        }
      )
      .limit((width * (height + 1)) + (height * (width + 1)));
  }
}
