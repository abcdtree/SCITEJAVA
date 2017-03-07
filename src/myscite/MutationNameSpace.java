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
