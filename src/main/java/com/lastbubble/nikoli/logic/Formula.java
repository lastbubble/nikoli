package com.lastbubble.nikoli.logic;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Formula {

  public static <T> Var<T> var(T data) { return new Var<T>(data); }

  public static Not not(Formula target) { return new Not(target); }

  public static And and(Formula left, Formula right) { return new And(left, right); }

  public static Or or(Formula left, Formula right) { return new Or(left, right); }

  public static Implies implies(Formula left, Formula right) { return new Implies(left, right); }

  public static AllOf allOf(Formula... targets) { return new AllOf(asList(targets)); }

  public static AllOf allOf(Stream<Formula> targets) { return new AllOf(targets.collect(Collectors.toList())); }

  public static AnyOf anyOf(Formula... targets) { return new AnyOf(asList(targets)); }

  public static AnyOf anyOf(Stream<Formula> targets) { return new AnyOf(targets.collect(Collectors.toList())); }

  public static boolean evaluate(Formula formula, Predicate<Formula> truth) {
    return formula.match(
      var -> truth.test(var),
      not -> !evaluate(not.target(), truth),
      and -> evaluate(and.left(), truth) && evaluate(and.right(), truth),
      or -> evaluate(or.left(), truth) || evaluate(or.right(), truth),
      implies -> !evaluate(implies.left(), truth) || evaluate(implies.right(), truth),
      allOf -> allOf.targets().allMatch(f -> evaluate(f, truth)),
      anyOf -> anyOf.targets().anyMatch(f -> evaluate(f, truth))
    );
  }

  private Formula() { }

  public abstract <T> T match(
    Function<Var<?>, T> var,
    Function<Not, T> not,
    Function<And, T> and,
    Function<Or, T> or,
    Function<Implies, T> implies,
    Function<AllOf, T> allOf,
    Function<AnyOf, T> anyOf
  );

  public static class Var<T> extends Formula {

    private final T data;

    private Var(T data) { this.data = data; }

    public T data() { return data; }

    @Override public <T> T match(
      Function<Var<?>, T> var,
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
      Function<Var<?>, T> var,
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
      Function<Var<?>, T> var,
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
      Function<Var<?>, T> var,
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
      Function<Var<?>, T> var,
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

    private AllOf(List<Formula> targets) { this.targets = targets; }

    @Override public <T> T match(
      Function<Var<?>, T> var,
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

    private AnyOf(List<Formula> targets) { this.targets = targets; }

    @Override public <T> T match(
      Function<Var<?>, T> var,
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
