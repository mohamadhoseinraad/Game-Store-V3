package ir.ac.kntu.menu.User;

import ir.ac.kntu.HelperClasses.GameHelper;
import ir.ac.kntu.HelperClasses.SelectItemHelper;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.games.Game;

import java.util.ArrayList;

public class UserCountPriceMenu extends Menu {

    private final ArrayList<Product> products;

    private final User user;

    private ArrayList<Product> productsSelected = new ArrayList<>();

    public UserCountPriceMenu(Store storeDB, User user) {
        this.products = storeDB.getProducts();
        this.user = user;
    }

    @Override
    public void showMenu() {
        Product currentProduct;
        do {
            System.out.println("Select whit product to sum price : (0 to end)");
            currentProduct = SelectItemHelper.handleSelect(products);
            productsSelected.add(currentProduct);
        }
        while (currentProduct != null);
        double sumPrice = 0;
        if (productsSelected.size() != 0) {
            for (Product product : productsSelected) {
                if (product != null) {
                    if (product.getClass() == Game.class) {
                        sumPrice += GameHelper.applyOffer(product.getPrice(), user.getScore());
                    } else {
                        sumPrice += product.getPrice();
                    }
                }
            }
        }
        System.out.println("Sum of price is : " + sumPrice);
    }
}
