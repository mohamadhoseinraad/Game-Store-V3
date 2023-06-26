package ir.ac.kntu.menu.Auth;

import ir.ac.kntu.models.Admin;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.menu.Admin.AdminMenu;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.User.UserMenu;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.UserType;

public class AuthMenu extends Menu {

    private Store storeDB;

    public AuthMenu(Store store) {
        this.storeDB = store;
    }

    @Override
    public void showMenu() {
        AuthMenuOptions option;
        while ((option = printMenuOptions("Game Store", AuthMenuOptions.class)) != AuthMenuOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case LOGIN: {
                        loginUser();
                        break;
                    }
                    case SING_UP: {
                        singUp();
                        break;
                    }
                    default:
                        System.out.println("Invalid choose");
                }
            }
        }
    }

    private void loginUser() {
        TerminalColor.blue();
        System.out.println("---- Log in ----");
        TerminalColor.reset();
        System.out.println("User name:");
        String username = Scan.getLine().toUpperCase().trim();
        System.out.println("Password:");
        String password = Scan.getLine().trim();
        if (storeDB.loginUser(username, password)) {
            User user = storeDB.findUserByUsername(username);
            if (user == null){
                TerminalColor.red();
                System.out.println("Username or password incorrect!");
                TerminalColor.reset();
                return;
            }
            if (user.userType == UserType.USER) {
                UserMenu userMenu = new UserMenu(storeDB, user);
                userMenu.showMenu();
            } else {
                AdminMenu adminMenu = new AdminMenu(storeDB, (Admin) user);
                adminMenu.showMenu();
            }
        } else {
            TerminalColor.red();
            System.out.println("Username or password incorrect!");
            TerminalColor.reset();
        }
    }

    private void singUp() {
        TerminalColor.blue();
        System.out.println("---- Log in ----");
        TerminalColor.reset();
        System.out.println("User name:");
        String username = Scan.getLine().trim().toUpperCase();
        System.out.println("Phone number:");
        String phoneNumber = Scan.getLine().trim();
        System.out.println("Email:");
        String email = Scan.getLine().toLowerCase().trim();
        System.out.println("Password:");
        String password = Scan.getLine().trim();
        TerminalColor.red();
        if (username.length() < 3) {
            System.out.println("Enter valid username ( must be 3 or more character)");
            TerminalColor.reset();
            return;
        }
        if (storeDB.findUserByUsername(username) != null) {
            System.out.println("This username already taken!");
        } else if (!email.matches(".+@.+")) {
            System.out.println("Email is not valid!");
        } else if (!phoneNumber.matches("[0-9+]+")) {
            System.out.println("phone number is not valid!");
            return;
        } else if (password.length() < 8) {
            System.out.println("Password length must 8 or more!");
        } else {
            boolean result = storeDB.addUser(new User(username, phoneNumber, email, password, UserType.USER));
            if (!result) {
                System.out.println("Sing up unsuccessfully!");
            } else {
                TerminalColor.green();
                System.out.println("Sing up successfully.Now you can log in");
            }
        }
        TerminalColor.reset();
    }
}
