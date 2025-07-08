/*  Gaborni, Kyle Jon B.
    ITE114 IT2D Multimedia Laboratory
    Make an algorithm and implement it in Java on how to Add or Multiply two or more conformable matrices.
    The dimension of the matrices may be at most 10 by 10.
    Input: list/series of matrices 0
    Output: display the following matrices to be added or to be multiplied and the result;
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class Gaborni_2021_1254_MatrixOperations2 {
    private static String matrixName; //this class contains the main method which is the entry point for the program.
    public static void main(String[] args) {// This block of code defines the main method.
        Scanner input_console = new Scanner(System.in); // It creates a Scanner object to read input from the console
        HashMap<Integer, String> matrixNames = new HashMap<>(); // create a map to store the names of matrices
        int matrixCount = 1; //integer variable called matrixCount to count the number of matrices.
        boolean continueprog = true; //loops the program as long as the boolean condition is true
        boolean conformmatrix = true; //loops the program by asking user to input a comformable matrix as long as the boolean condition is true

        List<int[][]> matrices = new ArrayList<>(); // create a list to store the matrices

        while (continueprog) {
            if(matrices.size() > 0) { //reset all variables
                matrices.clear(); //clears the matrix
                matrixCount = 1; //count the number of matrices as the program continues
                matrixNames.clear(); //resets the name of the matrix
                while (conformmatrix) {
                    int row; int col; //initializes the dimension of the matrices
                    System.out.print("Enter a name for this matrix: ");
                    String matrixName = input_console.next(); //ask user to input name of the matrix
                    matrixNames.put(matrixCount, matrixName); // add the name of the matrix to the map
                    matrixCount++; //increments the name count

                    System.out.print("Enter matrix dimension\nRows: ");
                    while (true) {
                        try {
                            row = input_console.nextInt();
                            if (row < 2) {
                                System.out.print("Matrix Required Dimension Error: Must be above 1\nRows: "); //prompts error message when user enters out-of-bound row dimension
                                throw new InputMismatchException();
                            } else if (row > 10) {
                                System.out.print("Matrix Dimension Limit Error: Must be below 11\nRows: "); //prompts error message when user enters out-of-bound row dimension
                                throw new InputMismatchException();
                            }
                        } catch (InputMismatchException e) { //catches the error
                            continue;
                        }
                        break;
                    }

                    System.out.print("Columns: ");
                    while (true) {
                        try {
                            col = input_console.nextInt();
                            if (col < 1) {
                                System.out.print("Matrix Required Dimension Error: Must be above 1\nColumns: "); //prompts error message when user enters out-of-bound column dimension
                                throw new InputMismatchException();
                            } else if (col > 10) {
                                System.out.print("Matrix Dimension Limit Error: Must be below 11\nColumns: "); //prompts error message when user enters out-of-bound column dimension
                                throw new InputMismatchException();
                            }
                        } catch (InputMismatchException e) { //catches the error
                            continue;
                        }
                        break;
                    }

                    int[][] matrix = new int[row][col];
                    System.out.println("Enter matrix elements: ");
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            System.out.printf("[%d][%d]: ", i, j);
                            matrix[i][j] = input_console.nextInt();
                        }
                    }
                    matrices.add(matrix); // add the matrix to the list

                    System.out.print("Add another matrix? [y]/[n]: ");
                    char appendMatrix = input_console.next().charAt(0);
                    if (appendMatrix != 'y') {
                        break;
                    }
                }
            } else {
                while (conformmatrix) {
                    int row; int col; //initializes the dimension of the matrices
                    System.out.print("Enter a name for this matrix: ");
                    String matrixName = input_console.next(); //ask user to input name of the matrix
                    matrixNames.put(matrixCount, matrixName); // add the name of the matrix to the map
                    matrixCount++; //increments the name count

                    System.out.print("Enter matrix dimension\nRows: ");
                    while (true) {
                        try {
                            row = input_console.nextInt();
                            if (row < 2) {
                                System.out.print("Matrix Required Dimension Error: Must be above 1\nRows: "); //prompts error message when user enters out-of-bound row dimension
                                throw new InputMismatchException();
                            } else if (row > 10) {
                                System.out.print("Matrix Dimension Limit Error: Must be below 11\nRows: "); //prompts error message when user enters out-of-bound row dimension
                                throw new InputMismatchException();
                            }
                        } catch (InputMismatchException e) { //catches the error
                            continue;
                        }
                        break;
                    }

                    System.out.print("Columns: ");
                    while (true) {
                        try {
                            col = input_console.nextInt();
                            if (col < 1) {
                                System.out.print("Matrix Required Dimension Error: Must be above 1\nColumns: "); //prompts error message when user enters out-of-bound column dimension
                                throw new InputMismatchException();
                            } else if (col > 10) {
                                System.out.print("Matrix Dimension Limit Error: Must be below 11\nColumns: "); //prompts error message when user enters out-of-bound column dimension
                                throw new InputMismatchException();
                            }
                        } catch (InputMismatchException e) { //catches the error
                            continue;
                        }
                        break;
                    }

                    int[][] matrix = new int[row][col];
                    System.out.println("Enter matrix elements: ");
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            System.out.printf("[%d][%d]: ", i, j);
                            matrix[i][j] = input_console.nextInt();
                        }
                    }
                    matrices.add(matrix); // add the matrix to the list

                    System.out.print("Add another matrix? [y]/[n]: ");
                    char appendMatrix = input_console.next().charAt(0);
                    if (appendMatrix != 'y') {
                        break;
                    }
                }
            }

            for (int i = 0; i < matrices.size(); i++) {
                int[][] matrix = matrices.get(i);
                String matrixName = matrixNames.get(i + 1);
                System.out.println("Matrix elements: [" + matrixName + "]");
                for (int[] row : matrix) {
                    for (int elem : row) {
                        System.out.print(elem + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }

            while (true) {
                System.out.print("Select Operation: [1] Addition / [2] Multiplication / [3] Exit: ");
                int choice = input_console.nextInt();
                for (int i = 0; i < matrices.size(); i++) {
                    int[][] matrix = matrices.get(i);
                    String matrixName = matrixNames.get(i + 1);
                    System.out.println("Matrix elements: [" + matrixName + "]");
                    for (int[] row : matrix) {
                        for (int elem : row) {
                            System.out.print(elem + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                if (choice == 1 || choice == 2) { // checks whether the user wants to add or multiply the matrices
                    int[][] result = matrices.get(0); // get the first matrix
                    for (int i = 1; i < matrices.size(); i++) { // iterate over the remaining matrices
                        int[][] matrix = matrices.get(i);
                        if (choice == 1) { //addition
                            for (int row = 0; row < result.length; row++) {
                                for (int col = 0; col < result[0].length; col++) {
                                    if (result.length != matrix.length || result[0].length != matrix[0].length) {
                                        System.out.println("Error: Matrices must have the same dimensions for addition.");
                                        conformmatrix = false;
                                        break;
                                    }
                                    else {
                                        result[row][col] += matrix[row][col];
                                    }
                                }
                            }
                            break;
                        }
                        else if (choice == 2) { //multiplication
                            for (int row = 0; row < result.length; row++) {
                                for (int col = 0; col < result[0].length; col++) {
                                    if (result.length != matrix.length || result[0].length != matrix[0].length) {
                                        System.out.println("Error: Matrices must have the same dimensions for multiplication.");
                                        conformmatrix = false;
                                        break;
                                    }
                                    else {
                                        result[row][col] *= matrix[row][col];
                                    }
                                }
                            }
                            break;
                        }
                    }
                    for (int i = 0; i < matrices.size(); i++) {
                        if (choice == 1) {
                            int[][] matrix = matrices.get(i);
                            if (result[0].length == matrix.length) {
                                System.out.println("Matrices Sum: "); //displays the sum of all matrices
                            } else {
                                break;
                            }
                        }
                        else if (choice == 2) {
                            int[][] matrix = matrices.get(i);
                            if (result[0].length == matrix.length) {
                                System.out.println("Matrices Product: "); //displays the product of all matrices
                            } else {
                                break;
                            }
                        }
                    }
                    for (int[] row : result) {
                        for (int elem : row) {
                            System.out.print(elem + " ");
                        }
                        System.out.println();
                    }
                }
                else if (choice == 3) {
                    System.out.println("Program Terminated!");
                    System.exit(1);
                }

                System.out.print("Add another matrix? [y]/[n] ");
                char appendMatrix = input_console.next().charAt(0);
                if (appendMatrix != 'y') {
                    break;
                }

                System.out.print("Enter a name for this matrix: ");
                matrixName = input_console.next();
                matrixNames.put(matrixCount, matrixName); // add the name of the matrix to the map
                matrixCount++;

                System.out.print("Enter matrix dimension:\nRows: ");
                int row = input_console.nextInt();
                System.out.print("Columns: ");
                int col = input_console.nextInt();

                int[][] matrix = new int[row][col];
                System.out.println("Enter matrix elements: ");
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.printf("[%d][%d]: ", i, j);
                        matrix[i][j] = input_console.nextInt();
                    }
                }
                matrices.add(matrix); // add the matrix to the list
            }
            System.out.print("Do you want to continue? [y]/[n] "); // Asks the user if they want to continue running the program.
            String userresponse = input_console.next(); //Reads the user's input for whether or not to continue running the program.
            continueprog = userresponse.equalsIgnoreCase("Y") || userresponse.equalsIgnoreCase("y");
            if (continueprog == userresponse.equalsIgnoreCase("N") || userresponse.equalsIgnoreCase("n")){
                System.out.println("Program Terminated! ");
                System.exit(1);
            }
        }
    }
}