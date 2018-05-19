package com.lastbubble.nikoli.logic;

import static com.lastbubble.nikoli.logic.Formula.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VarSet {

  private final Map<Object, Var> varsByData = new HashMap<>();
  private final Map<Var, Integer> idsByVar = new HashMap<>();
  private final List<Var> vars = new ArrayList<>();

  public Var add(Object data) {

    checkArgument(data != null, "Data cannot be null");

    Var var = varsByData.get(data);

    if (var == null) {

      var = var(data);
      varsByData.put(data, var);
      vars.add(var);
      idsByVar.put(var, vars.size());
    }

    return var;
  }

  public int idFor(Var var) {

    checkArgument(var != null, "Var cannot be null");

    checkArgument(idsByVar.containsKey(var), "Var not registered");

    return idsByVar.get(var);
  }

  public Var varFor(int id) {

    checkArgument(id != 0, "Illegal id");

    return vars.get(Math.abs(id) - 1);
  }

  private static void checkArgument(boolean condition, String reason) {

    if (!condition) { throw new IllegalArgumentException(reason); }
  }
}
