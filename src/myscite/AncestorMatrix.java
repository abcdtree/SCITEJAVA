/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.lang.Math.*;
/**
 *
 * @author Jianshu
 */
public class AncestorMatrix {
    private int[][] matrix;
    private int size;
    
    public AncestorMatrix(int mSize){
        if(mSize > 0){
            this.size = mSize;
            matrix = new int[mSize][mSize];
        }
        else{
            throw new Error("size must be bigger than 0");
        }
    }
    
    public AncestorMatrix(int[][] data){
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
}
