/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
/**
 *
 * @author Yang Wei
 */

public class VAFMatrix {
    /*
    */
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
            this.nameSpace=nameSpace;
        }
        else{
            throw new ArithmeticException("Lengths of nameSpace and vafs are different.");
        }        
    }
    public VAFMatrix(MutationNameSpace nameSpace, String vafsFile){
            
            this.initVafArray(readVafsFile(nameSpace.getNames(true),vafsFile));
            this.rowSize = nameSpace.size();
            this.columnSize =nameSpace.size();
            this.data = new int[this.rowSize][this.columnSize];           
            this.initVafMatrix();
            this.nameSpace=nameSpace;
    }
    
    public VAFMatrix(MutationNameSpace nameSpace, String vafsFile, boolean s){
            
            this.initVafArray(readVafsFile(nameSpace.getNames(true),vafsFile, s));
            this.rowSize = nameSpace.size();
            this.columnSize =nameSpace.size();
            this.data = new int[this.rowSize][this.columnSize];           
            this.initVafMatrix();
            this.nameSpace=nameSpace;
    }
    
    public VAFMatrix(String csvFile, MutationNameSpace nameSpace){
        ArrayList<String> muts = new ArrayList<String>();
        ArrayList<Double> vafs = new ArrayList<Double>();
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line;
            //ArrayList<String> vafs = new ArrayList<String>();
            line = br.readLine();
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                if(lines.length < 1){
                    break;
                }
                muts.add(lines[3]);
                vafs.add(Double.parseDouble(lines[4]));
            }
            if(nameSpace.size() != muts.size()){
                throw new Error("NameSpace not Match");
            }
            double[] vafd = new double[nameSpace.size()];
            int i = 0;
            for(String name: nameSpace.getNames()){
                if(muts.contains(name)){
                    vafd[i] = vafs.get(muts.indexOf(name));
                }
                else{
                    throw new Error("Name could not be found");
                }
                i++;
            }
            this.initVafArray(vafd);
            this.rowSize = nameSpace.size();
            this.columnSize =nameSpace.size();
            this.data = new int[this.rowSize][this.columnSize];           
            this.initVafMatrix();
            this.nameSpace=nameSpace;
            
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    private double[] readVafsFile(String[] names,String vafsFile){
        Hashtable<String, Double> mutationVafs= new Hashtable<String, Double>();
        double[] vafsInOderOfNameSpace;        
        vafsInOderOfNameSpace=new double [names.length];        
        try(BufferedReader br = new BufferedReader(new FileReader(vafsFile))){
            String line;
            //ArrayList<String> vafs = new ArrayList<String>();
            while((line = br.readLine()) != null){
                line=line.trim();
                mutationVafs.put(line.split("_")[0],Double.parseDouble(line.split("_")[1])/100.0);
            }
            
            if(mutationVafs.size()==names.length){
                for(int i=0; i<names.length;i++){
                    vafsInOderOfNameSpace[i]=mutationVafs.get(names[i]);
                }                
            }
            else{
                throw new ArithmeticException("Lengths of nameSpace and vafs are different.");
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return vafsInOderOfNameSpace;
    }
    
    private double[] readVafsFile(String[] names,String vafsFile, boolean s){
        Hashtable<String, Double> mutationVafs= new Hashtable<String, Double>();
        double[] vafsInOderOfNameSpace;        
        vafsInOderOfNameSpace=new double [names.length];        
        try(BufferedReader br = new BufferedReader(new FileReader(vafsFile))){
            String line;
            //ArrayList<String> vafs = new ArrayList<String>();
            while((line = br.readLine()) != null){
                line=line.trim();
                if(s){
                    mutationVafs.put(line,Double.parseDouble(line.split(" ")[1])/100.0);
                }
                else{
                    mutationVafs.put(line,Double.parseDouble(line.split("_")[1])/100.0);
                }
            }
            
            if(mutationVafs.size()==names.length){
                for(int i=0; i<names.length;i++){
                    vafsInOderOfNameSpace[i]=mutationVafs.get(names[i]);
                }                
            }
            else{
                throw new ArithmeticException("Lengths of nameSpace and vafs are different.");
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return vafsInOderOfNameSpace;
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
    public int getRowSize(){
        return this.rowSize;
    }
    public int getColumnSize(){
        return this.columnSize;
    }
    public MutationNameSpace getNameSpace(){
        return this.nameSpace;
    }
    public int[] getColumn(int i){
        int[] column = new int[this.rowSize];
        for(int j = 0; j < this.rowSize; j++){
            column[j] = this.data[j][i];
        }
        return column;
    }    
    public int[] getRow(int i){
        int[] row = new int[this.columnSize];
        for(int j = 0; j < this.columnSize; j++){
            row[j] = this.data[i][j];
        }
        return row;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int[] d: this.data){
            sb.append("[ ");
            for(int p: d){
                sb.append(Integer.toString(p) + " ");
            }
            sb.append("]\n");
        }
        return sb.toString().trim();
    }
    
    public int size(){
        return this.rowSize;
    }
    
    
}
