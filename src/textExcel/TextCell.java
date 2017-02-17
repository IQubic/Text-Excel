package textExcel;

public class TextCell implements Cell {
    private String value;

    public TextCell(String value) {
        this.value = value;
    }

    @Override
    public String abbreviatedCellText() {
        if (this.value.length() < 10) {
            return this.value;
        } else {
            return this.value.substring(0, 10);
        }
   }

    @Override
    public String fullCellText() {
        return "\"" + this.value + "\"";
    }
}
