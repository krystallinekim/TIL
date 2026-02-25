package com.beyond.graph.practice;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LinkedListGraph {
    // 꼭짓점의 개수
    private final int numOfVertices;

    private final List<List<Edge>> vertices;

    public LinkedListGraph(int numOfVertices) {
        this.numOfVertices = numOfVertices;
        this.vertices = new LinkedList<>();

        for (int i = 0; i < this.numOfVertices; i++) {
            this.vertices.add(new LinkedList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        vertices.get(src).add(new Edge(dest, weight));

        // 그래프가 방향이 없는 경우
        // vertices.get(dest).add(new Edge(src, weight));
    }

    public void removeEdge(int src, int dest) {
        List<Edge> vertex = vertices.get(src);

        for (Edge edge : vertex) {
            if (edge.vertex == dest) {
                vertex.remove(edge);

                break;
            }
        }
    }

    public void depthFirstSearch(int vertex) {
        boolean[] visited = new boolean[this.numOfVertices];

        depthFirstSearch(vertex, visited);
    }

    private void depthFirstSearch(int vertex, boolean[] visited) {
        System.out.print(vertex + " ");

        visited[vertex] = true;

        for (Edge edge : this.vertices.get(vertex)) {
            if (!visited[edge.vertex]) {
                depthFirstSearch(edge.vertex, visited);
            }
        }
    }

    public void breadthFirstSearch(int vertex) {
        boolean[] visited = new boolean[this.numOfVertices];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(vertex);

        while (!queue.isEmpty()) {
            vertex = queue.poll();

            System.out.print(vertex + " ");

            visited[vertex] = true;

            for (Edge edge : this.vertices.get(vertex)) {
                if (!visited[edge.vertex]) {
                    queue.add(edge.vertex);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < vertices.size(); i++) {
            for (Edge edge : vertices.get(i)) {
                sb.append(String.format("Vertex %d -> Vertex %d (Weight: %d)\n",
                        i, edge.vertex, edge.weight));
            }
        }

        return sb.toString();
    }

    private static class Edge {
        private int vertex;

        private int weight;

        private Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
