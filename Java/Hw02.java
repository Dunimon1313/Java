/*=============================================================================
| Assignment: HW 02 - Skip List
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
| Due Date: 4/3/21
|
+=============================================================================*/



import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hw02 {
    public static void main(String[] args){
        //text file input
        String r = "N";
        try{
            r = args[1];//captures r specification
        }catch(ArrayIndexOutOfBoundsException e){
            r = "N";
        }
        String difficulty = "za459250;2.8;6";
        skiplist skl = new skiplist();
        skl.setSeed(r);// sets random to either 42 or sys-time
        ArrayList<String> txtinput = new ArrayList<String>();//input buffer
        File in = new File(args[0]);
        try {//initialises input into array
            Scanner reader = new Scanner(in);
            while(reader.hasNext()){
                txtinput.add(reader.nextLine());
            }
        }catch(Exception e){
            System.out.println("Oops...");
        }
        //begin print
        System.out.println();
        System.out.println(difficulty);
        System.out.println("For the input file named "+args[0]);
        if(r == "R" || r == "r") {
            System.out.println("RNG seeded with 42,");
        }
        else{
            System.out.println("With the RNG unseeded,");
        }

        //input interpreter
        int i = 0;
        for(i = 0; i < txtinput.size(); i++){
            inputBrain(txtinput.get(i), skl);
        }
    }


    public static void inputBrain(String command, skiplist sk) {
        char com = command.charAt(0);
        String com2 = command.replaceAll("[^0-9]", "");
        int subject = -1;
        if(com == 'i' || com == 'd' || com == 's') {
            subject = Integer.parseInt(com2);
        }
        switch(com) {
            case 'i'://insertion
                sk.insertNode(subject);
                System.out.println("insertion verification:"+sk.searchList(subject).val);
                break;
            case 's'://search
                sk.searchCom(subject);
                break;
            case 'd'://delete
                sk.delete(subject);
                break;
            case'p'://print
                sk.printAll();
                break;
            case 'q':
                //exit
                System.exit(0);
                break;
        }
    }





    static class skiplist {

        static class node {
            public int val;
            private node up;
            private node down;
            private node next;
            private node back;

            public node(int x) {
                this.val = x;
                this.up = null;
                this.down = null;
                this.back = null;
                this.next = null;
            }


        }


        private node head;
        private node tail;
        private int height = 0;
        final int negINF = Integer.MIN_VALUE;
        final int posINF = Integer.MAX_VALUE;
        long seed;

        public void setSeed(String u) {
            if(u.charAt(0) == 'R' || u.charAt(0) == 'r') {
                seed = System.currentTimeMillis();
            }
            else{
                seed = 42;
            }
        }

        public Random random = new Random(seed);

        public skiplist () {
            head = new node(negINF);
            tail = new node(posINF);
            head.next = tail;
            tail.back = head;
        }

        private void searchCom(int target) {
            node g = searchList(target);
            if(g.val == target) {
                System.out.println(target+" found");
            }
            else{
                System.out.println(target+" NOT FOUND");
            }
        }

        private void printAll() {
            node lowest = head;
            System.out.println("the current Skip List is shown below");
            System.out.println("---infinity");

            while (lowest.next != null) {
                while (lowest.down != null) {//brings lowest to bottom list
                    lowest = lowest.down;
                }
                while (lowest.up != null) {//outputs next non-null list node, moves lowest to the top
                    System.out.println(String.format("%6d;", lowest.val));
                    lowest = lowest.up;
                }
                while (lowest.down != null) {//brings lowest to bottom list again
                    lowest = lowest.down;
                }
                lowest = lowest.next;
                System.out.println();
            }
            System.out.println("+++infinity");
            System.out.println("---End of Skip List---");
        }

        public node searchList(int target) {
            node g = head;
            while(g.down != null) {//this goes as far down as it can
                g = g.down;
                while(g.next.val < target) {//this goes as far right as it can
                    g = g.next;
                }
            }// together the loops will find the node if it exists
            return g;
        }

        private int HeadsOrTails () {
            int numHeads = 0;
            while (((random.nextInt() % 2 + 2) % 2) == 1) {
                numHeads++;
            }
            return numHeads;
        }

        private node placeNode(node exists, node p) {
            exists.next.back = p;
            p.next = exists.next;
            exists.next = p;
            p.back = exists;
            return p;
        }

        public node insertNode(int target) {
            node exists = searchList(target);
            node p = new node(target);
            int numHeads = HeadsOrTails();

            //fixes left and right linkages
            placeNode(exists, p);
            //height linking and the left and right linkages of the new height copies
            canCreateList(numHeads, exists);

            return p;
        }

        private void canCreateList(int nH, node exists) {
            addNewList(nH, exists);
        }

        private node findUpLeftLink(node target) {
            node h = target;
            while(h.up == null) {
                h = h.back;
            }
            return h.up;
        }

        private node findUpRightLink (node target) {
            node j = target;
            while(j.up == null) {
                j = j.next;
            }
            return j.up;
        }

        private void boundaryExpansion() {
            node newHead = new node(negINF);
            node newTail = new node(posINF);

            newHead.next = newTail;
            newHead.down = head;
            newHead.down.up = newHead;
            newTail.back = newHead;
            newTail.down = tail;
            newTail.down.up = newTail;

            head = newHead;
            tail = newTail;
        }

        private void numberTowerCreation(node towerBase) {
            node leftLink = findUpLeftLink(towerBase);
            node rightLink = findUpRightLink(towerBase);
            node copy = new node(towerBase.val);
            towerBase.up = copy;
            copy.back = leftLink;
            copy.next = rightLink;
            copy.down = towerBase;
        }

        private void addNewList(int num, node exists) {
            node temp = exists.next;

            //boundary updating - I want to always maintain a top layer of only -inf -> +inf, so this creates an equal number of layers+1
            if(height <= num) {
                for(int j = 0; j < (num-height); j++) {
                    boundaryExpansion();
                }
                height = num;
            }
            //node vertical creation
            for(int i = 0; i < num; i++) {
                numberTowerCreation(temp);
            }
        }

        public node delete(int target) {
            node TargetNode = searchList(target);
            if(TargetNode.val == target) {
                removeLinks(TargetNode);
                while(1 == 1) {
                    removeLinks(TargetNode);
                    if(TargetNode.up != null) {
                        TargetNode = TargetNode.up;
                    }
                    else{
                        System.out.println(target+" deleted");
                        break;
                    }
                }
                return TargetNode;
            }
            else{
                System.out.println(target+" integer not found - delete not successful");
                return null;
            }
        }

        private void removeLinks(node TargetNode) {
            node afterTarget = TargetNode.next;
            node beforeTarget = TargetNode.back;
            afterTarget.back = beforeTarget;
            beforeTarget.next = afterTarget;
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