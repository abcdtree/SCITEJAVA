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
        //System.out.println(this.treeMatrix);
        int repeatCounter = 0;
        double currentScore = this.treeMatrix.getScore(this.dMatrix, this.alpha, this.beta);
        while(repeatCounter <= maxRepeat){
            AncestorMatrix tempMatrix = new AncestorMatrix(this.treeMatrix);
            Random rm = new Random();
            int method= rm.nextInt(7);
            int i = rm.nextInt(treeMatrix.size());
            int j = rm.nextInt(treeMatrix.size());
            if(i != j){
                if(method%4 == 0){
                    if(this.treeMatrix.isLinear(i, j) != 1){
                        tempMatrix.pruneAndReattach(i, j);
                    }
                    else{
                        continue;
                    }
                }
                else if(method%4 == 1){
                    if(this.treeMatrix.isLinear(i, j) == 0){
                        tempMatrix.swapSubtree(i, j);
                    }
                    else{
                        continue;
                    }
                }
                else if(method%4 == 2){
                    if(this.treeMatrix.isLinear(i, j) > 0){
                        tempMatrix.nestedSubtreeSwap(i, j);
                    }
                    else if(this.treeMatrix.isLinear(i, j) < 0){
                        tempMatrix.nestedSubtreeSwap(j, i);
                    }
                    else{
                        continue;
                    }
                }
                else{
                    if(!tempMatrix.changeRoot(i)){
                        repeatCounter++;
                        continue;
                    }
                    
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
                //this.treeMatrix = tempMatrix;
            }
            else{
                repeatCounter++;
            }
            
            
        }
        return currentScore;
    }
    
    public double startMCMC(int maxRepeat, int goBack){
        int repeatCounter = 0;
        ArrayList<AncestorMatrix> pathRecord = new ArrayList<AncestorMatrix>();
        double currentScore = this.treeMatrix.getScore(this.dMatrix, this.alpha, this.beta);
        while(repeatCounter <= maxRepeat){
            AncestorMatrix tempMatrix = new AncestorMatrix(this.treeMatrix);
            Random rm = new Random();
            int method= rm.nextInt(7);
            int i = rm.nextInt(treeMatrix.size());
            int j = rm.nextInt(treeMatrix.size());
            if(i != j){
                if(method%4 == 0){
                    if(this.treeMatrix.isLinear(i, j) != 1){
                        tempMatrix.pruneAndReattach(i, j);
                    }
                    else{
                        continue;
                    }
                }
                else if(method%4 == 1){
                    if(this.treeMatrix.isLinear(i, j) == 0){
                        tempMatrix.swapSubtree(i, j);
                    }
                    else{
                        continue;
                    }
                }
                else if(method%4 == 2){
                    if(this.treeMatrix.isLinear(i, j) > 0){
                        tempMatrix.nestedSubtreeSwap(i, j);
                    }
                    else if(this.treeMatrix.isLinear(i, j) < 0){
                        tempMatrix.nestedSubtreeSwap(j, i);
                    }
                    else{
                        continue;
                    }
                }
                else{
                    if(!tempMatrix.changeRoot(i)){
                        //repeatCounter++;
                        continue;
                    }
                }
            }
            else{
                //repeatCounter++;
                continue;
            }
            
            double mScore = tempMatrix.getScore(this.dMatrix, this.alpha, this.beta);
            if( mScore > currentScore){
                repeatCounter = 0;
                pathRecord.add(this.treeMatrix);
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
        double attempScore = currentScore;
        AncestorMatrix currentBest = new AncestorMatrix(this.treeMatrix);
        ArrayList<AncestorMatrix> newPath = new ArrayList<AncestorMatrix>();
        for(int t = 0; t < goBack; t++){
            
            System.out.println("Goback Score Start from " + attempScore);
            if(attempScore > currentScore){
                currentBest = new AncestorMatrix(this.treeMatrix);
                currentScore = attempScore;
                pathRecord = new ArrayList<AncestorMatrix>(pathRecord.subList(0, pathRecord.size()/2));
                pathRecord.addAll(newPath);
            }
            if(pathRecord.size() == 0){
                break;
            }
            this.treeMatrix = pathRecord.get(pathRecord.size()/2);
            pathRecord = new ArrayList<AncestorMatrix>(pathRecord.subList(0, pathRecord.size()/2));
            newPath = new ArrayList<AncestorMatrix>();
            int newRepeatCounter = 0;
            attempScore = this.treeMatrix.getScore(this.dMatrix, this.alpha, this.beta);
            while(newRepeatCounter <= maxRepeat){
                AncestorMatrix tempMatrix = new AncestorMatrix(this.treeMatrix);
                Random rm = new Random();
                int method= rm.nextInt(7);
                int i = rm.nextInt(treeMatrix.size());
                int j = rm.nextInt(treeMatrix.size());
                if(i != j){
                    if(method%4 == 0){
                        if(this.treeMatrix.isLinear(i, j) != 1){
                            tempMatrix.pruneAndReattach(i, j);
                        }
                        else{
                            continue;
                        }
                    }
                    else if(method%4 == 1){
                        if(this.treeMatrix.isLinear(i, j) == 0){
                            tempMatrix.swapSubtree(i, j);
                        }
                        else{
                            continue;
                        }
                    }
                    else if(method%4 == 2){
                        if(this.treeMatrix.isLinear(i, j) > 0){
                            tempMatrix.nestedSubtreeSwap(i, j);
                        }
                        else if(this.treeMatrix.isLinear(i, j) < 0){
                            tempMatrix.nestedSubtreeSwap(j, i);
                        }
                        else{
                            continue;
                        }
                    }
                    else{
                        if(!tempMatrix.changeRoot(i)){
                            //newRepeatCounter++;
                            continue;
                        }
                    }
                }
                else{
                    //newRepeatCounter++;
                    continue;
                }

                double mScore = tempMatrix.getScore(this.dMatrix, this.alpha, this.beta);
                if( mScore > attempScore){
                    newRepeatCounter = 0;
                    newPath.add(this.treeMatrix);
                    this.treeMatrix = tempMatrix;
                    System.out.println("ML Score are optimized from " + attempScore + " to " + mScore);
                    attempScore = mScore;
                }
                else if(mScore == attempScore){
                    newRepeatCounter++;
                    this.treeMatrix = tempMatrix;
                }
                else{
                    newRepeatCounter++;
                }            
            }
        }
        this.treeMatrix = currentBest;
        return currentScore > attempScore ? currentScore: attempScore;
    }
    
    public double startMCMCPlus(int maxRepeat, int goBack, double lambda){
        int repeatCounter = 0;
        ArrayList<AncestorMatrix> pathRecord = new ArrayList<AncestorMatrix>();
        double treeScore = this.treeMatrix.getScore(this.dMatrix, this.alpha, this.beta);
        double vafScore = this.treeMatrix.getVafScore(this.vafMatrix);
        double currentScore = treeScore * lambda + vafScore *(1- lambda);
        //System.out.println(this.treeMatrix.getVafScore(this.vafMatrix));
        System.out.println(currentScore);
        while(repeatCounter <= maxRepeat){
            AncestorMatrix tempMatrix = new AncestorMatrix(this.treeMatrix);
            Random rm = new Random();
            int method= rm.nextInt(7);
            int i = rm.nextInt(treeMatrix.size());
            int j = rm.nextInt(treeMatrix.size());
            if(i != j){
                if(method%4 == 0){
                    if(this.treeMatrix.isLinear(i, j) != 1){
                        tempMatrix.pruneAndReattach(i, j);
                    }
                    else{
                        continue;
                    }
                }
                else if(method%4 == 1){
                    if(this.treeMatrix.isLinear(i, j) == 0){
                        tempMatrix.swapSubtree(i, j);
                    }
                    else{
                        continue;
                    }
                }
                else if(method%4 == 2){
                    if(this.treeMatrix.isLinear(i, j) > 0){
                        tempMatrix.nestedSubtreeSwap(i, j);
                    }
                    else if(this.treeMatrix.isLinear(i, j) < 0){
                        tempMatrix.nestedSubtreeSwap(j, i);
                    }
                    else{
                        continue;
                    }
                }
                else{
                    if(!tempMatrix.changeRoot(i)){
                        //repeatCounter++;
                        continue;
                    }
                }
            }
            else{
                //repeatCounter++;
                continue;
            }
            double mTreeScore = tempMatrix.getScore(this.dMatrix, this.alpha, this.beta);
            double mVafScore = tempMatrix.getVafScore(this.vafMatrix);
            double mScore = mTreeScore *(lambda) + mVafScore * (1- lambda);
            //System.out.println(mScore + " " + mTreeScore + " " + mVafScore);
            if( mScore > currentScore){
                repeatCounter = 0;
                pathRecord.add(this.treeMatrix);
                this.treeMatrix = tempMatrix;
                System.out.println("ML Score are optimized from " + currentScore + " to " + mScore + ", with TreeScore from " + treeScore + " to " + mTreeScore + " and VafScore from " + vafScore + " to " + mVafScore );
                currentScore = mScore;
                treeScore = mTreeScore;
                vafScore = mVafScore;
            }
            else if(mScore == currentScore){
                repeatCounter++;
                this.treeMatrix = tempMatrix;
            }
            else{
                repeatCounter++;
            }            
        }
        double attempScore = currentScore;
        AncestorMatrix currentBest = new AncestorMatrix(this.treeMatrix);
        ArrayList<AncestorMatrix> newPath = new ArrayList<AncestorMatrix>();
        for(int t = 0; t < goBack; t++){
            
            System.out.println("Goback Score Start from " + attempScore);
            if(attempScore > currentScore){
                currentBest = new AncestorMatrix(this.treeMatrix);
                currentScore = attempScore;
                pathRecord = new ArrayList<AncestorMatrix>(pathRecord.subList(0, pathRecord.size()/2));
                pathRecord.addAll(newPath);
            }
            if(pathRecord.size() == 0){
                break;
            }
            this.treeMatrix = pathRecord.get(pathRecord.size()/2);
            pathRecord = new ArrayList<AncestorMatrix>(pathRecord.subList(0, pathRecord.size()/2));
            newPath = new ArrayList<AncestorMatrix>();
            int newRepeatCounter = 0;
            double tempTreeScore = this.treeMatrix.getScore(this.dMatrix, this.alpha, this.beta);
            double tempVafScore = this.treeMatrix.getVafScore(this.vafMatrix);
            attempScore = tempTreeScore*(lambda) + tempVafScore*(1-lambda);
            while(newRepeatCounter <= maxRepeat){
                AncestorMatrix tempMatrix = new AncestorMatrix(this.treeMatrix);
                Random rm = new Random();
                int method= rm.nextInt(7);
                int i = rm.nextInt(treeMatrix.size());
                int j = rm.nextInt(treeMatrix.size());
                if(i != j){
                    if(method%4 == 0){
                        if(this.treeMatrix.isLinear(i, j) != 1){
                            tempMatrix.pruneAndReattach(i, j);
                        }
                        else{
                            continue;
                        }
                    }
                    else if(method%4 == 1){
                        if(this.treeMatrix.isLinear(i, j) == 0){
                            tempMatrix.swapSubtree(i, j);
                        }
                        else{
                            continue;
                        }
                    }
                    else if(method%4 == 2){
                        if(this.treeMatrix.isLinear(i, j) > 0){
                            tempMatrix.nestedSubtreeSwap(i, j);
                        }
                        else if(this.treeMatrix.isLinear(i, j) < 0){
                            tempMatrix.nestedSubtreeSwap(j, i);
                        }
                        else{
                            continue;
                        }
                    }
                    else{
                        if(!tempMatrix.changeRoot(i)){
                            //newRepeatCounter++;
                            continue;
                        }
                    }
                }
                else{
                    //newRepeatCounter++;
                    continue;
                }

                double mTreeScore = tempMatrix.getScore(this.dMatrix, this.alpha, this.beta);
                double mVafScore = tempMatrix.getVafScore(this.vafMatrix);
                double mScore = mTreeScore * lambda + mVafScore * (1- lambda);
                if( mScore > attempScore){
                    newRepeatCounter = 0;
                    newPath.add(this.treeMatrix);
                    this.treeMatrix = tempMatrix;
                    System.out.println("ML Score are optimized from " + attempScore + " to " + mScore + ", with TreeScore from " + tempTreeScore + " to " + mTreeScore + " and VafScore from " + tempVafScore + " to " + mVafScore );
                    attempScore = mScore;
                    tempTreeScore = mTreeScore;
                    tempVafScore = mVafScore;
                }
                else if(mScore == attempScore){
                    newRepeatCounter++;
                    this.treeMatrix = tempMatrix;
                }
                else{
                    newRepeatCounter++;
                }            
            }
        }
        this.treeMatrix = currentBest;
        return currentScore > attempScore ? currentScore: attempScore;
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
