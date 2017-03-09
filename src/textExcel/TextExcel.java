package textExcel;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextExcel {
    public static void main(String[] args) {
        // Initialization
        System.out.println("Welcome to TextExcel");
        Spreadsheet sheet = new Spreadsheet();

        Scanner in = new Scanner(System.in);

        // Main processing loop
        System.out.print("Enter a Command: ");
        String command = in.nextLine();
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println(sheet.processCommand(command));
            System.out.print("Enter a Command: ");
            command = in.nextLine();
        }

        // Goodby Message
        System.out.println("Thanks for using TextExcel");
    }
}
