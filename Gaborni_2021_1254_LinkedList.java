/* Gaborni, Kyle Jon B.
 * CCC121 IT2B Multimedia Laboratory
 * Make a LinkedList queue algorithm of its relevant functions or methods
 * Input: NodeList Containing: Name, Age, Course (size of 10 data)
 * Output: NodeList using implemented LL Operations
 */
import java.util.*; //importing Scanner from java util
class Queue{
    int NodeSize; Node NodeAtFront; Node NodeAtRear; //initialized size as integer literal, node pointers to class Node
    public Queue() {
        this.NodeSize = 0; this.NodeAtFront = null; this.NodeAtRear = null; //initialized nodesize to 0, the node pointers is initialized to null
    }

    public void ListSorter(boolean AgeSorter, boolean SortAscending){//utilized Bubble Sort to perform SortList() method
        if(this.isEmpty()){
            System.out.println("EMPTY QUEUE"); //if the queue is empty then it will print the line
        }else if(this.NodeAtFront != this.NodeAtRear){
            Node[] nodes = new Node[this.NodeSize];  Node node = this.NodeAtRear; //initialize nodes as an object for the purpose of method operation; with the node variable assigned to rearpointer
            for(int i = 0; i < this.NodeSize; i++){ //using for loop to check every array indices
                nodes[i] = node; //the array node i is assigned to the node which is at rear when in sorting process
                node = node.NodeAtNext; //then the node will be assigned to the next node
            }
            if(AgeSorter){//the age sorter will be executed as opted by the user
                int[] StudentAge = new int[this.NodeSize]; //array integer student size will be assigned to the size of the node
                for(int i = 0; i < this.NodeSize; i++){ //using for loop to check every array indices
                    StudentAge[i] = nodes[i].StudentAge; //the array student age will be assigned to the node student age
                }
                for(int RearPtr = this.NodeSize; RearPtr > 0; RearPtr--){
                    int Index1 = 0; int Index2 = 1; //index1 will be stored as 0 and index2 will be stored as 1 to compare two arrays
                    while(Index2 < RearPtr){
                        if(SortAscending){
                            if(StudentAge[Index1] > StudentAge[Index2]){
                                SwapSorter(StudentAge, nodes, Index1, Index2);
                            }
                        }else{
                            if(StudentAge[Index1] < StudentAge[Index2]){
                                SwapSorter(StudentAge, nodes, Index1, Index2);
                            }
                        }
                        Index1++; Index2++; //index 1 and index 2 are then incremented after the nodes are sorted in order
                    }
                }
            } else{ //the name sorter will be executed as opted by the user instead
                String[] names = new String[this.NodeSize];
                for(int i = 0; i < this.NodeSize; i++){
                    names[i] = nodes[i].StudentName;
                }

                for(int end = this.NodeSize; end > 0; end--){
                    int Index1 = 0; int Index2 = 1;
                    while(Index2 < end){
                        if(SortAscending){
                            if(NextGreatNode(names[Index1].toLowerCase(), names[Index2].toLowerCase())){
                                swap(names, nodes, Index1, Index2);
                            }
                        }else{
                            if(!NextGreatNode(names[Index1].toLowerCase(), names[Index2].toLowerCase())){
                                swap(names, nodes, Index1, Index2);
                            }
                        }
                        Index1++; Index2++; //index 1 and index 2 are then incremented after the nodes are sorted in order
                    }
                }
            }
            for(int i = 0; i < this.NodeSize - 1; i++){
                nodes[i].NodeAtNext = nodes[i+1]; //using for loop to assign the next node from the current node during the sorting process
            }
            nodes[this.NodeSize - 1].NodeAtNext = null; this.NodeAtRear = nodes[0]; this.NodeAtFront = nodes[this.NodeSize - 1];
        }
    }

    private void SwapSorter(int[] AgeOfStudent, Node[] nodes, int Index1, int Index2){ //swap method to handle any case of age sorting order from age inputs
        int holder = AgeOfStudent[Index1]; //initialize variable holder as primary storage of the data in listages i
        AgeOfStudent[Index1] = AgeOfStudent[Index2]; //the listages i will be assigned to listages i2 accordingly
        AgeOfStudent[Index2] = holder; //the listages i2 will be assigned back to agesholder again accordingly

        Node node = nodes[Index1]; //the variable node will be assigned to listnode i
        nodes[Index1] = nodes[Index2]; //the listnode i will be assigned to listnode i2
        nodes[Index2] = node; //the listnode i2 will be assigned back to variable node again accordingly
    }

    private boolean NextGreatNode(String FirstName, String SecondName){//sort method to handle any case of name sorting order from surname inputs
        int length; //initialize variable length as integer literal
        if(FirstName.length() < SecondName.length()){
            length = FirstName.length();
        } else{
            length = SecondName.length();
        }
        for(int i = 0; i < length; i++){
            if(FirstName.charAt(i) > SecondName.charAt(i)){
                return true;
            }
            else if(FirstName.charAt(i) < SecondName.charAt(i)){
                return false;
            }
        }
        return (length == FirstName.length()); //returns the data names in alphabetical order accordingly
    }

