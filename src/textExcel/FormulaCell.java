package textExcel;

import java.util.*;

public class FormulaCell extends RealCell {
    private String formula;
    private List<String> rpn;
    private Spreadsheet sheetRef;

    // super.doubleValue is never used, as getDoubleValue is overridden here
    public FormulaCell(String formula, Spreadsheet sheetRef) {
        // This field is never used in a formula cell
        super(Double.NaN);
        this.formula = formula.toUpperCase();
        // Use the uppercase form
        this.rpn = convertToRPN(this.formula);
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
        precedence.put("SUM", 3);
        precedence.put("AVG", 3);
        // A stack to use for the rpn conversion
        Deque<String> opStack = new ArrayDeque<>();

        // Start of Shunting Yard Algorithm
        for (String curToken : tokens) {
            // Token is either a number, a cell reference or a range
            if (!precedence.keySet().contains(curToken)) {
                // Token is a range
                if (curToken.contains("-")) {
                    String[] corners = curToken.split("-");
                    rpn.add(corners[0]);
                    rpn.add(corners[1]);

                // Token is number or a cell reference
                } else {
                    // TODO add depdency finding here
                    rpn.add(curToken);
                }

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
        Deque<String> evalStack = new ArrayDeque<>();

        for (String token : this.rpn) {
            // Next token is a number or cell reference
            if (!isOperator(token)) {
                evalStack.push(token);

            // Token is a operation
            } else {
                if (token.equals("SUM") || token.equals("AVG")) {
                    Location ULCorner = new SpreadsheetLocation(evalStack.pop());
                    Location DRCorner = new SpreadsheetLocation(evalStack.pop());
                    evalStack.push(Double.toString(this.methodFormula(ULCorner, DRCorner, token)));
                }
                double num2 = this.tokenToDouble(evalStack.pop());
                double num1 = this.tokenToDouble(evalStack.pop());
                evalStack.push(Double.toString(this.eval(num1, num2, token)));
            }
        }

        return Double.parseDouble(evalStack.pop());
    }

    // Evaluates a two operand math expression
    private double eval(double num1, double num2, String op) throws IllegalArgumentException {
        double result = 0;
        switch (op) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                // Check for divide by zero
                if (num2 == 0) {
                    throw new IllegalArgumentException();
                }
                // Do the division
                result = num1 / num2;
                break;
        }
        return result;
    }

    // Evalutes a method forumula
    private double methodFormula(Location ULCorner, Location DRCorner, String op) throws IllegalArgumentException {
        double result = 0;
        switch (op) {
            case "SUM":
                result = this.sum(ULCorner, DRCorner);
                break;
            case "AVG":
                result = this.avg(ULCorner, DRCorner);
                break;
        }
        return result;
    }

    private double sum(Location ULCorner, Location DRCorner) throws IllegalArgumentException {

    }

    private double avg(Location ULCorner, Location DRCorner) throws IllegalArgumentException {

    }

    // Converts a token like "5", "13", "A1", or "b12" to the correct double value
    // TODO Add circular dependency support
    private double tokenToDouble(String token) throws IllegalArgumentException {
        // Token is a number
        if (!token.matches("[A-Za-z][0-9][0-9]?")){
            return Double.parseDouble(token);
        }

        // Token is a cell reference
        return this.locToDouble(new SpreadsheetLocation(token));
   }

    // Gets the value of the cell at loc
    private double locToDouble(Location loc) throws IllegalArgumentException {
        Cell curCell = sheetRef.getCell(loc);

        // Formula refers to something that is not a RealCell
        if (!(curCell instanceof RealCell)) {
            throw new IllegalArgumentException();
        }

        // curCell is a RealCell
        RealCell curRealCell = (RealCell) curCell;
        double value = curRealCell.getDoubleValue();

        // Make sure curCell doesn't have an error
        if (curRealCell.hasError()) {
            throw new IllegalArgumentException();
        }

        return value;
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
        } else if (token.equals("SUM")) {
            isOperator = true;
        } else if (token.equals("AVG")) {
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
