/** Gaborni, Kyle Jon B.
 ITE114 IT2D Multimedia Laboratory
 Input: Display the elements of the matrix with only conformable matrices.
 Output: Displays the matrix elements, its identity matrix, augmented matrix, reduced row echelon matrix form and inverse matrix.
 */
import java.util.*;
public class Gaborni_2021_1254_InverseMatrix {
    public static void main(String[] args) { //Main method
        try {
            Scanner console_input = new Scanner(System.in); Matrix_GetMethod();
            boolean continueprogram = true;
            while (continueprogram) {
                System.out.print("Another Program? [y/n] ");
                char user_choice = console_input.next().charAt(0);
                if (user_choice == 'Y' || user_choice == 'y') {
                    Matrix_GetMethod();
                } else if (user_choice == 'N' || user_choice == 'n') {
                    System.out.println("Program Terminated! ");
                    continueprogram = false;
                } else {
                    System.out.println("Invalid Input! Please try the correct input. ");
                    continueprogram = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detected! Try Again. ");
        }
    }

    public static void Matrix_GetMethod() { //Method that gets the matrix
        try {
            boolean continueprogram = true;
            Scanner console_input = new Scanner(System.in);
            while (continueprogram) {
                int Matrix_Rows, Matrix_Columns;
                System.out.print("Enter the name of the matrix: ");
                String Matrix_Name = console_input.next();
                System.out.print("Enter the size of the matrix ["+ Matrix_Name +"]:\n");
                System.out.print("Row: ");
                while (true) {
                    try {
                        Matrix_Rows = console_input.nextInt();
                        if (Matrix_Rows < 1) {
                            System.out.print("Row Dimension must be greater than 0\nRows: ");
                            throw new InputMismatchException();
                        }
                    }
                    catch (InputMismatchException e){
                        continue;
                    }
                    break;
                }
                System.out.print("Column: ");
                while (true) {
                    try {
                        Matrix_Columns = console_input.nextInt();
                        if (Matrix_Columns < 1) {
                            System.out.print("Column Dimension must be greater than 0\nColumns: ");
                            throw new InputMismatchException();
                        }
                    }
                    catch (InputMismatchException e){
                        continue;
                    }
                    break;
                }

                if (Matrix_Rows != Matrix_Columns) {
                    System.out.print("Non-Conformable Matrices! Modify Previous Program? [y/n] ");
                    char response = console_input.next().charAt(0);
                    if (response == 'Y' || response == 'y') {
                        continueprogram = true;
                    } else if (response == 'N' || response == 'n') {
                        break;
                    }
                } else if (Matrix_Rows == Matrix_Columns) {
                    System.out.println("Enter the size of the matrix [" + Matrix_Name + "]: ");
                    double[][] matrix = new double[Matrix_Rows][Matrix_Columns];
                    for (int i = 0; i < Matrix_Rows; i++) {
                        for (int j = 0; j < Matrix_Columns; j++) {
                            System.out.print("[" + (i) +"]["+ (j) + "]: ");
                            matrix[i][j] = console_input.nextInt();
                        }
                    } System.out.println();

                    String matrixmes = "";
                    printMatrix(matrix, Matrix_Name, matrixmes);
                    System.out.println();

                    int m = matrix.length; // Number of rows in A
                    int n = matrix[0].length; // Number of columns in A
                    double[][] Identity_Matrix = MatrixIdentity(m, n);

                    if (!InvertibleMatrices_Checker(matrix)) {
                        String IdentityMatrix = "Identity ";
                        printMatrix(Identity_Matrix, Matrix_Name, IdentityMatrix);
                        System.out.println();

                        double[][] Augmented_Identity = AugmentedIdentity_Matrix(matrix);
                        String AugmentedIdentity = "Augmented Identity ";
                        printMatrix(Augmented_Identity, Matrix_Name, AugmentedIdentity);
                        System.out.println();

                        double[][] FindReducedRow_Echelon = MatrixRow_Operations(matrix, Matrix_Name);
                        String ReducedRowEchelon = "\nReduced Row Echelon Form ";
                        printMatrix(FindReducedRow_Echelon, Matrix_Name, ReducedRowEchelon);
                        System.out.println();

                        System.out.print("Matrix Uninvertible: The Determinant is Zero!\nModify Program? [y/n] ");
                        char user_choice = console_input.next().charAt(0);
                        if (user_choice == 'Y' || user_choice == 'y') {
                            continueprogram = true;
                        } else if (user_choice == 'N' || user_choice == 'n') {
                            break;
                        }
                    }
                    else {
                    String IdentityMatrix = "Identity ";
                    printMatrix(Identity_Matrix, Matrix_Name, IdentityMatrix);
                    System.out.println();

                    double[][] Augmented_Identity = AugmentedIdentity_Matrix(matrix);
                    String AugmentedIdentity = "Augmented Identity ";
                    printMatrix(Augmented_Identity, Matrix_Name, AugmentedIdentity);
                    System.out.println();

                    double[][] FindReducedRow_Echelon = MatrixRow_Operations(matrix, Matrix_Name);
                    String ReducedRowEchelon = "\nReduced Row Echelon Form ";
                    printMatrix(FindReducedRow_Echelon, Matrix_Name, ReducedRowEchelon);
                    System.out.println();
                        if (InvertibleMatrices_Checker(matrix)) {
                            double[][] Inverse_Matrix = GetInvertedMatrix(Augmented_Identity); // Invert the augmented matrix
                            String InverseMatrix = "Inverse ";
                            printMatrix(Inverse_Matrix, Matrix_Name, InverseMatrix);
                            System.out.println();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detected! Try Again.");
        }
    }

    public static double[][] MatrixIdentity(int Rows, int Columns) { //Method for getting the Identity Matrix
        double[][] FindMatrix_Identity = new double[Rows][Columns];
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                if (i == j) {
                    FindMatrix_Identity[i][j] = 1;
                } else {
                    FindMatrix_Identity[i][j] = 0;
                }
            }
        } return FindMatrix_Identity;
    }

    public static double[][] AugmentedIdentity_Matrix(double[][] matrix) { //Method for getting the augmented identity of the matrix
        int Rows = matrix.length;  int Columns = matrix[0].length;// Number of rows and columns in A
        double[][] AI = new double[Rows][2 * Columns]; // New matrix with 2 * Columns columns
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                AI[i][j] = matrix[i][j]; // Copy the elements of A into the left half of AI
            }
            AI[i][Columns + i] = 1; // Set the diagonal elements of the right half of AI to 1
        } return AI;
    }

    public static double[][] FindReducedRow_Echelon(double[][] matrix) { //Method that gets the reduced row echelon form of the matrix
        int m = matrix.length; int n = matrix[0].length; // Number of rows and columns in A
        double[][] AI = new double[m][2 * n]; // New matrix with 2 * n columns
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                AI[i][j] = matrix[i][j]; // Copy the elements of A into the left half of AI
            }
            AI[i][n + i] = 1; // Set the diagonal elements of the right half of AI to 1
        }
        for (int i = 0; i < m; i++) { // convert AI to reduced row echelon form
            int pivot = -1; // find pivot
            for (int j = 0; j < n * 2; j++) {
                if (AI[i][j] != 0) {
                    pivot = j;
                    break;
                }
            }

            if (pivot == -1) {
                continue; // row has all zeros, move to next row
            }
            double divisor = AI[i][pivot]; // divide row by pivot element
            for (int j = 0; j < n * 2; j++) {
                AI[i][j] /= divisor;
            }
            for (int j = 0; j < m; j++) { // subtract multiples of row from other rows to make column zeros
                if (j == i) {
                    continue; // skip current row
                }
                double multiple = AI[j][pivot];
                for (int k = 0; k < n * 2; k++) {
                    AI[j][k] -= multiple * AI[i][k];
                }
            }
        } return AI;
    }

