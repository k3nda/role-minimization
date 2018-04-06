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

    public Graph() {
        this.vertices = new HashSet<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public Vertex addOrGetVertex(Integer id, VertexType vertexType) {
        return vertices.stream()
                .filter(vertex -> vertex.getId().equals(id) && vertex.getType().equals(vertexType))
                .findAny()
                .orElseGet(() -> {
                    Vertex newVertex = new Vertex(id, vertexType);
                    vertices.add(newVertex);
                    return newVertex;
                });
    }
    public Vertex getVertex(Integer id, VertexType vertexType) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("vertices", vertices)
                .toString();
    }
}
