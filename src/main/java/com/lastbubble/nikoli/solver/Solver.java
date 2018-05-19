package com.lastbubble.nikoli.solver;

import com.lastbubble.nikoli.logic.Formula;
import com.lastbubble.nikoli.logic.Formula.Var;
import com.lastbubble.nikoli.logic.VarSet;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

public class Solver {

  private final ISolver solver = SolverFactory.instance().defaultSolver();
  protected final VarSet vars = new VarSet();

  public void add(Formula formula) {

    formula.match(
      var -> { addClause( new int[] { toId(var) }); return true; },
      not -> { addClause( new int[] { toId(not) }); return true; },
      and -> { throw illegalFormula(); },
      or -> { throw illegalFormula(); },
      implies -> { throw illegalFormula(); },
      allOf -> { allOf.targets().forEach(f -> add(f)); return true; },
      anyOf -> { addClause(anyOf.targets().mapToInt(f -> toId(f)).toArray()); return true; }
    );
  }

  private void addClause(int[] ids) {

    try { solver.addClause( new VecInt(ids)); }
    catch (Exception e) { throw new RuntimeException("Failed to add clause: " + e, e); }
  }

  private int toId(Formula formula) {

    return formula.match(
      var -> vars.idFor(var),
      not -> -toId(not.target()),
      and -> { throw illegalFormula(); },
      or -> { throw illegalFormula(); },
      implies -> { throw illegalFormula(); },
      allOf -> { throw illegalFormula(); },
      anyOf -> { throw illegalFormula(); }
    );
  }

  public void solve(Consumer<Stream<Var>> consumer) {

    int[] model = findModel();

    while (model != null) {

      consumer.accept(solutionFor(model));

      excludeClause(model);

      model = findModel();
    }
  }

  private int[] findModel() {

    try { return solver.findModel(); }
    catch (Exception e) { throw new RuntimeException("Solver failed: " + e, e); }
  }

  private void excludeClause(int[] model) {

    int[] excludeModel = new int[model.length];

    for (int i = 0; i < model.length; i++) { excludeModel[i] = -model[i]; }

    try { solver.addClause( new VecInt(excludeModel)); }
    catch (Exception e) { throw new RuntimeException("Failed to exclude clause: " + e, e); }
  }

  private Stream<Var> solutionFor(int[] model) {

    return Arrays.stream(model).filter(n -> n > 0).mapToObj(vars::varFor);
  }

  private static RuntimeException illegalFormula() { return new IllegalArgumentException("Illegal formula!"); }
}
