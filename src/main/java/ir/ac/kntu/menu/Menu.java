package ir.ac.kntu.menu;

import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

public abstract class Menu {

    public abstract void showMenu();

    public <T extends Enum<T>> T getOption(Class<T> menuEnum) {
        T[] options = menuEnum.getEnumConstants();
        String choiceStr = Scan.getLine().trim();
        int choice = -1;
        if (choiceStr.matches("[0-9]+")) {
            choice = Integer.parseInt(choiceStr) - 1;
        }

        if (choice >= 0 && choice < options.length) {
            return options[choice];
        }
        TerminalColor.red();
        System.out.println("Wrong choice !");
        TerminalColor.reset();
        return null;
    }

    public <T extends Enum<T>> T printMenuOptions(String title, Class<T> menuEnum) {
        TerminalColor.cyan();
        System.out.println("----------" + title + "----------");
        TerminalColor.reset();
        T[] options = menuEnum.getEnumConstants();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        System.out.print("Enter your choice : ");
        return getOption(menuEnum);
    }
}
