package com.beyond.stack.practice;

public interface Stack<E> {
    void push(E element);

    int size();

    boolean isEmpty();

    E pop();

    boolean contains(E element);

    E peek();
}
