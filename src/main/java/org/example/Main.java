package org.example;

import java.io.IOException;
import java.util.*;
import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        //计算出工作目录绝对路径
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:"+workDir);
        //获取用户输入的文件路径/文件名
        System.out.println("Input text file:");
        Scanner scanner = new Scanner(System.in);
        String filepath = scanner.nextLine();
        //参数传递给fileModule
        File file = new File(workDir,filepath);
        filepath = file.getAbsolutePath();

        nodeList tree;
        graphDrawer Drawer = new graphDrawer();
        try{
            //构造单词树
            fileModule FileModule = new fileModule(filepath);
            FileModule.loadFile_formTree();
            //画有向图
            tree = FileModule.fileNodes;

            //System.out.println("Input directed graph file name(format: .png):");
            //String outputFile = scanner.nextLine();
            //outputFile = workDir + outputFile;
            //String filename = "F:\\Campus\\engineer\\softwareEngineering\\lab1\\mySW\\src\\main\\java\\org\\example\\graph.png";
            Drawer.drawDirectedGraph(tree,"origin.png");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("initialize already done...you can enter command now.");

        while (true) {

            System.out.println(">3.查询桥接词");
            System.out.println(">4.根据输入文本查询桥接词生成新文本");
            System.out.println(">5.计算最短路径");
            System.out.println(">6.随机游走");
            System.out.println(">Enter 'exit' to end.");
            System.out.print("> ");
            String command = scanner.nextLine();
            String result = "";
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                //break;
                scanner.close();
                System.exit(0);
            }
            //3-查询桥接词
            if(command.equals("3")){
                System.out.print("input two words: ");
                String words = scanner.nextLine();
                result = processCommand(tree,words,3);
            }
            //4-根据输入文本查询桥接词生成新文本
            if(command.equals("4")){
                System.out.print("input text: ");
                String text = scanner.nextLine();
                result = processCommand(tree,text,4);
            }
            //5-计算最短路径
            if(command.equals("5")){
                System.out.print("input one word or two words: ");
                String words = scanner.nextLine();
                result = processCommand(tree,words,5);
                //File f = new File("path.png");
                //Drawer.displayImage(f);
            }
            //6-随机游走
            if(command.equals("6")){
                result = processCommand(tree,"",6);
            }
           // String result = processCommand(command);
            System.out.print(result);
        }

        //scanner.close();
    }

    public static String processCommand(nodeList nodelist,String words,int comd) throws IOException {
        //int result = -1;
        queryBridgeWords query = new queryBridgeWords(nodelist);
        String[] word = words.split(" ");
        if(comd==3){
           //queryBridgeWords query = new queryBridgeWords(nodelist);
           //result = query.queryBW(word[0],word[1]);
            if(word.length!=2){
                System.out.println("enter two words");
                return null;
            }
            query.queryBW(word[0],word[1]);
            return query.output;
        }
        if(comd==4){
            generateNewText func = new generateNewText();
            String  output = func.generateNewText(words, nodelist);
            System.out.println(output);

        }
        if(comd ==5){

            String[] word_5 = words.split(" ");
            pathCalc pathcalc = new pathCalc();
            graphDrawer lightedGraph = new graphDrawer();
            if (word_5.length == 2){
                pathCalc.PathResult pathResult = pathcalc.calcShortestPath(word_5[0], word_5[1],nodelist);
                if(pathResult == null){
                    System.out.println("no such path");
                }
                else{
                    lightedGraph.drawHighlightGraph(nodelist, pathResult.path,pathResult.pathEdge,"h_graph.png");
                    System.out.println("The shortest distance between " + word_5[0] + " and " + word_5[1] + " is "+ pathResult.length);
                }

            } else if (word_5.length == 1) {
                Map<node, pathCalc.PathResult> resultMap= pathcalc.calcShortestPathsFromNode(word_5[0], nodelist);
                // 打印最短路径信息
                if(resultMap!=null){
                    for (Map.Entry<node, pathCalc.PathResult> entry : resultMap.entrySet()) {
                        node targetNode = entry.getKey();
                        pathCalc.PathResult result = entry.getValue();

                        System.out.print("Shortest path to " + targetNode.name + ": ");
                        for (node pathNode : result.path) {
                            System.out.print(pathNode.name + " ");
                        }

                        System.out.println("[with distance] " + result.length);
                    }
                }
                else{
                    System.out.println("no such word");
                }


            }else {
                System.out.println("Enter one word or two");
            }

        }
        if(comd == 6){
            System.out.println("Enter 's' to end the random walk:");
            randomWalk random = new randomWalk();
            String result = random.randomWalk(nodelist);
            //System.out.println(result);


        }
        return  "";
    }

}