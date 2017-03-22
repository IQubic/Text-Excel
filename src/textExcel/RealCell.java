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
        return this.formatCellText(Double.toString(this.getDoubleValue()));
    }

    public String abbreviatedCellText(String text) {
        if (hasError) {
            return super.formatCellText("#ERROR");
        } else {
            return super.formatCellText(text);
        }
    }

    public double getDoubleValue() {
        if (hasError) {
            return Double.NaN;
        } else {
            return this.doubleValue;
        }
    }

    public void setError(boolean hasError) {
        this.hasError = hasError;
    }

    public boolean hasError() {
        return this.hasError;
    }
}
