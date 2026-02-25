package com.beyond.linkedlist.practice;

public interface List<E> {
    void addFirst(E element);

    void addLast(E element);

    void add(int index, E element);

    E get(int index);

    int size();

    boolean contains(E element);

    void removeFirst();

    void removeLast();

    void remove(int index);

    void remove(E element);
}
