/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscite;
import java.util.*;
import java.lang.*;
import java.io.*;
/**
 *
 * @author Jianshu
 */
public class SciteTree {
    private TreeNode root;
    
    private SciteTree(){
        this.root = null;
    }
    
    private SciteTree(TreeNode root){
        this.root = root;
    }
    
    public ArrayList<String> getNames(){
        ArrayList<String> nameSpace = new ArrayList<String>();
        recName(this.root, nameSpace);
        return nameSpace;
    }
    
    private void recName(TreeNode current, ArrayList<String> nameSpace){
        nameSpace.add(current.getName());
        for(TreeNode tn : current.getChildren()){
            recName(tn, nameSpace);
        }
    }
    
    public TreeNode getRoot(){
        return this.root;
    }
    
    public static SciteTree makeASciteTree(AncestorMatrix ancestorMatrix, ArrayList<String> nameSpace){
        if(ancestorMatrix.size() != nameSpace.size()){
            throw new Error("NameSpace does not match the size of AncestorMatrix");
        }
        int mSize = ancestorMatrix.size();
        Map<Integer, ArrayList<Integer>> lengthMap = new HashMap<Integer, ArrayList<Integer>>();
        for(int i = 0; i < mSize; i++){
            int len = columnLength(ancestorMatrix.getColumn(i));
            if(lengthMap.containsKey(len)){
                ArrayList<Integer> temp = lengthMap.get(len);
                temp.add(i);
            }
            else{
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(i);
                lengthMap.put(len, temp);
            }
        }
        /*
        for(Integer i: lengthMap.keySet()){
            System.out.println("" + i + " :" +lengthMap.get(i));
        }*/
        ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
        for(String name: nameSpace){
            TreeNode node = new TreeNode(name);
            nodeList.add(node);
        }
        SciteTree mSciteTree;
        int p = lengthMap.keySet().size();
        if(lengthMap.get(1).size() == 1){
            mSciteTree = new SciteTree(nodeList.get(lengthMap.get(1).get(0)));
        }
        else{
            TreeNode node = new TreeNode("Root");
            mSciteTree = new SciteTree(node);
            for(int j: lengthMap.get(1)){
                node.addChild(nodeList.get(j));
            }
        }
        for(int j = 1; j < p; j++){
            ArrayList<Integer> parentList = lengthMap.get(j);
            ArrayList<Integer> childrenList = lengthMap.get(j + 1);
            
            for(int pId: parentList){
                int[] pColumn = ancestorMatrix.getColumn(pId);
                TreeNode pNode = nodeList.get(pId);
                for(int cId: childrenList){
                    int[] cColumn = ancestorMatrix.getColumn(cId);
                    if(isParent(pColumn, cColumn)){
                        pNode.addChild(nodeList.get(cId));
                    }
                }
            }
        }
        
        return mSciteTree;
        
    }
    
    private static boolean isParent(int[] columnP, int[] columnC){
        if(columnP.length != columnC.length){
            return false;
        }
        else{
            int sum = 0;
            for(int i = 0; i < columnP.length; i++){
                if(columnP[i] != columnC[i]){
                    sum++;
                }
            }
            if(sum == 1){
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    private static int columnLength(int[] column){
        int sum = 0;
        for(int i: column){
            sum += i;
        }
        return sum;
    }
    
    
    
    public static SciteTree makeASciteTree(String csvFile){
        String[] filename = csvFile.split("\\.");
        if(filename[filename.length - 1].equals("csv")){
            System.out.println("CSV Format");
            return new SciteTree(withCsvInput(csvFile));
        }
        else{
            System.out.println("Other Format");
            return new SciteTree(new TreeNode("Temp"));
        }
        
    }
    
    private static TreeNode withCsvInput(String csvFile){
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line = "";
            Map<String, String> idNameMap = new HashMap<String, String>();
            line = br.readLine();
            while((line = br.readLine()) != null){
                String[] lines = line.split(",");
                idNameMap.put(lines[0], lines[2]);
            }
            TreeNode myRoot = new TreeNode(idNameMap.get("1"));
            recBuild(myRoot, "1", idNameMap);
            return myRoot;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new TreeNode("Temp");
    }
    
    private static void recBuild(TreeNode start, String currentId, Map<String, String> idNameMap){
        for(int i = 0; i < 10; i++){
            String id = currentId + Integer.toString(i);
            if(idNameMap.containsKey(id)){
                TreeNode child = new TreeNode(idNameMap.get(id));
                start.addChild(child);
                recBuild(child, id, idNameMap);
            }
        }
    }
    
    public int size(){
        return recCount(this.root);
    }
    
    private int recCount(TreeNode node){
        ArrayList<TreeNode> children = node.getChildren();
        int msize = 1;
        if (!children.isEmpty())
        {
            for(TreeNode child: children){
                msize += recCount(child);
            }
        }
        return msize;
    }
    
    public AncestorMatrix getAncestorMatrix(){
        int mSize = this.size();
        ArrayList<String> nameSpace = this.getNames();
        AncestorMatrix mMatrix = new AncestorMatrix(mSize);
        recBuildMatrix(this.root, mMatrix, nameSpace, mSize);
        return mMatrix;
    }
    
    private void recBuildMatrix(TreeNode current, AncestorMatrix mMatrix, ArrayList<String> nameSpace, int mSize){
        int[] column = new int[mSize];
        ArrayList<String> ancestors = current.getAncestors();
        for(int i = 0; i < mSize; i ++){
            if(ancestors.contains(nameSpace.get(i))){
                column[i] = 1;
            }
        }
        int j = nameSpace.indexOf(current.getName());
        mMatrix.setColumn(column, j);
        ArrayList<TreeNode> children = current.getChildren();
        for(TreeNode child: children){
            recBuildMatrix(child, mMatrix, nameSpace, mSize);
        }
    }
    
}
