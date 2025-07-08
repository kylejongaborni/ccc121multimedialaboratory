/*  Make a Java program on the application of Linear Combination and Independence:
    For Linear Combination: Input a set of vectors u₁ ⃑, u₂ ⃑, u₃ ⃑, ..., uₙ ⃑ and a vector X ⃑. Show that vector X ⃑ is a linear combination of vectors u₁ ⃑, u₂ ⃑, u₃ ⃑, ..., uₙ ⃑.
    For linear dependence/independence: If vector X ⃑ is a zero vector, then show whether or not the vectors u₁, u₂, u₃, ..., uₙ are linearly dependent, that is a₁u₁ ⃑ + a₂u₂ ⃑ + a₃u₃ ⃑ + ...  aₙuₙ ⃑  = 0 when not all aᵢ'ₛ equal to zero.
    Otherwise, the vectors u₁ ⃑, u₂ ⃑, u₃ ⃑, ..., uₙ ⃑ are linearly dependent

//    Java program that allows you to input a set of vectors and perform linear combination and linear dependence/independence checks.
//    It uses matrix notation and includes error handling.
//    This program provides a menu-based interface where you can choose between linear combination and linear dependence/independence checks.
//    It uses a Matrix class to handle matrix operations, and a GaussianElimination class to perform row reduction using the Gaussian elimination method.
//    The resulting number of equations is equal to the number of unknowns to have a square coefficient matrix.

    Input: set of vectors of the linear combination and the linear dependence/independence.
    Output: display the matrix of the Equation, augmented Matrix of the Equation, the Linear Combination or Linear Dependence/Independence, and the solution.
*/

import java.text.DecimalFormat;
import java.util.Scanner;
public class Gaborni_2021_1254_LCI {
    private static final double EPSILON = 1e-10; //method that gets the reduced row echelon form of the matrix

