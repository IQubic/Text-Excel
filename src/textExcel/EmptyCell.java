package textExcel;

public class EmptyCell extends ACell {
    @Override
    public String fullCellText() {
        return "";
    }

    @Override
    public String abbreviatedCellText() {
        return super.formatCellText("");
    }

    @Override
    public int compareTo(ACell other) {
        if (other instanceof EmptyCell) {
            return 0;
        }
        return -1;
    }
}
