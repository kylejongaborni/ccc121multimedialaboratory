/** Gaborni, Kyle Jon B.
 ITE114 IT2D Multimedia Laboratory
 Make an algorithm and implement it in Java on how to Add or Multiply two or more conformable matrices.
 The dimension of the matrices may be at most 10 by 10.
 Input: list/series of matrices 0
 Output: display the following matrices to be added or to be multiplied and the result;
 */

//import necessary java library utilities
import java.util.InputMismatchException;
import java.util.Scanner;

//class for modifying Scanner object to get input from user
class ModifiedScanner{
    Scanner scanner;
    public ModifiedScanner(){
        this.scanner = new Scanner(System.in);
    }

    // Get matrix input from user
    public Matrix inputMatrix(String label, int rows, int columns){
        Matrix A = new Matrix(label, rows, columns);
        int[][] e = A.elements;

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                e[r][c] = this.inputNumberRange(Integer.MIN_VALUE, Integer.MAX_VALUE,
                        ("[" + r + "][" + c + "]: ")
                );
            }
        }

        return A;
    }

    // this method is for inputting a number that is within min and max inclusively. custom prompt included.
    public int inputNumberRange(int min, int max, String prompt){
        int num;
        while(true){
            try {
                System.out.print(prompt); // prints custom prompt
                num = this.scanner.nextInt();
                if(num < min || num > max){
                    // this branch is for printing error message and then looping again when num fails the range check.
                    System.out.println("Must be between " + min + " and " + max);
                    continue;
                }
                // consumes newline character (\n) left over by nextInt method to prepare for next inputs
                this.scanner.nextLine();
            }catch(InputMismatchException e){
                System.out.println("Input must be an integer.");
                this.scanner.nextLine();
                continue;
            }
            break;
        }
        return num;
    }
}

//class for modifying Matrix object to display and evaluate operands input from user
class Matrix{
    String label;
    int rows;
    int columns;
    int[][] elements;

    public Matrix(String label, int rows, int columns){
        this.label = label;
        this.rows = rows;
        this.columns = columns;
        this.elements = new int[rows][columns];
    }

    // Check if matrix can be added to matrix B
    public boolean isAdditionConformable(Matrix B){
        return this.rows == B.rows && this.columns == B.columns;
    }

    // Check if matrix can be multiplied with matrix B
    public boolean isMultiplicationConformable(Matrix B){
        return this.columns == B.rows;
    }

    // Add matrix B to this matrix
    public Matrix add(Matrix B, String label) throws ArithmeticException{
        if(!this.isAdditionConformable(B)){
            throw new ArithmeticException("Matrices are not conformable for addition.");
        }

        Matrix C = new Matrix(label, this.rows, this.columns);
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                C.elements[r][c] = this.elements[r][c] + B.elements[r][c];
            }
        }
        return C;
    }

    // Multiply this matrix with matrix B
    public Matrix multiply(Matrix B, String label) throws ArithmeticException{
        if(!this.isMultiplicationConformable(B)){
            throw new ArithmeticException("Matrices are not conformable for multiplication.");
        }

        Matrix C = new Matrix(label, this.rows, B.columns);
        for(int Ar = 0; Ar < this.rows; Ar++){
            for(int Bc = 0; Bc < B.columns; Bc++){
                for(int Ac = 0; Ac < this.columns; Ac++){
                    C.elements[Ar][Bc] = C.elements[Ar][Bc] + this.elements[Ar][Ac] * B.elements[Ac][Bc];
                }
            }
        }
        return C;
    }

    // string representation of a matrix object: shows its label and its contents
    public String toString(){
        StringBuilder sb = new StringBuilder("   ");
        for(int i = 0; i < this.label.length() - 1; i++){
            sb.append(" ");
        }
        sb.append("\t");
        String offset = sb.toString();

        StringBuilder str = new StringBuilder();
        for(int r = 0; r < this.rows; r++){
            if(r != this.rows - 1){
                str.append(offset);
            }else{
                str.append(this.label).append(" =\t");
            }
            str.append("|\t");
            for(int c = 0; c < this.columns; c++){
                str.append(this.elements[r][c]).append("\t");
            }
            str.append("|\n");
        }
        return str.toString();
    }
}

