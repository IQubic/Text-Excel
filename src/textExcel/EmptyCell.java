package textExcel;

public class EmptyCell extends ACell {
    public EmptyCell() {
        super("");
    }

    @Override
    public String fullCellText() {
        return "";
    }
}
