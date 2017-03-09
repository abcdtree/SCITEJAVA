/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.util.*;
/**
 *
 * @author Jianshu
 */
public class MySCITE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        // TODO code application logic here
        SciteTree mTree = SciteTree.makeASciteTree("./CsvTrees/VAF-SIMU-NUM-0.csv");
        //System.out.println(mTree.size());
        ArrayList<String> tArray = mTree.getNames();
        //System.out.println(tArray.size());
        AncestorMatrix am = mTree.getAncestorMatrix();
        System.out.println(am);
        System.out.println(am.getNameSpace());
        //SciteTree mTree2 = SciteTree.makeASciteTree(am, tArray);
        //System.out.println(mTree2.getNames());
        DataMatrix dm = DataMatrix.getDataMatrix("./ExperimentData/MutMatrix-VAF-SIMU-NUM-0.csv", "./NameSpace/MutMatrix-VAF-SIMU-NUM-0.txt");
        System.out.println(dm.getNameSpace());
        
        am.reArrange(dm.getNameSpace());
        System.out.println(am);
        //System.out.println(dm);
        
        double alpha = 0.00001;
        double beta = 0.00001;
        
        System.out.println(am.getScore(dm, alpha, beta));
        */
        
        //move test
        int[][] testMatrix = {{1,1,1,1,1,1}, {0,1,0,1,0,0},{0,0,1,0,1,1},{0,0,0,1,0,0},{0,0,0,0,1,0},{0,0,0,0,0,1}};
        String[] names = {"1","2","3","4","5","6"};
        AncestorMatrix am = new AncestorMatrix(testMatrix, new MutationNameSpace(names));
        System.out.println(am);
        am.swapSubtree(1, 4);
        System.out.println(am);
    }
    
}
