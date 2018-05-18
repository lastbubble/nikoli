package com.lastbubble.nikoli.logic;

import static com.lastbubble.nikoli.logic.Formula.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

public class FormulaTest {

  @Test public void assignVar() {

    Object data = new Object();

    Var var = var(data);

    assertThat(var.data(), is(sameInstance(data)));
  }

  @Test public void assignNot() {

    Not not = not(formula1);

    assertThat(not.target(), is(sameInstance(formula1)));
  }

  @Test public void assignAnd() {

    And and = and(formula1, formula2);

    assertThat(and.left(), is(sameInstance(formula1)));
    assertThat(and.right(), is(sameInstance(formula2)));
  }

  @Test public void assignOr() {

    Or or = or(formula1, formula2);

    assertThat(or.left(), is(sameInstance(formula1)));
    assertThat(or.right(), is(sameInstance(formula2)));
  }

  @Test public void assignImplies() {

    Implies implies = implies(formula1, formula2);

    assertThat(implies.left(), is(sameInstance(formula1)));
    assertThat(implies.right(), is(sameInstance(formula2)));
  }

  @Test public void assignAllOf() {

    AllOf allOf = allOf(formula1, formula2, formula3);

    assertThat(allOf.targets().collect(Collectors.toList()), contains(formula1, formula2, formula3));
  }

  @Test public void evaluateLogicUsingMatch() {

    assertThat(evaluate(t), is(true));
    assertThat(evaluate(f), is(false));

    assertThat(evaluate(not(t)), is(false));
    assertThat(evaluate(not(f)), is(true));

    assertThat(evaluate(and(t, t)), is(true));
    assertThat(evaluate(and(t, f)), is(false));
    assertThat(evaluate(and(f, t)), is(false));
    assertThat(evaluate(and(f, f)), is(false));

    assertThat(evaluate(or(t, t)), is(true));
    assertThat(evaluate(or(t, f)), is(true));
    assertThat(evaluate(or(f, t)), is(true));
    assertThat(evaluate(or(f, f)), is(false));

    assertThat(evaluate(implies(t, t)), is(true));
    assertThat(evaluate(implies(t, f)), is(false));
    assertThat(evaluate(implies(f, t)), is(true));
    assertThat(evaluate(implies(f, f)), is(true));

    assertThat(evaluate(allOf(t, t, t)), is(true));
    assertThat(evaluate(allOf(t, f, t)), is(false));

    assertThat(evaluate(anyOf(t, f, f)), is(true));
    assertThat(evaluate(anyOf(f, f, f)), is(false));
  }

  private boolean evaluate(Formula formula) {
    return formula.match(
      var -> (var == t),
      not -> !evaluate(not.target()),
      and -> evaluate(and.left()) && evaluate(and.right()),
      or -> evaluate(or.left()) || evaluate(or.right()),
      implies -> !evaluate(implies.left()) || evaluate(implies.right()),
      allOf -> allOf.targets().allMatch(isTrue()),
      anyOf -> anyOf.targets().anyMatch(isTrue())
    );
  }

  private Predicate<Formula> isTrue() { return formula -> evaluate(formula); }

  private static final Formula t = var(true);;
  private static final Formula f = var(false);

  private static final Formula formula1 = mock(Formula.class, "formula1");
  private static final Formula formula2 = mock(Formula.class, "formula2");
  private static final Formula formula3 = mock(Formula.class, "formula3");
}
