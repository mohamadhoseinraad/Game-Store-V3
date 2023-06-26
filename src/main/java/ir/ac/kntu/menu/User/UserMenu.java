package ir.ac.kntu.menu.User;

import ir.ac.kntu.DAOStore;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.User.Friend.UserFriendMenu;
import ir.ac.kntu.menu.User.Library.UserLibrary;
import ir.ac.kntu.menu.User.Profile.ProfileMenu;
import ir.ac.kntu.menu.User.Store.UserStore;
import ir.ac.kntu.models.User;

public class UserMenu extends Menu {

    private final Store storeDB;

    private final User currentUser;

    public UserMenu(Store store, User currentUser) {
        this.storeDB = store;
        this.currentUser = currentUser;
    }

    @Override
    public void showMenu() {
        UserMenuOption option;
        TerminalColor.purple();
        System.out.println("Welcome " + currentUser.getUsername());
        TerminalColor.reset();
        while ((option = printMenuOptions("User Menu", UserMenuOption.class)) != UserMenuOption.EXIT) {
            if (option != null) {
                switch (option) {
                    case PROFILE: {
                        profile();
                        break;
                    }
                    case STORE: {
                        store();
                        break;
                    }
                    case LIBRARY: {
                        library();
                        break;
                    }
                    case FRIENDS: {
                        friends();
                        break;
                    }
                    case COUNT_PRICE: {
                        countPrice();
                        break;
                    }
                    case LOGOUT:
                        currentUser.isLogout();
                        System.out.println("Back soon :)");
                        DAOStore.write(storeDB);
                        return;
                    default:
                        System.out.println("Invalid choose");
                }
            }
            DAOStore.write(storeDB);
        }
        DAOStore.write(storeDB);
        System.exit(0);
    }

    private void countPrice() {
        UserCountPriceMenu userCountPriceMenu = new UserCountPriceMenu(storeDB, currentUser);
        userCountPriceMenu.showMenu();
    }

    public void profile() {
        ProfileMenu profileMenu = new ProfileMenu(storeDB, currentUser);
        profileMenu.showMenu();
    }

    public void store() {
        UserStore userStore = new UserStore(storeDB, currentUser);
        userStore.showMenu();
    }

    public void library() {
        UserLibrary userLibrary = new UserLibrary(storeDB, currentUser);
        userLibrary.showMenu();
    }

    public void friends() {
        UserFriendMenu userFriendMenu = new UserFriendMenu(storeDB, currentUser);
        userFriendMenu.showMenu();
    }
}
