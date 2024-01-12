/*=============================================================================
| Assignment: HW 03 - Hashing Algorthym
|
| Author: Zachary Taylor
| Language: Java
|
| To Compile: javac Hw03.java
|
| To Execute: java Hw03 filename
| where filename is in the current directory and contains
| commands to insert, delete, print.
|
| Class: COP3503 - CS II Spring 2021
| Instructor: McAlpin
| Due Date: 4/30/21
|
+=============================================================================*/

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class HW3 {
    public static void main(String[] args) {
        ArrayList<String> txtinput = new ArrayList();
        File inp = new File(args[0]);
        System.out.println("za459250;3;8.5");
        try {//file reading and collection into string array
            Scanner reader = new Scanner(inp);
            while(reader.hasNext()) {
                txtinput.add(reader.nextLine());
            }
        } catch (Exception var5) {
            System.out.println("Oops...");
        }

        int hash;
        //loop through array to apply each string to hash function
        for(int i = 0; i < txtinput.size(); i++) {
            hash = hashMe((String)txtinput.get(i), txtinput.get(i).length());
            System.out.format("%10x:%s\n", hash, txtinput.get(i));
        }
        System.out.println("Input file processed");
    }



    public static int hashMe(String r, int len) {
        byte[] data = r.getBytes();
        int tempData;
        int randVal1 = 0xbcde98ef;
        int randVal2 = 0x7890face;
        int hashVal =  0xfa01bc96;
        int roundedEnd = len&0xfffffffc;

        for(int i = 0; i < roundedEnd; i=i+4) {
            tempData = (data[i] & 0xff) | ((data[i + 1] & 0xff) << 8) | ((data[i + 2] & 0xff) << 16) |
                    (data[i + 3] << 24);
            tempData = tempData*randVal1;
            tempData = Integer.rotateLeft(tempData, 12);
            tempData = tempData*randVal2;
            hashVal = hashVal^tempData;
            hashVal = Integer.rotateLeft(hashVal, 13);
            hashVal = hashVal*5+0x46b6456e;
        }
        //orphan collection - nothing weird going on here
        tempData = 0;
        if ((len & 0x03) == 3) {
            tempData = (data[roundedEnd + 2] & 0xff) << 16;
            len = len - 1;
        }
        if ((len & 0x03) == 2) {
            tempData |= (data[roundedEnd + 1] & 0xff) << 8;
            len = len - 1;
        }
        if ((len & 0x03) == 1) {
            tempData |= (data[roundedEnd] & 0xff);
            tempData = tempData*randVal1;
            tempData = Integer.rotateLeft(tempData, 14);
            tempData = tempData*randVal2;
            hashVal = hashVal^tempData;
        }
        hashVal = hashVal^len;
        hashVal = hashVal&0xb6acbe58;
        hashVal = hashVal^(hashVal >>> 13);
        hashVal = hashVal*0x53ea2b2c;
        hashVal = hashVal^(hashVal >>> 16);

        return hashVal;
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