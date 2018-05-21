package com.lastbubble.nikoli.takegaki;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class LoopsTest {

  @Test public void whenOneLoop() {

    whenEdgesAre(
      Edge.at(0, 0, Edge.H),
      Edge.at(0, 1, Edge.H),
      Edge.at(1, 0, Edge.V),
      Edge.at(0, 0, Edge.V)
    );

    assertThat(loops.size(), is(1));
    assertThat(loops.get(0), hasItems(
      Edge.at(0, 0, Edge.H),
      Edge.at(1, 0, Edge.V),
      Edge.at(0, 1, Edge.H),
      Edge.at(0, 0, Edge.V)
    ));
  }

  private void whenEdgesAre(Edge... edges) { loops = Loops.findLoopsIn(Stream.of(edges)); }

  private List<List<Edge>> loops;
}
