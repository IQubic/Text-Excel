package textExcel;

public class EmptyCell extends AbstractCell {
    public EmptyCell() {
        super("");
    }

    @Override
    public String fullCellText() {
        return "";
    }
}
