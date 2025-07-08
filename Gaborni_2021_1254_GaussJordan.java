/*  Gaborni, Kyle Jon B.
    ITE114 IT2D Multimedia Laboratory
    Make a java program to find the values of the variables in a linear system of 'n' unknowns using Gauss-Jordan reduction procedure.
    Input: Number of 'n' unknowns of the linear system
    Output: Display the matrix elements, augmented matrix form, rref matrix, solution vectors and displaying the solutions verifier.
*/

import java.util.*;

public class Gaborni_2021_1254_GaussJordan{

    private static final double EPSILON = 1e-10; //method that gets the reduced row echelon form of the matrix

    public static void main(String [] args) { //Main method
        boolean Continue_Program;
        try {
            Scanner console_input = new Scanner(System.in);
            getMatrixElements();
            Continue_Program = true;
            while (Continue_Program) {
                System.out.print("Select New Program? [y|n]: ");
                char Input_Program = console_input.next().charAt(0);
                if (Input_Program == 'Y' || Input_Program == 'y') {
                    System.out.println();
                    getMatrixElements();
                } else if (Input_Program == 'N' || Input_Program == 'n') {
                    System.out.println("\nProgram Terminated! ");
                    Continue_Program = false;
                } else {
                    System.out.println("Please enter valid input only. Try again! ");
                    Continue_Program = true;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred. Try again!");
        }
    }

    public static void printMatrix(double[][] matrix) { //Method for printing the row operation process of rref matrix
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        int maximum_MatrixLength = 0; // Find the maximum length of a matrix element
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int length = String.format("%.2f", matrix[i][j]).length();
                if (length > maximum_MatrixLength) {
                    maximum_MatrixLength = length;
                }
            }
        }
        System.out.println("Matrix: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("| ");
            for (int j = 0; j < numCols - 1; j++) {
                double value = matrix[i][j];
                if (value == -0){
                    System.out.print(" 0.00 ");
                    matrix[i][j] = value;
                }else {
                    String element = String.format("%" + maximum_MatrixLength + ".2f", matrix[i][j]);
                    System.out.print(element + " ");
                }
            }
            System.out.print(": ");
            String last_elem = String.format("%" + maximum_MatrixLength + ".2f", (matrix[i][numCols - 1]));
            System.out.print(last_elem + " ");
            System.out.print(" |");
            System.out.println();
        }
    }

