package textExcel;

public abstract class RealCell extends ACell {
    // The exact value that will be returned by getDoubleValue
    private double doubleValue;

    public RealCell(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    // Sets the new value of this cell
    // Only for use with formulaCells
    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }
}
