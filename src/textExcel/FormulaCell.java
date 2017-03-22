package textExcel;

import java.util.*;

public class FormulaCell extends RealCell {
    private String formula;
    private List<String> rpn;
    private Spreadsheet sheetRef;

    // Only way to get a FormulaCell is with createFormulaCell
    // super.doubleValue is never used, as getDoubleValue is overridden here
    private FormulaCell(String formula, List<String> rpn, Spreadsheet sheetRef) {
        super(0);
        this.formula = formula;
        this.rpn = rpn;
        this.sheetRef = sheetRef;
    }

    // TODO Add method formula support
    public static FormulaCell createFormulaCell(String formula, Spreadsheet sheetRef) {
        List<String> rpn = convertToRPN(formula);
        return new FormulaCell(formula, rpn, sheetRef);
    }

    // Converts an equation string into rpn
    // rpn conversion is done with Edsger Dijkstra's Shunting Yard Algorithm
    private static List<String> convertToRPN(String formula) {
        String[] tokens = formula.split("\\s+");
        List<String> rpn = new ArrayList<>(tokens.length);

        // Higher value means higher precedence
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        // A stack to use for the rpn conversion
        Deque<String> opStack = new ArrayDeque<>();

        // Start of Shunting Yard Algorithm
        for (String curToken : tokens) {
            // Token is either a number or a cell reference
            if (!precedence.keySet().contains(curToken)) {
                // TODO add depdency finding here
                rpn.add(curToken);

            // Token is a operator
            } else {
                while (!opStack.isEmpty() && precedence.get(curToken) <= precedence.get(opStack.peek())) {
                    rpn.add(opStack.pop());
                }
                opStack.push(curToken);
            } // END OF OPERATOR PARSING
        }
        // clear out the remaining operators
        while (!opStack.isEmpty()) {
            rpn.add(opStack.pop());
        }

        return rpn;
    }

    // Evaluates the formula stored in this cell
    // TODO Add cell refernce support
    private double eval() {
        Deque<Double> evalStack = new ArrayDeque<>();

        for (String token : this.rpn) {
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
    public double getDoubleValue() {
        System.out.println("TEST");
        if (super.hasError()) {
            return Double.NaN;
        } else {
            return this.eval();
        }
    }

    @Override
    public String fullCellText() {
        return "( " + formula + " )";
    }
}
