package org.example;

import java.util.*;

public class pathCalc {

    public static class PathResult {
        public List<node> path;
        public List<edge> pathEdge;
        public int length;

        public PathResult(List<node> path,List<edge> pathEdge, int length) {
            this.path = path;
            this.length = length;
            this.pathEdge = pathEdge;
        }
    }


    // If a path is found from word1 to word2, output the path and length; otherwise, return null
    public PathResult calcShortestPath(String word1, String word2, nodeList fileNodes) {
        Map<node, Integer> distance = new HashMap<>();
        Map<node, node> predecessor = new HashMap<>();
        Map<node, edge> predecessorEdge = new HashMap<>();

        for (node Node : fileNodes.returnAllNode()) {
            distance.put(Node, Integer.MAX_VALUE);
            predecessor.put(Node, null);
            predecessorEdge.put(Node, null);
        }

        node startNode = fileNodes.findNodeByName(word1);
        node endNode = fileNodes.findNodeByName(word2);
        distance.put(startNode, 0);

        Set<node> visited = new HashSet<>();

        while (!visited.contains(endNode)) {
            node currentNode = getNodeWithLowestDistance(distance, visited);
            if (currentNode == null) {
                // Not reachable
                return null;
            }
            visited.add(currentNode);

            for (edge Edge : currentNode.childlist) {
                node neighborNode = Edge.childNode;
                int edgeWeight = Edge.weight;
                int tentativeDistance = distance.get(currentNode) + edgeWeight;
                if (tentativeDistance < distance.get(neighborNode)) {
                    distance.put(neighborNode, tentativeDistance);
                    predecessor.put(neighborNode, currentNode);
                    predecessorEdge.put(neighborNode, Edge);
                }
            }
        }

        List<node> shortestPath = new ArrayList<>();
        List<edge> pathEdges = new ArrayList<>();
        node currentNode = endNode;

        while (currentNode != null) {
            shortestPath.add(currentNode);
            edge edgeToCurrent = predecessorEdge.get(currentNode);
            if (edgeToCurrent != null) {
                pathEdges.add(edgeToCurrent);
            }
            currentNode = predecessor.get(currentNode);
        }

        Collections.reverse(shortestPath);
        Collections.reverse(pathEdges);

        return new PathResult(shortestPath, pathEdges, distance.get(endNode));
    }

    // Calculate the shortest path from the given word to all other words in the graph and display them
    public Map<node, PathResult> calcShortestPathsFromNode(String word, nodeList fileNodes) {
        if(!fileNodes.is_node_exist(word)){
            return null;
        }
        Map<node, PathResult> results = new HashMap<>();
        Map<node, Integer> distance = new HashMap<>();
        Map<node, node> predecessor = new HashMap<>();
        Map<node, edge> predecessorEdge = new HashMap<>();

        for (node Node : fileNodes.returnAllNode()) {
            distance.put(Node, Integer.MAX_VALUE);
            predecessor.put(Node, null);
            predecessorEdge.put(Node, null);
        }

        node startNode = fileNodes.findNodeByName(word);
        distance.put(startNode, 0);

        Set<node> visited = new HashSet<>();

        while (visited.size() < fileNodes.returnAllNode().size()) {
            node currentNode = getNodeWithLowestDistance(distance, visited);
            if (currentNode == null) {
                break;
            }
            visited.add(currentNode);

            for (edge Edge : currentNode.childlist) {
                node neighborNode = Edge.childNode;
                int edgeWeight = Edge.weight;
                int tentativeDistance = distance.get(currentNode) + edgeWeight;
                if (tentativeDistance < distance.get(neighborNode)) {
                    distance.put(neighborNode, tentativeDistance);
                    predecessor.put(neighborNode, currentNode);
                    predecessorEdge.put(neighborNode, Edge);
                }
            }
        }

        for (node Node : fileNodes.returnAllNode()) {
            if (!Node.equals(startNode)) {
                List<node> shortestPath = new ArrayList<>();
                List<edge> pathEdges = new ArrayList<>();
                node currentNode = Node;

                while (currentNode != null) {
                    shortestPath.add(currentNode);
                    edge edgeToCurrent = predecessorEdge.get(currentNode);
                    if (edgeToCurrent != null) {
                        pathEdges.add(edgeToCurrent);
                    }
                    currentNode = predecessor.get(currentNode);
                }

                Collections.reverse(shortestPath);
                Collections.reverse(pathEdges);

                results.put(Node, new PathResult(shortestPath, pathEdges, distance.get(Node)));
            }
        }

        return results;
    }

    private static node getNodeWithLowestDistance(Map<node, Integer> distance, Set<node> visited) {
        int minDistance = Integer.MAX_VALUE;
        node minNode = null;
        for (Map.Entry<node, Integer> entry : distance.entrySet()) {
            node Node = entry.getKey();
            int dist = entry.getValue();
            if (!visited.contains(Node) && dist < minDistance) {
                minDistance = dist;
                minNode = Node;
            }
        }
        return minNode;
    }

}
