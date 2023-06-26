package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.product.FeedBack;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

import java.util.ArrayList;
import java.util.Collections;

public class SelectItemHelper {

    public static Product searchStoreProtectByName(Store storeDB) {
        System.out.println("Search Name of item : ");
        String name = Scan.getLine().trim().toUpperCase();
        return handleSelect(storeDB.findProductByName(name));
    }

    public static Product searchInCostumeProtectByName(ArrayList<Product> userLibrary) {
        System.out.println("Search Name of item : ");
        String name = Scan.getLine().trim().toUpperCase();
        return handleSelect(nameSearchUserLibrary(userLibrary, name));
    }

    private static ArrayList<Product> nameSearchUserLibrary(ArrayList<Product> products, String value) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().startsWith(value)) {
                result.add(product);
            }
        }
        return result;
    }

    public static Product searchProtectByPrice(Store storeDB) {
        String basePriceStr;
        String maxPriceStr;
        double basePrice;
        double maxPrice;
        while (true) {
            System.out.println("from : (enter x to cancel)");
            basePriceStr = Scan.getLine().trim();
            if (basePriceStr.equals("x")) {
                return null;
            }
            System.out.println("to : (enter x to cancel)");
            maxPriceStr = Scan.getLine().trim();
            if (maxPriceStr.equals("x")) {
                return null;
            }
            if (!maxPriceStr.matches("[0-9][0-9.]*") || !basePriceStr.matches("[0-9][0-9.]*")) {
                TerminalColor.red();
                System.out.println("Enter valid amount!");
                TerminalColor.reset();
            } else {
                basePrice = Double.parseDouble(basePriceStr);
                maxPrice = Double.parseDouble(maxPriceStr);
                return handleSelect(storeDB.findProductByPrice(basePrice, maxPrice));
            }
        }
    }

    public static Product searchProtectByPriceInCostumeList(Store storeDB, ArrayList<Product> products) {
        String basePriceStr;
        String maxPriceStr;
        double basePrice;
        double maxPrice;
        while (true) {
            System.out.println("from : (enter x to cancel)");
            basePriceStr = Scan.getLine().trim();
            if (basePriceStr.equals("x")) {
                return null;
            }
            System.out.println("to : (enter x to cancel)");
            maxPriceStr = Scan.getLine().trim();
            if (maxPriceStr.equals("x")) {
                return null;
            }
            if (!maxPriceStr.matches("[0-9][0-9.]*") || !basePriceStr.matches("[0-9][0-9.]*")) {
                TerminalColor.red();
                System.out.println("Enter valid amount!");
                TerminalColor.reset();
            } else {
                basePrice = Double.parseDouble(basePriceStr);
                maxPrice = Double.parseDouble(maxPriceStr);
                ArrayList<Product> result = new ArrayList<>();
                for (Product product : storeDB.findProductByPrice(basePrice, maxPrice)) {
                    if (result.contains(product)) {
                        result.add(product);
                    }
                }
                return handleSelect(result);
            }
        }
    }

    private static ArrayList<Product>[] makePagination(ArrayList<Product> products) {
        final int pageSize = 3;
        ArrayList<Product>[] pages = new ArrayList[products.size() / pageSize + 1];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = new ArrayList<>();
        }
        int j = 0;
        for (int i = 0; i < products.size(); i++) {
            pages[j].add(products.get(i));
            if (pages[j].size() == pageSize) {
                j++;
            }
        }
        return pages;
    }

    private static int checkInput(int page, String input, int totalPage) {
        if (input.equals("x")) {
            if (page + 1 <= totalPage - 1) {
                page++;
            } else {
                TerminalColor.red();
                System.out.println("this is last page!");
            }
        } else if (input.equals("y")) {
            if (page - 1 >= 0) {
                page--;
            } else {
                TerminalColor.red();
                System.out.println("this is first page!");
            }
        }
        return page;
    }

    public static Product handleSelect(ArrayList<Product> searchResult) {
        if (searchResult == null || searchResult.size() == 0) {
            return null;
        }
        int page = 0;
        while (true) {
            int totalPage = makePagination(searchResult).length;
            ArrayList<Product> eachPage = makePagination(searchResult)[page];
            printGameSearchResult(eachPage);
            System.out.println("---- chose number : (0 to back  x to next page y back page)");
            String input = Scan.getLine();
            if (!input.matches("[x y 0-9]+")) {
                TerminalColor.red();
                System.out.println("Chose valid input!");
                TerminalColor.reset();
            } else {
                if (input.matches("[x y]")) {
                    page = checkInput(page, input, totalPage);
                } else {
                    int choose = Integer.parseInt(input) - 1;
                    if (choose == -1) {
                        return null;
                    }
                    if (choose >= eachPage.size() || choose < 0) {
                        TerminalColor.red();
                        System.out.println("Chose valid number!");
                        TerminalColor.reset();
                    } else {
                        return eachPage.get(choose);
                    }
                }
            }
        }
    }

    public static FeedBack handleSelectFeedBack(ArrayList<FeedBack> searchResult) {
        if (searchResult == null) {
            return null;
        }
        while (true) {
            printFeedBackSearchResult(searchResult);
            if (searchResult.size() == 0) {
                return null;
            }
            System.out.println("---- chose number : (0 to back )");
            String input = Scan.getLine();
            if (!input.matches("[0-9]+")) {
                TerminalColor.red();
                System.out.println("Chose valid number!");
                TerminalColor.reset();
            } else {
                int choose = Integer.parseInt(input) - 1;
                if (choose == -1) {
                    return null;
                }
                if (choose >= searchResult.size() || choose < 0) {
                    TerminalColor.red();
                    System.out.println("Chose valid number!");
                    TerminalColor.reset();
                } else {
                    return searchResult.get(choose);
                }
            }
        }
    }

    private static void printGameSearchResult(ArrayList<Product> result) {
        if (result.size() == 0) {
            System.out.println("Not found ! :(");
        } else {
            int i = 1;
            for (Product product : result) {
                TerminalColor.blue();
                System.out.print(i);
                TerminalColor.yellow();
                System.out.print(" | ");
                TerminalColor.blue();
                System.out.println(product);
                TerminalColor.reset();
                i++;
            }
        }
    }

    private static void printFeedBackSearchResult(ArrayList<FeedBack> result) {
        if (result.size() == 0) {
            System.out.println("Not found ! :(");
        } else {
            int i = 1;
            for (FeedBack feedBack : result) {
                TerminalColor.blue();
                System.out.print(i);
                TerminalColor.yellow();
                System.out.print(" | ");
                TerminalColor.blue();
                System.out.println(feedBack);
                TerminalColor.reset();
                i++;
            }
        }
    }

    public static ArrayList<Product> getAllProducts(ArrayList<Product> products) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            Collections.addAll(result, product);
        }
        return result;
    }
}
