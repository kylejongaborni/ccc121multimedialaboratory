/* 1+2*5^2/5 = 11    5+4^2(2+3)/4 = 25     5*4-2+8/6 = 19.33    2(5-2)/2^2+6 = 7.5    4^2/2(3^3-7)+5 = 5.4
 * Gaborni, Kyle Jon B.
 * CCC121 IT2B Multimedia Laboratory
 * Make a Stack algorithm of its relevant functions or methods
 * Input: NodeList Containing: Name, Age, Course (size of 10 data)
 * Output: NodeList using implemented LL Operations
 */
import java.util.*;
public class Gaborni_2021_1254_Stack{
    public static void main(String[] args) {
        Main converter = new Main(); Scanner console = new Scanner(System.in);
        boolean termination = false;
        while (!termination){
            try {
                System.out.print("\nEnter an Infix Expression: "); String calculation = console.nextLine();
                System.out.println("Expression Infix: " + calculation); //ask user to input infix expression and returns the calculation
                System.out.println("Expression Postfix: " + converter.convertToPostfix(calculation));
                System.out.println("Calculation: " + converter.Evaluation(converter.convertToPostfix(Main.BalanceParenthesis(calculation))));
                System.out.println("\nDo You Want To Continue?\nY or N"); //program asks user to continue or terminate options
            }
            catch(ArithmeticException e){
                System.out.println("Arithmetic Error. Input Undefined. Try Again! "); //Every integer divided by 0 will be undefined thus it will result into error
                continue;
            }
            catch(StackOverflowError e){
                System.out.println("Input Error: Invalid Expression. Try Again!");
                continue;
            }
            catch(NumberFormatException e){
                System.out.println("NumberFormat Error: Invalid Number. Try Again!"); //if the input number is invalid. then throw NumberFormat Exception
                continue;
            }
            char response = new Scanner(System.in).nextLine().charAt(0); //ask user input to either continue or terminate program
            if (response == 'Y'){
                termination = false; //if user enters character Y, the program will continue
            }
            else if (response == 'N'){
                termination = true; //if user enters character N, the program will terminate
                System.out.println("\n< < < P R O G R A M   T E R M I N A T E D > > >");
            }
        }
    }
}
class Main{
    private static final char Addition = '+', Subtraction = '-', Multiplication = '*', Remainder = '%', Division = '/', Exponentiation = '^'; //initiate operator variables through static method
    private boolean Operator(char x){
        return  //program returns basic Java operators
                x == '+'  ||
                        x == '-'  ||
                        x == '*'  ||
                        x == '%'  ||
                        x == '('  ||
                        x == ')'  ||
                        x == '/'  ||
                        x == '^'  ;
    }

    private boolean Number(char x){
        return  //program returns basic Java number operands
                x == '0'  ||
                        x == '1'  ||
                        x == '2'  ||
                        x == '3'  ||
                        x == '4'  ||
                        x == '5'  ||
                        x == '6'  ||
                        x == '7'  ||
                        x == '8'  ||
                        x == '9'  ||
                        x == '.'  ;
    }

    private boolean Space(char x){
        return (x == ' '); //program returns space at the postfix expression after each operator
    }

    private boolean PrecendentialOrder(char FirstOperand, char SecondOperand) {
        return switch (FirstOperand) {//prioritize the order of precedence that will process the operators and operands based on the order
            case '+', '-' -> !(SecondOperand == '+' || SecondOperand == '-');
            case '*', '/', '^', '%' -> SecondOperand == '^' || SecondOperand == '(';
            case '(' -> true;
            default -> false;
        };
    }

    public String convertToPostfix(String infix) throws NumberFormatException{
        StringBuilder builder = new StringBuilder();
        int idx = 0;
        int length = infix.length();
        for(int i = 0; i < length; i++){
            char ch = infix.charAt(i);
            if(Character.isDigit(ch)){
                builder.append(ch);
                if(i + 1 < length){
                    if(infix.charAt(i + 1) == '('){
                        builder.append('*');
                    }
                }
            }else{
                builder.append(ch);
            }
        }
        infix = builder.toString();

        Stack operatorStack = new Stack(infix.length());  char Expression;
        try {
            String[] parser = parse(infix);
            StringBuilder postfix = new StringBuilder(infix.length()); //uses a StringBuilder for the postfix expression
            infix = BalanceParenthesis(infix);
            for (int i = 0; i < parser.length; i++){
                String token = parser[i];
                Expression = token.charAt(0);
                if ( (token.length() == 1) && Operator(Expression)){
                    while (!operatorStack.isEmpty() &&
                            !PrecendentialOrder(((String)operatorStack.peek()).charAt(0), Expression))
                        postfix.append(" ").append((String)operatorStack.pop());
                    if (Expression == ')'){
                        String operator = (String)operatorStack.pop();
                        while (operator.charAt(0)!='(') {
                            postfix.append(" ").append(operator);
                            operator = (String)operatorStack.pop();
                        }
                    }
                    else
                        operatorStack.push(token);
                }
                else if ((token.length() == 1) && Space(Expression)) {} //program returns space after each operator
                else{
                    postfix.append(" ").append(token);
                }
            }
            while (!operatorStack.isEmpty()){
                postfix.append(" ").append((String)operatorStack.pop());
            }
            return postfix.toString(); //return postfix string
        }
        catch (NumberFormatException e){
            throw new NumberFormatException(); //catch NumberFormat error during user input
        }
    }

