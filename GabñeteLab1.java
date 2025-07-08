import java.util.*;

public class GabñeteLab1 {

    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "~", 4, "^", 3, "v", 2, "->", 1, "<->", 1, "(", 0, ")", 0
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Choose an option:");
                System.out.println("1. Evaluate a Logical Expression");
                System.out.println("2. Generate a Truth Table");
                System.out.println("3. Exit");
                System.out.print("Please enter your chosen option (1/2/3): ");
                int choice;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                } else {
                    scanner.nextLine(); // Consume invalid input
                    throw new IllegalArgumentException("Please enter a valid number (1, 2, or 3).\n=========================================================================================");
                }

                switch (choice) {
                    case 1:
                        evaluateExpression(scanner);
                        break;
                    case 2:
                        System.out.print("Enter a logical expression (use '^', 'v', '~', '->', '<->'): ");
                        String expression = scanner.nextLine();
                        System.out.print("Enter the variables separated by space (e.g., p q r): ");
                        String[] variables = scanner.nextLine().split(" ");
                        generateTruthTable(expression, variables);
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }   catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == ' ') {
                continue;
            }

            if (ch == '(' || ch == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token = new StringBuilder();
                }
                tokens.add(String.valueOf(ch));
            } else if (isOperatorStart(ch)) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token = new StringBuilder();
                }
                token.append(ch);

                // Check for multi-character operators
                if (i + 2 < expression.length() && expression.substring(i, i + 3).equals("<->")) {
                    token.append(expression.substring(i + 1, i + 3));
                    i += 2;
                } else if (i + 1 < expression.length() && expression.substring(i, i + 2).equals("->")) {
                    token.append(expression.substring(i + 1, i + 2));
                    i += 1;
                }
                tokens.add(token.toString());
                token = new StringBuilder();
            } else {
                token.append(ch);
            }
        }

        if (token.length() > 0) {
            tokens.add(token.toString());
        }

        return tokens;
    }


    private static boolean isOperatorStart(char ch) {
        return ch == '^' || ch == 'v' || ch == '~' || ch == '-' || ch == '<';
    }

    public static List<String> toRPN(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();

        for (String token : tokens) {
            if (isOperator(token) && !token.equals("(") && !token.equals(")")) {
                while (!stack.isEmpty() && isHigherPrecedence(stack.peek(), token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // Pop the '(' from the stack
                }
            } else { // Token is a variable or operand
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Mismatched parentheses in expression");
            }
            output.add(stack.pop());
        }

        return output;
    }

    private static boolean isOperator(String token) {
        return PRECEDENCE.containsKey(token);
    }

    private static boolean isHigherPrecedence(String op1, String op2) {
        return PRECEDENCE.get(op1) > PRECEDENCE.get(op2);
    }

    public static boolean evaluateRPN(List<String> rpn, Map<String, Boolean> variableValues) {
        Deque<Boolean> stack = new ArrayDeque<>();

        for (String token : rpn) {
            if (isOperator(token)) {
                if (token.equals("~")) {
                    if (stack.isEmpty()) {
                        throw new IllegalStateException("Invalid RPN expression: missing operand for '~'");
                    }
                    stack.push(!stack.pop());
                } else {
                    if (stack.size() < 2) {
                        throw new IllegalStateException("Invalid RPN expression: missing operands for '" + token + "'");
                    }
                    boolean right = stack.pop();
                    boolean left = stack.pop();
                    switch (token) {
                        case "^":
                            stack.push(left && right);
                            break;
                        case "v":
                            stack.push(left || right);
                            break;
                        case "->":
                            stack.push(!left || right);
                            break;
                        case "<->":
                            stack.push(left == right);
                            break;
                        default:
                            throw new IllegalStateException("Unrecognized operator: " + token);
                    }
                }
            } else {
                Boolean value = variableValues.get(token);
                if (value == null) {
                    throw new IllegalArgumentException("Variable '" + token + "' not found in variable values map");
                }
                stack.push(value);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Invalid RPN expression: unprocessed operands remain");
        }

        return stack.pop();
    }
    private static void evaluateExpression(Scanner scanner) {
        try {
            System.out.print("Enter a logical expression (use '^', 'v', '~', '->', '<->'): ");
            String expression = scanner.nextLine();
            System.out.print("Enter the variables separated by space (e.g., p q r): ");
            String[] variables = scanner.nextLine().split(" ");
            Map<String, Boolean> variableValues = new HashMap<>();
            for (String var : variables) {
                System.out.print("Enter the truth value for " + var + " (T or F): ");
                String value = scanner.next();
                if (!value.equalsIgnoreCase("T") && !value.equalsIgnoreCase("F")) {
                    throw new IllegalArgumentException("Invalid truth value. Please enter 'T' or 'F'.");
                }
                variableValues.put(var, value.equalsIgnoreCase("T"));
            }
            scanner.nextLine(); // consume newline

            // Substitute and print truth values into the logical expression
            StringBuilder substitutedExpression = new StringBuilder(expression);
            for (Map.Entry<String, Boolean> entry : variableValues.entrySet()) {
                substitutedExpression.replace(
                        0,
                        substitutedExpression.length(),
                        substitutedExpression.toString().replace(entry.getKey(), entry.getValue() ? "T" : "F")
                );
            }

            boolean result = evaluateLogicExpression(expression, variableValues);
            System.out.println();
            System.out.println(substitutedExpression);
            System.out.println("The result of the expression is: " + (result ? "T" : "F"));
            System.out.println("=========================================================================================");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine(); // consume newline if exception occurs
        }
    }

    private static boolean evaluateLogicExpression(String expression, Map<String, Boolean> variableValues) {
        List<String> tokens = tokenize(expression);
        List<String> rpn = toRPN(tokens);
        return evaluateRPN(rpn, variableValues);
    }

    public static List<String> identifySubExpressions(String expression) {
        List<String> tokens = tokenize(expression);
        Deque<String> operatorStack = new ArrayDeque<>();
        Deque<String> outputStack = new ArrayDeque<>();
        List<String> subExpressions = new ArrayList<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!operatorStack.isEmpty() && isHigherPrecedence(operatorStack.peek(), token)) {
                    processOperator(operatorStack, outputStack, subExpressions);
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processOperator(operatorStack, outputStack, subExpressions);
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Pop the '('
                }
            } else { // Token is a variable or operand
                outputStack.push(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            processOperator(operatorStack, outputStack, subExpressions);
        }

        return subExpressions;
    }

    private static void processOperator(Deque<String> operatorStack, Deque<String> outputStack, List<String> subExpressions) {
        String operator = operatorStack.pop();
        String operand2 = outputStack.pop();
        String operand1 = operator.equals("~") ? "" : outputStack.pop(); // Unary operator has only one operand
        String subExpr = operand1.isEmpty() ? operator + operand2 : "(" + operand1 + " " + operator + " " + operand2 + ")";
        outputStack.push(subExpr);
        subExpressions.add(subExpr);
    }

    private static void generateTruthTable(String expression, String[] variables) {
        List<String> tokens = tokenize(expression);
        List<String> rpn = toRPN(tokens);
        List<String> subExpressions = identifySubExpressions(expression);

        // Calculate the total width needed for each column
        int variableColumnWidth = Arrays.stream(variables).mapToInt(String::length).max().orElse(0);
        int subExpressionColumnWidth = subExpressions.stream().mapToInt(String::length).max().orElse(0);

        // Print header
        for (String var : variables) {
            System.out.printf("| %-" + variableColumnWidth + "s", var);
        }
        for (String subExpr : subExpressions) {
            System.out.printf("| %-" + subExpressionColumnWidth + "s", subExpr);
        }
        System.out.println("|"); // Newline after header

        // Print separator line
        for (String var : variables) {
            System.out.print("|" + "-".repeat(variableColumnWidth + 1));
        }
        for (String subExpr : subExpressions) {
            System.out.print("|" + "-".repeat(subExpressionColumnWidth + 1));
        }
        System.out.println("|"); // Newline after separator line

        // Variables to determine the type of expression
        boolean allTrue = true;
        boolean allFalse = true;

        // Generate truth table rows
        int rows = (int) Math.pow(2, variables.length);
        Map<String, Boolean> variableValues = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (String var : variables) {
                boolean value = ((rows - 1 - i) >> (variables.length - Arrays.asList(variables).indexOf(var) - 1) & 1) == 1;
                variableValues.put(var, value);
                System.out.printf("| %-" + variableColumnWidth + "s", value ? "T" : "F");
            }

            for (String subExpr : subExpressions) {
                boolean subResult = evaluateLogicExpression(subExpr, variableValues);
                System.out.printf("| %-" + subExpressionColumnWidth + "s", subResult ? "T" : "F");
            }

            // Evaluate the main expression for determining its type (Tautology, Contradiction, or Neither)
            boolean result = evaluateLogicExpression(expression, variableValues);
            System.out.println("|"); // Newline at the end of each row

            // Update expression type variables
            allTrue &= result;
            allFalse &= !result;
        }

        // Print separator line after the last row
        for (String var : variables) {
            System.out.print("|" + "-".repeat(variableColumnWidth + 1));
        }
        for (String subExpr : subExpressions) {
            System.out.print("|" + "-".repeat(subExpressionColumnWidth + 1));
        }
        System.out.println("|"); // Newline after separator line

        // Determine and print the type of expression
        if (allTrue) {
            System.out.println("The logical expression is: Tautology");
        } else if (allFalse) {
            System.out.println("The logical expression is: Contradiction");
        } else {
            System.out.println("The logical expression is: Neither");
        }
    }
}
