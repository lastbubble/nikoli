package com.lastbubble.nikoli;

import java.io.BufferedReader;

public interface PuzzleFactory<C,E> {

  Puzzle<C> read(BufferedReader reader);

  Iterable<? extends Solution<C,E>> solve(Puzzle<C> puzzle);
}
