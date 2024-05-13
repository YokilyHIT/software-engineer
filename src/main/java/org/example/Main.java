package org.example;

public class Main {
    public static void main(String[] args) {
        /*
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
        */
        fileModule FileModule = new fileModule();
        FileModule.toformTree();
        nodeList tree = FileModule.fileNodes;
        for (node Node : tree.returnAllChild()){
            System.out.println(Node.name + ":");
            for (edge Edgek : Node.childlist){
                System.out.println(Edgek.childNode.name + Edgek.weight);
            }
        }
    }
}