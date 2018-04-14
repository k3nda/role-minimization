/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.graph.entity;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
public class Graph {

    private Set<Vertex> vertices;

    private Set<Edge> edges;

    public Graph() {
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }

    public void addEdge(Vertex vertexA, Vertex vertexB) {
        if (edges.contains(new Edge(vertexA, vertexB)) || edges.contains(new Edge(vertexB, vertexA))) {
            return;
        }

        edges.add(new Edge(vertexA, vertexB));
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public Vertex addOrGetVertex(String id, VertexType vertexType) {
        return vertices.stream()
                .filter(vertex -> vertex.getId().equals(id) && vertex.getType().equals(vertexType))
                .findAny()
                .orElseGet(() -> {
                    Vertex newVertex = new Vertex(id, vertexType);
                    vertices.add(newVertex);
                    return newVertex;
                });
    }
    public Vertex getVertex(String id, VertexType vertexType) {
        return vertices.stream()
                .filter(vertex -> vertex.getId().equals(id) && vertex.getType().equals(vertexType))
                .findFirst()
                .get();
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("vertices", vertices)
                .toString();
    }
}
