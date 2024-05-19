package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class graphDrawer {
    public String generateDirectedGraph(nodeList fileNodes){
        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");

        for (node Node : fileNodes.returnAllNode()) {
            dot.append("\t").append(Node.name).append(";\n");

            for (edge Edge : Node.childlist) {
                dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name).append(" [label=\"").append(Edge.weight).append("\"];\n");
            }
        }

        dot.append("}\n");

        return dot.toString();
    }

    public String generateHighLightGraph(nodeList fileNodes, List<node> highlightPath){
        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");

        for (node Node : fileNodes.returnAllNode()) {
            if (highlightPath.contains(Node.name)) {
                dot.append("\t").append(Node.name).append(" [color=red, fontcolor=red];\n");
            } else {
                dot.append("\t").append(Node.name).append(";\n");
            }

            for (edge Edge : Node.childlist) {
                if (highlightPath.contains(Node.name) && highlightPath.contains(Edge.childNode.name)) {
                    dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name)
                            .append(" [label=\"").append(Edge.weight).append("\", color=red, fontcolor=red];\n");
                } else {
                    dot.append("\t").append(Node.name).append(" -> ").append(Edge.childNode.name)
                            .append(" [label=\"").append(Edge.weight).append("\"];\n");
                }
            }
        }

        dot.append("}\n");
        return dot.toString();
    }

    public void drawDirectedGraph(nodeList fileNodes, String fileName) throws IOException {
        String dotSource = generateDirectedGraph(fileNodes);

        Graphviz graphviz = Graphviz.fromString(dotSource);
        graphviz.render(Format.PNG).toFile(new File(fileName));

    }

    private void displayImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(), image.getHeight());
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}


