package org.example;

import java.util.*;

public class QueryBridgeWords {
    nodeList fileNodes;
    /*
    String word1_name;
    String word2_name;
    */
    List<edge> findEdge;
    //-1-初始值 0-不存在单词 1-邻居 2-存在邻接词
    int reslt =-1;
    String output ="";
    //构造函数
    public QueryBridgeWords(nodeList fn){
        //his.fileNodes = fn;
        this.fileNodes = new nodeList(fn);  //在nodeList里新增了构造函数
        this.findEdge = new ArrayList<>();
    }
    //String  queryBridgeWords(String word1, String word2){
    //String  queryBW(String word1, String word2){
    void queryBW(String word1, String word2){
        //StringBuilder output = new StringBuilder();

        //有向图中存在两个单词
        if(fileNodes.is_node_exist(word1)&&fileNodes.is_node_exist(word2)){
            reslt=0;
            for(edge Edge : fileNodes.findNodeByName(word1).childlist){
                //两个单词是邻居，无邻接词or可能有别的路径or word2是word1父节点
                if(Edge.childNode.name.equals(word2)){
                    if(reslt==2){continue;}
                    reslt = 1;
                    //findEdge.add(Edge);
                }
                for(edge secondEdge : fileNodes.findNodeByName(Edge.childNode.name).childlist){
                    if(secondEdge.childNode.name.equals(word2)){
                        reslt = 2;
                        findEdge.add(Edge);
                    }
                }
            }

            if(reslt==2){
                StringBuilder strbd = new StringBuilder();
                strbd.append("The bridge words from " + "'").append(word1).append(" to '").append(word2).append("' : ");
                boolean isFirst = true;
                for (edge Edge : findEdge){
                    if(isFirst){
                        strbd.append(Edge.childNode.name);
                        isFirst = false;
                    }
                    else{
                        strbd.append(',').append(Edge.childNode.name);
                    }
                }
                strbd.append(".\n");
                //this.output = strbd.toString();
                this.output = new String(strbd);
            }else{
                this.output += "No bridge words from "+"'" + word1+ "' to '"+word2+"'\n";
            }
        }
        //有向图不存在至少一个单词
        else{
            //if(!fileNodes.is_node_exist(word1)||!fileNodes.is_node_exist(word1)){
                reslt = 0;
                StringBuilder strbd = new StringBuilder();
                strbd.append("No '").append(word1).append("' or '").append(word2).append("' in the graph!\n");
            this.output = strbd.toString();
                // }
        }


    }
        }
        //return output.toString();
        //return reslt;
