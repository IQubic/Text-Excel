package textExcel;

public class EmptyCell extends ACell {
    @Override
    public String fullCellText() {
        return "";
    }

    @Override
    public String abbreviatedCellText() {
        return "          ";
    }
}
