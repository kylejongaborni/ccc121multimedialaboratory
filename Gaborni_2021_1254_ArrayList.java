/**
 Kyle Jon B. Gaborni
 CCC121 IT2B Friday 11AM - 1 PM Multimedia Laboratory
 Program Description
 Create a program that implements Basic Array Operations.
 The program should also be able to find the maximum number, find elements and compare elements in the array.
 Input: x1, x2, x3, ..., x20;
 Output: MaximumNumber, FindElements > InputValue, CompareElements: A[i] = A[i-1], boolean True/False
 */
import java.util.*; //importing Scanner
public class Gaborni_2021_1254_ArrayList{
    public static void main(String[] args) {
        Scanner panel = new Scanner(System.in);//using Scanner for user input
        int[] Arr = new int[20];//initializing array into 20 elements
        int latest ; int antecedent = 0;//for the Delete Operation activities
        Set<Integer> distinct = new HashSet<>();//java interface which extends collection in which duplicate values cannot be stored in array.Variable named to <distinct>.

        System.out.print("Enter array elements:\n");//user is ask to enter array elements
        for (int i = 0; i < Arr.length; i++) { //ask user input for elements until the condition is false
            System.out.print("Index " + (i) + ": ");
            while (true){
                try {
                    Arr[i] = panel.nextInt();//ask user input for array elements
                    if (Arr[i] < 0){//if user inputs negative values, program throw Exception and returns Catch() message
                        throw new IllegalArgumentException();
                    }
                    if (!(distinct.add(Arr[i]))){//code statement that only returns distinct values from user input; otherwise program will throw Exception and returns Catch() message
                        throw new IllegalArgumentException();
                    }
                }
                catch(Exception e){ //if encountering an error, then this message will return
                    System.out.println("Should be a distinct positive number above 0.");
                    continue;
                }
                break;
            }
            if (Arr[i] == 0){ //if user inputs 0, then inputting array elements is finish
                System.out.println("Program Ended");
                break;
            }
        }

        boolean terminate = false; //terminate the whole program if boolean results to false
        while (!terminate) {
            System.out.println("Select an Action:");
            System.out.println("[1] Traverse\n[2] Insert\n[3] Delete\n[4] Search\n[5] Update\n[6] Empty\n[7] Full\n[8] Find Max\n[9] Find Elements\n[10] Compare Elements");
            int operation = panel.nextInt();
            switch(operation) {
                case 1://Traverse
                    System.out.println("[Traverse Array]");
                    for(int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i] +" | ");//prints the array elements
                    }
                    System.out.println("\n");
                    break;//break-statement to stop the program from iterating the operation below this case

                case 2://Insert
                    System.out.print("[Insert] ArrayView:\n");
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    System.out.print("\nEnter which index to be inserted: ");
                    int InsrtIndx = 0;//initialize Inserting Index variable to 0
                    while(true){
                        InsrtIndx = panel.nextInt();//ask user input for the index to be inserted
                        try {
                            if (InsrtIndx > 19){ //will not accept arraylength indices beyond 20, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                            else if (InsrtIndx < 0){ //will not accept arraylength indices less than 0, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                        }
                        catch (Exception e){ //if encountering an error, then this message will return
                            System.out.println("Should be within the array length. Try Again.");
                            continue;
                        }
                        System.out.print("Enter a number to be inserted: ");
                        int InsrtNum = 0;//initialize to 0, otherwise line 103 code will called out variable "InsrtNum" as uninitialized
                        for (int y = 0; y < Arr.length; y++){//using for loop to search within array elements
                            while (true){
                                InsrtNum = panel.nextInt();//ask user input for a number to be inserted
                                try{
                                    if (InsrtNum < 0){//if user inputs negative values, program throw Exception and returns Catch() message
                                        throw new InputMismatchException();
                                    }
                                    if (!(distinct.add(InsrtNum))){//code statement that only returns distinct values from user input; otherwise program will throw Exception and returns Catch() message
                                        throw new IllegalArgumentException();
                                    }
                                }
                                catch (Exception e){//if encountering an error, then this message will return
                                    System.out.println("Should be a distinct positive number above 0. Try Again.");
                                    continue;
                                }
                                break;
                            }
                            break;
                        }
                        for(int i = Arr.length-1; i > InsrtIndx; i--){//using for loop
                            Arr[i] = Arr[i-1];
                        }
                        Arr[InsrtIndx] = InsrtNum;//the inputted index location will be assigned to the number contained in it.
                        System.out.println(Arrays.toString(Arr)+"\n"); //prints the array elements
                        break;
                    }
                    break;//break-statement to stop the program from iterating the operation below this case

                case 3://Delete
                    System.out.print("[Delete] ArrayView:\n");//traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    System.out.print("\nSelect which index to be deleted: ");
                    int DltIndx;//initialize the Index variable
                    while (true){
                        DltIndx = panel.nextInt();//ask user input to choose an index to be deleted
                        try {
                            if (DltIndx > 19){//will not accept arraylength indices beyond 20, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                            else if (DltIndx < 0){//will not accept arraylength indices less than 0, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                            else if (Arr[DltIndx] == 0) {//prints the array elements
                                throw new IllegalArgumentException();
                            }
                        }
                        catch (InputMismatchException e){
                            //if encountering an error, then this message will return
                            System.out.println("Should be within the array length. Try Again");
                            continue;
                        }
                        catch (IllegalArgumentException e){
                            //if encountering an error, then this message will return
                            System.out.println("Should be within the array length. Try Again");
                            continue;
                        }
                        for(int i = Arr.length - 1; i >= DltIndx; i--){//
                            latest = Arr[i]; //assigned the latest Array into the array elements
                            Arr[i] = antecedent; //assigned the array elements from the previous array
                            antecedent = latest; //assigned the previous array into the latest array
                            continue;
                        }
                        for(int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                            System.out.print(Arr[i] + " | ");//prints the array elements
                        }
                        break;
                    }
                    System.out.println("\n");
                    break;//break-statement to stop the program from iterating the operation below this case

                case 4://Search
                    System.out.print("[Search] ArrayView:\n");//traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    System.out.print("\nEnter a number to search: ");
                    int j; //initialize another variable for the array elements
                    int SrchArr = panel.nextInt(); //ask user input for a number to be search in array elements
                    boolean temp = false;// boolean returns false if input number is not found in array
                    for (j = 0; j < Arr.length; j++){//using for loop to search within array elements
                        if (SrchArr == Arr[j]){//if the value is found within array, then result is TRUE
                            temp = true;
                            break;
                        }
                    }
                    if (temp){//if the input is found in array, then result to TRUE
                        System.out.print("TRUE\n");
                    }
                    else{//if the input is not found in array, then result to FALSE
                        System.out.print("FALSE\n");
                    }
                    System.out.print("\n");
                    break;//break-statement to stop the program from iterating the operation below this case

                case 5://Update
                    System.out.print("[Update] ArrayView:\n"); //traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    System.out.print("\nEnter which index to update: ");
                    int UpdateIndx; //initialize a variable for an Index Number
                    while (true) {
                        UpdateIndx = panel.nextInt();//ask user input for index to be updated
                        try {
                            if (UpdateIndx > 19){//will not accept arraylength indices beyond 20, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                            else if (UpdateIndx < 0){//will not accept arraylength indices less than 0, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                        }
                        catch (Exception e){//if encountering an error, then this message will return
                            System.out.println("Should be a distinct positive number between 0 and 20. Try Again.");
                            continue;
                        }
                        System.out.print("Enter a number to update: ");
                        int UpdateNum = 0; //initialize a variable for Update number
                        for (int y = 0; y < Arr.length; y++){
                            while (true){
                                UpdateNum = panel.nextInt(); //ask user for an update number
                                try{
                                    if (UpdateNum < 0){//will not accept arraylength indices less than 0, program then throw Exception and returns Catch() message
                                        throw new InputMismatchException();
                                    }
                                    if (!(distinct.add(UpdateNum))){//code statement that only returns distinct values from user input; otherwise program will throw Exception and returns Catch() message
                                        throw new IllegalArgumentException();
                                    }
                                }
                                catch (Exception e){//if encountering an error, then this message will return
                                    System.out.println("Should be a distinct positive number above 0. Try Again.");
                                    continue;
                                }
                                break;
                            }
                            break;
                        }
                        Arr[UpdateIndx] = UpdateNum;//the inputted index location will be assigned to the number contained in it.
                        for(int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                            System.out.print(Arr[i] + " | " ); //prints the new array elements
                        }
                        System.out.println("\n");
                        break;
                    }
                    break;//break-statement to stop the program from iterating the operation below this case

                case 6://Empty
                    System.out.println("[Checking if Array is Empty...] ArrayView:"); //traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    boolean empty = true; //boolean implies true when array is empty
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        if (Arr[i] != 0) {//if the array indices doesn't contain zeroes, then array is full and thus return FALSE
                            empty = false;
                            break;
                        }
                    }
                    if (empty){//if the boolean condition is true, then the array is empty and thus return TRUE
                        System.out.println("\nTRUE\n");
                    }
                    else{//if the array indices contain no zero, then array is full and thus return FALSE
                        System.out.println("\nFALSE\n");
                    }
                    break;//break-statement to stop the program from iterating the operation below this case

                case 7://Full
                    System.out.println("[Checking if Array is Full...] ArrayView:"); //traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    boolean full = true; //boolean implies true when array is full
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        if (Arr[i] == 0) {//if the array indices contain 0, then array is not full and thus return FALSE
                            full = false;
                            break;
                        }
                    }
                    if (full){//if the boolean condition is true, then the array is full and thus return TRUE
                        System.out.println("\nTRUE\n");
                    }
                    else{//if the boolean condition is false, then the array is empty and thus return FALSE
                        System.out.println("\nFALSE\n");
                    }
                    break;//break-statement to stop the program from iterating the operation below this case

                case 8://FindMax
                    System.out.println("[Finding Maximum Number...] ArrayView:"); //traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    int max = Arrays.stream(Arr).max().getAsInt(); //stream method of Arrays class that returns the maximum element within the array
                    if (max != 0){ //if the array has elements greater than 0, then return this message and the maximum element
                        System.out.println("\n"+ max + " is the maximum element in the array\n");
                    }
                    else{ //if the array is empty, then return this message
                        System.out.println("\nThere is no maximum element in the array\n");
                        break;
                    }
                    break;//break-statement to stop the program from iterating the operation below this case

                case 9://FindElements
                    System.out.println("[Finding Maximum Elements] ArrayView:"); //traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    System.out.print("\nEnter a Value: "); //ask user to input a value to find the greater values than the input value
                    int FindElem;//initializing FindElem variable
                    while(true){
                        FindElem = panel.nextInt();//ask user to input a value
                        try{
                            if (FindElem < 0){ ////will not accept arraylength indices less than 0, program then throw Exception and returns Catch() message
                                throw new IllegalArgumentException();
                            }
                        }
                        catch (Exception e){//if encountering an error, then this message will return
                            System.out.println("Should be within the array. Try Again.");
                            continue;
                        }
                        for (int i = 0; i < Arr.length; i++){//using for loop to search within arraylength
                            if (FindElem < Arr[i]){//if the given input has greater elements, then return the greater elements
                                System.out.print(Arr[i]+ " | ");
                            }
                        }
                        System.out.print("\n");
                        break;
                    }
                    break;//break-statement to stop the program from iterating the operation below this case

                case 10://ComparingElements
                    System.out.println("[Comparing Elements] ArrayView:"); //traversing the array first for preview purposes
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        System.out.print(Arr[i]+ " | ");//prints the array elements
                    }
                    System.out.print("\nEnter a Number Index: ");//ask user to input an index location of an element for comparison
                    int CompareElemIndx; //intializing Compare Element Index variable
                    while(true){
                        CompareElemIndx = panel.nextInt();//ask user to input an index for comparison
                        try{
                            if (CompareElemIndx > 19){ //will not accept arraylength indices beyond 20, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                            else if (CompareElemIndx < 1){ //will not accept arraylength indices less than 1, program then throw Exception and returns Catch() message
                                throw new InputMismatchException();
                            }
                        }
                        catch (Exception e){//if encountering an error, then this message will return
                            System.out.println("Should be compared within the array length. Try Again.");
                            continue;
                        }
                        break;
                    }
                    for (int i = 0; i < Arr.length; i++){//using for loop to search within array elements
                        if (Arr[CompareElemIndx] > Arr[CompareElemIndx-1]){ //if the compared index is less than the compared input, the result is TRUE
                            System.out.println(Arr[CompareElemIndx] +" > "+ Arr[CompareElemIndx-1] + ": TRUE\n");
                            break;
                        }
                        else if (Arr[CompareElemIndx] < Arr[CompareElemIndx-1]){ //if the compared index is greater than the compared input, the result is FALSE
                            System.out.println(Arr[CompareElemIndx] + " > " + Arr[CompareElemIndx - 1] + ": FALSE\n");
                            break;
                        }
                        else if (Arr[CompareElemIndx] == 0){ //if the compared index and compared input is both 0; the result is TRUE
                            System.out.println(Arr[CompareElemIndx] + " = " + Arr[CompareElemIndx - 1] + ": TRUE\n");
                            break;
                        }
                        break;//break-statement to stop the case operation to loop
                    }
            }
            System.out.println("Continue Operation (Y or N)?");//ask user input to either continue or terminate program
            while (true){
                char response = new Scanner(System.in).nextLine().charAt(0); //scanner doesn't support nextChar()
                //next() function returns the next token in the input as a string and charAt(0) function returns the first character in that string.
                if (response == 'Y'){
                    terminate = false; //if user enters character Y, the program will continue
                } else if (response == 'N'){
                    terminate = true; //if user enters character N, the program will terminate
                    System.out.println("\n< < < P R O G R A M   T E R M I N A T E D > > >");
                }
                break; //break statement is placed here to stop the loop iteration in proceeding the program
            }
        }
    }
}


