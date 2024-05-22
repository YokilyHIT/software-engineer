package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class randomWalk {
    private final AtomicBoolean stopFlag = new AtomicBoolean(false);
    private final Scanner scanner = new Scanner(System.in); // 将 Scanner 对象设为类的成员变量
    private Thread monitorThread; // 用于监控用户输入的线程

    public String randomWalk(nodeList fileNodes) {
        Random random = new Random();
        StringBuilder output = new StringBuilder();
        List<node> allNodes = fileNodes.returnAllNode();
        if (allNodes.isEmpty()) {
            output.append("The graph is empty.");
            return output.toString();
        }

        node currentNode = allNodes.get(random.nextInt(allNodes.size()));
        Set<edge> visitedEdges = new HashSet<>();
        List<String> walkResult = new ArrayList<>();

        output.append("Starting random walk from node: ").append(currentNode.name).append("\n");
        System.out.println("Starting random walk from node: " + currentNode.name);
        walkResult.add(currentNode.name);

        monitorThread = new Thread(this::monitorStopCommand);
        monitorThread.start();

        while (!stopFlag.get()) {
            if (currentNode.childlist.isEmpty()) {
                output.append("No more outgoing edges from node: ").append(currentNode.name).append("\n");
                System.out.println("No more outgoing edges from node: " + currentNode.name);
                stopMonitoring();
                saveWalkResult(walkResult);
                return output.toString();
            }
            edge currentEdge = currentNode.childlist.get(random.nextInt(currentNode.childlist.size()));
            if (visitedEdges.contains(currentEdge)) {
                output.append("Encountered a repeated edge, stopping random walk.");
                System.out.println("Encountered a repeated edge, stopping random walk.");
                stopMonitoring();
                saveWalkResult(walkResult);
                return  output.toString();

            }
            visitedEdges.add(currentEdge);
            currentNode = currentEdge.childNode;
            walkResult.add(currentNode.name);

            output.append("Moved to node: ").append(currentNode.name).append("\n");
            System.out.println("Moved to node: " + currentNode.name);

            // 休眠一段时间以允许用户输入停止命令
            try {
                Thread.sleep(2000); // 每次游走停顿0.5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        saveWalkResult(walkResult);
        stopMonitoring(); // 在函数返回前停止监控线程
        return output.toString();
    }

    private void monitorStopCommand() {
        while (!Thread.currentThread().isInterrupted()) {
            //System.out.println("Enter 's' to end the random walk:");
            if (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if ("s".equalsIgnoreCase(command)) {
                    stopFlag.set(true);
                    break;
                }
            }
        }
    }

    private void stopMonitoring() {
        if (monitorThread != null && monitorThread.isAlive()) {
            monitorThread.interrupt(); // 中断监控线程
            try {
                monitorThread.join(); // 等待监控线程结束
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //scanner.close(); // 关闭 Scanner
    }

    private static void saveWalkResult(List<String> walkResult) {
        try (FileWriter writer = new FileWriter("random_walk.txt")) {
            for (String nodeName : walkResult) {
                writer.write(nodeName + " ");
            }
            System.out.println("Walk result saved to random_walk.txt");
        } catch (IOException e) {
            System.err.println("Error writing result to file: " + e.getMessage());
        }
    }



}