//public class containing the main
public class Gaborni_2021_1254_MatrixOperations {
    public static void main(String[] args){
        ModifiedScanner modscan = new ModifiedScanner();
        Matrix M;
        Matrix Hold;
        Matrix M2 = null;
        String label;
        int rows;
        int columns;
        char c;
        do { // loops indefinitely until user decides to end the program by inputting 'n' when prompted at the end
            System.out.print("Enter first matrix name: ");
            label = modscan.scanner.nextLine();
            rows = modscan.inputNumberRange(1, 25, "Rows: ");
            columns = modscan.inputNumberRange(1, 25, "Columns: ");
            M = modscan.inputMatrix(label, rows, columns);

            switch (modscan.inputNumberRange(1, 2,
                    "Choose an operation: (1) Add Matrices / (2) Multiply Matrices: ")) {
                case 1:
                    do { // loops the addition operation if user inputs 'y' to add another matrix to M
                        Hold = M;
                        while (true) { // loops until matrix addition operation is done successfully
                            try {
                                // inputting of second matrix
                                System.out.print("Enter matrix name: ");
                                label = modscan.scanner.nextLine();
                                rows = modscan.inputNumberRange(1, 25, "Rows: ");
                                columns = modscan.inputNumberRange(1, 25, "Columns: ");
                                if (rows != M.rows || columns != M.columns) { // error trapping for addition conforming
                                    throw new ArithmeticException("Matrices are not conformable for addition.");
                                }
                                M2 = modscan.inputMatrix(label, rows, columns);
                                M = M.add(M2, (M.label + " + " + M2.label)); // addition of matrices
                                break; // break out of the while loop if addition is successful
                            } catch (ArithmeticException e) {
                                System.out.println(e.getMessage());
                                M = Hold; // revert M back to its original value
                                break; // break out of the while loop if addition is not conformable
                            }
                        }
                        if (M != Hold) { // only print the result if addition was successful
                            // printing of operands
                            System.out.println("Matrices included in addition operation: ");
                            System.out.println(Hold);
                            System.out.println(M2);
                            System.out.println("----------------------------------------------");

                            // printing of result
                            System.out.println("Result: ");
                            System.out.println(M);
                        }

                        do { // loops until user inputs 'y' or 'n'
                            System.out.print("Add another matrix? (y/n): ");
                            c = modscan.scanner.nextLine().charAt(0);
                        } while (!(c == 'y' || c == 'n'));
                    } while (c != 'n');
                    break;

                case 2:
                    // multiply
                    do { // loops the multiplication operation if user inputs 'y' to multiply another matrix to M
                        Hold = M;
                        while (true) { // loops until matrix multiplication operation is done successfully
                            try {
                                // inputting of second matrix
                                System.out.print("Enter matrix name: ");
                                label = modscan.scanner.nextLine();
                                rows = modscan.inputNumberRange(1, 25, "Rows: ");
                                columns = modscan.inputNumberRange(1, 25, "Columns: ");
                                if (M.columns != rows) { // error trapping for multiplication conforming
                                    throw new ArithmeticException("Matrices are not conformable for multiplication.");
                                }
                                M2 = modscan.inputMatrix(label, rows, columns);
                                M = M.multiply(M2, (M.label + " x " + M2.label)); // multiplying of matrices
                                break; // break out of the while loop if multiplication is successful
                            } catch (ArithmeticException e) {
                                System.out.println(e.getMessage());
                                M = Hold; // revert M back to its original value
                                break; // break out of the while loop if multiplication is not conformable
                            }
                        }
                        if (M != Hold) { // only print the result if multiplication was successful
                            // printing of operands
                            System.out.println("Matrices included in multiplication operation: ");
                            System.out.println(Hold);
                            System.out.println(M2);
                            System.out.println("----------------------------------------------");

                            // printing of result
                            System.out.println("Result: ");
                            System.out.println(M);
                        }

                        do { // loops until user inputs 'y' or 'n'
                            System.out.print("Multiply another matrix? (y/n): ");
                            c = modscan.scanner.nextLine().charAt(0);
                        } while (!(c == 'y' || c == 'n'));
                    } while (c != 'n');
                    break;
            }

            do { // loops until user inputs 'y' or 'n'
                System.out.print("Continue Program? (y/n): ");
                c = modscan.scanner.nextLine().charAt(0);
                if (c == 'n'){
                    System.out.print("Program Terminated!");
                    break;
                }
            } while (!(c == 'y' || c == 'n'));
        } while (c != 'n');
    }
}