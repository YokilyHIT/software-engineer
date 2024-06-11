package org.example;

import org.w3c.dom.NodeList;

import java.util.Arrays;
import java.util.stream.Collectors;

import java.util.regex.Pattern;

public class generateNewText {
    //nodeList fileNodes;



    String generateNewText(String inputText, nodeList fileNodes){
        String[] words = inputText.split("\\s+");
        StringBuilder output = new StringBuilder();
        // 定义一个正则表达式，匹配标点符号
        String punctuationRegex = "[\\p{Punct}]";
        String buffer = null;
        node bufferNode = null;
        boolean isBufferNodeValid = false;
        boolean isBufferValid = false;
        for (String word : words){
            if(!word.isEmpty()){
                if (Pattern.matches(punctuationRegex, word)) {
                    output.append(word).append(" ");
                    buffer = word;
                    isBufferNodeValid =false;
                    isBufferValid = false;
                } else {
                    //第一个词
                    if(buffer==null){
                        //将第一个词转换成小写
                        char firstChar = word.charAt(0);
                        String remainingString = word.substring(1);
                        // 将首字母转换为小写
                        char lowerFirstChar = Character.toLowerCase(firstChar);
                        // 任选一种转换规则，以下示例中选择将首字母转换为大写

                        buffer = lowerFirstChar + remainingString;
                        isBufferValid = true;
                        output.append(word).append(" ");
                        if(fileNodes.is_node_exist(buffer)){
                            bufferNode = fileNodes.findNodeByName(buffer);
                            isBufferNodeValid = true;

                        }
                        else{
                            isBufferNodeValid = false;
                        }
                    }
                    //非第一个词
                    else {
                        buffer = word;
                        boolean is_bridge_word_exist = false;
                        if(!isBufferValid || !isBufferNodeValid){
                            output.append(word).append(" ");
                            }
                        else {
                            //选桥梁节点
                            for(edge child : bufferNode.childlist){
                                if(child.childNode.findChildByName(word)!=null){
                                    output.append(child.childNode.name).append(" ").append(word).append(" ");
                                    is_bridge_word_exist = true;
                                    break;
                                }
                            }
                            if(! is_bridge_word_exist){
                                output.append(word).append(" ");
                            }
                        }
                        if(fileNodes.is_node_exist(word)){
                            bufferNode = fileNodes.findNodeByName(word);
                            isBufferNodeValid = true;
                        }
                        else{
                            isBufferNodeValid = false;
                        }
                        isBufferValid = true;
                    }
                }

            }
        }
        return output.toString();
    }


}
