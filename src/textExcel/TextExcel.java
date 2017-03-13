package textExcel;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextExcel {
    public static void main(String[] args) {
        // Initialization
        System.out.println("Welcome to TextExcel");
        Spreadsheet sheet = new Spreadsheet();

        System.out.print(sheet.getGridText());

        Scanner in = new Scanner(System.in);

        // Main processing loop
        String command = in.nextLine();
        while (!command.equalsIgnoreCase("quit")) {
            System.out.print(sheet.processCommand(command));
            command = in.nextLine();
        }

        // Goodby Message
        System.out.println("Thanks for using TextExcel");
    }
}
