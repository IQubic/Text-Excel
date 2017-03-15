package textExcel;

public class ValueCell extends RealCell {
    public ValueCell(double doubleValue) {
        super(doubleValue);
    }

    @Override
    public String fullCellText() {
        return Double.toString(super.getDoubleValue());
    }
}
