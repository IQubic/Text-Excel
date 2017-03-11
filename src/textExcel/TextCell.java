package textExcel;

public class TextCell extends ACell {
    public TextCell(String value) {
        super(value);
    }

    @Override
    public String fullCellText() {
        return "\"" + super.getText() + "\"";
    }
}
