/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.graph.control;

import java.util.List;

import org.springframework.stereotype.Component;

import cz.medel.roleminimization.graph.entity.Vertex;


/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@Component
public class GraphManager {

    public boolean induceBiclique(List<Vertex> leftVertices, List<Vertex> rightVertices) {
        return leftVertices.stream()
                .allMatch(leftVertex -> rightVertices.stream()
                        .allMatch(rightVertex -> leftVertex.getAdjacent().contains(rightVertex)));
    }
}
