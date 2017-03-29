package textExcel;

import java.util.Deque;

public abstract class RealCell extends ACell {

    @Override
    public abstract String fullCellText();

    @Override
    public String abbreviatedCellText() {
        try {
            return super.formatCellText(Double.toString(this.getDoubleValue()));
        } catch (IllegalArgumentException e) {
            return super.formatCellText("#ERROR");
        }
    }

    public abstract double getDoubleValue();

    public abstract double getDoubleValue(Deque<RealCell> callStack);

    //TODO Implement RealCell compared to RealCell
    @Override
    public int compareTo(ACell other) {
        if (other instanceof RealCell) {
        }
        return -1;
    }

}
