/*=============================================================================
| Assignment: HW 01 - Building and managing a BST
|
| Author: Zachary Taylor
| Language: Java
|
| To Compile: javac Hw01.java
|
| To Execute: java Hw01 filename
| where filename is in the current directory and contains
| commands to insert, delete, print.
|
| Class: COP3503 - CS II Spring 2021
| Instructor: McAlpin
| Due Date: 2/28/21
|
+=============================================================================*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Hw01 {


    public static void main(String[] args) {
        System.out.println(args[0]);
        ArrayList<String> txtinput = new ArrayList<String>();
        bst tree = new bst();
        File inp = new File(args[0]);
        try {//initialises input into array
            System.out.print(inp);
            Scanner reader = new Scanner(inp);
            while(reader.hasNext()){
                txtinput.add(reader.nextLine());
            }
        }catch(Exception e){
            System.out.println("Oops...");
        }


        //reprints txt file
        System.out.println(args[0]+" contains:");
        int i = 0;
        for(i = 0; i < txtinput.size(); i++){
            System.out.println(txtinput.get(i));
        }
//command interpreter
        i = 0;
        for(i = 0; i < txtinput.size(); i++){
            inputParse(txtinput.get(i), tree);
        }

        //System.out.println("      "+tree.root.val);
        //System.out.println("  "+tree.root.left.val+" "+tree.root.right.val);
        //System.out.println(tree.root.left.left.val+" "+ tree.root.left.right.val);

    }



    //searches bst for a target value
    private static int find(tnode e, int tar) {
        tnode check = searchRecurs(e, tar);
        if (check != null) {
            return 1;
        } else {
            return 0;
        }
    }

    //the guts of the find function, is recursive
    static tnode searchRecurs(tnode u, int tar) {
        if (u == null || u.val == tar)
            return u;
        if (u.val > tar)
            return searchRecurs(u.left, tar);
        else
            return searchRecurs(u.right, tar);
    }

    //creates a string with all of the values of bst in order
    private static void printTree(tnode d){
        if(d!=null){
            printTree(d.left);
            System.out.print(d.val+" ");
            printTree(d.right);
        }
    }
//counts left nodes
    private static int countLNodes(tnode g){
        int chi = 0;
        if(g == null)
            return 0;
        if(g.left != null)
            chi = 1;
        return chi + countLNodes(g.left) + countLNodes(g.right);
    }

    private static int countRNodes(tnode g){
        int chi = 0;
        if(g == null)
            return 0;
        if(g.right != null)
            chi = 1;
        return chi + countLNodes(g.right);
    }
//counts left depth
    private static int countLDepth(tnode l){
        int d = 1;
        if(l.left != null)
            d = d + countRNodes(l.left);
        return d;
    }

    private static int countRDepth(tnode l){
        int d = 1;
        if(l.right != null)
            d = d + countRNodes(l.right);
        return d;
    }

    //counts left branch children
    private static int leftChild(tnode q){
        int chi = countLNodes(q);
        return chi;
    }

    private static int leftDepth(tnode q){
        int dep = countLDepth(q);
        return dep;
    }

    //same as leftChild & leftDepth above, except for the right
    private static int rightChild(tnode q){
        int chi = countRNodes(q);
        return chi;
    }

    private static int rightDepth(tnode q){
        int dep = countRDepth(q);
        return dep;
    }
//used to decide rotation for deletion
    static int findLesserNode(tnode w){
        int lesser = w.val;
        while(w.left != null){
            lesser = w.left.val;
            w = w.left;
        }
        return lesser;
    }

    static void inputParse(String commandString, bst h) {
        //breaks up line from array
        char com = commandString.charAt(0);
        String num = commandString.replaceAll("[^0-9]", "");
        int target;
//performs command decisions
        if (com == 'q' || com == 'p'){
            target = -69420;
            switch (com) {
                case 'i':
                    if (target != -69420 && find(h.root, target) == 0)
                        h.root = h.addNode(h.root, target);
                    break;
                case 'd':
                    if (target != -69420 && find(h.root, target) == 1) {
                        System.out.println("test delete");
                        h.deleteNode(target);
                    }
                    else{
                        System.out.println(com+" "+target+": integer "+target+" NOT found - NOT deleted");
                    }
                    break;
                case 's':
                    if (target != -69420 && find(h.root, target) == 1) {
                        String j = target + ": found";
                        System.out.println(j);
                    } else {
                        String j = target + ": NOT found";
                        System.out.println(j);
                    }
                    break;
                case 'p':
                    System.out.print(" ");
                    printTree(h.root);
                    System.out.println("");
                    String m2 = "left depth:             ";
                    int x = leftChild(h.root);
                    System.out.println("left children:          "+x);
                    x = leftDepth(h.root);
                    System.out.println("left depth:             "+x);
                    x = rightChild(h.root);
                    System.out.println("right children:         "+x);
                    x = rightDepth(h.root);
                    System.out.println("right depth:            "+x);
                    break;
                case 'q':
                    System.err.println("4107821; 3; 21");
                    System.exit(0);
                    break;
            }
        }
        else {
            if (num.length() == 0) {
                target = -69420;
                //make this output to file STDOUT.text
                System.out.println(com +" command:missing integer");
            } else {
                target = Integer.parseInt(num);
                switch (com) {
                    case 'i':
                        if (target != -69420 && find(h.root, target) == 0)
                            h.root = h.addNode(h.root, target);
                        break;
                    case 'd':
                        if (target != -69420 && find(h.root, target) == 1) {
                            System.out.println("test delete");
                            h.deleteNode(target);
                        }
                        else{
                            System.out.println(com+" "+target+": integer "+target+" NOT found - NOT deleted");
                        }
                        break;
                    case 's':
                        if (target != -69420 && find(h.root, target) == 1) {
                            String j = target + ": found";
                            System.out.println(j);
                        } else {
                            String j = target + ": NOT found";
                            System.out.println(j);
                        }
                        break;
                    case 'p':
                        System.out.print(" ");
                        printTree(h.root);
                        System.out.println("");
                        String m2 = "left depth:             ";
                        int x = leftChild(h.root);
                        System.out.println("left children:          "+x);
                        x = leftDepth(h.root);
                        System.out.println("left depth:             "+x);
                        x = rightChild(h.root);
                        System.out.println("right children:         "+x);
                        x = rightDepth(h.root);
                        System.out.println("right depth:            "+x);
                        break;
                    case 'q':
                        System.err.println("4107821; 3; 21");
                        System.exit(0);
                        break;
                }
            }
        }




//output "commandString: integer intInString NOT found - NOT deleted" to text file "STDOUT"
//output "commandString: integer intInString NOT found"
//output: tree in order NextLine left children NextLine left depth NextLine right children NextLine right depth

    }

    static class bst {
        public tnode createNode(int w) {
            tnode l = new tnode();
            l.val = w;
            l.left = null;
            l.right = null;
            return l;
        }

        public tnode addNode(tnode x, int y) {
            if (x == null) {
                return createNode(y);
            } else {
                if (x.val > y) {
                    x.left = addNode(x.left, y);
                } else {
                    x.right = addNode(x.right, y);
                }
                return x;
            }
        }

        tnode root;

        bst(){
            root = null;
        }

        void deleteNode(int target){
            root = deleteRecurse(root, target);
        }

        tnode deleteRecurse(tnode n, int target){
            if(target < n.val)
                deleteRecurse(n.left, target);
            else if(n.val > target)
                deleteRecurse(n.right, target);
            else{
                //when only 1 child
                if(n.left == null)
                    return n.right;
                else if(n.right == null)
                    return n.left;

                //when 2 children
                n.val = findLesserNode(n.right);
                n.right = deleteRecurse(n.right, n.val);
            }
            return n;
        }
    }


    static class tnode {
        public int val;
        private tnode left = null;
        private tnode right = null;

        void setVal(int x) {
            val = x;
        }
    }



}
/*=============================================================================
| I Zachary Taylor (4107821) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/