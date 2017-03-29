package textExcel;

public abstract class ACell implements Cell, Comparable<ACell> {
    @Override
    public abstract String fullCellText();

    @Override
    public abstract String abbreviatedCellText();

    public abstract int compareTo(ACell other);

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

}
