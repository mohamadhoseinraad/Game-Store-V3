package ir.ac.kntu.menu.User.Library;

import ir.ac.kntu.DAOStore;
import ir.ac.kntu.HelperClasses.StoreHelperClass;
import ir.ac.kntu.menu.ExportUserProduct;
import ir.ac.kntu.menu.User.SearchProduct;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.product.accessories.GamePad;
import ir.ac.kntu.models.product.accessories.Monitor;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.Product;

import java.util.ArrayList;
import java.util.Map;

public class UserLibrary extends Menu {

    private final Store storeDB;

    private final User currentUser;

    private final ArrayList<Product> userLibrary;

    public UserLibrary(Store store, User user) {
        this.storeDB = store;
        this.currentUser = user;
        userLibrary = StoreHelperClass.getAllLibrary(storeDB, currentUser);
    }

    @Override
    public void showMenu() {
        UseLibraryOptions option;
        while ((option = printMenuOptions("Library", UseLibraryOptions.class)) != UseLibraryOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL: {
                        allLibrary();
                        DAOStore.write(storeDB);
                        break;
                    }
                    case GAMES: {
                        games();
                        DAOStore.write(storeDB);
                        break;
                    }
                    case EXPORT_LIBRARY_TO_HTML: {
                        exportHtml();
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

    private void monitor() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, true, Monitor.class);
        searchProduct.showMenu();
    }

    private void gamePad() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, true, GamePad.class);
        searchProduct.showMenu();
    }

    private void games() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, true, Game.class);
        searchProduct.showMenu();
    }

    private void allLibrary() {
        SearchProduct searchProduct = new SearchProduct(currentUser, storeDB, true, Product.class);
        searchProduct.showMenu();
    }

    private void exportHtml() {
        ExportUserProduct exportUserProduct = new ExportUserProduct(getAllGames());
        exportUserProduct.showMenu();
    }

    private ArrayList<Product> getAllGames() {
        ArrayList<Product> result = new ArrayList<>();
        for (Map.Entry<String, String> gameName : currentUser.getLibrary().entrySet()) {
            Product game = storeDB.findProduct(gameName.getKey());
            result.add(game);
        }
        return result;
    }

}
