/**
 Kyle Jon B. Gaborni CCC121 IT2B Friday 11AM - 1 PM Multimedia Laboratory
 Program Description:
 Design an algorithm to find the three maximum numbers from a sequence of n distinct positive integers of size n >= 3
 Input: x1, x2, x3, ..., xn of all distinct positive whole integers
 Output: Max1, Max2, Max3
 **/
import java.util.*; //importing scanner for user input
public class Gaborni_2021_1254_ThreeMax {
    public static void main(String[] args){
        Scanner inspect = new Scanner(System.in);//declare Scanner variable to "inspect"
        boolean terminate = false; //declare boolean condition to control program looping
        while (!terminate) {//program will operate as long as it meets the while condition
            System.out.print("Enter Array Size:\n");//displaying array size
            int ArrySize; //declare arraysize determined by user input
            while (true){ //continue program while the boolean condition is true
                try { //implement try-catch to handle and prompt oncoming errors
                    ArrySize = inspect.nextInt();//asking user for input on arraysize
                    if (ArrySize < 4){
                        throw new InputMismatchException();//throws line error when user input a number less than 4
                    } else if (ArrySize % 1 != 0){
                        throw new InputMismatchException();//throws line error when user input decimal number
                    }
                } catch (InputMismatchException e){
                    System.out.println("Must only input 4 or above positive whole number!\nEnter Array Size: "); //warning message informing user to only input valid data
                    inspect.nextLine(); //prevent the program from looping the previous line after inputting fractions and decimals
                    continue;
                }
                break;
            }

            int[] Arr = new int[ArrySize];//asking user for array elements
            System.out.println("\nArray Elements:");//print to proceed user input for array elements
            for (int a = 0; a < Arr.length; a++){
                System.out.print("[" + (a + 1) + "]/[" + Arr.length + "]: ");//prints X numbering to pinpoint array indices
                while (true){
                    try {//implement try-catch to handle and prompt oncoming errors
                        Arr[a] = inspect.nextInt();//asking user for input on array elements
                        if (Arr[a] < 0) {
                            System.out.println("Must be a positive integer!\n[" + (a + 1) + "]/[" + Arr.length + "]: ");
                            throw new Exception();//throws line error when user input negative integer
                        }
                        for (int b = 0; b < a; b++){ //create another array to store only duplicate integers
                            if (Arr[b] == Arr[a]){
                                System.out.println("Must be a distinct integer!\n[" + (a + 1) + "]/[" + Arr.length + "]: ");
                                throw new Exception();//throws line error when user input duplicate integer
                            }
                        }
                    } catch (InputMismatchException e){
                        System.out.println("Must be an integer!\n[" + (a + 1) + "]/[" + Arr.length + "]: "); //catches other input errors such as fractions and decimals
                        inspect.nextLine();
                        continue;
                    } catch (Exception e){ //catches the previous error when inputting negative and non-distinct integers
                        inspect.nextLine();
                        continue;
                    }
                    break;
                }
            }

            int max1 = 0; int max2 = 0; int max3 = 0; //initializing the three maximum numbers into zero
            for (int a = 0; a < ArrySize; a++){//comparing all elements in the array
                if (max1 < Arr[a]){//finding the first maximum number in the array
                    max3 = max2;
                    max2 = max1;
                    max1 = Arr[a];
                } else if (max2 < Arr[a]){//finding the second maximum number in the array
                    max3 = max2;
                    max2 = Arr[a];
                } else if (max3 < Arr[a]){//finding the third maximum number in the array
                    max3 = Arr[a];
                }
            }
            System.out.println("\nMax 1st: " + max1 + "\nMax 2nd: " + max2 + "\nMax 3rd: " + max3);//print out three maximum numbers
            System.out.println("\nDo you want to continue the program?\n[Y] CONTINUE, [N] TERMINATE"); //asking user to continue or terminate program
            while (true){//continue program while the boolean condition is true
                char response = new Scanner(System.in).nextLine().charAt(0);//ask user input character
                if (response == 'Y'){//program continues if the condition is false
                    terminate = false; System.out.print("\n");
                } else if (response == 'N'){//program terminates if the condition is true
                    terminate = true;
                    System.out.println("\nProcess finished with exit code 0");
                }
                break;
            }
        }
    }
}