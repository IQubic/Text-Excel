package textExcel;

public class Spreadsheet implements Grid {
    private int rows;
    private int cols;
    private Cell[][] spreadsheet;

    public Spreadsheet() {
        this.rows = 20;
        this.cols = 12;
        this.spreadsheet = new Cell[rows][cols];
        this.clear();
    }

    @Override
    public String processCommand(String command) {
        // Cell Inspection
        if (command.matches("[A-Za-z][0-9][0-9]?")){
            Cell cell = this.getCell(new SpreadsheetLocation(command));
            return cell.fullCellText();
        // Assignment
        } else if (command.contains("=")) {
            // Seperate the cell and the contents
            String[] setCommand = command.split(" = ", 2);
            this.set(setCommand);
            return this.getGridText();
        // Clear all
        } else if (command.equalsIgnoreCase("clear")) {
            this.clear();
            return this.getGridText();
        // Clear a single cell
        } else if (command.toLowerCase().contains("clear")) {
            this.clear(new SpreadsheetLocation(command.substring(6)));
            return this.getGridText();
        // Something else was entered
        } else {
            return "";
        }
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
            this.set(loc, new FormulaCell(formula));
        // ValueCell
        } else {
            this.set(loc, new ValueCell(Double.parseDouble(value)));
        }
    }

    // loc now contains cell
    private void set(Location loc, Cell cell)  {
        this.spreadsheet[loc.getRow()][loc.getCol()] = cell;
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
