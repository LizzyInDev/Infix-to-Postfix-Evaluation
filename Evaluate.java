public class Evaluate {
    private Parser p;

    public void postfixStack() {
        p = new Parser();
        p.parser();
        String[] postfix = p.getPostfix();
        String[] stack = new String[256];   // stack evaluator

        /*
         * Count total non-null entries in String[] postfix
         * This will be the control variable for evaluate for-loop
         * */
        int length = 0;
        for (String p: postfix)
            if(p!=null)
                length++;

        /*
        * Evaluate Postfix
        * This conversion follows the Shunting Yard Algorithm
        * String[] postfix serves as the queue
        * String[] stack serves as the stacks where operands are stored
        * */
        int stackCount = 0;
        int operand1;
        int operand2;
        int error = 0;
        for (int i=0; i<length; i++) {
            String token = postfix[i];
            boolean bool = p.checkOperator(token);

            if(bool) {
                if(!token.equals("!")) {
                    operand2 = Integer.parseInt(stack[stackCount - 1]);
                    stack[stackCount - 1] = null;
                    stackCount--;
                    operand1 = Integer.parseInt(stack[stackCount - 1]);
                    stack[stackCount - 1] = null;
                    stackCount--;
                } else {
                    operand1 = Integer.parseInt(stack[stackCount - 1]);
                    stack[stackCount - 1] = null;
                    stackCount--;
                    operand2 = 1;
                }

                if(token.equals("/") && operand2==0) {
                    error = 1;
                    break;
                } else {
                    stack[stackCount] = String.valueOf(evaluate(operand1, operand2, token));
                    stackCount++;
                }

            } else {
                stack[stackCount] = token;
                stackCount++;
            }
        }
        if(error==1)
            System.out.println("Division by zero error!");
        else
            System.out.println("Evaluated value: " + stack[0]);
    }

    /*
    * Evaluates expression
    * Accepts parameters int operand1, int operand2, and String operator
    * Returns the result of the operation
    * */
    public int evaluate(int operand1, int operand2, String operator) {
        boolean x;
        boolean y;
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "%":
                return operand1 % operand2;
            case "^":
                return (int) Math.pow(operand1, operand2);
            case ">": {
                boolean b = operand1 > operand2;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "<": {
                boolean b = operand1 < operand2;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case ">=": {
                boolean b = operand1 >= operand2;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "<=": {
                boolean b = operand1 <= operand2;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "!=": {
                boolean b = operand1 != operand2;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "==": {
                boolean b = operand1 == operand2;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "!": {
                x = operand1 == 1;
                boolean b = !x;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "&&": {
                x = operand1 == 1;
                y = operand2 == 1;
                boolean b = x && y;
                if (b)
                    return 1;
                else
                    return 0;
            }
            case "||": {
                x = operand1 == 1;
                y = operand2 == 1;
                boolean b = x || y;
                if (b)
                    return 1;
                else
                    return 0;
            }
        }
        return 0;
    }
}
