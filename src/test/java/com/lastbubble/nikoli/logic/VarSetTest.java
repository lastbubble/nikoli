package com.lastbubble.nikoli.logic;

import static com.lastbubble.nikoli.logic.Formula.Var;
import static com.lastbubble.nikoli.logic.Formula.var;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class VarSetTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  private final VarSet vars = new VarSet();

  @Test public void add_requiresNonNullData() {

    thrown.expect(IllegalArgumentException.class);

    vars.add(null);
  }

  @Test public void add_sameVarForSameData() {

    Object data = new Object();

    assertThat(vars.add(data), is(sameInstance(vars.add(data))));
  }

  @Test public void add_sameVarForEqualData() {

    String data = "test";

    assertThat(vars.add(data), is(sameInstance(vars.add(data))));
  }

  @Test public void add_differentVarsForDifferentData() {

    Object data1 = new Object();
    Object data2 = new Object();

    assertThat(vars.add(data1), is(not(vars.add(data2))));
  }

  @Test public void idFor_requiresNonNullVar() {

    thrown.expect(IllegalArgumentException.class);

    vars.idFor(null);
  }

  @Test public void idFor_unrecognizedVar() {

    thrown.expect(IllegalArgumentException.class);

    Var var = var("test");

    vars.idFor(var);
  }

  @Test public void idFor_registeredVar() {

    Object data = "test";

    Var var = vars.add(data);

    assertThat(vars.idFor(var), is(1));

    assertThat(vars.idFor(vars.add(data)), is(1));
  }

  @Test public void idFor_differentVars() {

    Var var1 = vars.add("a");
    Var var2 = vars.add("b");

    assertThat(vars.idFor(var1), is(1));
    assertThat(vars.idFor(var2), is(2));
  }

  @Test public void varFor_illegalId() {

    thrown.expect(IllegalArgumentException.class);

    vars.varFor(0);
  }

  @Test public void varFor_invalidId() {

    thrown.expect(IndexOutOfBoundsException.class);

    vars.varFor(1);
  }

  @Test public void varFor_registeredId() {

    Var var1 = vars.add("a");
    Var var2 = vars.add("b");

    assertThat(vars.varFor(vars.idFor(var1)), is(var1));
    assertThat(vars.varFor(vars.idFor(var2)), is(var2));
  }

  @Test public void varFor_negativeId() {

    Var var1 = vars.add("a");
    Var var2 = vars.add("b");

    assertThat(vars.varFor(-vars.idFor(var1)), is(var1));
    assertThat(vars.varFor(-vars.idFor(var2)), is(var2));
  }
}