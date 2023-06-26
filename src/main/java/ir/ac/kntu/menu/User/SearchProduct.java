package ir.ac.kntu.menu.User;

import ir.ac.kntu.HelperClasses.SelectItemHelper;
import ir.ac.kntu.HelperClasses.StoreHelperClass;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.User.Library.AccessoryLibraryMenu;
import ir.ac.kntu.menu.User.Library.GameLibraryMenu;
import ir.ac.kntu.menu.User.Store.ProductStoreMenu;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.ProductType;
import ir.ac.kntu.models.product.accessories.Accessory;
import ir.ac.kntu.models.product.accessories.AccessoryType;
import ir.ac.kntu.models.product.accessories.GamePad;
import ir.ac.kntu.models.product.accessories.Monitor;
import ir.ac.kntu.models.product.games.Game;

import java.util.ArrayList;
import java.util.Collections;

public class SearchProduct extends Menu {

    private final User currentUser;

    private final Store storeDB;

    private final ProductType productType;

    private final AccessoryType accessoryType;

    private final ArrayList<Product> products;

    private final boolean isLibrary;


    public <T extends Product> SearchProduct(User currentUser, Store storeDB, boolean isLibrary, Class<T> product) {
        this.currentUser = currentUser;
        this.storeDB = storeDB;
        this.isLibrary = isLibrary;
        if (product == GamePad.class) {
            accessoryType = AccessoryType.GAME_PAD;
            productType = ProductType.ACCESSORIES;
        } else if (product == Monitor.class) {
            accessoryType = AccessoryType.MONITOR;
            productType = ProductType.ACCESSORIES;
        } else if (product == Game.class) {
            accessoryType = null;
            productType = ProductType.GAME;
        } else {
            accessoryType = null;
            productType = null;
        }
        products = createList();
    }

    @Override
    public void showMenu() {
        SearchProductOptions option;
        String title = " Search in ";
        if (isLibrary) {
            title += "Library ";
        }
        if (productType != null) {
            title += productType;
        }
        if (accessoryType != null) {
            title += " " + accessoryType;
        }
        while ((option = printMenuOptions(title, SearchProductOptions.class)) != SearchProductOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL: {
                        all();
                        break;
                    }
                    case TOP:{
                        top();
                        break;
                    }
                    case BY_NAME: {
                        searchByName();
                        break;
                    }
                    case BY_PRICE: {
                        searchBPrice();
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

    public void all() {
        Product selectedProduct = SelectItemHelper.handleSelect(products);
        if (selectedProduct == null) {
            return;
        }
        nextMenu(selectedProduct);
    }

    public void top() {
        ArrayList<Product> sortedProducts =new ArrayList<>(products);
        Collections.sort(sortedProducts,Collections.reverseOrder());
        Product selectedProduct = SelectItemHelper.handleSelect(sortedProducts);
        if (selectedProduct == null) {
            return;
        }
        nextMenu(selectedProduct);
    }

    public void searchByName() {
        Product selectedProduct = SelectItemHelper.searchInCostumeProtectByName(products);
        if (selectedProduct == null) {
            return;
        }
        nextMenu(selectedProduct);

    }

    public void searchBPrice() {
        Product selectedProduct = SelectItemHelper.searchProtectByPriceInCostumeList(storeDB, products);
        if (selectedProduct == null) {
            return;
        }
        nextMenu(selectedProduct);
    }

    private ArrayList<Product> createList() {
        ArrayList<Product> result = new ArrayList<>();
        if (accessoryType == null && productType == null && !isLibrary) {
            result = SelectItemHelper.getAllProducts(storeDB.getProducts());
        } else {
            ArrayList<Product> main = new ArrayList<>();
            if (isLibrary) {
                main = StoreHelperClass.getAllLibrary(storeDB, currentUser);
            } else {
                main = SelectItemHelper.getAllProducts(storeDB.getProducts());
            }
            for (Product product : main) {
                if (accessoryType == null) {
                    if (product.getClass() == Game.class) {
                        result.add(product);
                    }
                } else {
                    if (accessoryType == AccessoryType.GAME_PAD) {
                        if (product.getClass() == GamePad.class) {
                            result.add(product);
                        }
                    } else {
                        if (product.getClass() == Monitor.class) {
                            result.add(product);
                        }
                    }
                }
            }
        }

        return result;
    }

    private void nextMenu(Product selectedProduct) {
        if (isLibrary) {
            startProductMenu(selectedProduct);
        } else {
            ProductStoreMenu productStoreMenu = new ProductStoreMenu(currentUser, selectedProduct, storeDB);
            productStoreMenu.showMenu();
        }
    }

    private void startProductMenu(Product selectedProduct) {
        if (selectedProduct == null) {
            return;
        }
        switch (selectedProduct.getProductType()) {
            case GAME: {
                GameLibraryMenu gameLibraryMenu = new GameLibraryMenu(currentUser, (Game) selectedProduct, storeDB);
                gameLibraryMenu.showMenu();
                break;
            }
            case ACCESSORIES: {
                AccessoryLibraryMenu accessoryLibraryMenu = new AccessoryLibraryMenu(currentUser,
                        (Accessory) selectedProduct, storeDB);
                accessoryLibraryMenu.showMenu();
                break;
            }
            default: {

            }
        }
    }
}
