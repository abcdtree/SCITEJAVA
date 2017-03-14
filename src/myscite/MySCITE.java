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
        /*
        //move test
        int[][] testMatrix = {{1,1,1,1,1,1}, {0,1,0,1,0,0},{0,0,1,0,1,1},{0,0,0,1,0,0},{0,0,0,0,1,0},{0,0,0,0,0,1}};
        String[] names = {"1","2","3","4","5","6"};
        AncestorMatrix am = new AncestorMatrix(testMatrix, new MutationNameSpace(names));
        //System.out.println(am);
        //System.out.println(am.nestedSubtreeSwap(2, 4));
        //System.out.println(am);
        
        */
        /*
        //Test VAFMatrix
        double[] testVafs={1.0,0.4,0.6,0.8,0.2,0.1};        
        VAFMatrix vafM=new VAFMatrix(new MutationNameSpace(names),testVafs);
        System.out.println("VAFMatrix:");
        System.out.println(vafM);
        //Test VAFMatrix with input of vafFileDir
        String namesDir="./NameSpace/Names.txt";
        String vafFiledir="./NameSpace/VAFs.txt";
        VAFMatrix vafM2=new VAFMatrix(new MutationNameSpace(namesDir),vafFiledir);
        System.out.println("VAFMatrix2:");
        System.out.println(vafM2);
        
        System.out.println("AncestorMatrix: ");
        System.out.println(am);
        System.out.println(am.getVafScore(vafM));
        System.out.println("AncestorMatrix After a move: ");
        am.nestedSubtreeSwap(2, 4);
        System.out.println(am);
        System.out.println(am.getVafScore(vafM));
        */
        /*
        //test random tree initialize
        SciteTree random1 = SciteTree.makeARandomTree(new MutationNameSpace(names), true);
        SciteTree random2 = SciteTree.makeARandomTree(new MutationNameSpace(names), false);
        
        System.out.println(random1.getAncestorMatrix());
        System.out.println(random1.getNames());
        AncestorMatrix am2 = random1.getAncestorMatrix();
        am2.reArrange(new MutationNameSpace(names));
        System.out.println(am2);
        System.out.println("\n");
        System.out.println(random2.getAncestorMatrix());
        System.out.println(random2.getNames());
        */
        
        //MCMC Test
        //
        DataMatrix dm = DataMatrix.getDataMatrix("./TestData/dataNavin.csv", new MutationNameSpace("./TestData/dataNavin.geneNames"));
        VAFMatrix vafm = null;
        double alpha = 0.001;
        double beta = 0.001;
        
        MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
        double finalScore = myMCMC.startMCMC(50000);
        
        System.out.println(finalScore);
        System.out.println(myMCMC.getAncestorMatrix());
    }
    
}