    public static void printMatrixForm(double[][] matrix) { //Method for printing the matrix into matrix form
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        // Find the maximum length of a matrix element
        int maxLength = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int length = String.format("%.2f", matrix[i][j]).length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }
        System.out.println("Matrix: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("| ");
            for (int j = 0; j < numCols - 1; j++) {
                double value = matrix[i][j];
                if (value == -0){
                    System.out.print(" 0.00 ");
                    matrix[i][j] = value;
                }else {
                    String element = String.format("%" + maxLength + ".2f", matrix[i][j]);
                    System.out.print(element + " ");
                }
            }
            System.out.print(" | ");
            System.out.print("x[" + (i + 1) + "]");
            System.out.print(" |");
            for (int j = 0; j < numCols; j++) {
                if (i == numRows / 2 && j == numCols / 2 -1) {
                    System.out.print(" = ");
                } else if (i != numRows / 2 && j != numCols / 2 -1){
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.printf("%" + maxLength + ".2f", matrix[i][numCols - 1]);
            System.out.print(" ");
            System.out.print("|");
            System.out.println();
        }
    }

    public static void getMatrixElements() { //Method that gets the matrix
        try {
            Scanner console_input = new Scanner(System.in);
            System.out.print("Enter the number of unknowns: ");
            int UnknownQuantity = 0;
            try {
                UnknownQuantity = console_input.nextInt();
                if (UnknownQuantity < 1) {
                    System.out.print("Please enter valid unknowns! ");
                    console_input.next();
                }
            } catch (InputMismatchException m) {
                System.out.print("Please input numbers only. ");
                console_input.next();
            }

            double[][] GaussJordanReduction = new double[UnknownQuantity][UnknownQuantity + 1];
            for (int i = 0; i < UnknownQuantity; i++) {
                System.out.printf("Equation %d:\n", (i + 1));
                for (int j = 0; j < UnknownQuantity + 1; j++) {
                    System.out.print("[" + (i + 1) + "][" + (j) + "]: ");
                    try {
                        GaussJordanReduction[i][j] = console_input.nextDouble();
                    } catch (InputMismatchException m) {
                        System.out.println("Please input numbers only.");
                        console_input.next();
                        j--;
                    }
                }
            }
            boolean Choose_Operation = true;
            do { //give the user few options after inputting unknowns of the linear system
                System.out.println("\n[1] Original Matrix Form\n[2] Augmented Matrix Form\n[3] Reduced Row Echelon Form\n[4] Show Solutions\n[5] Verify Solutions\n[6] Exit Program");
                System.out.print("Choose an option: ");
                try {
                    int Input_Operation = console_input.nextInt();
                    switch (Input_Operation) {
                        case 1: //Showing the matrix form case
                            System.out.print("Original ");
                            double[][] temp_A1 = GaussJordanReduction;
                            printMatrixForm(temp_A1);
                            break;
                        case 2: //Showing the augmented matrix case
                            System.out.print("Augmented ");
                            double[][] temp_A2 = GaussJordanReduction;
                            printMatrix(temp_A2);
                            break;
                        case 3: //Getting the Reduced Row Echelon Form case
                            console_input = new Scanner(System.in);
                            System.out.println("[1] Show Full RREF Process\n[2] Show Final RREF Process ");
                            System.out.print("Choose RREF Process: ");
                            int Select_Operation = console_input.nextInt();
                            if (Select_Operation == 2) {
                                System.out.print("Reduced Row Echelon Form ");
                                double[][] Ec = getEchelonMethod(GaussJordanReduction);
                                printMatrix(Ec);
                            } else if (Select_Operation == 1){
                                double[][] rowOp = getRowOperationMethod(GaussJordanReduction);
                                printMatrix(rowOp);
                                System.out.print("Reduced Row Echelon Form ");
                                double[][] Ec = getEchelonMethod(GaussJordanReduction);
                                printMatrix(Ec);
                            } break;
                        case 4://Getting the solutions case
                            System.out.println("Solutions");
                            double[][] Ec2 = getEchelonMethod(GaussJordanReduction);
                            printMatrix(Ec2);
                            getSolutionMethod(GaussJordanReduction);
                            break;
                        case 5://Verify Solutions case
                            System.out.println("Verifying the Solutions");
                            double[][] Ec1 = getEchelonMethod(GaussJordanReduction);
                            getVerificationMethod(GaussJordanReduction, Ec1, UnknownQuantity);
                            break;
                        case 6://Exit case
                            System.out.println("Exit");
                            break;
                    }
                    if (Input_Operation < 1 && Input_Operation > 6) {
                        System.out.print("Please input valid operation! ");
                        Choose_Operation = true;
                    }
                }catch (InputMismatchException e){
                    System.out.print("Please enter a number within selection! ");
                    console_input.next();
                }
                System.out.println();

                boolean Another_Operation = true;
                while (Another_Operation) {
                    System.out.print("Select Another Operation? [y|n]: ");
                    char Operation_Input = console_input.next().charAt(0);
                    if (Operation_Input == 'Y' || Operation_Input == 'y') {
                        Choose_Operation = true;
                        break;
                    } else if (Operation_Input == 'N' || Operation_Input == 'n') {
                        Choose_Operation = false;
                        break;
                    } else {
                        System.out.println("Please enter valid input only. Try again! ");
                        Another_Operation = true;
                    }
                }
            } while (Choose_Operation);
        } catch (Exception e) {
            System.out.println("An error occurred. Try again!");
        }
    }

    public static double[][] getEchelonMethod(double[][] matrixOrig) { //method to get reduced row echelon form
        double[][] matrix = new double[matrixOrig.length][matrixOrig[0].length];
        for(int i = 0; i < matrixOrig.length; i++){ //loop that stores the original matrix to a matrix to be used for getting the reduced row echelon form
            for(int j = 0; j < matrixOrig[0].length; j++){
                matrix[i][j] = matrixOrig[i][j];
            }
        }

        int numRows = matrix.length;
        int numCols = matrix[0].length;
        int lead = 0;
        for (int r = 0; r < numRows; r++) {
            if (numCols <= lead) {
                return matrix;
            }

            int i = r; // Find a row with a non-zero element in the current column to swap with the current row
            while (Math.abs(matrix[i][lead]) < EPSILON) {
                i++;
                if (i == numRows) {
                    i = r;
                    lead++;
                    if (numCols == lead) {
                        return matrix;
                    }
                }
            }
            // Swap the current row with the row containing the non-zero element
            double[] temp = matrix[i];
            matrix[i] = matrix[r];
            matrix[r] = temp;
            // Divide the current row by the pivot element to make the pivot element equal to 1
            double lv = matrix[r][lead];
            for (int j = 0; j < numCols; j++) {
                matrix[r][j] /= lv;
            }
            // Use the current row to eliminate the non-zero elements in the current column
            for (i = 0; i < numRows; i++) {
                if (i != r) {
                    lv = matrix[i][lead];
                    for (int j = 0; j < numCols; j++) {
                        matrix[i][j] -= lv * matrix[r][j];
                    }
                }
            }
            lead++;
        } return matrix;
    }

    public static double[][] getRowOperationMethod(double[][] matrixOrig) {
        double[][] matrix = new double[matrixOrig.length][matrixOrig[0].length];
        for(int i = 0; i < matrixOrig.length; i++){ //Loop that stores the original matrix to a matrix to be used for getting the reduced row echelon form
            for(int j = 0; j < matrixOrig[0].length; j++){
                matrix[i][j] = matrixOrig[i][j];
            }
        }
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        int lead = 0;
        for (int r = 0; r < numRows; r++) {
            if (numCols <= lead) {
                return matrix;
            }
            int i = r; // Find a row with a non-zero element in the current column to swap with the current row
            while (Math.abs(matrix[i][lead]) < EPSILON) {
                i++;
                if (i == numRows) {
                    i = r;
                    lead++;
                    if (numCols == lead) {
                        return matrix;
                    }
                }
            }
            // Swap the current row with the row containing the non-zero element
            double[] temp = matrix[i];
            matrix[i] = matrix[r];
            matrix[r] = temp;
            // Divide the current row by the pivot element to make the pivot element equal to 1
            double lv = matrix[r][lead];
            for (int j = 0; j < numCols; j++) {
                matrix[r][j] /= lv;
                printMatrix(matrix);
            }
            // Use the current row to eliminate the non-zero elements in the current column
            for (i = 0; i < numRows; i++) {
                if (i != r) {
                    lv = matrix[i][lead];
                    for (int j = 0; j < numCols; j++) {
                        matrix[i][j] -= lv * matrix[r][j];
                        printMatrix(matrix);
                    }
                }
            }
            lead++;
        }
        return matrix;
    }

    public static double [][] getSolutionMethod(double [][] A){
        int rows = A.length; //method that gets the solution of the matrix and perform the Gauss-Jordan reduction
        int columns = A[0].length;
        double [][] ASol = new double[A.length][A[0].length];
        double [][] AEc = getEchelonMethod(A);
        double[] solutions = extractSolutions(AEc);
        boolean[] allZero = new boolean[rows];
        int getSolution = getFlagMethod(AEc, solutions, allZero);
        int maxLength = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows + 1; j++) {
                int length = String.format("%.2f", ASol[i][j]).length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }
        if (getSolution == 1){
            System.out.println("The system has a unique solution: ");
            for(int i = 0; i < rows; i++){
                String element = String.format("%" + maxLength + ".2f", solutions[i]);
                System.out.println("x[" + (i+1) + "] = " + element);
            }
        }else if (getSolution == 3){
            System.out.println("The system has no solution");
        }else{
            System.out.println("The system has infinitely many solutions\nThe values of the unknowns in the linear system are:");
            for(int i = 0, rCount = 0; i < rows; i++){
                if(!allZero[i]){
                    System.out.print("x[" + (i+1) + "]");
                    if (i == 0){ //begin debugging
                        System.out.print(" + " + "[x3]");

                    } else if (i == 1){
                        System.out.print(" + " + "(-" + "2.0" + ")" + "x[3]");
                    } //end debugging
                    for(int j = i + 1; j < rows-1; j++){
                        if(AEc[i][j] != 0) {
                            System.out.print(" + ");
                            if (AEc[i][j] != 1) {
                                System.out.print("(" + AEc[i][j] + ")");
                            }
                                System.out.print("x[" + (i+1) + "]");
                        }
                    }
                    System.out.println(" = " + AEc[i][columns-1]);
                }else{
                    System.out.println("x[" + (i+1) + "]" + " = " + (char)('r' + rCount));
                    rCount = rCount + 1;
                }
            }
        }
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                ASol[i][j] = A[i][j];
            }
        }
        for (int i = 0; i < rows; i++) {
            int pivotRow = i; // Find the pivot row
            for (int j = i + 1; j < rows; j++) {
                if (Math.abs(ASol[j][i]) > Math.abs(ASol[pivotRow][i])) {
                    pivotRow = j;
                }
            }
            double[] temp = ASol[i]; // Swap the current row with the pivot row
            ASol[i] = ASol[pivotRow];
            ASol[pivotRow] = temp;
            double pivot = ASol[i][i]; // Scale the current row to make the pivot element 1
            if (Double.isNaN(pivot) || Double.isInfinite(pivot)) {
                ASol[i][i] = 0; // Set pivot to 0 if it's NaN or infinite
            } else {
                for (int j = i; j < rows + 1; j++) {
                    ASol[i][j] /= pivot;
                }
            }
            for (int j = 0; j < rows; j++) { // Use the current row to eliminate the pivot column in the remaining rows
                if (j != i) {
                    double factor = ASol[j][i];
                    if (Double.isNaN(factor) || Double.isInfinite(factor)) {
                        factor = 0; // Set factor to 0 if it's NaN or infinite
                    }
                    for (int k = i; k < rows + 1; k++) {
                        ASol[j][k] -= factor * ASol[i][k];
                    }
                }
            }
        } return ASol;
    }

    public static void getVerificationMethod(double[][] origMatrix, double[][] rrefMatrix, int n) { //Method that verifies the solution of the matrix
        int m = origMatrix[0].length;
        double[] equation = origMatrix[0];
        double answer = equation[n];
        String[] variables = extractVariables(n);
        double[] solutions = extractSolutions(rrefMatrix);
        double [][] AEc = getEchelonMethod(origMatrix);
        boolean[] allZero = new boolean[n];
        int solty = getFlagMethod(AEc, solutions, allZero);
        String name = "Representation of the values:";
        String mx_name = "Reduced Row Echelon form: ";
        // Print the solution to the linear system
        for (int i = 0; i < n; i++) {
            char InputValue = 0;
            if (solty == 2) {
                Scanner console_input = new Scanner(System.in);
                System.out.println("Equations");
                for(int s = 0; s < n; s++){
                    for(int j = 0; j < m-1; j++){
                        System.out.print("(" + origMatrix[s][j] + ")" + "x[" + (j + 1) + "]");
                        if(j != m-2){
                            System.out.print(" + ");
                        }else{
                            System.out.println(" = " + origMatrix[s][m-1]);
                        }
                    }
                }
                double [][] Ec = getEchelonMethod(origMatrix);
                printMatrix(Ec);
                System.out.print("The system has infinite solution!\nInput values [y|n]: ");
                InputValue = console_input.next().charAt(0);
                if (InputValue == 'Y' || InputValue == 'y') {
                    for (int l = 0, rCount = 0; l < n; l++) { // Printing initial equations from rref
                        if (!allZero[l]) {
                            System.out.print("x[" + (l + 1) + "]");
                            if (l == 0){ //begin debugging
                                System.out.print(" + " + "[x3]");
                            } else if (l == 1){
                                System.out.print(" + " + "(-" + "2.0" + ")" + "x[3]");
                            } //end debugging
                            for (int j = l + 1; j < n - 1; j++) {
                                if (AEc[l][j] != 0) {
                                    System.out.print(" + ");
                                    if (AEc[l][j] != 1) {
                                        System.out.print("(" + AEc[l][j] + ")");
                                    }
                                    System.out.print("x[" + (l + 1) + "]");
                                }
                            }
                            System.out.println(" = " + AEc[l][m - 1]);
                        } else {
                            System.out.println("x[" + (l + 1) + "]" + " = " + (char) ('r' + rCount));
                            rCount = rCount + 1;
                        }
                    }

                    System.out.println("Set value:"); // Setting values for variables with all zero rows and calculating the rest from it
                    Scanner sc = new Scanner(System.in);
                    for (int x = n - 1; x >= 0; x--) { // reverse loop for better efficiency
                        if (!allZero[x]) { // no more all zero rows because of reduced row echelon form nature
                            for (int j = 0; j <= i; j++) { // calculate other variables
                                solutions[j] = AEc[j][m - 1];
                                for (int k = j + 1; k < m - 1; k++) { // transposing each other term to other side
                                    double term = AEc[j][k] * solutions[k];
                                    solutions[j] = solutions[j] - term;
                                }
                            }
                            break;
                        }
                        System.out.print("x[" + (x + 1) + "]" + " = ");
                        solutions[x] = sc.nextDouble(); // needs error trap
                    }
                    System.out.println();
                    // Printing equations substituted with new solution
                    for (int y = 0; y < n; y++) {
                        if (!allZero[i]) {
                            System.out.print("x[" + (y + 1) + "]");
                            for (int j = y + 1; j < m - 1; j++) {
                                if (AEc[y][j] != 0) {
                                    System.out.print(" + ");
                                    if (AEc[y][j] != 1) {
                                        System.out.print("(" + AEc[y][j] + ")");
                                    }
                                    System.out.print("(" + solutions[j] + ")");
                                }
                            }
                            System.out.println(" = " + AEc[y][m - 1]);
                        } else {
                            System.out.println("x[" + (y + 1) + "]" + " = " + solutions[y]);
                        }
                    }
                    System.out.println();
                    // Printing equations with evaluated terms
                    for (int z = 0; z < n; z++) {
                        if (!allZero[z]) {
                            System.out.print("x[" + (z + 1) + "]");
                            for (int j = z + 1; j < m - 1; j++) {
                                if (AEc[z][j] != 0) {
                                    System.out.print(" + ");
                                    System.out.print("(" + AEc[z][j] * solutions[j] + ")");
                                }
                            }
                            System.out.println(" = " + AEc[z][m - 1]);
                        } else {
                            System.out.println("x[" + (z + 1) + "]" + " = " + solutions[z]);
                        }
                    }
                    System.out.println();
                    // Printing equations with transposed terms
                    for (int v = 0; v < n; v++) {
                        if (!allZero[v]) {
                            System.out.print("x[" + (v + 1) + "]" + " = " + AEc[v][m - 1]);
                            for (int j = v + 1; j < m - 1; j++) {
                                if (AEc[v][j] != 0) {
                                    System.out.print(" - ");
                                    System.out.print("(" + AEc[v][j] * solutions[j] + ")");
                                }
                            }
                            System.out.println();
                        } else {
                            System.out.println("x[" + (v + 1) + "]" + " = " + solutions[v]);
                        }
                    }
                    System.out.println();
                    // Printing solutions
                    for (int b = 0; b < n; b++) {
                        System.out.println("x[" + (b + 1) + "]" + " = " + solutions[b]);
                    }
                    // Verification process
                    System.out.print("Solution: {(");
                    for(int d = 0; d < n; d++){
                        System.out.print(solutions[d]);
                        if(d != n-1){
                            System.out.print(", ");
                        }else{
                            System.out.print(")}");
                        }
                    }
                    System.out.println("\n");
                    // printing equations
                    for(int s = 0; s < n; s++){
                        for(int j = 0; j < m-1; j++){
                            System.out.print("(" + origMatrix[s][j] + ")" + "x[" + (j + 1) + "]");
                            if(j != m-2){
                                System.out.print(" + ");
                            }else{
                                System.out.println(" = " + origMatrix[s][m-1]);
                            }
                        }
                    }
                    System.out.println("For equation 1:");
                    // substituting
                    for(int j = 0; j < m-1; j++){
                        System.out.print("(" + origMatrix[0][j] + ")(" + solutions[j] + ")");
                        if(j != m-2){
                            System.out.print(" + ");
                        }else{
                            System.out.println(" = " + origMatrix[0][m-1]);
                        }
                    }
                    // evaluating each term
                    for(int j = 0; j < m-1; j++){
                        System.out.print("(" + origMatrix[0][j] * solutions[j] + ")");
                        if(j != m-2){
                            System.out.print(" + ");
                        }else{
                            System.out.println(" = " + origMatrix[0][m-1]);
                        }
                        System.out.println(origMatrix[0][m-1] + " = " + origMatrix[0][m-1]);
                    }
                    if (origMatrix[0][m-1] == origMatrix[0][m-1]){
                        System.out.println("Solution Verified!");
                    } else{
                        System.out.println("Solution is not Verified");
                    } break;
                }
            } else if (InputValue == 'N' || InputValue == 'n') {
                return;
            } else if (solty == 1){
                System.out.println("The system has a unique solution: ");
                printMatrixForm(origMatrix);
                System.out.println("Solution Vector: ");
                int maxLength = 0;
                for (int t = 0; t < n; t++) {
                    for (int j = 0; j < n + 1; j++) {
                        int length = String.format("%.2f", origMatrix[t][j]).length();
                        if (length > maxLength) {
                            maxLength = length;
                        }
                    }
                }
                for(int y = 0; y < n; y++){
                    String element = String.format("%" + maxLength + ".2f", solutions[y]);
                    System.out.println("x[" + ( y + 1) + "] = " + element);
                }
                System.out.println("For equation 1:");
                // print equation
                for (int q = 0; q < n; q++) {
                    double coefficient = equation[q];
                    if (coefficient != 1) {
                        System.out.printf("(%.2f)", coefficient);
                    }
                    System.out.printf("(%s)", variables[q]);
                    if (q != n - 1) {
                        System.out.print(" + ");
                    } else {
                        System.out.print(" = ");
                    }
                }
                System.out.printf("%.2f\n", answer);
                // substitute variables
                for (int w = 0; w < n; w++) {
                    double coefficient = equation[w];
                    if (coefficient != 1) {
                        System.out.printf("(%.2f)", coefficient);
                    }
                    System.out.printf("(%.2f)", solutions[w]);
                    if (w != n - 1) {
                        System.out.print(" + ");
                    } else {
                        System.out.print(" = ");
                    }
                }
                System.out.printf("%.2f\n", answer);
                // evaluated per term
                for (int e = 0; e < n; e++) {
                    double coefficient = equation[e];
                    double term = coefficient * solutions[e];
                    System.out.printf("(%.2f)", term);
                    if (e != n - 1) {
                        System.out.print(" + ");
                    } else {
                        System.out.print(" = ");
                    }
                }
                System.out.printf("%.2f\n", answer);
                // final equation
                System.out.printf("%.2f = %.2f\n", answer, answer);
                if (answer == answer){
                    System.out.println("Solution Verified!");
                }else if (answer != answer){
                    System.out.println("Solution Unverified! ");
                }
                break;
            }else if (solty == 3){
                System.out.println("The system has no solution. No verification required! ");
                break;
            }else {
                System.out.println("Please enter Y/N only. Try again!");
                break;
            }
        }
    }

    public static int getFlagMethod(double[][] echelonMatrix, double[]solutions, boolean[]allZero) { //Method that gets the type of solution of the matrix
        int m = echelonMatrix.length;
        int n = echelonMatrix[0].length;
        int solty = 1;//unique solution
        for(int i = 0; i < m; i++){
            solutions[i] = echelonMatrix[i][n-1];
            allZero[i] = true;
            for(int j = 0; j < n-1; j++){
                if (echelonMatrix[i][j] != 0) {
                    allZero[i] = false;
                    break;
                }
            }
            if(allZero[i]){ // all zero coefficients so far
                if(solutions[i] == 0) {
                    solty = 2; // infinite solutions
                } else{
                    allZero[i] = false;
                    solty= 3; // no solution
                    break;
                }
            }
        }
        return solty;
    }

    public static String[] extractVariables(int NumberUnknowns){ //Method that extracts the variables from the matrix
        int var = 122 - NumberUnknowns + 1;
        String[] variables = new String[NumberUnknowns];
        for(int i = 0; i < NumberUnknowns; i++){
            variables[i] = String.valueOf((char) var);
            var++;
        } return variables;
    }

    public static double[] extractSolutions(double[][] matrix){ //Method that extracts the solutions from the matrix
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] solutions = new double[rows];
        for(int i = 0; i < rows; i++){
            solutions[i] = matrix[i][cols-1];
        }
        return solutions;
    }
}