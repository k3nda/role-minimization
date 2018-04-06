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
public class Vertex {

    private final Integer id;

    private final VertexType type;

    private Set<Vertex> adjacent;

    public Vertex(Integer id, VertexType type) {
        this.id = id;
        this.type = type;
        this.adjacent = new HashSet<>();
    }

    public void addAdjacent(Vertex vertex) {
        adjacent.add(vertex);
    }

    public boolean isDominator(Vertex vertex) {
        Set<Vertex> dominators = new HashSet<>(adjacent);
        dominators.add(this);

        Set<Vertex> vertices = new HashSet<>(vertex.getAdjacent());
        vertices.add(vertex);

        return dominators.containsAll(vertices);
    }

    public Integer getId() {
        return id;
    }

    public VertexType getType() {
        return type;
    }

    public Set<Vertex> getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(Set<Vertex> adjacent) {
        this.adjacent = adjacent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Vertex vertex = (Vertex) o;

        if (id != null ? !id.equals(vertex.id) : vertex.id != null) { return false; }
        return type == vertex.type;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("type", type)
                .append("adjacent", adjacent)
                .toString();
    }
}
