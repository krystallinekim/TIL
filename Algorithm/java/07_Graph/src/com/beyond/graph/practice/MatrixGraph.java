package com.beyond.graph.practice;

public class MatrixGraph {
    // 꼭짓점의 개수
    private final int numOfVertices;

    // 인접 행렬로 사용될 2차원 배열
    private final int[][] matrix;

    // 꼭짓점의 개수가 n이라면 n * n 형태의 2차원 배열이 인접 행렬로 사용된다.
    public MatrixGraph(int numOfVertices) {
        this.numOfVertices = numOfVertices;
        this.matrix = new int[numOfVertices][numOfVertices];
    }

    public void addEdge(int src, int dest, int weight) {
        // 행이 시작 꼭짓점
        // 열이 도착 꼭짓점
        this.matrix[src][dest] = weight;

        // 그래프가 방향이 없는 경우
        // this.matrix[dest][src] = weight;
    }

    public void removeEdge(int src, int dest) {
        matrix[src][dest] = 0;

        // 그래프가 방향이 없는 경우
        // matrix[dest][src] = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] <= 0) {
                    continue;
                }

                sb.append(String.format("Vertex %d -> Vertex %d (Weight: %d)\n",i , j, matrix[i][j]));
            }
        }

        return sb.toString();
    }
}
