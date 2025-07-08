# Gaborni, Kyle Jon
# Cañete, Rommel Jacob
# ITE153 - IT3D

# Write a program that features two options:
# a) takes an arbitrary propositional logic expression as an input,
# then takes either true or false as arguments for the variables,
# and then returns its truth-value, or
# b) generates a truth table for an input logical expression.

import itertools

def evaluate_expression(expression, variable_values):
    stack = []
    operators = {'∧', '∨', '~', '->', '<->'}

    variables = sorted(set(char for char in expression if char.isalpha()))

    # Converting to Postfix part
    for char in expression:
        if char == ' ':
            continue

        if char.isalpha():
            stack.append(variable_values[char])
        elif char in operators or char == '(':
            stack.append(char)()
            # Inside the ')' case in the evaluate_expression function
        elif char == ')':
            while stack and stack[-1] != '(':
                current_operator = stack.pop()
                if current_operator == '~':
                    operand = stack.pop()
                    stack.append(not operand)
                elif current_operator == '->':
                    operand2 = stack.pop() if stack else None  # Initialize operand2

                    # Check if the stack is not empty before popping operand1
                    operand1 = stack.pop() if stack else None

                    stack.append(operand2 if operand1 is None else not operand1 or operand2)

            if stack and stack[-1] == '(':
                stack.pop()  # Remove the '('

    else: # Pag pass
            operand2 = stack.pop()
            operator = stack.pop()
            operand1 = stack.pop() if operator != '~' else None

            if operator == '∧':
                stack.append(operand1 and operand2)
            elif operator == '∨':
                stack.append(operand1 or operand2)
            elif operator == '~':
                stack.append(not operand2)
            elif operator == '<->':
                stack.append(operand1 == operand2)

            if stack:
                stack.pop()  # Remove the '('

    print(f"Postfix: {stack}")
    while len(stack) > 1: # Postfix Evaluator part
        operand2 = stack.pop()
        operator = stack.pop()
        operand1 = stack.pop() if operator != '~' else None

        if operator == '∧':
            stack.append(operand1 and operand2)
        elif operator == '∨':
            stack.append(operand1 or operand2)
        elif operator == '~':
            stack.append(not operand2)
        elif operator == '->':
            stack.append(operand2 if operand1 is None else not operand1 or operand2) #IMPLICATION
        elif operator == '<->':
            stack.append(operand1 == operand2)

    return stack[0] if stack else None

def generate_truth_table(expression):
    variables = sorted(set(char for char in expression if char.isalpha()))
    truth_values_combinations = list(itertools.product([True, False], repeat=len(variables)))

    print("\nTruth Table:")
    print("Variables: " + ", ".join(variables))
    header = variables + [expression]
    max_variable_length = max(map(len, header))

    print(f"| {header[0]:^{max_variable_length}} ", end="")
    for variable in header[1:]:
        print(f"| {variable:^{max_variable_length}} ", end="")
    print("|")

    separator = "+".join(["-" * (max_variable_length + 2) for _ in header])
    print(f"+{separator[1:]}+")

    for values in truth_values_combinations:
        variable_values = dict(zip(variables, values))
        result = evaluate_expression(expression, variable_values)
        row_values = [str("T" if value else "F") for value in values] + [str("T" if result else "F")]
        print(f"| {row_values[0]:^{max_variable_length}} ", end="")
        for value in row_values[1:]:
            print(f"| {value:^{max_variable_length}} ", end="")
        print("|")

    print(f"+{separator[1:]}+\n")

# main
def main():
    while True:  # user selects an option
        print("[1] Evaluate Logical Expression")
        print("[2] Generate Truth Table")
        print("[3] Exit Program")

        choice = input("Choose an option: ")

        if choice == '1':
            expression = input("Enter a propositional logic expression: ")
            try:
                variables = sorted(set(char for char in expression if char.isalpha()))
                variable_values = {}
                for variable in variables:
                    value = input(f"Enter truth value for {variable} (T/F): ")
                    if value.upper() not in ('T', 'F'):
                        raise ValueError("Invalid truth value. Enter 'T' or 'F'.")
                    variable_values[variable] = value.upper() == 'T'
                result = evaluate_expression(expression, variable_values)
                print(f"\nExpression: {expression}")
                print(f"Result: {result}\n")
            except Exception as e:
                print(f"Error: {e}\n")

        elif choice == '2':
            expression = input("Enter a propositional logic expression: ")
            try:
                generate_truth_table(expression)
            except Exception as e:
                print(f"Error: {e}\n")

        elif choice == '3':
            print("Exiting Program")
            break

        else:
            print("Invalid option! Try Again.\n")


if __name__ == "__main__":
    main()