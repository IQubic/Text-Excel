package textExcel;

import java.util.*;

public class FormulaCell extends RealCell {
    private String formula;
    // private Map<SOMETHING, Integer> dependecies;
    private List<String> rpn;

    public FormulaCell(String formula, List<String> rpn) {
        super(FormulaCell.firstEval(rpn));
        this.formula = formula;
        this.rpn = rpn;
    }

    // TODO Add Dependecy Handling here
    private static double firstEval(List<String> rpn) {
        Deque<Double> evalStack = new ArrayDeque<>();

        for (String token : rpn) {
            // Next token is a number
            if (!isOperator(token)) {
                evalStack.push(Double.parseDouble(token));

            // Token is a operation
            } else {
                double num2 = evalStack.pop();
                double num1 = evalStack.pop();
                evalStack.push(eval(num1, num2, token.charAt(0)));
            }
        }

        return evalStack.pop();
    }

    // Evaluates a two operand math expression
    private static double eval(double num1, double num2, char op) {
        double result = 0;
        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
        }
        return result;
    }

    // Returns true if token is an operator
    private static boolean isOperator(String token) {
        boolean isOperator = false;

        if (token.equals("+")) {
            isOperator = true;
        } else if (token.equals("-")) {
            isOperator = true;
        } else if (token.equals("*")) {
            isOperator = true;
        } else if (token.equals("/")) {
            isOperator = true;
        }

        return isOperator;
 }

    @Override
    public String fullCellText() {
        return "( " + formula + " )";
    }
}
