package com.lastbubble.nikoli.logic;

import static com.lastbubble.nikoli.logic.Formula.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

public class EquivalenceTest {

  @Test public void impliesAsCnf() {

    given(
      implies(a, b),
      anyOf(not(a), b)
    );

    booleans().forEach(aTruth -> {
      booleans().forEach(bTruth -> {
        whenTruthIs(aTruth, bTruth);
        assertEquivalent();
      });
    });
  }

  @Test public void impliesExactlyOneOfTwoCnf() {

    given(
      implies(a, or(and(b, not(c)), and(not(b), c))),
      allOf(
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), b, c)
      )
    );

    booleans().forEach(aTruth -> {
      booleans().forEach(bTruth -> {
        booleans().forEach(cTruth -> {
          whenTruthIs(aTruth, bTruth, cTruth);
          assertEquivalent();
        });
      });
    });
  }

  @Test public void impliesExactlyOneOfThreeAsCnf() {

    given(
      implies(a,
        anyOf(
          allOf(b, not(c), not(d)),
          allOf(not(b), c, not(d)),
          allOf(not(b), not(c), d)
        )
      ),
      allOf(
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), not(b), not(d)),
        anyOf(not(a), not(c), not(d)),
        anyOf(not(a), b, c, d)
      )
    );

    booleans().forEach(aTruth -> {
      booleans().forEach(bTruth -> {
        booleans().forEach(cTruth -> {
          booleans().forEach(dTruth -> {
            whenTruthIs(aTruth, bTruth, cTruth, dTruth);
            assertEquivalent();
          });
        });
      });
    });
  }

  @Test public void exactlyOneAsCnf() {

    given(
      allOf(
        anyOf(a, b, c, d),
        implies(a, allOf(not(b), not(c), not(d))),
        implies(b, allOf(not(a), not(c), not(d))),
        implies(c, allOf(not(a), not(b), not(d))),
        implies(d, allOf(not(a), not(b), not(c)))
      ),
      allOf(
        anyOf(a, b, c, d),
        anyOf(not(a), not(b)),
        anyOf(not(a), not(c)),
        anyOf(not(a), not(d)),
        anyOf(not(b), not(c)),
        anyOf(not(b), not(d)),
        anyOf(not(c), not(d))
      )
    );

    booleans().forEach(aTruth -> {
      booleans().forEach(bTruth -> {
        booleans().forEach(cTruth -> {
          booleans().forEach(dTruth -> {
            whenTruthIs(aTruth, bTruth, cTruth, dTruth);
            assertEquivalent();
          });
        });
      });
    });
  }

  @Test public void exactlyTwoAsCnf() {

    given(
      allOf(
        anyOf(a, b, c, d),
        implies(a, anyOf(b, c, d)),
        implies(b, anyOf(a, c, d)),
        implies(c, anyOf(a, b, d)),
        implies(d, anyOf(a, b, c)),
        implies(and(a, b), and(not(c), not(d))),
        implies(and(a, c), and(not(b), not(d))),
        implies(and(a, d), and(not(b), not(c))),
        implies(and(b, c), and(not(a), not(d))),
        implies(and(b, d), and(not(a), not(c))),
        implies(and(c, d), and(not(a), not(b)))
      ),
      allOf(
        anyOf(a, b, c, d),
        anyOf(not(a), b, c, d),
        anyOf(a, not(b), c, d),
        anyOf(a, b, not(c), d),
        anyOf(a, b, c, not(d)),
        anyOf(not(a), not(b), not(c)),
        anyOf(not(a), not(b), not(d)),
        anyOf(not(a), not(c), not(d)),
        anyOf(not(b), not(c), not(d))
      )
    );

    booleans().forEach(aTruth -> {
      booleans().forEach(bTruth -> {
        booleans().forEach(cTruth -> {
          booleans().forEach(dTruth -> {
            whenTruthIs(aTruth, bTruth, cTruth, dTruth);
            assertEquivalent();
          });
        });
      });
    });
  }

  @Test public void exactlyThreeAsCnf() {

    given(
      allOf(
        anyOf(not(a), not(b), not(c), not(d)),
        implies(not(a), allOf(b, c, d)),
        implies(not(b), allOf(a, c, d)),
        implies(not(c), allOf(a, b, d)),
        implies(not(d), allOf(a, b, c))
      ),
      allOf(
        anyOf(not(a), not(b), not(c), not(d)),
        anyOf(a, b),
        anyOf(a, c),
        anyOf(a, d),
        anyOf(b, c),
        anyOf(b, d),
        anyOf(c, d)
      )
    );

    booleans().forEach(aTruth -> {
      booleans().forEach(bTruth -> {
        booleans().forEach(cTruth -> {
          booleans().forEach(dTruth -> {
            whenTruthIs(aTruth, bTruth, cTruth, dTruth);
            assertEquivalent();
          });
        });
      });
    });
  }

  private void given(Formula f1, Formula f2) { formula1 = f1; formula2 = f2; }

  private void whenTruthIs(boolean... truths) {

    truthAssignment.clear();

    Var[] vars = new Var[] { a, b, c, d };

    for (int i = 0; i < truths.length; i++) { truthAssignment.put(vars[i], truths[i]); }
  }

  private void assertEquivalent() { assertEquals(evaluate(formula1, truth), evaluate(formula2, truth)); }

  private Formula formula1;
  private Formula formula2;

  private final Map<Var, Boolean> truthAssignment = new HashMap<>();

  private final Predicate<Formula> truth = f -> truthAssignment.get(f);

  private static Stream<Boolean> booleans() { return Stream.of(true, false); }

  private static final Var a = var("a");
  private static final Var b = var("b");
  private static final Var c = var("c");
  private static final Var d = var("d");
}
