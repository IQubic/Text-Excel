package textExcel;

public abstract class RealCell extends ACell {
    // The exact value that will be returned by getDoubleValue
    private double doubleValue;
    private boolean hasError;

    public RealCell(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public abstract String fullCellText();

    @Override
    public String abbreviatedCellText() {
        return this.formatCellText(Double.toString(this.doubleValue));
    }

    public String abbreviatedCellText(String text) {
        if (hasError) {
            return super.formatCellText("#ERROR");
        } else {
            return super.formatCellText(text);
        }
    }

    // Sets the new value of this cell
    // Only for use with formulaCells
    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public void setError(boolean hasError) {
        this.hasError = hasError;
    }

    public boolean hasError() {
        return this.hasError;
    }
}
