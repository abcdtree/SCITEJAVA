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
import java.util.*;

/**
 *
 * @author Jianshu
 */
public class DataMatrix {
    private int[][] data;
    private int rowSize;
    private int columnSize;
    private MutationNameSpace nameSpace;
    
    
    private DataMatrix(int row, int column){
        this.rowSize = row;
        this.columnSize = column;
        this.data = new int[row][column];
    }
    
    private DataMatrix(int[][] dataMatrix, MutationNameSpace nameSpace){
        this.rowSize = dataMatrix.length;
        this.columnSize = dataMatrix[0].length;
        this.data = dataMatrix;
        this.nameSpace = nameSpace;
    }
    
    public int rowSize(){
        return this.rowSize;
    }
    
    public int columnSize(){
        return this.columnSize;
    }
    
    public static DataMatrix getDataMatrix(String csvFile){
        String[] filename = csvFile.split("\\.");
        if(filename[filename.length - 1].equals("csv")){
            System.out.println("CSV Format");
            ArrayList<String> names = new ArrayList<String>();
            int size = 0;
            try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
                String line = "";
                line = br.readLine();
                size = line.split(",").length - 1;
                int[][] matrix = new int[size][size];
                int j = 0;
                while((line = br.readLine()) != null){
                    
                    String[] lines = line.split(",");
                    if(lines.length < 1){
                        break;
                    }
                    names.add(lines[0]);
                    for(int i = 1; i < lines.length; i++){
                        matrix[j][i-1] = Integer.parseInt(lines[i]);
                    }
                    j++;
                }
                return new DataMatrix(matrix, new MutationNameSpace(names));

            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            
        }
        else{
            System.out.println("Other Format");
            return new DataMatrix(2,2);
        }
        return new DataMatrix(2, 2);
    }
    
    public static DataMatrix getDataMatrix(String csvFile, String nameSpaceFile){
        String[] filename = csvFile.split("\\.");
        MutationNameSpace mNameSpace = readNameSpace(nameSpaceFile);
        if(filename[filename.length - 1].equals("csv")){
            System.out.println("CSV Format");
            return new DataMatrix(readCsvData(csvFile), mNameSpace);
        }
        else{
            System.out.println("Other Format");
            return new DataMatrix(2,2);
        }
    }
    public static DataMatrix getDataMatrix(String csvFile, MutationNameSpace nameSpace){
        String[] filename = csvFile.split("\\.");
        if(filename[filename.length - 1].equals("csv")){
            System.out.println("CSV Format");
            return new DataMatrix(readCsvData(csvFile), nameSpace);
        }
        else{
            System.out.println("Other Format");
            return new DataMatrix(2,2);
        }
    }
    
    
    private static MutationNameSpace readNameSpace(String nameSpaceFile){
        try(BufferedReader br = new BufferedReader(new FileReader(nameSpaceFile))){
            String line = "";
            ArrayList<String> names = new ArrayList<String>();
            while((line = br.readLine()) != null){
                if(line.trim().length() != 0){
                    names.add(line.trim());
                }
            }
            return new MutationNameSpace(names);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new MutationNameSpace(new ArrayList<String>());
    }
    
    private static int[][] readCsvData(String csvFile){
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line = "";
            ArrayList<ArrayList<Integer>> totalData = new ArrayList<ArrayList<Integer>>();
            while((line = br.readLine()) != null){
                ArrayList<Integer> temp = new ArrayList<Integer>();
                String[] lines = line.split(" ");
                for(String item: lines){
                    temp.add(Integer.parseInt(item));
                }
                totalData.add(temp);
            }
            int row = totalData.size();
            int column = 0;
            if(row > 0){
                column = totalData.get(0).size();
            }
            int[][] matrix = new int[row][column];
            for(int i = 0; i < row; i++){
                for(int j = 0; j < column; j++){
                    matrix[i][j] = totalData.get(i).get(j);
                }
            }
            return matrix;          
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new int[2][2];
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
    
    public MutationNameSpace getNameSpace(){
        return this.nameSpace;
    }
}
