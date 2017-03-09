package textExcel;

public abstract class AbstractCell implements Cell {
    private String value;
    private Location loc;

    public AbstractCell(String value) {
        this(value, null);
    }

    public AbstractCell(String value, Location loc) {
        this.value = value;
        this.loc = loc;
    }

    @Override
    public abstract String fullCellText();

    @Override
    public String abbreviatedCellText() {
        if (this.value.length() < 10) {
            return pad(this.value);
        } else {
            return this.value.substring(0, 10);
        }
    }

    private static String pad(String s) {
        for (int i = s.length(); i < 10; i++) {
            s += " ";
        }
        return s;
    }

    public String getValue() {
        return this.value;
    }

    public Location getLoc() {
        return this.loc;
    }

}
