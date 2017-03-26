package textExcel;

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
}
