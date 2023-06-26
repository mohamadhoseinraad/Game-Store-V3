package ir.ac.kntu.menu.Admin.Game;

import ir.ac.kntu.utils.GenerateHTML;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.product.Product;

import java.util.ArrayList;

public class ExportProducts extends Menu {

    private Store storeDB;



    public ExportProducts(Store store) {
        this.storeDB = store;
    }

    @Override
    public void showMenu() {
        ExportProductsOptions option;
        while ((option = printMenuOptions("Export html", ExportProductsOptions.class)) != ExportProductsOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL: {
                        allProduct();
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

    private void allProduct() {
        ArrayList<Product> result = getAllProduct();
        String title = "All Product in Store";
        GenerateHTML.generateHTML(title, convertArray(result));
    }

    private ArrayList<Product> getAllProduct() {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : storeDB.getProducts()) {
            result.add(product);
        }
        return result;
    }

    private void searchByName() {
        System.out.println("Search Name of product : ");
        String name = Scan.getLine().trim().toUpperCase();
        ArrayList<Product> result = storeDB.findProductByName(name);
        GenerateHTML.generateHTML("Products with "+name+"in their name", convertArray(result));

    }

    private ArrayList<Object> convertArray(ArrayList<Product> input){
        ArrayList<Object> result = new ArrayList<>();
        for (Product product : input){
            result.add(product);
        }
        return result;
    }

    private void searchBPrice(){
        System.out.println("from : ");
        String basePriceStr = Scan.getLine().trim();
        System.out.println("to : ");
        String maxPriceStr = Scan.getLine().trim();
        if (!maxPriceStr.matches("[0-9][0-9.]*") || !basePriceStr.matches("[0-9][0-9.]*")){
            TerminalColor.red();
            System.out.println("Enter valid amount!");
            TerminalColor.reset();
            return;
        }
        double basePrice = Double.parseDouble(basePriceStr);
        double maxPrice = Double.parseDouble(maxPriceStr);
        ArrayList<Product> result = storeDB.findProductByPrice(basePrice, maxPrice);
        String title = "Products filter by price from " + basePrice + "to" +maxPrice;
        GenerateHTML.generateHTML(title, convertArray(result));
    }

}
