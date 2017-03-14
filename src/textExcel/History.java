package textExcel;

import java.util.ArrayList;

// This class uses the singleton design pattern
public class History {
    private static History instance;
    // Items are added to the start of the list
    private ArrayList<String> history;
    private int size;
    private boolean tracking;

    private History() {
    }

    public static History getHistory() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    // Initializes the history
    public void init() {
        this.history = new ArrayList<>();
        this.tracking = false;
    }

    // Start tracking the history
    public void start(int size) {
        this.size = size;
        this.tracking = true;
    }

    // Adds an item to the array if we're currently tracking
    public void add(String command) {
        System.out.println(size);
        if (this.tracking) {
            this.history.add(0, command);

            // Remove the farthest back command if we have too many commands stored
            if (this.history.size() > size) {
                this.history.remove(this.history.size() - 1);
            }

        }
    }

    // Removes the last amt items from the history
    public void clear(int amt) {
        while (!history.isEmpty() && amt >= 1) {
            history.remove(0);
            amt--;
        }
    }

    // Stops tracking the history
    public void stop() {
        this.history.clear();
        this.tracking = false;
    }

    // Gets the contents of the history
    public String getContents() {
        StringBuilder histText = new StringBuilder();
        for (String histElement : this.history) {
            histText.append(histElement);
            histText.append("\n");
        }
        return histText.toString();
    }

}
