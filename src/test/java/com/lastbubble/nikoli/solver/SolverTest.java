package com.lastbubble.nikoli.solver;

import static com.lastbubble.nikoli.logic.Formula.*;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SolverTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  private Solver<String> solver = new Solver<>();

  @Test public void solve() {

    solver.add(solver.varFor("a"));
    solver.add(not(solver.varFor("b")));
    solver.add(and(solver.varFor("c"), solver.varFor("d")));
    solver.add(or(solver.varFor("e"), solver.varFor("f")));
    solver.addExactly(1, Stream.of("a", "b"));

    solver.solve(solution -> {
      assertThat(solution, hasItems("a", "c", "d"));
    });
  }

  @Test public void reject_negatedAnd() {

    thrown.expect(IllegalArgumentException.class);

    solver.add(not(and(solver.varFor("a"), solver.varFor("b"))));
  }

  @Test public void reject_negatedOr() {

    thrown.expect(IllegalArgumentException.class);

    solver.add(not(or(solver.varFor("a"), solver.varFor("b"))));
  }

  @Test public void reject_negatedImplies() {

    thrown.expect(IllegalArgumentException.class);

    solver.add(not(implies(solver.varFor("a"), solver.varFor("b"))));
  }

  @Test public void reject_negatedAllOf() {

    thrown.expect(IllegalArgumentException.class);

    solver.add(not(allOf(solver.varFor("a"), solver.varFor("b"))));
  }

  @Test public void reject_negatedAnyOf() {

    thrown.expect(IllegalArgumentException.class);

    solver.add(not(anyOf(solver.varFor("a"), solver.varFor("b"))));
  }
}
