package textExcel;

public abstract class RealCell extends ACell {
    // The value that will be returned by getDoubleValue
    double value;

    public RealCell(double value) {
        this.value = value;
    }

    // Sets the new value of this cell
    public void setDoubleValue(double Value) {
        this.value = value;
    }

    public double getDoubleValue() {
        return this.value;
    }
}
