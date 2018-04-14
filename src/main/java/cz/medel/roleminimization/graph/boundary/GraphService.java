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

    public Graph test(Graph bipartiteGraph) {
        Graph edgeDualGraph = new Graph();

        bipartiteGraph.getEdges()
                .forEach(edgeA -> {
                    String dualVertexIndex = edgeA.getVertexA().getId() + edgeA.getVertexB().getId();
                    Vertex dualVertex = edgeDualGraph.addOrGetVertex(dualVertexIndex, VertexType.NONE);

                    bipartiteGraph.getEdges()
                            .forEach(edgeB -> {
                                if (!edgeA.equals(edgeB)) {
                                    List<Vertex> leftVertices = List.of(edgeA.getVertexA(), edgeB.getVertexA());
                                    List<Vertex> rightVertices = List.of(edgeA.getVertexB(), edgeB.getVertexB());
                                    if (graphManager.induceBiclique(leftVertices, rightVertices)) {
                                        String dualVertexIndex2 = edgeB.getVertexA().getId() + edgeB.getVertexB().getId();
                                        Vertex dualVertex2 = edgeDualGraph.addOrGetVertex(dualVertexIndex2, VertexType.NONE);
                                        edgeDualGraph.addEdge(dualVertex, dualVertex2);
                                        dualVertex.addAdjacent(dualVertex2);
                                        dualVertex2.addAdjacent(dualVertex);
                                    }
                                }
                            });
                });

        return edgeDualGraph;
    }

    public Graph createEdgeDualGraph(Graph bipartiteGraph) {
        Graph edgeDualGraph = new Graph();

        bipartiteGraph.getVertices().stream()
                .filter(vertex -> !vertex.getAdjacent().isEmpty())
                .filter(vertex -> vertex.getType() == VertexType.USER)
                .forEach(
                        userVertex -> userVertex.getAdjacent().forEach(
                                permissionVertex -> {
                                    String dualIndex = userVertex.getId() + permissionVertex.getId();
                                    Vertex dualVertex = edgeDualGraph.addOrGetVertex(dualIndex, VertexType.NONE);

                                    //add adjacent - permissions
                                    permissionVertex.getAdjacent().stream()
                                            .filter(adjacent -> !adjacent.equals(userVertex))
                                            .forEach(adjacent -> {
                                                String dualIndex2 = adjacent.getId() + permissionVertex.getId();
                                                dualVertex.getAdjacent().add(edgeDualGraph.addOrGetVertex(dualIndex2, VertexType.NONE));
                                            });
                                    //add adjacent - users
                                    userVertex.getAdjacent().stream()
                                            .filter(adjacent -> !adjacent.equals(permissionVertex))
                                            .forEach(adjacent -> {
                                                String dualIndex2 = userVertex.getId() + adjacent.getId();
                                                dualVertex.getAdjacent().add(edgeDualGraph.addOrGetVertex(dualIndex2, VertexType.NONE));
                                            });

                                    //4 vertices biclique
                                    //graphManager.induceBiclique(Set.of(dualVertex), )
                                }
                        ));

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
