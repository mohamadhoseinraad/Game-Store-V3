package ir.ac.kntu.menu.Admin.Profile;

import ir.ac.kntu.HelperClasses.GetInputHelper;
import ir.ac.kntu.HelperClasses.UserHelper;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.User;

public class AdminProfile extends Menu {
    private final Store storeDB;

    private final Admin user;

    public AdminProfile(Store storeDB, Admin user) {
        this.storeDB = storeDB;
        this.user = user;
    }

    @Override
    public void showMenu() {
        AdminProfileMenuOptions option;
        while (userProfile() && (option = printMenuOptions("Profile",
                AdminProfileMenuOptions.class)) != AdminProfileMenuOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case CHANGE_USERNAME: {
                        changeUsername();
                        break;
                    }
                    case CHANGE_PHONE_NUMBER: {
                        changePhoneNumber();
                        break;
                    }
                    case CHANGE_EMAIL: {
                        changeEmail();
                        break;
                    }
                    case CHANGE_PASSWORD: {
                        changePassword();
                        break;
                    }
                    case BACK:
                        return;
                    default:
                        System.out.println("Invalid choose");
                }
            }
        }
        System.exit(0);
    }

    private boolean userProfile() {
        UserHelper.showAdminProfile(user);
        return true;
    }

    private void changeUsername() {
        String newUsername = GetInputHelper.inputUserName();
        if (newUsername == null) {
            return;
        }
        User temp = storeDB.findUserByUsername(newUsername);
        if (temp != null) {
            System.out.println("this username is already taken!");
            TerminalColor.reset();
            return;
        }
        user.setUsername(newUsername);
        TerminalColor.green();
        System.out.println("Username changed :D");
        TerminalColor.reset();
    }

    private void changeEmail() {
        String newEmail = GetInputHelper.inputEmail();
        if (newEmail != null) {
            user.setEmail(newEmail);
            TerminalColor.green();
            System.out.println("email changed :D");
            TerminalColor.reset();
        }
    }

    private void changePhoneNumber() {
        String newPhoneNumber = GetInputHelper.inputPhoneNumber();
        if (newPhoneNumber != null) {
            user.setPhoneNumber(newPhoneNumber);
            TerminalColor.green();
            System.out.println("phone number changed :D");
            TerminalColor.reset();
        }
    }

    private void changePassword() {
        int error = 0;
        while (error < 3) {
            String[] inputs = GetInputHelper.inputPassword();
            if (inputs[0].length() < 8) {
                TerminalColor.red();
                System.out.println("New password must be 8 or more character");
                TerminalColor.reset();
            } else if (user.setNewPassword(inputs[0], inputs[1])) {
                TerminalColor.green();
                System.out.println("Password successfully !");
                TerminalColor.reset();
                return;
            } else {
                TerminalColor.red();
                System.out.println("Old password is not correct!");
                TerminalColor.reset();
            }
            TerminalColor.red();
            System.out.println("Fail change password !");
            TerminalColor.reset();
            error++;
        }
    }
}
