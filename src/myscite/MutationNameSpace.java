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
public class MutationNameSpace {
    private ArrayList<String> nameSpace;
    private int size;
    public MutationNameSpace(String[] nameSpace){
        this.nameSpace = new ArrayList<String>();
        for(String s:nameSpace){
            this.nameSpace.add(s);
        }
        this.size = nameSpace.length;
    }
    
    public MutationNameSpace(ArrayList<String> nameSpace){
        this.nameSpace = new ArrayList<String>(nameSpace);
        this.size = nameSpace.size();
    }
    
    public MutationNameSpace(String nameSpaceFile)
    {
        ArrayList<String> names = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(nameSpaceFile))){
            String line = "";

            while((line = br.readLine()) != null ){
                if(line.trim().length() != 0){
                    names.add(line.trim());
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        this.nameSpace = names;
        this.size = names.size();
    }
    public int size(){
        return this.size;
    }
    
    public ArrayList<String> getNames(){
        return new ArrayList<String>(this.nameSpace);
    }
    
    public String toString(){
        return this.nameSpace.toString();
    }
}
