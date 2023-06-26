package ir.ac.kntu.menu.User.Store;

import ir.ac.kntu.DAOStore;
import ir.ac.kntu.menu.User.SearchProduct;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.accessories.GamePad;
import ir.ac.kntu.models.product.accessories.Monitor;
import ir.ac.kntu.models.product.games.Game;

public class UserStore extends Menu {

    private final Store storeDB;

    private final User currentUser;

    public UserStore(Store store, User user) {
        this.storeDB = store;
        this.currentUser = user;
    }

    @Override
    public void showMenu() {
        UserStoreOptions option;
        while ((option = printMenuOptions("Store", UserStoreOptions.class)) != UserStoreOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL: {
                        all();
                        break;
                    }
                    case GAMES: {
                        games();
                        break;
                    }
                    case GAME_PAD: {
                        gamePad();
                        break;
                    }
                    case MONITOR: {
                        monitor();
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

    public void all() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, false, Product.class);
        searchProduct.showMenu();
    }

    public void games() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, false, Game.class);
        searchProduct.showMenu();
    }

    public void gamePad() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, false, GamePad.class);
        searchProduct.showMenu();
    }

    public void monitor() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, false, Monitor.class);
        searchProduct.showMenu();
    }

}
