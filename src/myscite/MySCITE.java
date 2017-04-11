/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.util.*;
import java.lang.*;
import java.io.*;
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
        System.out.println(am);
        System.out.println(am.changeRoot(3));
        System.out.println(am);
        
        
        
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
        /*
        DataMatrix dm = DataMatrix.getDataMatrix("./TestData/MutMatrix-VAF-SIMU-NUM-0.csv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
        VAFMatrix vafm = null;
        double alpha = 0.000001;
        double beta = 0.000001;
        
        MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
        //double finalScore = myMCMC.startMCMC(50000);
        double finalScore = myMCMC.startMCMC(50000,5);
        
        System.out.println(finalScore);
        System.out.println(myMCMC.getAncestorMatrix());
        //Test output the result
        AncestorMatrix am = myMCMC.getAncestorMatrix();
        SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
        st.outputCSV("./testOutput.csv");
        */
        
        //SCITE PLUS TEST
        /*
        DataMatrix dm = DataMatrix.getDataMatrix("./TestData/MutMatrix-VAF-SIMU-NUM-0.csv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
        VAFMatrix vafm = new VAFMatrix(new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"),"./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames", true);
        double alpha = 0.000001;
        double beta = 0.000001;
        */
        /*
        System.out.println(vafm);
        SciteTree tree = SciteTree.makeARandomTree(new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"), false);
        AncestorMatrix am = tree.getAncestorMatrix();
        System.out.println(am.getVafScore(vafm));
        */
        /*
        MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
        //double finalScore = myMCMC.startMCMC(50000);
        //double finalScore = myMCMC.startMCMC(50000,5);
        double finalScore = myMCMC.startMCMCPlus(50000, 5, 0.9);
        
        System.out.println(finalScore);
        System.out.println(myMCMC.getAncestorMatrix());
        //Test output the result
        AncestorMatrix am = myMCMC.getAncestorMatrix();
        SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
        st.outputCSV("./testOutput.csv");
        */
        /*
        SciteTree st = SciteTree.makeASciteTree("./testOutput.csv");
        //compare to original Tree
        ResultQualify rq = new ResultQualify(SciteTree.makeASciteTree("./CsvTrees/New-VAF-SIMU-NUM-0.csv"));
        System.out.println(rq.getResultQualify(st));
        
        SciteTree tree = SciteTree.makeARandomTree(new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"), true);
        System.out.println(rq.getResultQualify(tree));
        
        SciteTree tree1 = SciteTree.makeARandomTree(new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"), false);
        System.out.println(rq.getResultQualify(tree1));
        
        SciteTree gv = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0_ml0.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
        System.out.println(gv.getAncestorMatrix());
        System.out.println(rq.getResultQualify(gv));
        */
        /*
        ResultQualify rq = new ResultQualify(SciteTree.makeASciteTree("./CsvTrees/New-VAF-SIMU-NUM-0.csv"));
        try(PrintWriter writer = new PrintWriter("./Simulation_Data_Test_Result2.txt","UTF-8")){    
            //Simulation Data Test
            DataMatrix dm = DataMatrix.getDataMatrix("./TestData/MutMatrix-VAF-SIMU-NUM-0.csv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
            VAFMatrix vafm = new VAFMatrix(new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"),"./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames", true);
            double alpha = 0.000001;
            double beta = 0.000001;
            MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
        */
            //SCITE Java
            /*
            double finalScore = myMCMC.startMCMC(50000,5);
            System.out.println("SCITE-JAVA-RESULT:" + finalScore);
            writer.println("SCITE-JAVA-RESULT:" + finalScore);
            AncestorMatrix am = myMCMC.getAncestorMatrix();
            SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
            st.outputCSV("./SCITE-JAVA.csv");
            System.out.println("Comparing Score: " + rq.getResultQualify(st));
            writer.println("Comparing Score: " + rq.getResultQualify(st));
            */
            /*
            SciteTree gv = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0_ml0.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
            gv.outputCSV("./SCITE.csv");
            //SCITE Plus
            MCMC myMCMC2 = new MCMC(dm, vafm, alpha, beta, true);
            double finalScore2 = myMCMC2.startMCMCPlus(50000, 5, 0.9);
            System.out.println("SCITE-PLUS-RESULT:" + finalScore2);
            writer.println("SCITE-PLUS-RESULT:" + finalScore2);
            AncestorMatrix am1 = myMCMC2.getAncestorMatrix();
            SciteTree st1 = SciteTree.makeASciteTree(am1, am1.getNameSpace());
            st1.outputCSV("./SCITE-PLUS.csv");
            System.out.println("Comparing Score: " + rq.getResultQualify(st1));
            writer.println("Comparing Score: " + rq.getResultQualify(st1));
            /*
            SciteTree gv = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0_ml0.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
            System.out.println("SCITE_RESULT: " + rq.getResultQualify(gv));
            writer.println("SCITE_RESULT: " + rq.getResultQualify(gv));
            SciteTree gv2 = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0_ml1.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
            System.out.println("SCITE_RESULT: " + rq.getResultQualify(gv2));
            writer.println("SCITE_RESULT: " + rq.getResultQualify(gv2));
            SciteTree gv3 = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0_ml2.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0.geneNames"));
            System.out.println("SCITE_RESULT: " + rq.getResultQualify(gv3));
            writer.println("SCITE_RESULT: " + rq.getResultQualify(gv3));
            */
        /*
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    
        //ResultQualify rq = new ResultQualify(SciteTree.makeASciteTree("./CsvTrees/New-VAF-SIMU-NUM-0.csv"));
        try(PrintWriter writer = new PrintWriter("./Simulation_Data_Test_Result_Bladdar.txt","UTF-8")){    
            //Simulation Data Test
            DataMatrix dm = DataMatrix.getDataMatrix("./TestData/BladdarSingleCellMatrix_SCITE_1.csv", new MutationNameSpace("./TestData/BladdarSingleCellMatrix_SCITE_1.geneNames"));
            VAFMatrix vafm = new VAFMatrix(new MutationNameSpace("./TestData/BladdarSingleCellMatrix_SCITE_1.geneNames"),"./TestData/BladdarSingleCellMatrix_SCITE_1.geneNames", false);
            double alpha = 0.000001;
            double beta = 0.000001;
            MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
            double finalScore = myMCMC.startMCMC(100000,5);
            System.out.println("SCITE-JAVA-Bladdar-RESULT:" + finalScore);
            writer.println("SCITE-JAVA-Bladdar-RESULT:" + finalScore);
            AncestorMatrix am = myMCMC.getAncestorMatrix();
            SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
            st.outputCSV("./SCITE-JAVA-Bladdar.csv");
            //System.out.println("Comparing Score: " + rq.getResultQualify(st));
            //writer.println("Comparing Score: " + rq.getResultQualify(st));
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    
    /*
    ResultQualify rq = new ResultQualify(SciteTree.makeASciteTree("./CsvTrees/New-VAF-SIMU-NUM-0.csv"));
    SciteTree gv = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0-30_ml0.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0-30.geneNames"));
    System.out.println(gv.getAncestorMatrix());
    System.out.println(rq.getResultQualify(gv));
    gv.outputCSV("./SCITE-30.csv");
     */   
    }
    
}