    public static boolean InvertibleMatrices_Checker(double[][] matrix) { //Method that checks whether the matrix is invertible or not
        int n = matrix.length;
        double[][] identity = MatrixIdentity(n, n);
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
                augmented[i][j + n] = identity[i][j];
            }
        }

        double[][] rref = FindReducedRow_Echelon(augmented);
        for (int i = 0; i < n; i++) { // Check if any pivot is 0, which would mean that the matrix is singular
            if (rref[i][i] == 0) {
                return false;
            }
        } return true;
    }

    //Method for showing the row operations
    public static double[][] MatrixRow_Operations(double [][] matrix, String name){
        int m = matrix.length;  int n = matrix[0].length; // Number of rows and columns in A
        double[][] AI = new double[m][2 * n]; // New matrix with 2 * n columns
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                AI[i][j] = matrix[i][j]; // Copy the elements of A into the left half of AI
            }
            AI[i][n + i] = 1; // Set the diagonal elements of the right half of AI to 1
        }
        int pivot = 0; // convert AI to reduced row echelon form
        for (int i = 0; i < m; i++) {
            int r = i;
            while(AI[r][pivot]==0){
                r++;
                if(r==m){
                    r = i;
                    pivot++;
                    if(pivot == n){
                        return AI;
                    }
                }
            }
            if(r != i){
                double[] temp = AI[r];
                AI[r] = AI[i];
                AI[i] = temp;
            }
            double divisor = AI[i][pivot];
            for (int j = 0; j < n*2; j++) {
                AI[i][j] /= divisor;
            }

            for (int j = 0; j < m; j++) { // subtract multiples of row from other rows to make column zeros
                if (j == i) {
                    continue; // skip current row
                }
                double multiple = AI[j][pivot];
                for (int k = 0; k < n*2; k++) {
                    AI[j][k] -= multiple * AI[i][k];
                }
            }
        }
        return AI;
    }

    public static double[][] GetInvertedMatrix(double[][] AI) { //Method that gets the inverse of the matrix
        int m = AI.length; // Number of rows in AI
        int n = AI[0].length; // Number of columns in AI
        for (int i = 0; i < m; i++) { // Apply Gaussian elimination to transform augmented identity matrix into the reduced row echelon form
            double pivot = AI[i][i]; // Divide the current row by its pivot element
            for (int j = i; j < n; j++) {
                AI[i][j] /= pivot;
            }
            for (int k = i + 1; k < m; k++) { // Subtract multiples of the current row from the rows below it to create zeros below the pivot
                double factor = AI[k][i];
                for (int j = i; j < n; j++) {
                    AI[k][j] -= factor * AI[i][j];
                }
            }
        }

        for (int i = m - 1; i >= 0; i--) { // Apply back substitution to transform AI into the form [In | A^-1]
            for (int k = i - 1; k >= 0; k--) { // Subtract multiples of the current row from the rows above it to create zeros above the pivot
                double factor = AI[k][i];
                for (int j = i; j < n; j++) {
                    AI[k][j] -= factor * AI[i][j];
                }
            }
        }

        double[][] inverse = new double[m][m];  // Extract the inverse matrix from the right half of AI
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                inverse[i][j] = AI[i][m + j];
            }
        } return inverse;
    }

    public static void printMatrix(double[][] matrix, String name, String message) { //Method for printing the matrix
        int numRows = matrix.length; int numCols = matrix[0].length;
        int maxLength = 0; // Find the maximum length of a matrix element
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int length = String.format("%.2f", matrix[i][j]).length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }
        System.out.println(message + "Matrix " + name + ":");
        for (int i = 0; i < numRows; i++) {
            System.out.print("|  ");
            for (int j = 0; j < numCols; j++) {
                String element = String.format("%" + maxLength + ".2f", matrix[i][j]);
                System.out.print(element + "   ");
            } System.out.print(" |");
            System.out.println();
        }
    }

    public static void printMatrix2(double[][] matrix) {
        int numRows = matrix.length; int numCols = matrix[0].length;
        int maxLength = 0; // Find the maximum length of a matrix element
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int length = String.format("%.2f", matrix[i][j]).length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }
        for (int i = 0; i < numRows; i++) {
            System.out.print("|  ");
            for (int j = 0; j < numCols; j++) {
                String element = String.format("%" + maxLength + ".2f", matrix[i][j]);
                System.out.print(element + "   ");
            } System.out.print(" |");
            System.out.println();
        }
    }
}