/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.execution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.medel.roleminimization.graph.boundary.GraphService;
import cz.medel.roleminimization.graph.entity.Graph;
import cz.medel.roleminimization.load.FileService;


/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@Component
public class MainService {

    @Autowired
    private FileService fileService;

    @Autowired
    private GraphService graphService;

    public void execute() {
        Graph graph = fileService.loadGraph("dataset/figure1.txt");

        graphService.createEdgeDualGraph(graph);
        /*graph.getVertices().forEach(vertex -> System.out.println(
                String.format(
                        "Vertex type=%s, id=%d is dominator for type=%s, id=%d? %s",
                        first.getType(),
                        first.getId(),
                        vertex.getType(),
                        vertex.getId(),
                        first.isDominator(vertex))));*/
    }
}
