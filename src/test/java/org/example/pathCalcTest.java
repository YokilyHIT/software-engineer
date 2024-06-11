import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.*;
import org.example.pathCalc.PathResult;
import org.example.pathCalc;

import java.io.File;
import java.util.Scanner;

class CalcShortestPathTest {

    @Test
    void testShortestPathExists() {
        //计算出工作目录绝对路径
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:"+workDir);

        String filepath = "test.txt";
        //参数传递给fileModule
        File file = new File(workDir,filepath);
        filepath = file.getAbsolutePath();

        fileModule FileModule = new fileModule(filepath);

        FileModule.loadFile_formTree();
        //画有向图
        nodeList tree = FileModule.fileNodes;

        pathCalc Pathcalc = new pathCalc();
        String word1 ="works";
        String word2 ="genius" ;
        PathResult result= Pathcalc.calcShortestPath(word1,word2,tree);

        assertNotNull(result);
        assertEquals(5, result.length);
        System.out.println("testShortestPathExists succeed");

        //assertEquals(1, result.getLength());
    }

    @Test
    void testNoPathExists() {
        //计算出工作目录绝对路径
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:"+workDir);

        String filepath = "test.txt";
        //参数传递给fileModule
        File file = new File(workDir,filepath);
        filepath = file.getAbsolutePath();

        fileModule FileModule = new fileModule(filepath);

        FileModule.loadFile_formTree();
        //画有向图
        nodeList tree = FileModule.fileNodes;

        pathCalc Pathcalc = new pathCalc();
        String word1 ="love";
        String word2 ="test" ;
        PathResult result= Pathcalc.calcShortestPath(word1,word2,tree);

        System.out.println("testNoPathExists succeed");
        //assertEquals(5, result.length);
    }

    @Test
    void testSameNode() {
        //计算出工作目录绝对路径
        String currDir = System.getProperty("user.dir");
        String relativeDir = "\\src\\main\\java\\org\\example\\";
        String workDir = currDir + relativeDir;
        System.out.println("workDir:"+workDir);

        String filepath = "test.txt";
        //参数传递给fileModule
        File file = new File(workDir,filepath);
        filepath = file.getAbsolutePath();

        fileModule FileModule = new fileModule(filepath);

        FileModule.loadFile_formTree();
        //画有向图
        nodeList tree = FileModule.fileNodes;

        pathCalc Pathcalc = new pathCalc();
        String word1 ="love";
        String word2 ="love" ;
        PathResult result= Pathcalc.calcShortestPath(word1,word2,tree);

        assertNotNull(result);
        assertEquals(0, result.length);
        System.out.println("testSameNode succeed");
        //assertEquals(5, result.length);
    }
}
