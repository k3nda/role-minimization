/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization.load;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import cz.medel.roleminimization.graph.entity.Graph;
import cz.medel.roleminimization.graph.entity.Vertex;
import cz.medel.roleminimization.graph.entity.VertexType;


/**
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@Component
public class FileService {

    public Graph loadGraph(String filePath) {
        Graph graph = new Graph();

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());

            List<String> lines = Files.lines(path).collect(Collectors.toList());

            IntStream.range(0, lines.size())
                    .forEach(lineIndex -> {
                        Vertex newUserVertex = new Vertex(String.valueOf(lineIndex), VertexType.USER);
                        graph.addVertex(newUserVertex);

                        String[] rights = lines.get(lineIndex).split(",");

                        IntStream.range(0, rights.length)
                                .filter(permissionIndex -> "1".equals(rights[permissionIndex]))
                                .forEach(j -> {
                                    Vertex permissionVertex = graph.addOrGetVertex(String.valueOf(j), VertexType.PERMISSION);

                                    graph.addEdge(newUserVertex, permissionVertex);
                                    newUserVertex.addAdjacent(permissionVertex);
                                    permissionVertex.addAdjacent(newUserVertex);
                                });
                    });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
