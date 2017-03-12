package textExcel;

public abstract class RealCell extends ACell {
    // The value that will be returned by getDoubleValue
    double doubleValue;

    public RealCell(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    // Sets the new value of this cell
    // Only for use with formulaCells
    public void setDoubleValue(double Value) {
        this.value = value;
    }

    public double getDoubleValue() {
        return this.value;
    }
}
