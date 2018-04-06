/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.graph.boundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import cz.medel.roleminimization.graph.entity.Graph;
import cz.medel.roleminimization.graph.entity.Vertex;


/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@Component
public class GraphService {

    private List<List<Vertex>> mcp = new ArrayList<>();

    public void reduce(Graph graph) {
        clique(new ArrayList<>(graph.getVertices()));

        System.out.println();

    }

    public List<Vertex> clique(List<Vertex> vertices) {
        ArrayList<Vertex> result = new ArrayList<>();

        if (vertices.isEmpty()) {
            return result;
        }

        if (vertices.get(0).getAdjacent().isEmpty()) {
            mcp.add(Arrays.asList(vertices.get(0)));
            clique(vertices.subList(1, vertices.size()));
        } else {
            Vertex dominator = vertices.stream()
                    .filter(v -> v.isDominator(vertices.get(0)))
                    .findFirst()
                    .get();

            ArrayList<Vertex> withoutDominator = new ArrayList<>(vertices);
            withoutDominator.remove(dominator);

            List<Vertex> clique = clique(withoutDominator);
            if (clique.contains(vertices.get(0))) {
                clique.add(dominator);
            }

            mcp.add(clique);
        }

        return result;
    }
}
