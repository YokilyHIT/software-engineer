package org.example;

import java.io.IOException;
import java.net.FileNameMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        /* 测试阶段一：构建树
        nodeList NodeList = new nodeList();
        node Node1 = new node();
        NodeList.add_node(Node1);
        Node1.name = "1";
        for (node Node : NodeList.returnAllChild()){
            System.out.println(Node.name + "\n");
        }
        edge Edge = new edge();
        Edge.fatherNode =Node1;
        node Node2 = new node();
        Node2.name = "2";
        NodeList.add_node(Node2);
        edge Edge1 = new edge();
        Edge1.fatherNode = Node1;
        Edge1.childNode = Node2;
        Edge1.weight = 1;
        NodeList.add_edge(Edge1);
        Node1.addChild(Edge1);

        for (node Node : NodeList.returnAllChild()){
        */
        /*测试阶段二：读文件构建树
        fileModule FileModule = new fileModule();
        FileModule.loadFile_formTree();
        nodeList tree = FileModule.fileNodes;
        for (node Node : tree.returnAllNode()){
            System.out.println(Node.name + ":");
            for (edge Edgek : Node.childlist){
                System.out.println(Edgek.childNode.name + Edgek.weight);
            }
        }
        */
        fileModule test3 = new fileModule();
        test3.loadFile_formTree();
        nodeList tree = test3.fileNodes;
        graphDrawer GraphDrawer = new graphDrawer();
        String fileName = "E:\\javaProject\\lab1_1\\test3.png";
        GraphDrawer.drawDirectedGraph(tree,fileName);



    }
}