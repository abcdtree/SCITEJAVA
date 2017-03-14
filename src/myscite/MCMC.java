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
public class MCMC {
    private AncestorMatrix treeMatrix;
    private DataMatrix dMatrix;
    private VAFMatrix vafMatrix;
    private double alpha;
    private double beta;
    
    private MCMC(AncestorMatrix matrix, DataMatrix dMatrix, VAFMatrix vMatrix, double a, double b){
        this.treeMatrix = matrix;
        this.dMatrix = dMatrix;
        this.vafMatrix = vMatrix;
        this.alpha = a;
        this.beta = b;
        this.treeMatrix.reArrange(this.dMatrix.getNameSpace());
    }
    //Initial with existing Tree
    public MCMC(SciteTree initialTree, DataMatrix dMatrix, VAFMatrix vMatrix, double a, double b){
        this(initialTree.getAncestorMatrix(), dMatrix, vMatrix, a, b);
    }
    //Initial with random Tree
    //binary = true to initialize random binary tree
    //binary = false to initialize a linear tree
    public MCMC(DataMatrix dMatrix, VAFMatrix vMatrix, double a, double b, boolean binary){
        this(SciteTree.makeARandomTree(dMatrix.getNameSpace(), binary), dMatrix, vMatrix, a, b);    
    }
    //maxRepeat indicates in each mcmc move attemp, the max number of times to try to improve the score
    public double startMCMC(int maxRepeat){
        int repeatCounter = 0;
        double currentScore = this.treeMatrix.getScore(this.dMatrix, this.alpha, this.beta);
        while(repeatCounter <= maxRepeat){
            AncestorMatrix tempMatrix = new AncestorMatrix(this.treeMatrix);
            Random rm = new Random();
            int method= rm.nextInt(3);
            int i = rm.nextInt(treeMatrix.size());
            int j = rm.nextInt(treeMatrix.size());
            if(i != j){
                if(method == 0){
                    tempMatrix.pruneAndReattach(i, j);                    
                }
                else if(method == 1){
                    tempMatrix.swapSubtree(i, j);
                }
                else{
                    tempMatrix.nestedSubtreeSwap(i, j);
                }
            }
            else{
                repeatCounter++;
                continue;
            }
            
            double mScore = tempMatrix.getScore(this.dMatrix, this.alpha, this.beta);
            if( mScore > currentScore){
                repeatCounter = 0;
                this.treeMatrix = tempMatrix;
                System.out.println("ML Score are optimized from " + currentScore + " to " + mScore);
                currentScore = mScore;
            }
            else if(mScore == currentScore){
                repeatCounter++;
                this.treeMatrix = tempMatrix;
            }
            else{
                repeatCounter++;
            }
            
            
        }
        return currentScore;
    }
    
    public AncestorMatrix getAncestorMatrix(){
        return new AncestorMatrix(this.treeMatrix);
    }
    
    public void setAlpha(double a){
        this.alpha = a;
    }
    
    public void setBeta(double b){
        this.beta = b;
    }
    
    public double checkAlpha(){
        return this.alpha;
    }
    
    public double checkBeta(){
        return this.beta;
    }
}
