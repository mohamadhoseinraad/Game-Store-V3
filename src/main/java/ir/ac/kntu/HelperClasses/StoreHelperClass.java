package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.Product;

import java.util.ArrayList;
import java.util.Map;

public class StoreHelperClass {
    public static ArrayList<Product> getAllLibrary(Store storeDB, User currentUser) {
        ArrayList<Product> result = new ArrayList<>();
        for (Map.Entry<String, String> productName : currentUser.getLibrary().entrySet()) {
            Product product = storeDB.findProduct(productName.getKey());
            result.add(product);
        }
        return result;
    }
}
