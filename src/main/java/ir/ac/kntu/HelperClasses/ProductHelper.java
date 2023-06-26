package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.ProductType;
import ir.ac.kntu.models.product.accessories.AccessoryType;
import ir.ac.kntu.models.product.accessories.GamePad;
import ir.ac.kntu.models.product.accessories.Monitor;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

import java.util.ArrayList;

public class ProductHelper {

    public static <T extends Enum<T>> T getInputEnumData(Class<T> inputenum) {
        System.out.println("Choose : ");
        T[] options = inputenum.getEnumConstants();
        for (int i = 0; i < options.length; i++) {
            System.out.println(String.valueOf(i) + options[i]);
        }
        String input;
        do {
            input = Scan.getLine();
        } while (checkInputEnum(input, inputenum));
        return options[Integer.parseInt(input)];
    }

    private static <T extends Enum> boolean checkInputEnum(String input, Class<T> inputEnum) {
        if (!input.matches("[0-9]+")) {
            System.out.println("Invalid input try again");
            return true;
        }
        if (Integer.parseInt(input) > inputEnum.getEnumConstants().length - 1) {
            System.out.println("Invalid input try again");
            return true;
        }
        if (Integer.parseInt(input) < 0) {
            System.out.println("Invalid input try again");
            return true;
        }
        return false;
    }

    public static void scoreColor(Product product) {
        if (product.getScore() < 3) {
            TerminalColor.red();
        } else if (product.getScore() < 6) {
            TerminalColor.yellow();
        } else {
            TerminalColor.green();
        }
    }

    public static ArrayList<Product> findProductByName(ArrayList<Product> products,
                                                       String name,
                                                       ProductType productType, AccessoryType accessoryType) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().startsWith(name)) {
                if (productType == ProductType.GAME) {
                    if (product.getClass() != Game.class) {
                        result.add(product);
                    }
                } else if (productType == ProductType.ACCESSORIES) {
                    if (accessoryType == AccessoryType.GAME_PAD) {
                        if (product.getClass() != GamePad.class) {
                            result.add(product);
                        }
                    } else if (accessoryType == AccessoryType.MONITOR) {
                        if (product.getClass() != Monitor.class) {
                            result.add(product);
                        }
                    }
                } else {
                    result.add(product);
                }
            }
        }
        return result;
    }


}
