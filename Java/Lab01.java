/* COP3503 - CS II
 * Lab 01 - Prim’s algorithm
 * Submitted by:
 *     Whitney Stuckey
 *     Zachary Taylor
 */

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.DecimalFormat;

class Lab01 {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(new File(args[0]));

        int vertices, edges;
        vertices = scan.nextInt();
        edges = scan.nextInt();

        //Builds an adjacency matrix
        float [][] matrix = new float[vertices][vertices];

        while (scan.hasNext()) {

            int vertex1 = scan.nextInt();
            int vertex2 = scan.nextInt();
            float weight = scan.nextFloat();

            //undirected graph
            matrix[vertex1][vertex2] = weight;
            matrix[vertex2][vertex1] = weight;
        }

        MST(matrix, vertices);
        scan.close();
    }

    public static void MST(float matrix[][], int vertices){

        /*To determine if a vertex has been visited or not, where 1 is visited and 0 is not visited */
        int [] visited = new int[vertices];

        //Make sure we always starts with vertex 0
        visited[0] = 1;

        int edges = 0;
        float totalWeight = 0.0f;

        //Format for decimals
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);
        df.setMinimumFractionDigits(5);

        while(edges < (vertices - 1)) {

            int row = 0, column = 0;
            float min = Integer.MAX_VALUE;

            for(int i = 0; i < vertices; i++) {

                if(visited[i] == 1) {

                    for(int j = 0; j < vertices; j++){

                        //Find the min weighted edge
                        if(matrix[i][j] != 0 && visited[j] == 0 && matrix[i][j] < min){
                            min = matrix[i][j];
                            row = i;
                            column = j;
                        }
                    }
                }
            }
            //Mark the vertex as visited
            visited[column] = 1;

            //Calculate total weight of the MST path
            totalWeight += matrix[row][column];

            System.out.println(row + "-" + column + " " + df.format(matrix[row][column]));
            edges++;
        }

        System.out.println(df.format(totalWeight));
    }
}//End of Main