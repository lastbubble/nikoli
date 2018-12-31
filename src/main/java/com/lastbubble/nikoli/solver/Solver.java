package com.lastbubble.nikoli.solver;

import static com.lastbubble.nikoli.logic.Formula.*;

import com.lastbubble.nikoli.logic.Formula;
import com.lastbubble.nikoli.logic.Formula.Var;
import com.lastbubble.nikoli.logic.VarSet;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

public class Solver<T> {

  private final ISolver solver = SolverFactory.instance().defaultSolver();
  private final VarSet<T> vars = new VarSet<T>();

  protected Var<T> varFor(T data) { return vars.add(data); }

  public void add(Formula formula) {

    formula.match(
      var -> { addClause( new int[] { toId(var) }); return true; },
      not -> { addClause( new int[] { toId(not) }); return true; },
      and -> { add(allOf(and.left(), and.right())); return true; },
      or -> { add(anyOf(or.left(), or.right())); return true; },
      implies -> { add(anyOf(not(implies.left()), implies.right())); return true; },
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

  public void addExactly(int n, Stream<T> elements) {

    try { solver.addExactly( new VecInt(elements.mapToInt(f -> toId(varFor(f))).toArray()), n); }
    catch (Exception e) { throw new RuntimeException("Failed to add exactly: " + e, e); }
  }

  public void solve(Consumer<Set<T>> consumer) {

    int[] model = findModel();

    while (model != null) {

      Set<T> solution = solutionFor(model);

      if (acceptable(solution.stream())) { consumer.accept(canonicalize(solution)); }

      excludeClause(model);

      model = findModel();
    }
  }

  protected boolean acceptable(Stream<T> solution) { return true; }

  protected Set<T> canonicalize(Set<T> solution) { return solution; }

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

  private Set<T> solutionFor(int[] model) {

    return Arrays.stream(model)
      .filter(n -> n > 0)
      .mapToObj(vars::varFor)
      .map(Var::data)
      .collect(Collectors.toSet());
  }

  private static RuntimeException illegalFormula() { return new IllegalArgumentException("Illegal formula!"); }
}
