package misc;

import data.Stack;

public class Calculator {
    private Stack<Double> numStack;
    private Stack<CalcEnum> operationStack;

    public Calculator(){
        numStack = new Stack<>();
        operationStack = new Stack<>();
    }

    public double calculate(String str) {
        try {
            char oldChar = ' ';

            for (char j : str.toCharArray()) {
                if (j == ' ') {
                    oldChar = j;
                    continue;
                }
                if (j == '+' || j == '-' || j == '*' || j == '/' || j == '^' || j == '(' || j == ')') {
                    CalcEnum CalcJ = CalcEnum.getOperation(j);
                    oldChar = j;
                    if (operationStack.getLength() == 0) {
                        operationStack.push(CalcJ);
                        continue;
                    } else if (operationStack.peek() == CalcEnum.OPEN_BRACKET) {
                        operationStack.push(CalcJ);
                        continue;
                    } else if (CalcJ == CalcEnum.CLOSE_BRACKET) {
                        while (true) {
                            if (operationStack.peek() == CalcEnum.OPEN_BRACKET) {
                                operationStack.pop();
                                break;
                            } else {
                                performOperation();
                            }
                        }
                        continue;
                    } else if (CalcJ == CalcEnum.OPEN_BRACKET) {
                        operationStack.push(CalcJ);
                        continue;
                    } else if (operationStack.peek().getPriority() < CalcJ.getPriority()) {
                        operationStack.push(CalcJ);
                        continue;
                    } else if (operationStack.peek().getPriority() >= CalcJ.getPriority()) {
                        while (true) {
                            if (operationStack.getLength() == 0 ||
                                    operationStack.peek() == CalcEnum.OPEN_BRACKET ||
                                    operationStack.peek().getPriority() < CalcJ.getPriority()) {
                                operationStack.push(CalcJ);
                                break;
                            } else {
                                performOperation();
                            }
                        }
                        continue;
                    }
                } else {
                    boolean isNum = true;
                    int numChar = 0;
                    double NumJ = convertToNumber(j);
                    try {
                        numChar = convertToNumber(oldChar);
                    } catch (IllegalArgumentException ex) {
                        isNum = false;
                    }
                    if (isNum) {
                        double lastNum = numStack.pop();
                        lastNum = lastNum * 10;
                        lastNum += NumJ;
                        numStack.push(lastNum);
                    } else {
                        numStack.push(NumJ);
                    }
                    oldChar = j;
                }
            }

            while (true) {
                if (operationStack.getLength() == 0) {
                    if (numStack.getLength() == 1) {
                        return numStack.pop();
                    } else {
                        throw new IllegalArgumentException("Плохая строка. Или код плохой.");
                    }
                } else {
                    performOperation();
                }
            }
        } catch (Exception ex) {
            System.out.println("Плохая строка!");
        }
        return 0;
    }

    private int convertToNumber(char c){
        if (c == '0'){
            return 0;
        }
        else if (c == '1'){
            return 1;
        }
        else if (c == '2'){
            return 2;
        }
        else if (c == '3'){
            return 3;
        }
        else if (c == '4'){
            return 4;
        }
        else if (c == '5'){
            return 5;
        }
        else if (c == '6'){
            return 6;
        }
        else if (c == '7'){
            return 7;
        }
        else if (c == '8'){
            return 8;
        }
        else if (c == '9'){
            return 9;
        }
        else{
            throw new IllegalArgumentException("This is not a number!");
        }
    }

    private void performOperation(){
        if (numStack.getLength() < 2) {
            throw new IllegalArgumentException("NullPointerException in NumStack!");
        }
        else if (operationStack.getLength() == 0) {
            throw new IllegalArgumentException("NullPointerException in OperationStack!");
        }
        else {
            double num1 = numStack.pop();
            double num2 = numStack.pop();
            CalcEnum operation = operationStack.pop();
            if (operation == CalcEnum.CLOSE_BRACKET || operation == CalcEnum.OPEN_BRACKET) {
                throw new IllegalArgumentException("Bracket in OperationStack.");
            }
            else if (operation == CalcEnum.PLUS){
                numStack.push(num2 + num1);
            }
            else if (operation == CalcEnum.MINUS){
                numStack.push(num2 - num1);
            }
            else if (operation == CalcEnum.DIVIDE){
                numStack.push(num2 / num1);
            }
            else if (operation == CalcEnum.MULTIPLY){
                numStack.push(num2 * num1);
            }
            else if (operation == CalcEnum.EXPONENTIATE){
                numStack.push(Math.pow(num2, num1));
            }
        }
    }

}
