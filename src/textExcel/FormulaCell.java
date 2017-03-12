package textExcel;

public class FormulaCell extends RealCell {
    // formula should not contain parentheses
    private String formula ;

    public FormulaCell(String formula) {
        this.formula = formula;
        super.setDoubleValue(0);
    }

    @Override
    public String fullCellText() {
        return "( " + formula + " )";
    }

    @Override
    public String abbreviatedCellText() {
        return super.formatCellText(this.doubleValue);
    }
}
