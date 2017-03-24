package textExcel;

import java.util.*;

public class FormulaCell extends RealCell {
    private String formula;
    private List<String> rpn;
    private Spreadsheet sheetRef;

    // super.doubleValue is never used, as getDoubleValue is overridden here
    public FormulaCell(String formula, Spreadsheet sheetRef) {
        super(0);
        this.formula = formula;
        this.rpn = convertToRPN(formula);
        this.sheetRef = sheetRef;
    }

    // Converts an equation string into rpn
    // RPN conversion is done with Edsger Dijkstra's Shunting Yard Algorithm
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
    // TODO Add circular dependency support
    private double eval() throws IllegalArgumentException {
        Deque<Double> evalStack = new ArrayDeque<>();

        for (String token : this.rpn) {
            // Next token is a number or cell reference
            if (!isOperator(token)) {
                double value = this.tokenToDouble(token);
                if (Double.isNaN(value)) {
                    throw new IllegalArgumentException();
                }
                evalStack.push(value);

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
    private static double eval(double num1, double num2, char op) throws IllegalArgumentException {
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
                // Check for divide by zero
                if (num2 == 0) {
                    throw new IllegalArgumentException();
                }

                result = num1 / num2;
                break;
        }
        return result;
    }

    // Converts a token like "5", "13", "A1", or "b12" to the correct double value
    // TODO Add circular dependency support
    private double tokenToDouble(String token) throws IllegalArgumentException {
        // Token is a number
        if (!token.matches("[A-Za-z][0-9][0-9]?")){
            return Double.parseDouble(token);
        }

        // Token is a cell reference
        Location loc = new SpreadsheetLocation(token);
        Cell curCell = sheetRef.getCell(loc);

        // Formula refers to something that is not a RealCell
        if (!(curCell instanceof RealCell)) {
            throw new IllegalArgumentException();
        }

        // curCell is a RealCell
        return ((RealCell) curCell).getDoubleValue();
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
        try {
            double value = this.eval();
            super.setErrorState(false);
            return value;
        } catch (IllegalArgumentException e) {
            super.setErrorState(true);
            return Double.NaN;
        }
    }

    @Override
    public String fullCellText() {
        return "( " + formula + " )";
    }
}
