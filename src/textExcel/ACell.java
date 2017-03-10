package textExcel;

public abstract class ACell implements Cell {
    // Text is the exact text that will be printed to the screen
    private String text;
    private Location loc;

    public ACell(String value) {
        this(value, null);
    }

    public ACell(String text, Location loc) {
        this.text = text;
        this.loc = loc;
    }

    @Override
    public abstract String fullCellText();

    @Override
    public String abbreviatedCellText() {
        return formatCellText(this.text);
    }

    public static String formatCellText(String s) {
        if (s.length() < 10) {
            for (int i = s.length(); i < 10; i++) {
                s += " ";
            }
            return s;
        } else {
            return s.substring(0, 10);
        }
    }

    public String getText() {
        return this.text;
    }

    public Location getLoc() {
        return this.loc;
    }

}
