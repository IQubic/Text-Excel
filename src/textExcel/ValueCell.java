package textExcel;

public class ValueCell extends RealCell {
    public ValueCell(double value) {
        super(value);
    }

    @Override
    public String fullCellText() {
        return Double.toString(this.value);
    }

    @Override
    public String abbreviatedCellText() {
        return super.formatCellText(Double.toString(this.value));
    }
}