    private void swap(String[] names, Node[] nodes, int i, int i2){ //swap method to handle any case of sorting order from data inputs
        String holder = names[i]; //initialize variable holder as primary storage of the data in listname i
        names[i] = names[i2]; //the listname i will be assigned to listname i2 accordingly
        names[i2] = holder; //the listname i2 will be assigned back to nameholder again accordingly

        Node node = nodes[i]; //the variable node will be assigned to listnode i
        nodes[i] = nodes[i2]; //the listnode i will be assigned to listnode i2
        nodes[i2] = node; //the listnode i2 will be assigned back to variable node again accordingly
    }

    public void Traversion(){ //traverse method to handle any case of traversing oepration
        if(this.isEmpty()){
            System.out.println("Empty Queue: Null Nodes. Input Data!"); //if no data is being traverse, then pop-up this message
        }
        else if (!this.isEmpty()){
            Node currentNode = this.NodeAtRear; //initialized current node to the rear pointer for the traversing method
            for(int i = 0; i < this.NodeSize; i++) {
                System.out.println("Student's Name: " + currentNode.StudentName); //display the names of the student/s
                System.out.println("Student's Age: " + currentNode.StudentAge); //display the ages of the student/s
                System.out.println("Student's Course: "+ currentNode.StudentCourse); //display the courses of the student/s
                currentNode = currentNode.NodeAtNext; //will displayed the updated note when the program executes traversion method
            }
            System.out.println("Total Number of Nodes Present: " + this.NodeSize); //display the total number of nodes in the queue
        }
    }

    public boolean isEmpty(){
        return this.NodeSize == 0; //checking the node is empty or not empty
    }

    public boolean isFull(){
        return this.NodeSize == 10; //checking the node is full or not full
    }

    public void Insertion(Node node) throws Exception{
        if(this.isEmpty()){
            this.NodeAtFront = node;
            this.NodeAtRear = node;
            this.NodeSize = 1;
        }else if(this.isFull()){
            System.out.println();
            throw new Exception();
        }else{
            node.NodeAtNext = this.NodeAtRear;
            this.NodeAtRear = node;
            this.NodeSize = this.NodeSize + 1;
        }
    }

    public void delete() throws Exception{
        if(this.isEmpty()){
            System.out.println();
            throw new Exception();
        }else{
            Node node = this.NodeAtRear;
            while(node.NodeAtNext.NodeAtNext != null){
                node = node.NodeAtNext;
            }
            node.NodeAtNext = null;
            this.NodeAtFront = node;
            this.NodeSize = this.NodeSize - 1;
        }
    }
}
class Node{
    String StudentName; String StudentCourse; Node NodeAtNext; int StudentAge; //initialized course and name as string literal, age as integer literal and the next node as the Node class
    public Node(String StudentNamer, int StudentAger, String StudentCourser){
        this.StudentName = StudentNamer; //assigned StudentName to the StudentNamer method
        this.StudentAge = StudentAger; //asigned StudentAge to the StudentAger method
        this.StudentCourse = StudentCourser; //assigned StudentCourse to the StudentCourser method
        this.NodeAtNext = null; //initialized the succeeding nodes as null as linkedlist structure is a contiguous memory storage
    }
}

