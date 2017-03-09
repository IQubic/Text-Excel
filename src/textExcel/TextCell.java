package textExcel;

public class TextCell implements Cell {
    private String value;

    public TextCell(String value) {
        this.value = value;
    }

    @Override
    public String abbreviatedCellText() {
        if (this.value.length() < 10) {
            return pad(this.value);
        } else {
            return this.value.substring(0, 10);
        }
   }

    @Override
    public String fullCellText() {
        return "\"" + this.value + "\"";
    }

    // Adds spaces to the end of a string, so that the string has 10 chars
    private static String pad(String s) {
        for (int i = s.length(); i < 10; i++) {
            s += " ";
        }
        return s;
    }
}
