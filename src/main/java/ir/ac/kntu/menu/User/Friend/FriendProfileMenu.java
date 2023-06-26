package ir.ac.kntu.menu.User.Friend;

import ir.ac.kntu.DAOStore;
import ir.ac.kntu.HelperClasses.GetInputHelper;
import ir.ac.kntu.HelperClasses.StoreHelperClass;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.Product;

public class FriendProfileMenu extends Menu {

    private final User friend;

    private final User currentUser;

    private final Store storeDB;

    public FriendProfileMenu(User friend, User currentUser, Store storeDB) {
        this.friend = friend;
        this.currentUser = currentUser;
        this.storeDB = storeDB;
    }

    @Override
    public void showMenu() {
        FriendProfileOptions option;
        while ((option = printMenuOptions(friend.getUsername(), FriendProfileOptions.class)) != FriendProfileOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case REMOVE_FRIEND: {
                        if (removeFriend()) {
                            DAOStore.write(storeDB);
                            return;
                        }
                        break;
                    }
                    case SHOW_USER_PRODUCTS: {
                        showFriendProducts();
                        DAOStore.write(storeDB);
                        break;
                    }
                    case BACK: {
                        DAOStore.write(storeDB);
                        return;
                    }
                    default:
                        System.out.println("Invalid choose");
                }
            }
            DAOStore.write(storeDB);
        }
        DAOStore.write(storeDB);
        System.exit(0);
    }

    private void showFriendProducts() {
        if (friend.getLibrary().size() == 0) {
            System.out.println("Your friend doesn't have any Product!");
            return;
        }
        int i = 1;
        for (Product product : StoreHelperClass.getAllLibrary(storeDB, friend)) {
            System.out.print(i);
            TerminalColor.yellow();
            System.out.print(" | ");
            TerminalColor.reset();
            System.out.println(product);
            i++;
        }
    }

    private boolean removeFriend() {
        if (GetInputHelper.inputConform().equals("Y")) {
            currentUser.removeFriend(friend);
            friend.removeFriend(currentUser);
            return true;
        }
        return false;
    }
}
