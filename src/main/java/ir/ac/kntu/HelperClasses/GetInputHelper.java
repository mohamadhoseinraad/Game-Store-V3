package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

public class GetInputHelper {

    public static double inputChargeWallet() {
        System.out.println("how much you want amount ? (1+ $)");
        String amount = Scan.getLine();
        TerminalColor.red();
        int errorCount = 0;
        while (!amount.matches("[1-9][0-9.]+") || Double.parseDouble(amount) < 1) {
            errorCount++;
            if (errorCount > 2) {
                System.out.println("Charge wallet fail !");
                return 0;
            }
            System.out.println("Invalid amount ! Try again :");
            amount = Scan.getLine();

        }
        return Double.parseDouble(amount);
    }

    public static String inputPhoneNumber() {
        System.out.println("Enter your new phone number:");
        String newPhoneNumber = Scan.getLine().toUpperCase().trim();
        TerminalColor.red();
        if (!newPhoneNumber.matches("[0-9+]+")) {
            System.out.println("New phone number is not valid!");
            return null;
        }
        return newPhoneNumber;
    }

    public static String inputEmail() {
        System.out.println("Enter your new email:");
        String newEmail = Scan.getLine().toLowerCase().trim();
        TerminalColor.red();
        if (!newEmail.matches(".*@.*")) {
            System.out.println("New email is not valid!");
            TerminalColor.reset();
            return null;
        }
        return newEmail;
    }

    public static String inputUserName() {
        System.out.println("Enter your new username:");
        String newUsername = Scan.getLine().toUpperCase().trim();
        TerminalColor.red();
        if (newUsername.length() < 3) {
            System.out.println("Username must be 3  or more character!");
            TerminalColor.reset();
            return null;
        }
        return newUsername;
    }

    public static String[] inputPassword() {
        String[] inputs = new String[2];
        System.out.println("Enter old password:");
        inputs[1] = Scan.getLine().trim();
        System.out.println("Enter new password:");
        inputs[0] = Scan.getLine().trim();
        return inputs;
    }

    public static String inputConform() {
        System.out.println("Are you  sure(Yes or No) ? (Y/N)");
        String input;
        while (!(input = Scan.getLine().trim()).matches("Y|N")) {
            TerminalColor.red();
            System.out.println("Wrong chios!");
            TerminalColor.reset();
            System.out.println("Are you  sure ? (Y/N)");
        }
        return input;
    }
}
