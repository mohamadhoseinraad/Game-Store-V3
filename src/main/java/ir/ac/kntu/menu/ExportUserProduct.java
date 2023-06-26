package ir.ac.kntu.menu;

import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.utils.GenerateHTML;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

import java.util.ArrayList;
import java.util.Collections;

public class ExportUserProduct extends Menu {

    private final ArrayList<Product> library;


    public ExportUserProduct(ArrayList<Product> products) {
        library = products;
    }


    @Override
    public void showMenu() {
        ExportUserGamesOptions option;
        while ((option = printMenuOptions("Export html", ExportUserGamesOptions.class)) != ExportUserGamesOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL: {
                        allGame();
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

    private void allGame() {
        ArrayList<Product> result = library;
        String title = "All Game ";
        GenerateHTML.generateHTML(title, convertArray(result));
    }


    private void searchByName() {
        System.out.println("Search Name of game : ");
        String name = Scan.getLine().trim().toUpperCase();
        ArrayList<Product> result = nameFilter(name);
        GenerateHTML.generateHTML( " Games with " + name + "in their name", convertArray(result));

    }

    private ArrayList<Product> nameFilter(String name) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product game : library) {
            if (game.getName().startsWith(name)) {
                result.add(game);
            }
        }
        return result;
    }

    private ArrayList<Object> convertArray(ArrayList<Product> input) {
        ArrayList<Object> result = new ArrayList<>();
        Collections.addAll(result, input);
        return result;
    }

    private void searchBPrice() {
        System.out.println("from : ");
        String basePriceStr = Scan.getLine().trim();
        System.out.println("to : ");
        String maxPriceStr = Scan.getLine().trim();
        if (!maxPriceStr.matches("[0-9][0-9.]*") || !basePriceStr.matches("[0-9][0-9.]*")) {
            TerminalColor.red();
            System.out.println("Enter valid amount!");
            TerminalColor.reset();
            return;
        }
        double basePrice = Double.parseDouble(basePriceStr);
        double maxPrice = Double.parseDouble(maxPriceStr);
        ArrayList<Product> result = priceFilter(basePrice, maxPrice);
        String title =  " Games filter by price from " + basePrice + "to" + maxPrice;
        GenerateHTML.generateHTML(title, convertArray(result));
    }

    private ArrayList<Product> priceFilter(double min, double max) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product game : library) {
            if (game.getPrice() >= min && game.getPrice() <= max) {
                result.add(game);
            }
        }
        return result;
    }

}