    public static void main(String[] args) {
        String continueProgram;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Input number of vectors: ");
            int cols = sc.nextInt();
            System.out.print("Input vector length: ");
            int rows = sc.nextInt();

            String[] VectorNames = new String[rows];
            for (int r = 0; r < rows; r++) {
                VectorNames[r] = "u_" + (r + 1);
                if (r == 1){
                    VectorNames[r] = "v_" + (r + 1);
                }
                else if (r == 2){
                    VectorNames[r] = "w_" + (r + 1);
                }
            }
            String[] CoefficentNames = new String[rows];
            for (int r = 0; r < rows; r++) {
                CoefficentNames[r] = "a_" + (r + 1);
            }

            double[][] vectors = new double[rows][cols];
            for (int c = 0; c < cols; c++) {
                System.out.println("Vector " + (c + 1) + ":");
                for (int r = 0; r < rows; r++) {
                    System.out.print("[" + r + "]: ");
                    vectors[r][c] = sc.nextDouble();
                }
            }

            System.out.println("Input result vector:");
            double[] resultVector = new double[rows];
            for (int r = 0; r < rows; r++) {
                System.out.print("[" + r + "]: ");
                resultVector[r] = sc.nextDouble();
            }

            double[][] aug = new double[rows][cols + 1]; // generate augmented matrix
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    aug[r][c] = vectors[r][c];
                }
                aug[r][cols] = resultVector[r];
            }

            double[][] rref = new double[rows][cols + 1]; // generate reduced row echelon
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    rref[r][c] = vectors[r][c];
                }
                rref[r][cols] = resultVector[r];
            }
            GaussianElimination(rref);
            int solutionType = getSolutionType(rref); // get type of solution

            while (true) {
                System.out.println("\n=======================================================");
                System.out.println("[0] New Program/Exit Program");
                System.out.println("[1] The Set of Vector/s");
                System.out.println("[2] The Equation: Linear Combination or Linear Dependence/Independence");
                System.out.println("[3] The Matrix of the Equation");
                System.out.println("[4] The Augmented Matrix of the Equation");
                System.out.println("[5] The Reduced Row Echelon Form");
                System.out.println("[6] The Vector Solution");
                System.out.print("Select Operation: ");
                int choice = sc.nextInt();
                if (choice == 0) {
                    break;
                } else if (choice == 1) { //displays the set of input vectors
                    System.out.println("\nSet of Vectors: ");
                    printVectors(vectors, VectorNames);
                    System.out.println("Result Vector: ");
                    for (int r = 0; r < rows; r++) {
                        System.out.println("| " + resultVector[r] + " |");
                    }
                } else if (choice == 2) { //displays the result -> if linear combination or linear dependence/independence
                    System.out.println("\nSystem of Equation: ");
                        printEquation(vectors, resultVector, CoefficentNames);
                } else if (choice == 3) { //displays the matrix form of the equation
                    System.out.println("\nMatrix Form of the Equation: ");
                    printMatrixForm(vectors, resultVector, CoefficentNames);
                } else if (choice == 4) { //displays the augmented matrix form of the equation
                    System.out.println("\nAugmented Matrix Form of the Equation: ");
                        printAugmentedMatrix(aug);
                } else if (choice == 5) {
                    System.out.println("[1] Show Full RREF Process\n[2] Show Final RREF Process ");
                    System.out.println("Select Operation: ");
                    int Select_Operation;
                    Select_Operation = sc.nextInt();
                    if (Select_Operation == 2) {
                        System.out.print("Reduced Row Echelon Form ");
                        printMatrix(rref);
                    } else if (Select_Operation == 1){
                        double[][] rowOp = getRowOperationMethod(rref);
                        System.out.println("Row Operation Method");
                        printMatrix(rowOp);
                        System.out.println("Reduced Row Echelon Form");
                        double[][] ec = getEchelonMethod(rowOp);
                        printMatrix(ec);
                    }
                } else if (choice == 6) { //displays the solution of the vector equation
                        verifySolution(rref, aug, CoefficentNames);
                        int flag = getSolutionType(rref);
                        if (flag == 2){
                            System.out.println("The system is linearly dependent");
                        }
                        if (flag == 1){
                            System.out.println("The system is linearly independent");
                        }
                    } else{
                        System.out.println("Invalid Option! Please Try Again.");
                    }
                }
            sc.nextLine();
            System.out.print("New Program? [y|n]: ");
            continueProgram = sc.nextLine();
            if(continueProgram.equalsIgnoreCase("n")){
                System.out.println("Program Terminated! ");
                break;
            }
        }while(continueProgram.equalsIgnoreCase("y"));
    }

    public static void printVectors(double[][] vectors, String[] vectorNames){
        int rows = vectors.length;
        int cols = vectors[0].length;
        for(int c = 0; c < cols; c++){
            System.out.println(vectorNames[c] + ": ");
            for(int r = 0; r < rows; r++){
                System.out.println("| " + vectors[r][c] + " |");
            }
        }
    }

    public static void printEquation(double[][] vectors, double[] resultVector, String[] coeffNames){
        int rows = vectors.length;
        int cols = vectors[0].length;
        int middle = rows/2;
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(r == middle){
                    System.out.print("(" + coeffNames[c] + ")");
                }else{
                    System.out.print("     ");
                }
                System.out.print("| " + vectors[r][c] + " |");
                if(r == middle){
                    if(c != cols - 1){
                        System.out.print(" + ");
                    }else{
                        System.out.print(" = ");
                    }
                }else{
                    System.out.print("   ");
                }
            }
            System.out.println("| " + resultVector[r] + " |");
        }
    }

    public static void printMatrixForm(double[][] vectors, double[] resultVector, String[] coeffNames){
        int rows = vectors.length;
        int cols = vectors[0].length;
        int middle = rows/2;
        for(int r = 0; r < rows; r++){
            System.out.print("| ");
            for(int c = 0; c < cols; c++){
                System.out.print(vectors[r][c]);
                if(c != cols - 1){
                    System.out.print("  ");
                }
            }
            System.out.print(" | | ");
            System.out.print(coeffNames[r] + " |");
            if(r == middle){
                System.out.print(" = ");
            }else{
                System.out.print("   ");
            }
            System.out.println("| " + resultVector[r] + " |");
        }
    }

    public static void printAugmentedMatrix(double[][] aug){
        int rows = aug.length;
        int cols = aug[0].length;
        for(int r = 0; r < rows; r++){
            System.out.print("| ");
            for(int c = 0; c < cols-1; c++){
                System.out.print(aug[r][c] + "  ");
            }
            System.out.println(": " + aug[r][cols-1] + " |");
        }
    }

    private static boolean hasNonzeroDecimal(double value) {
        String stringValue = String.valueOf(value);
        int dotIndex = stringValue.indexOf(".");
        if (dotIndex != -1) {
            for (int i = dotIndex + 1; i < stringValue.length(); i++) {
                if (stringValue.charAt(i) != '0') {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isZeroWithNonzeroDecimal(double value) {
        return value != Math.floor(value);
    }

    public static void verifySolution(double[][] rref, double[][] matrix, String[] variables){
        int rows = rref.length;
        int cols = rref[0].length;
        boolean[] allZero = new boolean[rows];
        DecimalFormat df = new DecimalFormat("#.##");
        for(int r = 0; r < rows; r++){
            allZero[r] = true;
            for(int c = 0; c < cols; c++){
                if(rref[r][c] != 0){
                    allZero[r] = false;
                    break;
                }
            }
        }
        int flag = getSolutionType(rref);
        double[] solutions = new double[rows];
        for(int r = 0; r < rows; r++){
            solutions[r] = rref[r][cols-1];
        }

        if(flag == 3){ // no solution
            System.out.println("The system has no solution: The system is not a linear combination");
        }else{
            if(flag == 2) { // infinite solutions so solutions must be altered first
                // Printing initial equations from rref

                for (int i = 0, rCount = 0; i < rows; i++) {
                    if (!allZero[i]) {
                        System.out.print(variables[i]);
                        for (int j = i + 1; j < cols - 1; j++) {
                            if (rref[i][j] != 0) {
                                System.out.print(" + ");
                                if (rref[i][j] != 1) {
                                    System.out.print("(" + rref[i][j] + ")");
                                }
                                System.out.print(variables[j]);
                            }
                        }
                        System.out.println(" = " + rref[i][cols - 1]);
                    } else {
                        System.out.println(variables[i] + " = " + (char)('r' + rCount));
                        rCount = rCount + 1;
                    }
                } System.out.println();
            }

            // Verification process
            System.out.print("Solution: {(");
            for(int i = 0; i < rows; i++){
                System.out.print(df.format(solutions[i]));
                if(i != rows-1){
                    System.out.print(", ");
                }else{
                    System.out.print(")}");
                }
            }
            System.out.println("\n");
            for(int i = 0; i < rows; i++){ // printing equations
                for(int j = 0; j < cols-1; j++){
                    System.out.print("(" + matrix[i][j] + ")" + variables[j]);
                    if(j != cols-2){
                        System.out.print(" + ");
                    }else{
                        System.out.println(" = " + matrix[i][cols-1]);
                    }
                }
            }
            System.out.println("Equation 1:");
            // substituting
            for(int j = 0; j < cols-1; j++){
                System.out.print("(" + matrix[0][j] + ")(" + df.format(solutions[j]) + ")");
                if(j != cols-2){
                    System.out.print(" + ");
                }else{
                    System.out.println(" = " + matrix[0][cols-1]);
                }
            }
            // evaluating each term
            for(int j = 0; j < cols-1; j++){
                //previous code: System.out.print("(" + matrix[0][j] * df.format(solutions[j]) + ")");
                System.out.print("(" + df.format(matrix[0][j] * solutions[j]) + ")");
                if(j != cols-2){
                    System.out.print(" + ");
                }else{
                    System.out.println(" = " + matrix[0][cols-1]);
                }
            }
            // equating
            System.out.println(matrix[0][cols-1] + " = " + matrix[0][cols-1]);
        }
    }

    public static int getSolutionType(double[][] rref){
        boolean[] freeVariables = getFreeVariables(rref);
        if(freeVariables == null){
            return 3;
        }
        for (int i = 0; i < freeVariables.length; i++){
            if(freeVariables[i]){
                return 2;
            }
        }
        return 1;
    }

    public static int GaussianElimination(double[][] a){
        int i, j, k, c, flag = 0, m = 0;
        double pro = 0;
        int n = a.length;

        // Performing elementary operations
        for (i = 0; i < n; i++) {
            if (a[i][i] == 0)
            {
                c = 1;
                while ((i + c) < n && a[i + c][i] == 0)
                    c++;
                if ((i + c) == n)
                {
                    flag = 1;
                    break;
                }
                for (j = i, k = 0; k <= n; k++)
                {
                    double temp =a[j][k];
                    a[j][k] = a[j+c][k];
                    a[j+c][k] = temp;
                }
            }

            for (j = 0; j < n; j++)
            {
                // Excluding all i == j
                if (i != j){
                    // Converting Matrix to reduced row
                    // echelon form(diagonal matrix)
                    double p = a[j][i] / a[i][i];

                    for (k = 0; k <= n; k++)
                        a[j][k] = a[j][k] - (a[i][k]) * p;
                }
            }
        }
        for(i = 0; i < n; i++){
            double pivot = a[i][i];
            for(j = i; j < n; j++){
                if(pivot != 0) {
                    a[i][j] = a[i][j] / pivot;
                }
            }
        }
        return flag;
    }

    public static boolean[] getFreeVariables(double[][] rref){
        int rows = rref.length;
        int cols = rref[0].length - 1;
        boolean[] freeVars = new boolean[cols];
        int pivot = 0;

        for(int r = 0; r < rows; r++){
            if(rref[r][pivot] == 1){
                freeVars[pivot] = false;
            }
            else{
                while(rref[r][pivot] != 1){
                    freeVars[pivot] = true;
                    pivot++;
                    if(pivot == cols) { //all zero coefficients
                        if(rref[r][cols] != 0){
                            return null;
                        }
                        break;
                    }
                }
                if (pivot >= cols){
                    break;
                }
                else{
                    freeVars[pivot] = false;
                }
            }
            pivot++;
            if(pivot == cols){
                break;
            }
        }
        return freeVars;
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
}