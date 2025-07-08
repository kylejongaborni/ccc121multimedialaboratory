# Gaborni, Kyle Jon
# Cañete, Rommel Jacob
# ITE153 - IT3D

# Write a program that features two options:
# a) takes an arbitrary propositional logic expression as an input,
# then takes either true or false as arguments for the variables,
# and then returns its truth-value, or
# b) generates a truth table for an input logical expression.

prc = { "~": 4, "^": 3, "v": 2, "->": 1, "<->": 1, "(": 0, ")": 0 }

def tokenize(expression):
    tokens = []
    i = 0
    while i < len(expression):
        if expression[i] == ' ':
            i += 1
        elif expression[i] in '()':
            tokens.append(expression[i])
            i += 1
        elif is_operator_start(expression[i]):
            operator = expression[i]
            if i < len(expression) - 1 and expression[i:i+2] in ['->', '<->']:
                operator += expression[i+1]
                i += 1
            if operator == '-' and i < len(expression) - 1 and expression[i+1] == '>':
                operator += expression[i+1]
                i += 1
            tokens.append(operator)
            i += 1
        else:
            j = i
            while j < len(expression) and expression[j].isalpha():
                j += 1
            tokens.append(expression[i:j])
            i = j
    return tokens

def is_operator_start(ch):
    return ch in ['^', 'v', '~', '-', '<']

def to_rpn(tokens):
    output = []
    stack = []

    for token in tokens:
        if is_operator(token) and token not in ["(", ")"]:
            while stack and is_higher_precedence(stack[-1], token):
                output.append(stack.pop())
            stack.append(token)
        elif token == "(":
            stack.append(token)
        elif token == ")":
            while stack and stack[-1] != "(":
                output.append(stack.pop())
            if not stack:
                raise ValueError("Parentheses mismatched")
            stack.pop()  # Pops the open parenthesis [(] from the stack
        else:  # Token is identified as either a  variable or operand
            output.append(token)

    while stack:
        if stack[-1] == "(":
            raise ValueError("Parentheses mismatched")
        output.append(stack.pop())

    return output

def is_operator(token):
    return token in prc

def is_higher_precedence(op1, op2):
    return prc[op1] > prc[op2]

def evaluate_rpn(rpn, variable_values):
    stack = []

    for token in rpn:
        if is_operator(token):
            if token == "~":
                if not stack:
                    raise ValueError("Missing operands for '~'.")
                stack.append(not stack.pop())
            else:
                if len(stack) < 2:
                    raise ValueError(f"Missing operands for '{token}'.")
                right = stack.pop()
                left = stack.pop()
                if token == "^":
                    stack.append(left and right)
                elif token == "v":
                    stack.append(left or right)
                elif token == "->":
                    stack.append(not left or right)
                elif token == "<->":
                    stack.append(left == right)
        else:
            value = variable_values.get(token)
            if value is None:
                raise ValueError(f"The variable '{token}' is not among identified values.")
            stack.append(value)

    if len(stack) != 1:
        raise ValueError("Certain Unprocessed Operands Remain.")

    return stack.pop()

def evaluate_expression():
    try:
        expression = input("Input the logical expression (Expressions: '^', 'v', '~', '->', '<->'): ")
        variables = input("Enter the variables and separate it using comma (Example: P,Q,R): ").split(',')
        variable_values = {}
        for var in variables:
            value = input(f"Input Truth Value of {var} (T or F): ")
            if value.upper() not in ["T", "F"]:
                raise ValueError("Truth Value Unidentified. Please input 'T' or 'F' only.")
            variable_values[var] = value.upper() == "T"

        # Substitute and print truth values into the logical expression
        substituted_expression = expression
        for key, value in variable_values.items():
            substituted_expression = substituted_expression.replace(key, "T" if value else "F")

        result = evaluate_logic_expression(expression, variable_values)
        print()
        print("= " + (substituted_expression))
        print("= " + ("T" if result else "F"))
        print("-" * 100)
        print("")
    except Exception as e:
        print("Error: " + str(e))
        print("")

def evaluate_logic_expression(expression, variable_values):
    tokens = tokenize(expression)
    rpn = to_rpn(tokens)
    return evaluate_rpn(rpn, variable_values)

def process_operator(operator_stack, output_stack, sub_expressions):
    if not operator_stack:
        raise ValueError("Operator stack is empty")
    operator = operator_stack.pop()

    if operator == "~":
        # Unary operator has only one operand
        if not output_stack:
            raise ValueError("Output stack is empty for unary operator")
        operand = output_stack.pop()
        sub_expr = operator + operand
    else:
        # Binary operator has two operands
        if len(output_stack) < 2:
            raise ValueError("Output stack is empty for binary operator")
        operand2 = output_stack.pop()
        operand1 = output_stack.pop()
        sub_expr = "(" + operand1 + " " + operator + " " + operand2 + ")"

    output_stack.append(sub_expr)
    sub_expressions.append(sub_expr)

def generate_truth_table(expression, variables):
    tokens = tokenize(expression)
    rpn = to_rpn(tokens)

    # Calculate the width needed for each column present
    variable_column_width = max(len(var) for var in variables)
    expression_column_width = max(len(expression), 4)  # At least as wide as the word "True" or "False"

    # Print head column
    for var in variables:
        print("| {0:<{1}}".format(var, variable_column_width), end="")
    print("| {0:<{1}} |".format(expression, expression_column_width))  # Newline after header

    # Print separator line
    for var in variables:
        print("|" + "-" * (variable_column_width + 1), end="")
    print("|" + "-" * (expression_column_width + 2) + "|")  # Newline after separator line

    # Generate truth table rows
    rows = 2 ** len(variables)
    variable_values = {}
    for i in range(rows):
        for var in variables:
            value = ((rows - 1 - i) >> (len(variables) - variables.index(var) - 1) & 1) == 1
            variable_values[var] = value
            print("| {0:<{1}}".format("T" if value else "F", variable_column_width), end="")
        result = evaluate_logic_expression(expression, variable_values)
        print("| {0:<{1}} |".format("T" if result else "F", expression_column_width))  # Newline at the end of each row

    # Print separator line after the last row for each table
    for var in variables:
        print("|" + "-" * (variable_column_width + 1), end="")
    print("|" + "-" * (expression_column_width + 2) + "|")
    print("")

    # Determine and print the type of expression
    all_true = all(evaluate_logic_expression(expression, variable_values) for i in range(rows)) == 'T'
    all_false = all(not evaluate_logic_expression(expression, variable_values) for i in range(rows))

    if all_true:
        print("The Expression is a Tautology.")
    elif all_false:
        print("The Expression is a Contradiction.")
    else:
        print("The Expression is neither a Tautology nor Contradiction.")
    print("")
    print("-" * 100)
    print("")

def main():
    while True:
        try:
            print("[0] Exit Program.")
            print("[1] Evaluate Logical Expression.")
            print("[2] Generate Truth Table.")
            choice = input("Choose an option: ")

            if not choice.isdigit():
                raise ValueError("Invalid option! Try Again.")

            choice = int(choice)

            if choice == 1:
                evaluate_expression()
            elif choice == 2:
                expression = input("Enter logical expression: ")
                variables = input("Enter variables separated by comma/s: ").split(',')
                generate_truth_table(expression, variables)
            elif choice == 0:
                print("Exiting Program...")
                break
            else:
                print("Invalid option! Try Again.")
        except Exception as e:
            print(f"Error: {e}")
            print("")

if __name__ == "__main__":
    main()
