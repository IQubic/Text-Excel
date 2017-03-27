package textExcel;

import java.util.List;

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

    public abstract double getDoubleValue(List<RealCell> callStack);
}
