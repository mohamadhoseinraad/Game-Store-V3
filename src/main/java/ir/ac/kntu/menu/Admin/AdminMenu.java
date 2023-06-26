package ir.ac.kntu.menu.Admin;

import ir.ac.kntu.HelperClasses.FeedBacksHelper;
import ir.ac.kntu.menu.Admin.Accessory.AdminAccessoriesMenu;
import ir.ac.kntu.menu.Admin.Admins.AdminAdminsSearch;
import ir.ac.kntu.menu.Admin.Profile.AdminProfile;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.menu.Admin.Game.AdminGamesMenu;
import ir.ac.kntu.menu.Admin.User.AdminUserSearch;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.utils.TerminalColor;

public class AdminMenu extends Menu {

    private Store storeDB;

    private Admin admin;

    public AdminMenu(Store store, Admin admin) {
        this.storeDB = store;
        this.admin = admin;
        FeedBacksHelper.updateAllFeedBack(store);
    }

    @Override
    public void showMenu() {
        AdminMenuOption option;
        while ((option = printMenuOptions("Amin Menu", AdminMenuOption.class)) != AdminMenuOption.EXIT) {
            if (option != null) {
                switch (option) {
                    case PROFILE: {
                        profile();
                        break;
                    }
                    case USERS: {
                        users();
                        break;
                    }
                    case GAMES: {
                        games();
                        break;
                    }
                    case EDIT_ADMINS: {
                        editAdmins();
                        break;

                    }
                    case ACCESSORY: {
                        accessory();
                        break;
                    }
                    case LOGOUT: {
                        return;
                    }
                    default:
                        System.out.println("Invalid choose");
                }
            }
        }
        System.exit(0);
    }

    private void accessory() {
        if (admin.isSeller() || admin.isMastetAdmin()) {
            AdminAccessoriesMenu adminAccessoriesMenu = new AdminAccessoriesMenu(storeDB, admin);
            adminAccessoriesMenu.showMenu();
        } else {
            TerminalColor.red();
            System.out.println("You don't have permission!");
            TerminalColor.reset();
        }
    }

    private void editAdmins() {
        if (admin.isMastetAdmin()) {
            AdminAdminsSearch adminAdminsSearch = new AdminAdminsSearch(storeDB);
            adminAdminsSearch.showMenu();
        } else {
            TerminalColor.red();
            System.out.println("You don't have permission!");
            TerminalColor.reset();
        }
    }

    private void profile() {
        AdminProfile adminProfile = new AdminProfile(storeDB, admin);
        adminProfile.showMenu();

    }

    private void users() {
        if (admin.isUserManager() || admin.isMastetAdmin()) {
            AdminUserSearch adminUserSearch = new AdminUserSearch(storeDB);
            adminUserSearch.showMenu();
        } else {
            TerminalColor.red();
            System.out.println("You don't have permission!");
            TerminalColor.reset();
        }

    }

    private void games() {
        if (admin.isMastetAdmin() || admin.isDeveloper()) {
            AdminGamesMenu adminGamesMenu = new AdminGamesMenu(storeDB, admin);
            adminGamesMenu.showMenu();
        } else {
            TerminalColor.red();
            System.out.println("You don't have permission!");
            TerminalColor.reset();
        }
    }
}
