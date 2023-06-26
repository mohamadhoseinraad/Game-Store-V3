package ir.ac.kntu.menu.Admin.Admins;

import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.SearchEnum.UserFilterBy;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.HelperClasses.UserHelper;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.models.User;

import java.util.ArrayList;

public class AdminAdminsSearch {
    private Store storeDB;

    public AdminAdminsSearch(Store storeDB) {
        this.storeDB = storeDB;
    }


    private void usernameSearch() {
        System.out.println("Search Admin by username : ");
        String name = Scan.getLine().trim().toUpperCase();
        ArrayList<Admin> result = storeDB.adminsFilterBy(name, UserFilterBy.USERNAME);
        printUserSearchResult(result);
        if (result.size() != 0) {
            Admin selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            AdminEditAdminMenu adminEditAdminMenu = new AdminEditAdminMenu(storeDB, selectedUser);
            adminEditAdminMenu.showMenu();
        }
    }

    private User emailSearch() {
        System.out.println("Search User by e-mail : ");
        String email = Scan.getLine().trim().toLowerCase();
        ArrayList<Admin> result = storeDB.adminsFilterBy(email, UserFilterBy.EMAIL);
        printUserSearchResult(result);
        if (result.size() != 0) {
            Admin selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return null;
            }
            AdminEditAdminMenu adminEditAdminMenu = new AdminEditAdminMenu(storeDB, selectedUser);
            adminEditAdminMenu.showMenu();
        }
        return null;
    }

    private User phoneSearch() {
        System.out.println("Search User by phone number : ");
        String phoneNumber = Scan.getLine().trim().toLowerCase();
        ArrayList<Admin> result = storeDB.adminsFilterBy(phoneNumber, UserFilterBy.PHONE_NUMBER);
        printUserSearchResult(result);
        if (result.size() != 0) {
            Admin selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return null;
            }
            AdminEditAdminMenu adminEditAdminMenu = new AdminEditAdminMenu(storeDB, selectedUser);
            adminEditAdminMenu.showMenu();
        }
        return null;
    }


    private Admin handleSelect(ArrayList<Admin> searchResult) {
        System.out.println("---- chose number : ");
        String input = Scan.getLine();
        if (!input.matches("[0-9]+")) {
            TerminalColor.red();
            System.out.println("Chose valid number!");
            TerminalColor.reset();
        } else {
            int choose = Integer.parseInt(input) - 1;
            if (choose >= searchResult.size() || choose < 0) {
                TerminalColor.red();
                System.out.println("Chose valid number!");
                TerminalColor.reset();
            } else {
                Admin user = searchResult.get(choose);
                return user;
            }
        }
        return null;

    }

    private void allAdmin() {
        ArrayList<Admin> result = getAllAdmins();
        printUserSearchResult(result);
        if (result.size() != 0) {
            Admin selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            AdminEditAdminMenu adminEditAdminMenu = new AdminEditAdminMenu(storeDB, selectedUser);
            adminEditAdminMenu.showMenu();
        }
    }

    private ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> result = new ArrayList<>();
        for (Admin user : storeDB.getAdmins()) {
            result.add(user);
        }
        return result;
    }

    private void printUserSearchResult(ArrayList<Admin> result) {
        if (result.size() == 0) {
            System.out.println("Not found ! :(");
        } else {
            int i = 1;
            for (User user : result) {
                TerminalColor.blue();
                System.out.print(i);
                TerminalColor.yellow();
                System.out.print(" | ");
                TerminalColor.blue();
                System.out.println(user);
                TerminalColor.reset();
                i++;
            }
        }
    }

    public void showMenu() {
        AdminAdminsSearchOption option;
        while ((option = printMenuOptions("Search Admins",
                AdminAdminsSearchOption.class)) != AdminAdminsSearchOption.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL: {
                        allAdmin();
                        break;
                    }
                    case BY_USERNAME: {
                        usernameSearch();
                        break;
                    }
                    case BY_EMAIL: {
                        emailSearch();
                        break;
                    }
                    case BY_PHONE_NUMBER: {
                        phoneSearch();
                        break;
                    }
                    case ADD_ADMIN: {
                        UserHelper.makeAdmin(storeDB);
                        break;
                    }
                    case BACK: {
                        return;
                    }
                    default:
                        System.out.println("Invalid choose");
                }
            }
        }
        System.exit(0);
    }

    private <T extends Enum<T>> T getOption(Class<T> menuEnum) {
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

    private <T extends Enum<T>> T printMenuOptions(String title, Class<T> menuEnum) {
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

