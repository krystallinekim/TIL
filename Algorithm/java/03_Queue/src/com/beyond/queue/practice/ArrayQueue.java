package com.beyond.queue.practice;

public class ArrayQueue<E> implements Queue<E> {
    // 큐의 첫 번째 데이터가 저장되어 있는 배열의 인덱스
    private int front;

    // 큐의 마지막 데이터가 저장되어 있는 배열의 인덱스
    private int rear;
    
    // 큐의 최대 크기
    private final int maxSize;

    // 큐에 저장된 데이터의 수
    private int size;

    // 데이터를 저장할 배열
    private final Object[] elements;

    public ArrayQueue() {
        this(16);
    }

    public ArrayQueue(int maxSize) {
        this.front = 0;
        this.rear = -1;
        this.maxSize = maxSize;
        this.size = 0;
        this.elements = new Object[maxSize];
    }

    @Override
    public void enqueue(E element) {
        if (size == maxSize) {
            throw new RuntimeException("큐가 가득 찼습니다.");
        }

        if (rear == (maxSize - 1)) {
            rear = -1;
        }

        elements[++rear] = element;

        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("큐가 비어 있습니다.");
        }

        E element = (E) elements[front];

        front = (front + 1) % maxSize;

        size--;

        return element;
    }

    @Override
    public boolean contains(E element) {
        boolean result = false;
        int front = this.front;

        for (int i = 0; i < size; i++) {
            if (elements[front].equals(element)) {
                result = true;

                break;
            }

            front = (front + 1) % maxSize;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("큐가 비어 있습니다.");
        }

        return (E) elements[front];
    }

    @Override
    public String toString() {
        int front = this.front;
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i < size; i++) {
            sb.append(elements[front]).append(", ");

            front = (front + 1) % maxSize;
        }

        if (sb.lastIndexOf(",") != -1) {
            sb.replace(sb.lastIndexOf(","), sb.length(), "");
        }

        sb.append("]");

        return sb.toString();
    }
}
