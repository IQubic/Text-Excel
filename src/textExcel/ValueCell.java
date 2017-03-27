package textExcel;

import java.util.List;

public class ValueCell extends RealCell {
    private double doubleValue;

    public ValueCell(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public String fullCellText() {
        return Double.toString(this.doubleValue);
    }

    @Override
    public double getDoubleValue() {
        return this.doubleValue;
    }

    @Override
    public double getDoubleValue(List<RealCell> callStack) {
        return this.doubleValue;
    }
}