    public double Evaluation(String expr) throws NumberFormatException, ArithmeticException{
        Stack stack = new Stack(expr.length()); double FirstOperand, SecondOperand, calculation;
        String token; //StringTokenizer tokenizer = new StringTokenizer(expr);
        try{
            String[] parser = parse(expr);
            for (int i = 0; i < parser.length; i++){
                token = parser[i];
                char x = token.charAt(0); //initiate variable x to character token
                if (Operator(x)) {
                    SecondOperand = Double.parseDouble(stack.pop());
                    FirstOperand = Double.parseDouble(stack.pop());
                    calculation = OperatorEvaluation(token.charAt(0), FirstOperand, SecondOperand);
                    stack.push(String.valueOf(calculation));
                }
                else if(Number (x)){
                    stack.push(token);
                }
                else{
                    System.out.println("That is not a number nor an operator"); //If the given operand by the user is not a number nor operator then throw Arithmetic Exception
                    throw new ArithmeticException();
                }
            }
            calculation = Double.parseDouble(stack.pop());
            return calculation;
        } catch(NumberFormatException e){
            throw new NumberFormatException(); //catch NumberFormat error during user input
        }
    }

    public String[] parse(String infix)throws NumberFormatException{
        String[] parse = new String[infix.length()];
        int parseindex = 0;
        for(int i = 0; i < infix.length() ; i++){
            if (Number(infix.charAt(i))){
                try{
                    StringBuilder number = new StringBuilder();
                    while(Number(infix.charAt(i))){
                        number.append(infix.charAt(i++));
                        if(i == infix.length()){ //if variable i is equal to the length of infix
                            break;
                        }
                    }
                    i--;
                    Double.parseDouble(number.toString());
                    parse[parseindex] = number.toString();
                    parseindex++;
                }catch(NumberFormatException e) {
                    throw new NumberFormatException();
                }
            }else if(Operator(infix.charAt(i)) || infix.charAt(i) == ')' || infix.charAt(i) == '(' ){
                parse[parseindex] = String.valueOf(infix.charAt(i));
                parseindex++;
            }
        }
        String[] newArr = new String[parseindex];
        for(int i = 0; i < parseindex; i++){
            newArr[i] = parse[i];
        }
        return newArr; //returns the new array
    }

    private double OperatorEvaluation(char OperationEvaluation, double FirstOperand, double SecondOperand) throws ArithmeticException{
        double calculation = 0; //variable calculation initialized into 0
        switch (OperationEvaluation) {
            case Addition: calculation = FirstOperand + SecondOperand; //Addition operation
                break;
            case Subtraction: calculation = FirstOperand - SecondOperand;//Substraction operation
                break;
            case Multiplication: calculation = FirstOperand * SecondOperand;//Multiplication operation
                break;
            case Remainder: calculation = FirstOperand % SecondOperand;//Remainder operation
                break;
            case Division :  //Divide operation
                if (SecondOperand == 0){
                    //System.out.println("Cannot divide 0."); //Every integers divided by 0 will be undefined thus it will calculation into error
                    throw new ArithmeticException();
                }
                else {
                    calculation = FirstOperand / SecondOperand;
                }
                break;
            case Exponentiation : //Power operation
                calculation = Math.pow(FirstOperand, SecondOperand);
        }
        return calculation;
    }

    static String BalanceParenthesis(String infix){
        StringBuilder postfix = new StringBuilder();
        for(int i = 0; i < infix.length(); i++){
            char c = infix.charAt(i);
            if(Character.isDigit(c)){
                postfix.append(c);
                if(i < (infix.length() - 1)){
                    if(infix.charAt(i + 1) == '('){
                        postfix.append('*');
                    }
                }
            }else if(c == ')'){
                postfix.append(c);
                if(i < (infix.length() - 1)){
                    if(Character.isDigit(infix.charAt(i+1))){
                        postfix.append('*');
                    }
                    if(i < (infix.length() - 1)){
                        if(infix.charAt(i + 1) == '('){
                            postfix.append('*');
                        }
                    }
                }
            }else postfix.append(c);
        }
        return postfix.toString();
    }
}

class Stack{

    String[] string; private int pointer;

    public Stack(int StackSize){
        this.string = new String[StackSize]; //string size is dependent on user input
        this.pointer = -1; //pointer initializr to -1
    }
    public void push(String string) throws StackOverflowError{
        if(this.pointer == this.string.length - 1){
            throw new StackOverflowError("Stack Overflow."); // if Stack is  == Max - 1 throw Stack overflow
        } else {
            this.string[++this.pointer] = string;
        }
    }
    public String pop() throws StackOverflowError{
        String toPop = this.peek();
        this.string[this.pointer--] = null;
        return toPop;
    }
    public String peek() throws StackOverflowError{
        if(this.pointer == -1){
            throw new StackOverflowError("Stack Underflow."); //will throw Stack Underflow when pointer == -1
        } else{
            return this.string[this.pointer]; //returns the specified pointer of the string
        }
    }
    public int top(){
        return this.pointer; //returns the topmost pointer in the stack
    }
    public boolean isEmpty(){
        return this.pointer == -1; //if Stack is Empty, then this code is executed
    }
    public boolean isFull(){
        return this.pointer == this.string.length - 1; //if stack is Full, then this code is executed
    }
}