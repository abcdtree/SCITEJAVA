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
        
        
        //Example - SCITE JAVA and SCITE PLUS
        DataMatrix dm = DataMatrix.getDataMatrix("./TestData/MutMatrix-VAF-SIMU-NUM-0-30.csv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0-30.geneNames"));
        //there are several VAFMatrix initialize methods
        VAFMatrix vafm = new VAFMatrix(new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0-30.geneNames"),"./TestData/MutMatrix-VAF-SIMU-NUM-0-30.geneNames", true);
        //Dont forget to set up alpha and beta
        double alpha = 0.000001;
        double beta = 0.000001;
        SciteTree st = sciteJava(dm, vafm, alpha, beta, 100000, 5);
        st.outputCSV("./temp.csv");
        SciteTree st1 = scitePlus(dm, vafm, alpha, beta, 100000, 5, 0.9);
        st1.outputCSV("./temp2.csv");
        //Example - SCITE OUTPUT Transform and ResultQualify with SIMULATION RIGHT ANSWER
        SciteTree gv = SciteTree.makeASciteTree("./gvTrees/MutMatrix-VAF-SIMU-NUM-0-30_ml0.gv", new MutationNameSpace("./TestData/MutMatrix-VAF-SIMU-NUM-0-30.geneNames"));
        gv.outputCSV("./Trans.csv");
        
        ResultQualify rq = new ResultQualify(SciteTree.makeASciteTree("./CsvTrees/New-VAF-SIMU-NUM-0.csv"));
        System.out.println(rq.getResultQualify(gv));
        System.out.println(rq.getResultQualify(st));
        System.out.println(rq.getResultQualify(st1));
    }
    
    static public SciteTree sciteJava(DataMatrix dm, VAFMatrix vafm, double alpha, double beta, int repeatLimits, int goBack){
        MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
        double finalScore = myMCMC.startMCMC(repeatLimits,goBack);
        System.out.println("SCITE-JAVA-Final-Score: " + finalScore);
        AncestorMatrix am = myMCMC.getAncestorMatrix();
        SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
        return st;
    }
    //percent is the percentage that original SCITE Score contributes to the total result
    static public SciteTree scitePlus(DataMatrix dm, VAFMatrix vafm, double alpha, double beta, int repeatLimits, int goBack, double percent){
        MCMC myMCMC = new MCMC(dm, vafm, alpha, beta, true);
        double finalScore = myMCMC.startMCMCPlus(repeatLimits,goBack,percent);
        System.out.println("SCITE-PlUS-Final-Score: " + finalScore);
        AncestorMatrix am = myMCMC.getAncestorMatrix();
        SciteTree st = SciteTree.makeASciteTree(am, am.getNameSpace());
        return st;
    }
    
    
}
