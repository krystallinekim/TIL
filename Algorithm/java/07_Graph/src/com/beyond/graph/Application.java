package com.beyond.graph;

import com.beyond.graph.practice.LinkedListGraph;
import com.beyond.graph.practice.MatrixGraph;

public class Application {
    public static void main(String[] args) {
        // 인접 행렬 테스트
        MatrixGraph matrixGraph = new MatrixGraph(4);
        
        // addEdge() 메소드 구현
        matrixGraph.addEdge(0, 1, 30);
        matrixGraph.addEdge(0, 2, 20);
        matrixGraph.addEdge(1, 2, 40);
        matrixGraph.addEdge(2, 3, 50);

        System.out.println(matrixGraph);
        System.out.println();

        // removeEdge() 메소드 구현
        matrixGraph.removeEdge(0, 1);
        matrixGraph.removeEdge(0, 2);

        System.out.println(matrixGraph);
        System.out.println();

        // 인접 리스트 테스트
        LinkedListGraph linkedListGraph = new LinkedListGraph(4);

        // addEdge() 메소드 구현
        linkedListGraph.addEdge(0, 1, 30);
        linkedListGraph.addEdge(0, 2, 20);
        // linkedListGraph.addEdge(1, 2, 40);
        // linkedListGraph.addEdge(2, 3, 50);

        System.out.println(linkedListGraph);
        System.out.println();

        // removeEdge() 메소드 구현
        linkedListGraph.removeEdge(0, 1);
        linkedListGraph.removeEdge(0, 2);

        System.out.println(linkedListGraph);
        System.out.println();

        // 그래프 탐색
        linkedListGraph.addEdge(0, 1, 30);
        linkedListGraph.addEdge(0, 2, 40);
        linkedListGraph.addEdge(1, 2, 50);
        linkedListGraph.addEdge(2, 0, 60);
        linkedListGraph.addEdge(2, 3, 70);
        linkedListGraph.addEdge(3, 3, 80);

        System.out.println(linkedListGraph);
        System.out.println();

        // 깊이 우선 탐색 (시작 꼭짓점은 2)
        linkedListGraph.depthFirstSearch(2);
        System.out.println();

        // 너비 우선 탐색 (시작 꼭짓점은 2)
        linkedListGraph.breadthFirstSearch(2);
        System.out.println();
    }
}
