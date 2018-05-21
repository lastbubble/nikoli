package com.lastbubble.nikoli.takegaki;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loops {

  private Loops() { }

  public static List<List<Edge>> findLoopsIn(Stream<Edge> edges) {

    List<List<Edge>> loops = new ArrayList<>();

    List<Edge> path = new ArrayList<Edge>();

    Set<Edge> edgeSet = edges.collect(Collectors.toSet());

    while (edgeSet.isEmpty() == false) {

      if (path.isEmpty()) {

        Edge edge = edgeSet.iterator().next();
        edgeSet.remove(edge);
        path.add(edge);

      } else {

        Edge lastEdge = path.get(path.size() - 1);

        Optional<Edge> ifNextEdge = Stream.concat(lastEdge.neighbors(true), lastEdge.neighbors(false))
          .filter(edgeSet::contains)
          .findFirst();

        if (ifNextEdge.isPresent()) {

          Edge nextEdge = ifNextEdge.get();

          edgeSet.remove(nextEdge);
          path.add(nextEdge);

          if (path.size() > 2 && nextEdge.isConnected(path.get(0))) {

            loops.add(path);
            path = new ArrayList<>();
          }

        } else {

          path = new ArrayList<>();
        }
      }
    }

    return loops;
  }
}
