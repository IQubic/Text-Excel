package textExcel;

import java.util.ArrayList;

public class FormulaCell extends RealCell {
    // formula should not contain parentheses
    private String formula;
    ArrayList<String> rpn;

    // Zero is just a dummy value that gets replaced
    // With the correct value after the call to the superclass costructor.
    public FormulaCell(String formula) {
        super(0);
        this.formula = formula;
        this.rpn = this.convertToRPN();
        super.setDoubleValue(this.eval());
    }

    private ArrayList<String> convertToRPN() {
        return new ArrayList<String>();
    }

    public double eval() {
        return 0;
    }

    @Override
    public String fullCellText() {
        return formula;
    }

    @Override
    public String abbreviatedCellText() {
        return super.formatCellText(Double.toString(super.getDoubleValue()));
    }
}
