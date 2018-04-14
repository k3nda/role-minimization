/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.graph.entity;

/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
public class Edge {

    private final Vertex vertexA;

    private final Vertex vertexB;

    public Edge(Vertex vertexA, Vertex vertexB) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
    }

    public Vertex getVertexA() {
        return vertexA;
    }

    public Vertex getVertexB() {
        return vertexB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Edge edge = (Edge) o;

        if (vertexA != null ? !vertexA.equals(edge.vertexA) : edge.vertexA != null) { return false; }
        return vertexB != null ? vertexB.equals(edge.vertexB) : edge.vertexB == null;
    }

    @Override
    public int hashCode() {
        int result = vertexA != null ? vertexA.hashCode() : 0;
        result = 31 * result + (vertexB != null ? vertexB.hashCode() : 0);
        return result;
    }
}
