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
public class ResultQualify {
    AncestorMatrix rightTreeMatrix;
    
    public ResultQualify(SciteTree mRightTree){
        this.rightTreeMatrix = mRightTree.getAncestorMatrix();
    }
    
    public ResultQualify(AncestorMatrix mMatrix){
        this.rightTreeMatrix = mMatrix;
    }
    
    public double getResultQualify(SciteTree resultTree){
        AncestorMatrix am = resultTree.getAncestorMatrix();
        return getResultQualify(am);
    }
    
    public double getResultQualify(AncestorMatrix resultMatrix){
        AncestorMatrix rightSub = this.getSubMatrix(resultMatrix.getNameSpace());
        int size = resultMatrix.size();
        double rightCount = 0;
        for(int i = 0; i < size; i++ ){
            for(int j = 0; j < size; j++){
                if(rightSub.getRow(i)[j] == resultMatrix.getRow(i)[j]){
                    rightCount += 1;
                }
            }
        }
        return rightCount/(size*size);
    }
    
    public AncestorMatrix getSubMatrix(MutationNameSpace subNameSpace){
        int size = subNameSpace.size();
        MutationNameSpace currentNameSpace = rightTreeMatrix.getNameSpace();
        ArrayList<String> names = currentNameSpace.getNames();
        ArrayList<Integer> subToCurrent = new ArrayList<Integer>();
        for(String name: subNameSpace.getNames()){
            if(names.contains(name)){
                subToCurrent.add(names.indexOf(name));
            }
            else{
                throw new Error("NameSpace does not Overlap");
            }
        }
        
        int[][] newMatrix = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                newMatrix[i][j] = this.rightTreeMatrix.getRow(subToCurrent.get(i))[subToCurrent.get(j)];
            }
        }
        
        return new AncestorMatrix(newMatrix, subNameSpace);
    }
}
