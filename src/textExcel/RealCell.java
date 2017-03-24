package textExcel;

public abstract class RealCell extends ACell {
    private double doubleValue;
    private boolean hasError;

    public RealCell(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public abstract String fullCellText();

    @Override
    public String abbreviatedCellText() {
        // hasError is set properly during the getDoubleValue method call
        double value = this.getDoubleValue();

        // Has an error
        if (this.hasError) {
            return super.formatCellText("#ERROR");
        }

        // No error
        return super.formatCellText(Double.toString(value));
    }

    public String abbreviatedCellText(String text) {
        if (hasError) {
            return super.formatCellText("#ERROR");
        } else {
            return super.formatCellText(text);
        }
    }

    public double getDoubleValue() {
            return this.doubleValue;
    }

    public void setErrorState(boolean hasError) {
        this.hasError = hasError;
    }

    public boolean hasError() {
        return this.hasError;
    }
}
