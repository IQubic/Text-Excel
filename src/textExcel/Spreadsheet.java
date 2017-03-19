package textExcel;

import java.util.*;

public class Spreadsheet implements Grid {
    private int rows;
    private int cols;
    private Cell[][] spreadsheet;

    public Spreadsheet() {
        this.rows = 20;
        this.cols = 12;
        this.spreadsheet = new Cell[rows][cols];
        History.getHistory().init();
        this.clear();
    }

    @Override
    public String processCommand(String command) {
        // Variables for logging history
        String output;
        String originalCommand = command;
        boolean logCommand = true;
        // Cell Inspection
        if (command.matches("[A-Za-z][0-9][0-9]?")){
            Cell cell = this.getCell(new SpreadsheetLocation(command));
            output = cell.fullCellText();
        // Assignment
        } else if (command.contains("=")) {
            // Seperate the cell and the contents
            String[] setCommand = command.split(" = ", 2);
            this.set(setCommand);
            output = this.getGridText();
         } else if (command.toLowerCase().contains("history")) {
            // Don't log this command
            logCommand = false;

            // Grab the arguments to the history command
            output = this.processHistoryCommand(command.split(" ", 2)[1].split(" "));
       // Clear all
        } else if (command.toLowerCase().equals("clear")) {
            this.clear();
            output = this.getGridText();
        // Clear a single cell
        } else if (command.toLowerCase().contains("clear")) {
            this.clear(new SpreadsheetLocation(command.substring(6)));
            output = this.getGridText();
        } else {
            output = "";
        }

        if (logCommand) {
            History.getHistory().add(originalCommand);
        }
        return output;
    }

    // Sets the value of a cell
    // Also creates a new cell object of the correct type
    // command[0] = location
    // command[1] = value
    private void set(String[] command) {
        Location loc = new SpreadsheetLocation(command[0]);
        String value = command[1];
        // TextCell
        if (value.contains("\"")) {
            // Substring extracts the String's text
            String contents = value.substring(1, value.length() - 1);
            this.set(loc, new TextCell(contents));
        // PercentCell
        } else if (value.contains("%")) {
            double percentValue = Double.parseDouble(value.substring(0, value.length() - 1));
            this.set(loc, new PercentCell(percentValue));
        // FormulaCell
        } else if (value.contains("(")) {
            String formula = value.substring(2, value.length() - 2);
            this.set(loc, Spreadsheet.createFormulaCell(formula));
        // ValueCell
        } else {
            this.set(loc, new ValueCell(Double.parseDouble(value)));
        }
    }

    // loc now contains cell
    private void set(Location loc, Cell cell)  {
        this.spreadsheet[loc.getRow()][loc.getCol()] = cell;
    }

    // Converts an equation string into rpn
    // rpn conversion is done with Edsger Dijkstra's Shunting Yard Algorithm
    // Also creates a dependecy map
    // and populates it with the current values.
    // Finally it creates a formula cell
    private static FormulaCell createFormulaCell(String formula) {
        // START OF SHUNTING YARD ALGORITHM
        // Initialization for Shunting Yard Algorithm
        String[] tokens = formula.split("\\s+");

        // Higher value means higher precedence
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        // A stack to use for the rpn conversion
        Deque<String> opStack = new ArrayDeque<>();
        // Final rpn output
        List<String> rpn = new ArrayList<>(tokens.length);
        // Dependency HashMap
        // Will conatain the intial values of referenced cells
        // TODO replace SOMETHING with an actual type
        // Map<SOMETHING, Integer> dependecies = new HashMap<>();

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
        // END OF SHUNTING YARD ALGORITHM
        return new FormulaCell(formula, rpn);
    }

    // Sets every cell in the spreadsheet to an EmptyCell
    private void clear() {
        for (int row = 0; row < spreadsheet.length; row++) {
            for (int col = 0; col < spreadsheet[row].length; col++) {
                spreadsheet[row][col] = new EmptyCell();
            }
        }
    }

    // Sets just one cell to an EmptyCell
    private void clear(Location loc) {
        spreadsheet[loc.getRow()][loc.getCol()] = new EmptyCell();
    }

    // Processes a history command and sends it to the history object
    public String processHistoryCommand(String[] command) {
        // Default output
        String output = "";
        // Current History
        History hist = History.getHistory();

        // Process the command
        if (command[0].equalsIgnoreCase("stop")) {
            hist.stop();
        } else if (command[0].equalsIgnoreCase("display")) {
            output = hist.getContents();
        } else if (command[0].toLowerCase().equals("start")) {
            hist.start(Integer.parseInt(command[1]));
        } else if (command[0].toLowerCase().equals("clear")) {
            hist.clear(Integer.parseInt(command[1]));
        }
        return output;
    }

    @Override
    // Using a StringBuilder here saves memory
    public String getGridText() {
        StringBuilder grid = new StringBuilder();

        // Header
        grid.append("   |");
        for (int i = 0; i < this.cols; i++) {
            String colLetter = Character.toString((char) ('A' + i));
            grid.append(pad(colLetter, 10) + "|");
        }

        // Print the rows
        for (int rows = 0; rows < this.rows; rows++) {
            // Get the row number properly spaced
            grid.append("\n" + pad(Integer.toString(rows + 1), 3) + "|");
            // Append the row content
            for (int cols = 0; cols < this.cols; cols++) {
                Cell curCell = this.getCell(new SpreadsheetLocation(rows, cols));
                grid.append(curCell.abbreviatedCellText() + "|");
            }
        }
        grid.append("\n");

        return grid.toString();
    }

    // Adds spaces to the end of a string, so that the string has length chars
    private static String pad(String s, int length) {
        for (int i = s.length(); i < length; i++) {
            s += " ";
        }
        return s;
    }

    @Override
    public Cell getCell(Location loc) {
        return spreadsheet[loc.getRow()][loc.getCol()];
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getCols() {
        return this.cols;
    }
}
