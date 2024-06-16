package org.example;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;




public class QueryBridgeWordsTest {
    private QueryBridgeWords queryBridgeWords;
    private final String words_12 = "the and";
    private final String words_3 = "to and";
    private final String words_4 = "the is";
    private final String[] word_12 = words_12.split(" ");
    private final String[] word_3 = words_3.split(" ");
    private final String[] word_4 = words_4.split(" ");
    @Before
    public void setUp() throws IOException {
        // 创建测试用的有向图
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:"+workDir);
        String filepath = "test.txt";
        File file = new File(workDir,filepath);
        filepath = file.getAbsolutePath();

        nodeList tree;
        fileModule FileModule = new fileModule(filepath);
        FileModule.loadFile_formTree();
        tree = FileModule.fileNodes;

        queryBridgeWords = new QueryBridgeWords(tree);

    }
    @Test
    public void TestqueryBW_1_2() {
       queryBridgeWords.queryBW(word_12[0],word_12[1]);
       String result = queryBridgeWords.output;
       assertEquals("condition 1/2:Have bridge words.","The bridge words from 'the to 'and' : space,wonders.\n",result);
    }
    @Test
    public void TestqueryBW_3() {
        queryBridgeWords.queryBW(word_3[0],word_3[1]);
        String result = queryBridgeWords.output;
        assertEquals("condition 3:No bridge words.","No bridge words from 'to' to 'and'\n",result);
    }
    @Test
    public void TestqueryBW_4() {
        queryBridgeWords.queryBW(word_4[0],word_4[1]);
        String result = queryBridgeWords.output;
        assertEquals("condition 4:Not in the graph.","No 'the' or 'is' in the graph!\n",result);
    }

}