package com.lastbubble.nikoli.logic;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Formula {

  public static Var var(Object data) { return new Var(data); }

  public static Not not(Formula target) { return new Not(target); }

  public static And and(Formula left, Formula right) { return new And(left, right); }

  public static Or or(Formula left, Formula right) { return new Or(left, right); }

  public static Implies implies(Formula left, Formula right) { return new Implies(left, right); }

  public static AllOf allOf(Formula... targets) { return new AllOf(targets); }

  public static AnyOf anyOf(Formula... targets) { return new AnyOf(targets); }

  private Formula() { }

  public abstract <T> T match(
    Function<Var, T> var,
    Function<Not, T> not,
    Function<And, T> and,
    Function<Or, T> or,
    Function<Implies, T> implies,
    Function<AllOf, T> allOf,
    Function<AnyOf, T> anyOf
  );

  public static class Var extends Formula {

    private final Object data;

    private Var(Object data) { this.data = data; }

    public Object data() { return data; }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
    ) {
      return var.apply(this);
    }
  }

  public static class Not extends Formula {

    private final Formula target;

    public Formula target() { return target; }

    private Not(Formula target) { this.target = target; }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
      ) {
      return not.apply(this);
    }
  }

  public static class And extends Formula {

    private final Formula left;
    private final Formula right;

    public Formula left() { return left; }
    public Formula right() { return right; }

    private And(Formula left, Formula right) { this.left = left; this.right = right; }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
      ) {
      return and.apply(this);
    }
  }

  public static class Or extends Formula {

    private final Formula left;
    private final Formula right;

    public Formula left() { return left; }
    public Formula right() { return right; }

    private Or(Formula left, Formula right) { this.left = left; this.right = right; }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
      ) {
      return or.apply(this);
    }
  }

  public static class Implies extends Formula {

    private final Formula left;
    private final Formula right;

    public Formula left() { return left; }
    public Formula right() { return right; }

    private Implies(Formula left, Formula right) { this.left = left; this.right = right; }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
      ) {
      return implies.apply(this);
    }
  }

  public static class AllOf extends Formula {

    private final List<Formula> targets;

    public Stream<Formula> targets() { return targets.stream(); }

    private AllOf(Formula... targets) { this.targets = asList(targets); }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
      ) {
      return allOf.apply(this);
    }
  }

  public static class AnyOf extends Formula {

    private final List<Formula> targets;

    public Stream<Formula> targets() { return targets.stream(); }

    private AnyOf(Formula... targets) { this.targets = asList(targets); }

    @Override public <T> T match(
      Function<Var, T> var,
      Function<Not, T> not,
      Function<And, T> and,
      Function<Or, T> or,
      Function<Implies, T> implies,
      Function<AllOf, T> allOf,
      Function<AnyOf, T> anyOf
      ) {
      return anyOf.apply(this);
    }
  }
}
