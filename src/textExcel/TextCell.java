package textExcel;

public class TextCell extends ACell {
    private String text;

    public TextCell(String text) {
        this.text = text;
    }

    @Override
    public String fullCellText() {
        return "\"" + this.text + "\"";
    }

    public String abbreviatedCellText() {
        return super.formatCellText(this.text);
    }
}
