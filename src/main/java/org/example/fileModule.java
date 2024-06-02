package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class fileModule {
    //请写入本地存放文本的绝对路径
    String filePath = "";
    //String filePath = "F:\\Campus\\engineer\\softwareEngineering\\lab1\\mySW\\src\\main\\java\\org\\example\\test.txt";
   // String filePath = "E:\\javaProject\\lab1_1\\src\\main\\java\\org\\example\\test.txt";
    nodeList fileNodes = new nodeList();

    //文件路径作为参数的构造函数
    public  fileModule(String fpath){
        this.filePath = fpath;
    }
    void loadFile_formTree(){
    
        {
            try {
                // 获取文件路径
                Path path = Paths.get(this.filePath);

                // 使用readAllLines方法按行读取文件内容
                List<String> lines = Files.readAllLines(path);

                Pattern pattern = Pattern.compile("[\\W_]+");
                //全文所有word
                List<String> words = new ArrayList<>();
                // 遍历每一行
                for (String line : lines) {
                    // 将换行符、标点符号和非字母字符替换为空格
                    String cleanedLine = pattern.matcher(line).replaceAll(" ");

                    // 使用空格分割 cleanedLine，并过滤掉空字符串，同时转换为小写
                    List<String> lineWords = Arrays.stream(cleanedLine.split("\\s+"))
                            .map(String::toLowerCase)
                            .filter(word -> !word.isEmpty())
                            .collect(Collectors.toList());
                    words.addAll(lineWords);
                }

                node buffer = new node();
                for (String word : words.toArray(new String[0])) {
                    // 如果单词非空，则添加到数组中中
                    if (!word.isEmpty()) {
                        if (buffer.name == null) {                            buffer.name = word;
                            //System.out.println("First word: "+buffer.name);
                            fileNodes.add_node(buffer);
                        }
                        else{
                            if(fileNodes.is_node_exist(word)){
                                boolean is_child_exist = buffer.isChildExist(fileNodes.findNodeByName(word));
                                //父节点已有该子节点的边，边权重加1
                                if(is_child_exist){
                                    //System.out.println(word+"已存在，与"+buffer.name+"的边权值+1");
                                    buffer.addWeight(buffer.findChildByName(word));
                                }else {
                                    //父节点没有指向该子节点的边，则新建边，将边加到边集合edges,将边加到父节点的child里
                                    //System.out.println(word+"与"+buffer.name+"建立新边");
                                    edge newEdge = new edge();
                                    newEdge.fatherNode = buffer;
                                    newEdge.weight = 1;
                                    newEdge.childNode = fileNodes.findNodeByName(word);
                                    fileNodes.add_edge(newEdge);

                                    buffer.addChild(newEdge);
                                }
                                    //buffer.addChild(fileNodes.findNodeByName(word));
                                    //更新buffer
                                    buffer = fileNodes.findNodeByName(word);
                            }
                                else{
                                    //第一次出现的单词，直接成为buffer的子节点
                                    //System.out.println(word+"第一次出现");
                                    node NewNode = new node();
                                    NewNode.name = word;
                                    NewNode.father =buffer;

                                fileNodes.add_node(NewNode);

                                edge NewEdge = new edge();
                                NewEdge.fatherNode = buffer;
                                NewEdge.weight = 1;
                                NewEdge.childNode = NewNode;

                                fileNodes.add_edge(NewEdge);

                                buffer.addChild(NewEdge);
                                //更新buffer
                                buffer = NewNode;

                            }


                        }

                    }
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //


        }
    }
}

