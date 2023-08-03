/**********************************
 * SinghAnmolpreetA4Q1
 *
 * COMP 1020 SECTION A01
 * INSTRUCTOR    HEATHER MATHESON
 * ASSIGNMENT    ASSIGNMENT 4, QUESTION 1
 * @author       ANMOLPREET SINGH, 7983556
 * @version      AUGUST 2 , 2023
 *
 * PURPOSE: SIMULATING GAME OF LIFE  
 ************************************/

import java.util.Scanner;
import java.io.*;


public class SinghAnmolpreetA4Q1 {

    // global variables for size of the grid
    public static int rMax;
    public static int cMax;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // prompts the user for inputing the name of the file containing coordinates of the organism
        System.out.print("please enter the name of the file containing a grid:");
        String fileName = sc.nextLine();
        try{
            BufferedReader line = new BufferedReader(new FileReader(fileName));
            String firstLine = line.readLine();
            String [] size = firstLine.split("\\s+");

            // gets row size from the file
             rMax = Integer.parseInt(size[0]);

            // gets column size from the file
             cMax = Integer.parseInt(size[1]);
        }
        catch(IOException e){
            System.out.println("Something went wrong, the file doesn't exist." + e.getMessage());
        }
            // creates grid with that row and column size 
            Grid gen0 = new Grid(rMax,cMax);

            // fills the grid with empty dots 
            gen0.fillGrid('.');

            // fills the grid with organisms 
            gen0.grid(fileName);
            System.out.println("Generation 0 is:");

            // prints the zeroth generation to the console 
            System.out.println(gen0);
            System.out.print("How many generations would you like to print:");
            int num = sc.nextInt();

            // runs the loop the number of generation the user wants 
            for(int i=1; i<=num ;i++){
                System.out.println("generation " + i + " is:" );
                Grid next = gen0.nextGeneration();
                System.out.println(next);
                gen0 = next;
            }
            System.out.println("Program terminated sucessfully.");
           
    }
}


class Grid{

    // instance variable
    private char[][] grid;

    // constructor for the Grid 
    public Grid(){
    grid = new char[10][10];
    }

    // constructor to fill the grid till row and column size 
    public Grid(int rMax, int cMax){
        grid = new char[rMax ][cMax];
    }

    // this function fills the grid with asterisk on places specified by the file 
    public void grid(String fileName){
        try{BufferedReader reader = new BufferedReader(new FileReader(fileName));
             String line = reader.readLine();
        
           while ((line = reader.readLine()) != null) {
            String[] coordinates = line.split("\\s+");
            int r = Integer.parseInt(coordinates[0]);
            int c = Integer.parseInt(coordinates[1]);
            set(r, c);
        }
        reader.close();
        }
        catch(IOException  e){
            System.out.println("Some error occured, file doesn't exist." + e.getMessage());
        }
    }
     
    // sets the coordinate in the array to be asterisk
    public void set(int r, int c){
        grid[r][c] = '*';

    }

    // fill the grid with any character specified 
    public void fillGrid(char a){
        for(int r=0; r<grid.length;r++){
            for(int c=0; c<grid[r].length;c++){
                grid[r][c] = a;
            }
        }


    }

    // checks if the grid has organism at a particular point 
    public boolean isAlive(int r, int c ){
        boolean result = false;
        if(isValidPoint(r, c) && grid[r][c] == '*'){
            result = true;
        }
        return result;
    }

    // removes the organism from the grid and replaces it with point 
    public void clear(int r, int c ){
        grid[r][c] = '.';
    }
    

    // counts the number of neighbours of a particular organism 
    public int numNeighbours(int r, int c) {
    int result = 0;

     for (int row = r - 1; row <= r + 1; row++) {
        for (int col = c - 1; col <= c + 1; col++) {
            if (isValidPoint(row, col) && grid[row][col] == '*') {
                result++;
            }
        }
    }
    return result ;
}


// checks whether the point lies inside the array 
private boolean isValidPoint(int r, int c) {
    boolean result = false;
    if( r >= 0 && r < grid.length && c >= 0 && c < grid[0].length){
        result = true;
    }
    return result;
}

    // this function returns grid for next generation using the original grid 
    public Grid nextGeneration(){

        Grid nextGen = new Grid(grid.length, grid[0].length);
        nextGen.fillGrid('.');
         for(int i=0; i<grid.length;i++){
            for(int j=0; j<grid[0].length;j++){
                if(numNeighbours(i, j) == 3){
                    nextGen.set(i,j);
                }
                else if(numNeighbours(i, j) < 2 || numNeighbours(i, j) > 3){
                    nextGen.clear(i, j);
                }

            }
         }

         return nextGen;
    }

    // to String method to print the grid 
    public String toString() {
    String out = "";

    // Add column numbers as the top header
    out += "  ";
    for (int c = 0; c < grid[0].length; c++) {
        out += c + " ";
    }
    out += "\n";

    // Add rows with row numbers on the left side
    for (int r = 0; r < grid.length; r++) {
        out += r + " ";

        for (int c = 0; c < grid[r].length; c++) {
            out += grid[r][c] + " ";
        }

        out += "\n";
    }
    return out ;
}


}
