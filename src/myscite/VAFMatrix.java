/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.io.*;
/**
 *
 * @author Yang Wei
 */

public class VAFMatrix {
    private double[] vafs;
    private MutationNameSpace nameSpace; 
    private int[][] data;
    private int rowSize;
    private int columnSize;
    
    public VAFMatrix(MutationNameSpace nameSpace, double[] vafs){
        if (nameSpace.size()==vafs.length){
            this.rowSize = vafs.length;
            this.columnSize =vafs.length;
            this.data = new int[this.rowSize][this.columnSize];
            this.initVafArray(vafs);
            this.initVafMatrix();
        }
        else{
            throw new ArithmeticException("Lengths of nameSpace and vafs are different.");
        }        
    }
    private void initVafArray(double[] inputVafs){
        this.vafs=new double[inputVafs.length];
        for(int i=0;i<inputVafs.length; i++){
            this.vafs[i]=inputVafs[i];
        }
    }
    private void initVafMatrix(){
        for(int i=0;i<this.rowSize;i++){
            
            for(int j=0;j<this.columnSize;j++){
                if(this.vafs[i]>=this.vafs[j]){
                    this.data[i][j]=1;
                }
                else{
                    this.data[i][j]=0;
                }
            }
        }
    }
    public int[][] getVafMatrix(){
        return this.data;
    }
    
    
}
