package org.example;

import java.util.*;

public class pathCalc {

    public static class PathResult {
        public List<node> path;
        public int length;

        public PathResult(List<node> path, int length) {
            this.path = path;
            this.length = length;
        }
    }


    // 如果找到从word1到word2的路径，则输出路径和长度，否则返回null
    PathResult calcShortestPath(String word1, String word2, nodeList fileNodes) {
        Map<node, Integer> distance = new HashMap<>();
        Map<node, node> predecessor = new HashMap<>();
        for (node Node : fileNodes.returnAllNode()) {
            distance.put(Node, Integer.MAX_VALUE);
            predecessor.put(Node, null);
        }

        node startNode = fileNodes.findNodeByName(word1);
        node endNode = fileNodes.findNodeByName(word2);
        distance.put(startNode, 0);

        Set<node> visited = new HashSet<>();

        while (!visited.contains(endNode)) {
            node currentNode = getNodeWithLowestDistance(distance, visited);
            if (currentNode == null) {
                // 不可达
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
                }
            }
        }

        List<node> shortestPath = new ArrayList<>();
        node currentNode = endNode;
        while (currentNode != null) {
            shortestPath.add(currentNode);
            currentNode = predecessor.get(currentNode);
        }
        Collections.reverse(shortestPath);

        return new PathResult(shortestPath, distance.get(endNode));
    }

    // 计算出该单词到图中其他任一单词的最短路径，并逐项展示出来
    Map<node, PathResult> calcShortestPathsFromNode(String word, nodeList fileNodes) {
        Map<node, PathResult> results = new HashMap<>();
        Map<node, Integer> distance = new HashMap<>();
        Map<node, node> predecessor = new HashMap<>();
        for (node Node : fileNodes.returnAllNode()) {
            distance.put(Node, Integer.MAX_VALUE);
            predecessor.put(Node, null);
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
                }
            }
        }

        for (node Node : fileNodes.returnAllNode()) {
            if (!Node.equals(startNode)) {
                List<node> shortestPath = new ArrayList<>();
                node currentNode = Node;
                while (currentNode != null) {
                    shortestPath.add(currentNode);
                    currentNode = predecessor.get(currentNode);
                }
                Collections.reverse(shortestPath);
                results.put(Node, new PathResult(shortestPath, distance.get(Node)));
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
