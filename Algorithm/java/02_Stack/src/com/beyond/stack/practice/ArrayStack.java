package com.beyond.stack.practice;

public class ArrayStack<E> implements Stack<E> {
    // 스택의 최상위 요소를 가리키는 인덱스
    private int top;

    // 스택의 최대 크기
    private final int maxSize;

    // 스택 배열
    private final E[] elements;

    public ArrayStack() {
        this(16);
    }

    public ArrayStack(int maxSize) {
        this.top = -1;
        this.maxSize = maxSize;
        this.elements = (E[]) new Object[maxSize];
    }

    @Override
    public void push(E element) {
        if (top == (maxSize - 1)) {
            throw new RuntimeException("스택이 가득 찼습니다.");
        } else {
            elements[++top] = element;
        }
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("스택이 비어 있습니다.");
        } else {
            return elements[top--];
        }
    }

    @Override
    public boolean contains(E element) {
        boolean result = false;

        for (int i = 0; i <= top; i++) {
            if (elements[i].equals(element)) {
                result = true;

                break;
            }
        }

        return result;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("스택이 비어 있습니다.");
        } else {
            return elements[top];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i <= top; i++) {
            sb.append(elements[i]).append(", ");
        }

        if (sb.lastIndexOf(",") != -1) {
            sb.replace(sb.lastIndexOf(","), sb.length(), "");
        }

        sb.append("]");

        return sb.toString();
    }
}
