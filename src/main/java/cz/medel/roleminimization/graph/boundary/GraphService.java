/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.graph.boundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.medel.roleminimization.graph.control.GraphManager;
import cz.medel.roleminimization.graph.entity.Graph;
import cz.medel.roleminimization.graph.entity.Vertex;
import cz.medel.roleminimization.graph.entity.VertexType;


/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@Component
public class GraphService {

    @Autowired
    private GraphManager graphManager;

    private List<List<Vertex>> mcp = new ArrayList<>();

    public Graph createEdgeDualGraph(Graph bipartiteGraph) {
        Graph edgeDualGraph = new Graph();

        bipartiteGraph.getEdges()
                .forEach(edgeX -> {
                    String dualVertexIndex = edgeX.getVertexA().getId() + edgeX.getVertexB().getId();
                    Vertex dualVertex = edgeDualGraph.addOrGetVertex(dualVertexIndex, VertexType.NONE);

                    bipartiteGraph.getEdges()
                            .forEach(edgeY -> {
                                if (!edgeX.equals(edgeY)) {
                                    List<Vertex> leftVertices = List.of(edgeX.getVertexA(), edgeY.getVertexA());
                                    List<Vertex> rightVertices = List.of(edgeX.getVertexB(), edgeY.getVertexB());
                                    if (graphManager.induceBiclique(leftVertices, rightVertices)) {
                                        String dualVertexIndex2 = edgeY.getVertexA().getId() + edgeY.getVertexB().getId();
                                        Vertex dualVertex2 = edgeDualGraph.addOrGetVertex(dualVertexIndex2, VertexType.NONE);
                                        edgeDualGraph.addEdge(dualVertex, dualVertex2);
                                        //dualVertex.addAdjacent(dualVertex2);
                                        //dualVertex2.addAdjacent(dualVertex);
                                    }
                                }
                            });
                });

        return edgeDualGraph;
    }

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
