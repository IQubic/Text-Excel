package textExcel;

public class TextCell extends AbstractCell {
    public TextCell(String value) {
        super(value);
    }

    @Override
    public String fullCellText() {
        return "\"" + super.getValue() + "\"";
    }
}