public class Gaborni_2021_1254_LinkedList{
    public static void main(String[] args) throws Exception{
        Scanner monopanel = new Scanner(System.in); Queue LineSequence = new Queue(); //initialized monopanel variable to be used as Scanner input method; LineSequence used as queue object method
        boolean Termination = true; //variable Termination initialized as boolean condition to control program's operation flow
        do{
            System.out.println("Choose an Operation");//having the user pick on what operation they want to perform,
            System.out.println("[1] Traverse\n[2] Insert\n[3] Delete\n[4] Empty\n[5] Full\n[6] Other\n");//they will pick whether they want to perform traverse,insert,delete,empty,full, or other operation.
            monopanel = new Scanner(System.in); String operation = monopanel.nextLine();
            switch (operation.toLowerCase()) {
                case "1":  //traverse operation
                    System.out.println("Traversion Operation Initialized...");
                    LineSequence.Traversion(); //traverse the node data of user inputs, if no data is null, then prompts message "Null Node"
                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation

                case "2": //insertion operation
                    System.out.println("Insertion Operation Initialized...");
                    System.out.print("Input Student's Surname: "); String name = monopanel.nextLine();//asks user input for student name
                    System.out.print("Input Student's Age: "); //asks user to input the student age
                    int Age; //initialize variable Age as integer literal
                    while (true){
                        Age = monopanel.nextInt(); //ask user input for student age (number)
                        try {
                            if (Age >= 100) { //if user inputs 100 and above numbers for the age, then program catches the error
                                throw new Exception(); //program throws the Exception handling in case of possible input error
                            }
                        } catch (Exception e){
                            System.out.println("Must be realistically within average lifespan\nInput Student's Age: ");
                            continue; //to continue executing the program, the continue is placed in case user is prompted to input again when inputting an invalid input
                        }
                        break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                    }
                    monopanel.nextLine();//prints the age input using this method to avoid line break and code mess
                    System.out.print("Input Student's Course: "); String course = monopanel.nextLine(); //asks user input for student course
                    Node insert = new Node(name, Age, course); LineSequence.Insertion(insert); //data will simultaneously displayed once user input is done; the insertion operation will be enqueue accordingly
                    System.out.println("Data Inserted: \n" + "Name: " + name + "\nAge: " + Age + "\nCourse: " + course);//prints all the data
                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation

                case "3": //delete operation
                    System.out.println("Delete Operation Initialized..."); //pops message prompting the delete operation is initialized after user opted node dequeue
                    LineSequence.delete(); LineSequence.Traversion(); //using the delete operation will delete the recently added data, eventually be traverse accordingly
                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation

                case "4": //isempty operation
                    System.out.println("Empty NodeChecker Initialized. Checking...");
                    if(LineSequence.isEmpty()){ //checking if the node data is empty, otherwise prompts "Node not empty"
                        System.out.println("NODE EMPTY"); //if the queue checks empty node, then this message will pop-up
                    }
                    else{
                        System.out.println("NODE NOT EMPTY");//otherwise if the queue checks non-empty node, then this message will pop-up
                    }
                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation

                case "5": //isfull operation
                    System.out.println("Full NodeChecker Initialized. Checking...");
                    if (LineSequence.isFull()){ //checks whether the queue is full or not
                        System.out.println("NODE FULL");//if queue is Full
                    }
                    else{
                        System.out.println("NODE NOT FULL");//if queue is NOT Full
                    }
                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation

                case "6": //sortlist operation
                    System.out.println("SortList Operation Initialized...\nChoose A Sorting Method:\n[1] Sort Age\n[2] Sort Name\n"); //using bubble sort to choose whether user wants to sort by name or age at own will
                    String Sort; //this string connects on whether the user wants to sort by age or name
                    boolean AgeSorter; boolean SortAscending;
                    while(true){
                        try{
                            Sort = monopanel.nextLine();
                            switch (Sort.toLowerCase()){
                                case "1":
                                    AgeSorter = true;//If boolean condition is true, then program only executes sortlist() method by age
                                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                                case "2":
                                    AgeSorter = false; //If boolean condition is false, the program will then only executes sortlist() method by name
                                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                                default:
                                    System.out.println("Scanner Error: Invalid Input. Try Again!"); //if user mindlessly input without the given choices, the program catches the error and prompts the error message
                                    throw new Exception(); //program throws the Exception handling in case of possible input error
                            }
                        }catch(Exception e){
                            continue; //to continue executing the program, the continue is placed in case user is prompted to input again when inputting an invalid input
                        }
                        break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                    }
                    System.out.println("[1] Initialize SortAscend\n[2] Initialize SortDescend\n[3] Initialize NoChoice"); //user has the choice to either sort data ascending or descending order accordingly
                    while(true){
                        try{
                            Sort = monopanel.nextLine(); //asks user to choose between the sorting order
                            switch(Sort.toLowerCase()){
                                case "1": //will be sorted by ascending accordingly
                                    SortAscending = true; //if program checks the SortAscending to be true, then the ascending order will be executed accordingly
                                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                                case "2": //will be sorted descendingly
                                    SortAscending = false; //if program checks the SortAscending to be false, then the descending order will be executed accordingly instead
                                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                                default: //default is used when user decides to input outside the choice cases
                                    System.out.println("Scanner Error: Invalid Input. Try Again!"); //pops error message when user enters invalid choice input
                                    throw new Exception(); //program throws the Exception handling in case of possible input error
                            }
                        }catch(Exception e){
                            continue; //to continue executing the program, the continue is placed in case user is prompted to input again when inputting an invalid input
                        }
                        break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
                    }
                    LineSequence.ListSorter(AgeSorter, SortAscending); // the function is called at this place
                    LineSequence.Traversion();//sorted result will then be traversed
                    break; //to avoid executing succeeding operations, break is placed after every block of code in each opted operation
            }
            System.out.print("\nContinue Program?\n[Y] Yes\n[N] No\n");//will serve as a loop within the main function
            char TerminalInput; boolean Looping = true; //initialized TerminalInput variable as character literal for program continuation/termination; initialized Looping variable as boolean condition for program continuation/termination
            do{
                TerminalInput = monopanel.next().charAt(0); //ask user to input 'Y' or 'N' character to handle program operation
                if(TerminalInput == 'n' || TerminalInput =='N'){
                    Looping = false; Termination = false; //data entry of the program loop will terminate if user inputs 'N'
                    System.out.println("< < < < P R O G R A M  T E R M I N A T E D > > > >");
                } else if (TerminalInput == 'y' || TerminalInput == 'Y') {
                    Looping = false; //data entry of the program loop will continue if user inputs 'Y'
                } else System.out.println("Scanner Error: Invalid Input. Try Again!"); //pops error message when user enters invalid choice input
            }
            while(Looping); //while looping is true, then the whole program will continue unless user opted to terminate program
        }
        while(Termination); //while termination is true, then the whole program will continue unless user opted to terminate program
    }
}