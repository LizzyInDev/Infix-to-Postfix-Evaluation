import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Parser {
    private String[] postfix = new String[256];

    public void parser() {
        Scanner scanner = new Scanner(System.in);
        String[] infix = new String[256];
        String[] stack = new String[256];   // for operators

        // get infix expression input
        System.out.print("Enter infix expression: ");
        String input = scanner.next();

        /*
         * Convert input into a temporary array
         * This puts each element of input into a single array cell
         * e.g. 123 will be in 3 separate array cells,
         *      && will be in 2 separate array cells
         * */
        String[] temp = input.split("");

        /*
         * Convert input to infix array
         * This groups operands and operators accordingly into a single array cell
         * e.g. 123 will now be in 1 array cell as well as &&, <=, etc.
         * */
        int checkpoint=0;
        int check=0;
        for (int i = 0; i < input.length(); i++) {
            String token = temp[i];
            boolean isOperand = checkOperand(token);
            boolean isOperator = checkOperator(token);

            if(isOperand) {
                boolean isAlso = checkOperand(temp[i-1]);
                if(checkpoint!=0 && isAlso)
                    infix[checkpoint] = infix[checkpoint] + temp[i];
                else {
                    infix[check] = temp[i];
                    checkpoint = i;
                    check++;
                }
            } else if(isOperator) {
                boolean isAlso = checkOperator(temp[i-1]);
                if(checkpoint!=0 && isAlso)
                    infix[checkpoint] = infix[checkpoint] + temp[i];
                else {
                    infix[check] = temp[i];
                    checkpoint = i;
                    check++;
                }
            } else {
                infix[check] = temp[i];
                check++;
            }
        }

        /*
         * Count total non-null entries in String[] infix
         * This will be the control variable for the infix to postfix for-loop
         * */
        int length = 0;
        for (int i=0; i<input.length(); i++)
            if (infix[i]!=null)
                length++;

        /*
         * Converting Infix to Postfix
         * This conversion follows the Shunting Yard Algorithm
         * String[] postfix serves as the queue
         * String[] stack serves as the stack where operators are stored and popped
         * */
        int stackCount = 0;
        int queueCount = 0;
        for (int i = 0; i < length; i++) {
            String token = infix[i];
            assert token != null;
            boolean isOperand = checkOperand(token.substring(0,1));
            boolean isOperator = checkOperator(token);

            if(isOperand) {
                postfix[queueCount] = token;
                queueCount++;
            }
            else if(isOperator) {
                // check operator hierarchy
                while(!Objects.equals(stack[stackCount], null))
                    if (checkPrecedence(token) <= checkPrecedence(stack[stackCount])) {
                        postfix[queueCount] = stack[stackCount - 1];
                        queueCount++;
                        stackCount--;
                    }
                stack[stackCount] = token;
                stackCount++;
            } else if(infix[i].equals("(")) {
                stack[stackCount] = token;
                stackCount++;
            } else if(infix[i].equals(")")) {
                while(!Objects.equals(stack[stackCount - 1], null) && !stack[stackCount - 1].equals("(")) {
                    postfix[queueCount] = stack[stackCount - 1];
                    stack[stackCount-1] = null;
                    queueCount++;
                    stackCount--;
                }
                // pop open parenthesis
                stack[stackCount-1] = null;
                stackCount--;
            }
        }

        /*
         * Once there are no more elements in String[] infix,
         *   we pop all elements and enqueue them to String[] postfix
         * */
        for (int i = 0; i < stackCount; i++) {
            postfix[queueCount] = stack[stackCount];
            queueCount++;
            stackCount--;
        }

        System.out.println(Arrays.toString(postfix));
        // calls print to output String[] postfix
        print();
    }

    /*
     * Checks precedence order
     * Accepts an operator in string which will be evaluated in if-else condition
     * Returns corresponding rank where 1 is lowest and 3 is highest
     * */
    public int checkPrecedence (String operator) {
        if(operator.equals("^"))
            return 3;
        else if(operator.equals("*") || operator.equals("/") || operator.equals("%"))
            return 2;
        else
            return 1;
    }

    /*
     * Checks if variable is an operand
     * Accepts parameter String token which will be evaluated by boolean method
     * Returns true if the passed token falls under 0-9
     * */
    public boolean checkOperand (String token) {
        return token.equals("0") || token.equals("1") || token.equals("2") || token.equals("3") || token.equals("4") || token.equals("5") || token.equals("6") || token.equals("7") || token.equals("8") || token.equals("9");
    }

    /*
     * Checks if variable is an operator
     * Accepts parameter String token which will be evaluated by boolean method
     * Returns true if the passed token falls under any of the arithmetic, relational, and logical operators
     * */
    public boolean checkOperator (String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%") || token.equals("^") || token.equals(">") || token.equals("<") || token.equals("=") || token.equals("==") || token.equals(">=") || token.equals("<=") || token.equals("!") || token.equals("!=") || token.equals("&") || token.equals("|") || token.equals("&&") || token.equals("||");
    }

    // prints the output String[] postfix
    public void print () {
        System.out.print("Postfix: ");
        for (String s : postfix)
            if (s != null)
                System.out.print(s + " ");
        System.out.println();
    }

    public String[] getPostfix() {
        return postfix.clone();
    }
}