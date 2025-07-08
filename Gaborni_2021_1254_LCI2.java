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

import java.util.Scanner;

public class Gaborni_2021_1254_LCI2 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numVectors = getPositiveIntInput("Enter the number of vectors: ");
        int vectorSize = getPositiveIntInput("Enter the size of each vector: ");

        double[][] vectors = new double[numVectors][vectorSize];
        double[] xVector = new double[vectorSize];

        System.out.println("Enter the values for each vector:");
        for (int i = 0; i < numVectors; i++) {
            System.out.println("Vector " + (i + 1) + ":");
            for (int j = 0; j < vectorSize; j++) {
                vectors[i][j] = getDoubleInput("Enter value " + (j + 1) + ": ");
            }
        }

        System.out.println("Enter the values for the X vector:");
        for (int i = 0; i < vectorSize; i++) {
            xVector[i] = getDoubleInput("Enter value " + (i + 1) + ": ");
        }

        while (true) {
            System.out.println("\nLinear Combination and Dependence/Independence Program");
            System.out.println("1. Show the set of vectors");
            System.out.println("2. Show the equation: Linear Combination or Linear Dependence/Independence");
            System.out.println("3. Show the matrix of the equation");
            System.out.println("4. Show the augmented matrix of the equation");
            System.out.println("5. Show the solution");
            System.out.println("6. Exit");
            int choice = getPositiveIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("\nSet of Vectors:");
                    displayVectors(vectors);
                    break;
                case 2:
                    System.out.println("\nEquation: Linear Combination or Linear Dependence/Independence");
                    if (isLinearCombination(vectors, xVector)) {
                        System.out.println("The vector X is a linear combination of the given vectors.");
                    } else {
                        System.out.println("The vector X is not a linear combination of the given vectors.");
                    }
                    break;
                case 3:
                    System.out.println("\nMatrix of the Equation:");
                    displayMatrix(vectors, xVector);
                    break;
                case 4:
                    System.out.println("\nAugmented Matrix of the Equation:");
                    displayAugmentedMatrix(vectors, xVector);
                    break;
                case 5:
                    System.out.println("\nSolution:");
                    displaySolution(vectors, xVector);
                    break;
                case 6:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static int getPositiveIntInput(String message) {
        int value;
        while (true) {
            try {
                System.out.print(message);
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    break;
                }
                System.out.println("Please enter a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
        return value;
    }

    private static double getDoubleInput(String message) {
        double value;
        while (true) {
            try {
                System.out.print(message);
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return value;
    }

    private static void displayVectors(double[][] vectors) {
        for (int i = 0; i < vectors.length; i++) {
            System.out.print("Vector " + (i + 1) + ": [");
            for (int j = 0; j < vectors[i].length; j++) {
                System.out.print(vectors[i][j]);
                if (j < vectors[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    private static boolean isLinearCombination(double[][] vectors, double[] xVector) {
        int numVectors = vectors.length;
        int vectorSize = vectors[0].length;

        double[][] matrix = new double[numVectors][vectorSize + 1];
        for (int i = 0; i < numVectors; i++) {
            for (int j = 0; j < vectorSize; j++) {
                matrix[i][j] = vectors[i][j];
            }
            matrix[i][vectorSize] = xVector[i];
        }

        return GaussianElimination.isConsistent(matrix);
    }

    private static void displayMatrix(double[][] vectors, double[] xVector) {
        int numVectors = vectors.length;
        int vectorSize = vectors[0].length;

        double[][] matrix = new double[numVectors][vectorSize + 1];
        for (int i = 0; i < numVectors; i++) {
            for (int j = 0; j < vectorSize; j++) {
                matrix[i][j] = vectors[i][j];
            }
            matrix[i][vectorSize] = xVector[i];
        }

        displayMatrix(matrix);
    }

    private static void displayMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.2f ", value);
            }
            System.out.println();
        }
    }

    private static void displayAugmentedMatrix(double[][] vectors, double[] xVector) {
        int numVectors = vectors.length;
        int vectorSize = vectors[0].length;

        double[][] augmentedMatrix = new double[numVectors][vectorSize + 2];
        for (int i = 0; i < numVectors; i++) {
            for (int j = 0; j < vectorSize; j++) {
                augmentedMatrix[i][j] = vectors[i][j];
            }
            augmentedMatrix[i][vectorSize] = xVector[i];
            augmentedMatrix[i][vectorSize + 1] = 0;
        }

        displayMatrix(augmentedMatrix);
    }

    private static void displaySolution(double[][] vectors, double[] xVector) {
        int numVectors = vectors.length;
        int vectorSize = vectors[0].length;

        double[][] matrix = new double[numVectors][vectorSize + 1];
        for (int i = 0; i < numVectors; i++) {
            for (int j = 0; j < vectorSize; j++) {
                matrix[i][j] = vectors[i][j];
            }
            matrix[i][vectorSize] = xVector[i];
        }

        double[] solution = GaussianElimination.solve(matrix);
        if (solution != null) {
            System.out.print("Solution: [");
            for (int i = 0; i < solution.length; i++) {
                System.out.print(solution[i]);
                if (i < solution.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        } else {
            System.out.println("No solution exists.");
        }
    }
}

class GaussianElimination {
    private static final double EPSILON = 1e-10;

    public static boolean isConsistent(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length - 1;

        for (int i = 0; i < rows; i++) {
            boolean allZeroes = true;
            for (int j = 0; j < cols; j++) {
                if (Math.abs(matrix[i][j]) > EPSILON) {
                    allZeroes = false;
                    break;
                }
            }
            if (allZeroes && Math.abs(matrix[i][cols]) > EPSILON) {
                return false; // Inconsistent system
            }
        }
        return true; // Consistent system
    }

    public static double[] solve(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length - 1;

        for (int i = 0; i < rows; i++) {
            // Find pivot row
            int pivotRow = i;
            for (int j = i + 1; j < rows; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Swap rows
            swapRows(matrix, i, pivotRow);

            // Check if the system is inconsistent
            if (Math.abs(matrix[i][i]) < EPSILON) {
                return null;
            }

            // Perform row operations to eliminate lower triangular part
            for (int j = i + 1; j < rows; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                subtractRow(matrix, j, i, factor);
            }
        }

        // Back substitution
        double[] solution = new double[cols];
        for (int i = rows - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < cols; j++) {
                sum += matrix[i][j] * solution[j];
            }
            solution[i] = (matrix[i][cols] - sum) / matrix[i][i];
        }

        return solution;
    }

    private static void swapRows(double[][] matrix, int i, int j) {
        double[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    private static void subtractRow(double[][] matrix, int targetRow, int sourceRow, double factor) {
        int cols = matrix[0].length;

        for (int j = 0; j < cols; j++) {
            matrix[targetRow][j] -= matrix[sourceRow][j] * factor;
        }
    }
}
