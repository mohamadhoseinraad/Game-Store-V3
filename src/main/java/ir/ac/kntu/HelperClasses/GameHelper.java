package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.UserType;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.product.games.Genre;
import ir.ac.kntu.models.product.games.Level;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

public class GameHelper {
    public static Game makeGame(Admin admin) {
        System.out.println("Pleas enter game name :");
        String name = Scan.getLine();
        System.out.println("Enter Genre : ");
        Genre genre = ProductHelper.getInputEnumData(Genre.class);
        System.out.println("Enter Lever : ");
        Level level = ProductHelper.getInputEnumData(Level.class);
        System.out.println("Pleas enter detail of game :");
        String detail = Scan.getLine();
        System.out.println("Pleas enter price :");
        String priceSrt = Scan.getLine();
        TerminalColor.red();
        if (!priceSrt.matches("[0-9.]+")) {
            System.out.println("Price is not Valid!");
            TerminalColor.reset();
            return null;
        }
        if (name.length() < 3 || detail.length() < 3) {
            System.out.println("Name and detail must be more than 3 character!");
            TerminalColor.reset();
            return null;
        }
        TerminalColor.reset();
        double price = Double.parseDouble(priceSrt);
        return new Game(name, detail, price, genre, level, admin);
    }


    public static void printGame(Game game, User user) {
        TerminalColor.blue();
        System.out.println("|----------------------------");
        if (game.isBetaVersion()){
            TerminalColor.yellow();
            System.out.println("|---- this is beta version of this game ----");
        }
        TerminalColor.cyan();
        System.out.print("| Name     : " + game.getName());
        TerminalColor.reset();
        System.out.print("  -----  ");
        if (game.getPrice() == 0) {
            TerminalColor.green();
            System.out.println("Free");
        } else {
            TerminalColor.cyan();
            System.out.print(game.getPrice() + "$ coast  ");
            handleOffPrice(game, user);
        }
        TerminalColor.yellow();
        System.out.print("| Genre : " + game.getGenre());
        System.out.print(" | Score : ");
        ProductHelper.scoreColor(game);
        System.out.print(game.getScore());
        TerminalColor.cyan();
        System.out.println(" (" + game.getRates().size() + ")");
        System.out.println(game.getDetails());
        System.out.println("Level : " + game.getLevel());
        TerminalColor.blue();
        System.out.println("|----------------------------");
        TerminalColor.reset();
    }

    private static void handleOffPrice(Game game, User user) {
        if (user.getUserType() == UserType.USER) {
            TerminalColor.green();
            if (user.getScore() < 20) {
                System.out.println("OFF " + 0 + "%");
            } else if (user.getScore() < 50) {
                System.out.println("OFF " + 10 + "%");
            } else if (user.getScore() < 100) {
                System.out.println("OFF " + 20 + "%");
            } else {
                System.out.println("OFF " + 30 + "%");
            }
        }
    }

    public static boolean checkUserLevel(Game game, User user) {
        switch (game.getLevel()) {
            case LEVEL_1: {
                return true;
            }
            case LEVEL_2: {
                return (user.getScore() >= 20);
            }
            case LEVEL_3: {
                return (user.getScore() >= 50);
            }
            case LEVEL_4: {
                return (user.getScore() >= 100);
            }
            default: {
                return false;
            }
        }
    }

    public static double applyOffer(double price, int score) {
        if (score < 50) {
            return price * 0.9;
        } else if (score < 100) {
            return price * 0.8;
        } else if (score > 100) {
            return price * 0.7;
        } else {
            return price;
        }
    }
}
