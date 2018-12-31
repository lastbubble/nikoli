package com.lastbubble.nikoli;

import java.util.Set;

public interface Solution<C,E> extends Rasterable {

  Puzzle<C> puzzle();

  Set<E> elements();
}
