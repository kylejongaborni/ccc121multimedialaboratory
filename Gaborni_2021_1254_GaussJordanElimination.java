/*  Gaborni, Kyle Jon B.
    ITE114 IT2D Multimedia Laboratory
    Make a java program to find the values of the variables in a linear system of 'n' unknowns using Gauss-Jordan reduction procedure.
    Input: Number of 'n' unknowns of the linear system
    Output: Display the matrix elements, augmented matrix form, rref matrix, solution vectors and displaying the solutions verifier.
*/

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Gaborni_2021_1254_GaussJordanElimination {
    public static void main(String[] args) {
        Scanner consoleInput = new Scanner(System.in);
        boolean continueProgram = true;
        while (continueProgram) {
            System.out.print("Enter the number of equations: ");
            int UnknownQuantity;
            while (true) {
                try {
                    UnknownQuantity = consoleInput.nextInt();
                    if (UnknownQuantity <= 0) {
                        throw new InputMismatchException();
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("You entered an invalid or out-of-bound input. Try again!\nEnter the number of unknowns: ");
                    consoleInput.nextLine();
                }
            }

            double[][] gaussJordanReduction = new double[UnknownQuantity][UnknownQuantity + 1];
            while (true) {
                for (int i = 0; i < UnknownQuantity; i++) {
                    System.out.printf("Equation %d:\n", (i + 1));
                    for (int j = 0; j < UnknownQuantity + 1; j++) {
                        try {
                            System.out.printf("A[%d][%d]: ", i, j);
                            gaussJordanReduction[i][j] = consoleInput.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("You entered an invalid or out-of-bound input. Try again!");
                            consoleInput.nextLine();
                            j--; // Decrement 'j' to ask again for the same variable
                        }
                    }
                }
                break;
            }

            boolean displayOperation = true;
            while (displayOperation) {
                System.out.println("[1] Matrix Form\n[2] Augmented Matrix Form\n[3] Reduced Row Echelon Form\n[4] Solution Vector\n[5] Verify Solution\n[6] Exit Program");
                System.out.print("Choose an operation: ");
                int userOperation = consoleInput.nextInt();
                switch (userOperation) {
                    case 1:
                        displayMatrix(gaussJordanReduction);
                        break;
                    case 2:
                        displayAugmentedMatrix(gaussJordanReduction, UnknownQuantity);
                        break;
                    case 3:
                        chooseDisplayMethod(consoleInput, gaussJordanReduction, UnknownQuantity);
                        break;
                    case 4:
                        showSolution(gaussJordanReduction, new String[UnknownQuantity], UnknownQuantity);
                        break;
                    case 5:
                        verifySolution(gaussJordanReduction, gaussJordanReduction, new String[UnknownQuantity]);
                        break;
                    case 6:
                        System.out.println("Program Terminated!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid input");
                        break;
                }
                System.out.print("\nChoose another operation? [y|n]: ");
                String userChoice = consoleInput.next();
                displayOperation = userChoice.equalsIgnoreCase("y");
                if (!displayOperation) {
                    break;
                }
            }

            System.out.print("Continue another program? [y|n]: ");
            String userChoice = consoleInput.next();
            continueProgram = userChoice.equalsIgnoreCase("y");
            if (!continueProgram) {
                System.out.println("Program terminated!");
                break;
            }
            System.out.println();
        }
    }

    private static void displayMatrix(double[][] gaussJordanReduction) {
        double[][] copy = new double[gaussJordanReduction.length][gaussJordanReduction[0].length];
        for(int i = 0; i < gaussJordanReduction.length; i++){
            copy[i] = Arrays.copyOf(gaussJordanReduction[i], gaussJordanReduction[0].length);
        }
        System.out.println("\nMatrix Form:");
        for (int i = 0; i < copy.length; i++) {
            System.out.print("|");
            for (int j = 0; j < copy.length; j++) {
                System.out.printf("%7.2f", copy[i][j]);
            }
            System.out.print("  | ");
            if (i == 0) {
                System.out.print("x | =");
            } else if (i == 1) {
                System.out.print("y | =");
            } else if (i == 2) {
                System.out.print("z | =");
            }
            System.out.printf("%7.2f  |\n", copy[i][copy.length]);
        }
    }
    private static void displayAugmentedMatrix(double[][] gaussJordanReduction, int UnknownQuantity) {
        double[][] copy = new double[gaussJordanReduction.length][gaussJordanReduction[0].length];
        for(int i = 0; i < gaussJordanReduction.length; i++){
            copy[i] = Arrays.copyOf(gaussJordanReduction[i], gaussJordanReduction[0].length);
        }
        for (int i = 0; i < UnknownQuantity; i++){
            for (int j = 0; j < UnknownQuantity; j++){
                if (i == j){ //skip the diagonal elements
                    continue;
                }
                double factor = copy[j][i] / copy[i][i];
                for (int k = 0; k < UnknownQuantity + 1; k++){
                    copy[j][k] -= factor * copy[i][k];
                }
            }
        }
        System.out.println("\nAugmented Matrix Form:"); //print the augmented matrix
        for (int i = 0; i < copy.length; i++) {
            System.out.print("|");
            for (int j = 0; j < copy.length; j++) {
                double value = copy[i][j];
                if (Double.isNaN(value)) {
                    value = 0.0;
                }
                System.out.printf("%7.2f", value);
            }
            System.out.print("  | ");
            double constantTerm = copy[i][copy.length];
            if (Double.isNaN(constantTerm)) {
                constantTerm = 0.0;
            }
            System.out.printf("%7.2f  |\n", constantTerm);
        }
    }
    private static void chooseDisplayMethod(Scanner consoleInput, double[][] gaussJordanReduction, int UnknownQuantity) {
        System.out.println("[1] Display 'Full' RREF Steps\n[2] Display 'Final' RREF Step");
        System.out.print("Choose an option: ");
        int userOption = consoleInput.nextInt();
        switch (userOption) {
            case 1:
                // Display the full process of rref by calling the rowOperations method
                double[][] copy = new double[gaussJordanReduction.length][gaussJordanReduction[0].length];
                for(int i = 0; i < gaussJordanReduction.length; i++){
                    copy[i] = Arrays.copyOf(gaussJordanReduction[i], gaussJordanReduction[0].length);
                }
                double[][] rowProcess = rowOperations(copy);
                printMatrix2(rowProcess);  // Print the row process
                displayReducedRowEchelonForm(gaussJordanReduction, UnknownQuantity);
                break;
            case 2:
                // Display only the final process of rref by calling the displayReducedRowEchelonForm method
                displayReducedRowEchelonForm(gaussJordanReduction, UnknownQuantity);
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private static void displayReducedRowEchelonForm(double[][] gaussJordanReduction, int UnknownQuantity) {
        double[][] copy = new double[gaussJordanReduction.length][gaussJordanReduction[0].length];
        for(int i = 0; i < gaussJordanReduction.length; i++){
            copy[i] = Arrays.copyOf(gaussJordanReduction[i], gaussJordanReduction[0].length);
        }
        for (int i = 0; i < UnknownQuantity; i++){
            double pivot = copy[i][i];
            for (int j = i; j < UnknownQuantity + 1; j++){
                copy[i][j] /= pivot;
            }
            for (int j = 0; j < UnknownQuantity; j++){
                if (j == i){
                    continue;
                }
                double factor = copy[j][i] / copy[i][i];
                for (int k = i; k < UnknownQuantity + 1; k++){
                    copy[j][k] -= factor * copy[i][k];
                }
            }
        }
        System.out.println("\nReduced Row Echelon Form:");
        for (int i = 0; i < UnknownQuantity; i++) {
            System.out.print("|");
            for (int j = 0; j < UnknownQuantity; j++) {
                copy[i][j] = Math.round(copy[i][j] * 100.0) / 100.0;
                System.out.printf("%7.2f", copy[i][j]);
            }
            System.out.print("  | ");
            double constantTerm = copy[i][UnknownQuantity];
            if (Double.isNaN(constantTerm)) {
                constantTerm = 0.0;
            }
            System.out.printf("%7.2f  |\n", constantTerm);
        }
    }

    public static void showSolution(double[][] rref, String[] variables, int unknownQuantity) {
        double[][] copy = new double[rref.length][rref[0].length];
        for(int i = 0; i < rref.length; i++){
            copy[i] = Arrays.copyOf(rref[i], rref[0].length);
        }
        for (int i = 0; i < unknownQuantity; i++){
            double pivot = copy[i][i];
            for (int j = i; j < unknownQuantity + 1; j++){
                copy[i][j] /= pivot;
            }
            for (int j = 0; j < unknownQuantity; j++){
                if (j == i){
                    continue;
                }
                double factor = copy[j][i] / copy[i][i];
                for (int k = i; k < unknownQuantity + 1; k++){
                    copy[j][k] -= factor * copy[i][k];
                }
            }
        }
        System.out.println("\nReduced Row Echelon Form:");
            for (int i = 0; i < unknownQuantity; i++) {
                System.out.print("|");
                for (int j = 0; j < unknownQuantity; j++) {
                    copy[i][j] = Math.round(copy[i][j] * 100.0) / 100.0;
                    System.out.printf("%7.2f", copy[i][j]);
                }
                System.out.print("  | ");
                double constantTerm = copy[i][unknownQuantity];
                if (Double.isNaN(constantTerm)) {
                    constantTerm = 0.0;
                }
                System.out.printf("%7.2f  |\n", constantTerm);
            }
        System.out.println("\nSolution Vector: ");
        int rows = rref.length;
        int cols = rref[0].length;
        double[] solutions = extractSolutions(rref);
        boolean[] allZero = new boolean[rows];
        int flag = getFlag(rref, solutions, allZero);

        if (flag == 3) { // no solution
            System.out.println("The system has no solution.");
        } else if (flag == 2) { // infinite solutions
            System.out.println("The system has infinitely many solutions.");
            for (int i = 0, rCount = 0; i < rows; i++) {
                if (allZero[i]) {
                    System.out.println(variables[i] + " = " + (char)('r' + rCount));
                    rCount = rCount + 1;
                } else {
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
                }
            }
        } else if (flag == 1){
            for (int i = 0; i < unknownQuantity; i++) {
                String variable = "";
                if (i == 0) {
                    variable = "x";
                } else if (i == 1) {
                    variable = "y";
                } else if (i == 2) {
                    variable = "z";
                }
                System.out.println(variable + " = " + Math.ceil(solutions[i]));
            }
        }
    }
    public static double[][] rowOperations(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] rowprocess = new double[m][2 * n];

        for (int i = 0; i < m; i++) {
            System.arraycopy(matrix[i], 0, rowprocess[i], 0, n);
            rowprocess[i][n + i] = 1;
        }

        int pivot = 0;
        for (int i = 0; i < m; i++) {
            int r = i;
            while (rowprocess[r][pivot] == 0) {
                r++;
                if (r == m) {
                    r = i;
                    pivot++;
                    if (pivot == n) {
                        return rowprocess;
                    }
                }
            }
            if (r != i) {
                System.out.println("\nType I: ");
                double[] temp = rowprocess[r];
                rowprocess[r] = rowprocess[i];
                rowprocess[i] = temp;
                printMatrix2(rowprocess);
            }
            System.out.println("\nType II: ");
            double divisor = rowprocess[i][pivot];
            for (int j = 0; j < n * 2; j++) {
                rowprocess[i][j] /= divisor;
                rowprocess[i][j] = roundToTwoDecimalPlaces(rowprocess[i][j]);
                if (Double.isNaN(rowprocess[i][j])) {
                    rowprocess[i][j] = 0.0; // Set NaN to 0
                }
            }
            printMatrix2(rowprocess);
            for (int j = 0; j < m; j++) {
                if (j == i) {
                    continue;
                }
                System.out.println("\nType III: ");
                double multiple = rowprocess[j][pivot];
                for (int k = 0; k < n * 2; k++) {
                    rowprocess[j][k] -= multiple * rowprocess[i][k];
                    rowprocess[j][k] = roundToTwoDecimalPlaces(rowprocess[j][k]);
                    if (Double.isNaN(rowprocess[j][k])) {
                        rowprocess[j][k] = 0.0; // Set NaN to 0
                    }
                }
                printMatrix2(rowprocess);
            }
        }
        return rowprocess;
    }

    public static double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(value));
    }

    public static void printMatrix2(double[][] matrix) { // Helper method to print the matrix
        for (double[] row : matrix) {
            for (double element : row) {
                System.out.print(element + "\t");
            }
            System.out.println();
        }
        System.out.println("— ------------ — ------------ —");
    }

    public static int getFlag(double[][] rref, double[] solutions, boolean[] allZero) {
        int rows = rref.length;
        int cols = rref[0].length;

        int flag = 1; // unique solution
        for (int i = 0; i < rows; i++) { //populate and if there is 0, check corresponding row if all is zero
            solutions[i] = rref[i][cols - 1];
            allZero[i] = true;
            for (int j = 0; j < cols - 1; j++) {
                if (rref[i][j] != 0) {
                    allZero[i] = false;
                    break;
                }
            }
            if (allZero[i]) { //all zero coefficients
                if (solutions[i] == 0) {
                    flag = 2; // infinite solutions
                } else {
                    allZero[i] = false;
                    flag = 3; // no solution
                    break;
                }
            }
        }
        return flag;
    }

    public static void verifySolution(double[][] rref, double[][] matrix, String[] variables) {
        int rows = rref.length;
        int cols = rref[0].length;
        double[] solutions = extractSolutions(rref);
        boolean[] allZero = new boolean[rows];
        int flag = getFlag(rref, solutions, allZero);

        if (flag == 2) { // infinite solutions so solutions must be altered first
            System.out.println("The system has infinitely many solutions.");
            for (int i = 0, rCount = 0; i < rows; i++) {// Printing initial equations from rref
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
                    System.out.println(variables[i] + " = " + (char) ('r' + rCount));
                    rCount = rCount + 1;
                }
            }

            System.out.println("Set value:"); // Setting values for variables with all zero rows and calculating the rest from it
            Scanner sc = new Scanner(System.in);
            for (int i = rows - 1; i >= 0; i--) { // reverse loop for better efficiency
                if (!allZero[i]) { // no more all zero rows because of reduced row echelon form nature
                    for (int j = 0; j <= i; j++) { // calculate other variables
                        solutions[j] = rref[j][cols - 1];
                        for (int k = j + 1; k < cols - 1; k++) { // transposing each other term to other side
                            double term = rref[j][k] * solutions[k];
                            solutions[j] = solutions[j] - term;
                        }
                    }
                    break;
                }
                System.out.print(variables[i] + " = ");
                solutions[i] = sc.nextDouble(); // needs error trap
            }
            System.out.println();

            for (int i = 0; i < rows; i++) { // Printing equations substituted with new solution
                if (!allZero[i]) {
                    System.out.print(variables[i]);
                    for (int j = i + 1; j < cols - 1; j++) {
                        if (rref[i][j] != 0) {
                            System.out.print(" + ");
                            if (rref[i][j] != 1) {
                                System.out.print("(" + rref[i][j] + ")");
                            }
                            System.out.print("(" + solutions[j] + ")");
                        }
                    }
                    System.out.println(" = " + rref[i][cols - 1]);
                } else {
                    System.out.println(variables[i] + " = " + solutions[i]);
                }
            }
            System.out.println();

            for (int i = 0; i < rows; i++) { // Printing equations with evaluated terms
                if (!allZero[i]) {
                    System.out.print(variables[i]);
                    for (int j = i + 1; j < cols - 1; j++) {
                        if (rref[i][j] != 0) {
                            System.out.print(" + ");
                            System.out.print("(" + Math.ceil(rref[i][j] * solutions[j]) + ")");
                        }
                    }
                    System.out.println(" = " + rref[i][cols - 1]);
                } else {
                    System.out.println(variables[i] + " = " + Math.ceil(solutions[i]));
                }
            }
            System.out.println();

            for (int i = 0; i < rows; i++) { // Printing equations with transposed terms
                if (!allZero[i]) {
                    System.out.print(variables[i] + " = " + rref[i][cols - 1]);
                    for (int j = i + 1; j < cols - 1; j++) {
                        if (rref[i][j] != 0) {
                            System.out.print(" - ");
                            System.out.print("(" + Math.ceil(rref[i][j] * solutions[j]) + ")");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println(variables[i] + " = " + Math.ceil(solutions[i]));
                }
            }
            System.out.println();

            for (int i = 0; i < rows; i++) { // Printing solutions
                System.out.println(variables[i] + " = " + Math.ceil(solutions[i]));
            }
        }

        System.out.print("\nVerify Solution: {("); // Verification process
        for (int i = 0; i < rows; i++) {
            System.out.print(Math.ceil(solutions[i]));
            if (i != rows - 1) {
                System.out.print(", ");
            } else {
                System.out.print(")}");
            }
        } System.out.println();

        for (int i = 0; i < rows; i++) { // printing equations
            for (int j = 0; j < cols - 1; j++) {          //Math.ceil(variables[j])
                System.out.print("(" + matrix[i][j] + ")" + Math.ceil(solutions[j]));
                if (j != cols - 2) {
                    System.out.print(" + ");
                } else {
                    System.out.println(" = " + Math.ceil(matrix[i][cols - 1]));
                }
            }
        }
        System.out.println("Equation 1:");
        for (int j = 0; j < cols - 1; j++) { // substituting
            System.out.print("(" + matrix[0][j] + ")(" + Math.ceil(solutions[j]) + ")");
            if (j != cols - 2) {
                System.out.print(" + ");
            } else {
                System.out.println(" = " + Math.ceil(matrix[0][cols - 1]));
            }
        }
        for (int j = 0; j < cols - 1; j++) { // evaluating each term
            System.out.print("(" + matrix[0][j] * Math.ceil(solutions[j]) + ")");
            if (j != cols - 2) {
                System.out.print(" + ");
            } else {
                System.out.println(" = " + Math.ceil(matrix[0][cols - 1]));
            }
        }
        System.out.println(Math.ceil(matrix[0][cols - 1]) + " = " + Math.ceil(matrix[0][cols - 1]));
        if (flag == 3) { // no solution
            System.out.println("The system has no solution.");
        }
    }

    public static double[] extractSolutions(double[][] rref){
        int rows = rref.length;
        int cols = rref[0].length;
        double[] solutions = new double[rows];
        for(int i = 0; i < rows; i++){
            solutions[i] = rref[i][cols-1];
        }
        return solutions;
    }
}
