package com.beyond.queue.practice;

public class LinkedQueue<E> implements Queue<E> {
    // 큐에 저장된 노드의 수
    private int size;

    // 큐의 첫 번째 노드
    private Node<E> front;

    // 큐의 마지막 노드
    private Node<E> rear;

    public LinkedQueue() {
        this.size = 0;
        this.front = null;
        this.rear = null;
    }

    @Override
    public void enqueue(E element) {
        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            this.front = newNode;
        } else {
            this.rear.next = newNode;
        }

        this.rear = newNode;

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
    public E dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("큐가 비어 있습니다.");
        }

        Node<E> tempNode = front;
        E element = tempNode.data;

        front = tempNode.next;
        tempNode.data = null;
        tempNode.next = null;

        size--;

        return element;
    }

    @Override
    public boolean contains(E element) {
        boolean result = false;
        Node<E> currentNode = front;

        while (currentNode != null) {
            if (currentNode.data.equals(element)) {
                result = true;

                break;
            }

            currentNode = currentNode.next;
        }

        return result;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("큐가 비어 있습니다.");
        }

        return front.data;
    }

    @Override
    public String toString() {
        Node<E> currentNode = this.front;
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        while (currentNode != null) {
            sb.append(currentNode.data).append(", ");

            currentNode = currentNode.next;
        }

        if (sb.lastIndexOf(",") != -1) {
            sb.replace(sb.lastIndexOf(","), sb.length(), "");
        }

        sb.append("]");

        return sb.toString();
    }

    private static class Node<E> {
        private E data;

        private Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
}
