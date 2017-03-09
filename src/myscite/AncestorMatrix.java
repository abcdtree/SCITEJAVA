/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.lang.Math.*;
import java.util.*;
/**
 *
 * @author Jianshu
 */
public class AncestorMatrix {
    private int[][] matrix;
    private int size;
    private MutationNameSpace nameSpace;
    
    public AncestorMatrix(int mSize){
        if(mSize > 0){
            this.size = mSize;
            matrix = new int[mSize][mSize];
        }
        else{
            throw new Error("size must be bigger than 0");
        }
    }
    
    public AncestorMatrix(int[][] data, MutationNameSpace nameSpace){
        if(data.length > 0){
            boolean logic = true;
            for(int[] d : data){
                if(data.length != d.length){
                    logic = false;
                    break;
                }
            }
            if(logic){
                matrix = data;
                this.size = matrix.length;
            }
            else{
                throw new Error("AncestorMatrix must be a square matrix");
            }
        }
        else{
            throw new Error("size must be bigger than 0");
        }
    }
    
    public boolean setNameSpace(MutationNameSpace nameSpace){
        if(this.size == nameSpace.size()){
            this.nameSpace = nameSpace;
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean reArrange(MutationNameSpace nameSpace){
        if(this.nameSpace.size() != nameSpace.size()){
            return false;
        }
        else{
            int[][] temp = new int[nameSpace.size()][nameSpace.size()];
            HashMap<Integer, Integer> reMap= new HashMap<Integer, Integer>();
            ArrayList<String> currentName = this.nameSpace.getNames();
            ArrayList<String> newName = nameSpace.getNames();
            for(int i = 0; i < newName.size(); i++){
                int j = currentName.indexOf(newName.get(i));
                if(j >= 0){
                    reMap.put(i, j);
                }
                else{
                    return false;
                }
            }
            for(int i = 0; i < this.size; i++){
                for(int j = 0; j < this.size; j++){
                    temp[i][j] = this.matrix[reMap.get(i)][reMap.get(j)];
                }
            }
            this.matrix = temp;
            this.nameSpace = nameSpace;
            return true;
        }
    }
    
    public MutationNameSpace getNameSpace(){
        return this.nameSpace;
    }
    
    public int size(){
        return this.size;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int[] d: this.matrix){
            sb.append("[ ");
            for(int p: d){
                sb.append(Integer.toString(p) + " ");
            }
            sb.append("]\n");
        }
        return sb.toString().trim();
    }
    
    public boolean setRow(int[] row, int i){
        if(i >= 0 && i < this.size && row.length == this.size){
            for(int j = 0; j < this.size; j++){
                this.matrix[i][j] = row[j];
            }
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean setColumn(int[] column, int i){
        if(i >= 0 && i < this.size && column.length == this.size){
            for(int j = 0; j < this.size; j++){
                this.matrix[j][i] = column[j];
            }
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean setCell(int value, int i, int j){
        if(i >= 0 && j >= 0 && i < this.size && j < this.size){
            this.matrix[i][j] = value;
            return true;
        }
        else{
            return false;
        }
    }
    
    public int[] getColumn(int i){
        int[] column = new int[this.size];
        for(int j = 0; j < this.size; j++){
            column[j] = this.matrix[j][i];
        }
        return column;
    }
    
    public int[] getRow(int i){
        int[] row = new int[this.size];
        for(int j = 0; j < this.size; j++){
            row[j] = this.matrix[i][j];
        }
        return row;
    }
    
    public double getScore(DataMatrix dm, double alpha, double beta){
        int columnSize = dm.columnSize();
        double totalScore = 0.0;
        for(int i = 0; i < columnSize; i++){
            double maxScore = -10000;
            //int[] tempColumn = dm.getColumn(i);
            int[] dColumn = dm.getColumn(i);
            for(int j = 0; j < this.size; j++){
                int[] aColumn = this.getColumn(j);
                //printArray(aColumn);
                //printArray(dColumn);
                double tempScore = compareColumn(aColumn, dColumn, alpha, beta);
                if(checkTheSame(aColumn, dColumn)){
                    System.out.println("Find the same column!");
                }
                //System.out.println(tempScore);
                maxScore = (tempScore > maxScore) ? tempScore:maxScore;
                
            }
            System.out.println(maxScore);
            totalScore += maxScore;
        }
        return totalScore;
    }
    
    public double getVafScore(VAFMatrix vafM){
        if(this.size != vafM.size()){
            throw new Error("Two Matrices(AncestorMatrix and VAFMatrix) must have the same size");
        }
        int[][] vafMatrix = vafM.getVafMatrix();
        double tempScore = 0.0;
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                if(this.matrix[i][j] == vafMatrix[i][j]){
                    tempScore -= 1;
                }
                else{
                    tempScore = (vafMatrix[i][j] == 1) ? tempScore + 1: tempScore +100;
                }
            }
        }
        //System.out.println(tempScore);
        return Math.log10(1.0/(1.0 + Math.exp(tempScore)));
    }
    
    
    
    private static void printArray(int[] a){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for(int i : a){
            sb.append(Integer.toString(i) + " ");
        }
        sb.append("]");
        System.out.println(sb);
    }
    
    private static double compareColumn(int[] c1, int[] c2, double alpha, double beta){
        if(c1.length != c2.length){
            System.out.println("Here");
            return -10000;
        }
        else{
            double score = 0.0;
            for(int i = 0; i< c1.length; i++){
                score += Math.log10(cellCal2(c1[i], c2[i], alpha, beta));
            }
            //System.out.println(score);
            return score;
        }
    }
    
    private static double cellCal(int a, int d, double alpha, double beta){
        if(a == 0){
            if(d == 0){
                return (1.0-alpha-(alpha*beta/2.0));
            }
            else if(d == 1){
                return alpha;
            }
            else{
                return alpha*beta/2.0;
            }
        }
        else{
            if(d == 0){
                return beta/2.0;
            }
            else if(d == 1){
                return (1.0 - beta);
            }
            else{
                return beta/2.0;
            }
        }
    }
    
    private static double cellCal2(int a, int d, double alpha, double beta){
        if(a == 0){
            if(d == 0){
                return (1.0-alpha);
            }
            else{
                return alpha;
            }
        }
        else{
            if(d == 0){
                return beta;
            }
            else{
                return (1.0 - beta);
            }
        }
    }
    
    private static boolean checkTheSame(int[] c1, int[] c2){
        if(c1.length != c2.length){
            return false;
        }
        else{
            boolean flag = true;
            for(int i = 0; i < c1.length ; i++){
                if(c1[i] != c2[i]){
                    flag = false;
                    break;
                }
            }
            return flag;
        }
    }
    //Random Choose two nodes in the tree, cut one off the original branch and connect to the other.
    public boolean pruneAndReattach(int i, int j){
        if(i == j || i >= this.size || j >= this.size || i < 0 || j < 0){
            return false;
        }
        else{
            //int[] firstColumn = this.getColumn(i);
            //int[] secondColumn = this.getColumn(j);
            ArrayList<Integer> firstParents = getAncestors(this.getColumn(i), i);
            ArrayList<Integer> secondParents = getAncestors(this.getColumn(j));
            for(int p = 0; p < this.size; p++){
                if(this.matrix[i][p] == 1){
                    for(Integer q: firstParents){
                        this.matrix[q][p] = 0;
                    }
                    for(Integer q: secondParents){
                        this.matrix[q][p] = 1;
                    }
                }
            }
            return true;
            
        }
    }
    //Random choose two nodes which are not linear related, swap the two nodes with their subtrees together
    public boolean swapSubtree(int i, int j){
        if(i == j || i >= this.size || j >= this.size || i < 0 || j < 0){
            return false;
        }
        else{
            ArrayList<Integer> firstParents = getAncestors(this.getColumn(i), i);
            ArrayList<Integer> secondParents = getAncestors(this.getColumn(j), j);
            for(int p = 0; p < this.size; p++){
                //no linear relationship between two nodes means there is not a node with both of them as parents
                if(this.matrix[i][p] == 1){
                    for(Integer q: firstParents){
                        this.matrix[q][p] = 0;
                    }
                    for(Integer q: secondParents){
                        this.matrix[q][p] = 1;
                    }
                }
                else if(this.matrix[j][p] == 1){
                    for(Integer q: secondParents){
                        this.matrix[q][p] = 0;
                    }
                    for(Integer q: firstParents){
                        this.matrix[q][p] = 1;
                    }
                }
            }
            return true;
        }
    }
    //Random choose two nodes which have linear relationship
    //Step 1, cut down the bottom subtree(j) and connect it to the parent of the top chosen node(i)
    //Step 2, cut down the top chosen node(i) and connect it to a random pick offsprint of the bottom node(j)
    //i must not be the root
    public boolean nestedSubtreeSwap(int i, int j){
        //i is ancestor of j
        int parent = this.getParent(i);
        Random randomizer = new Random();
        ArrayList<Integer> offSprings = getOffSpring(this.getRow(j));
        int child = offSprings.get(randomizer.nextInt(offSprings.size()));
        if(parent >= 0){
            this.pruneAndReattach(j, parent);
            this.pruneAndReattach(i, child);
            return true;
        }
        else{
            return false;
        }
    }
    
    private static ArrayList<Integer> getOffSpring(int[] row){
        ArrayList<Integer> childrenList = new ArrayList<Integer>();
        for(int i = 0; i < row.length; i++){
            if(row[i] == 1){
                childrenList.add(i);
            }
        }
        return childrenList;
    }
    
    private int getParent(int i){
        int[] current = this.getColumn(i);
        current[i] = 0;
        for(int j = 0; j < this.size; j++){
            if(checkTheSame(current, this.getColumn(j))){
                return j;
            }
        }
        return -1;
    }
    

    
    private static ArrayList<Integer> getAncestors(int[] column){
        ArrayList<Integer> parentsList = new ArrayList<Integer>();
        for(int i = 0; i < column.length; i++){
            if(column[i] == 1){
                parentsList.add(i);
            }
        }
        return parentsList;
    }
    
    private static ArrayList<Integer> getAncestors(int[] column, int p){
        ArrayList<Integer> parentsList = new ArrayList<Integer>();
        for(int i = 0; i < column.length; i++){
            if(column[i] == 1 && i != p){
                parentsList.add(i);
            }
        }
        return parentsList;
    }
}
