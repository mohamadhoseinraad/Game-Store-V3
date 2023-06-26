package ir.ac.kntu.menu.Admin.Game;

import ir.ac.kntu.DAOStore;
import ir.ac.kntu.HelperClasses.GetInputHelper;
import ir.ac.kntu.HelperClasses.ProductHelper;
import ir.ac.kntu.menu.Admin.Admins.AdminEditAdminMenu;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.SearchEnum.UserFilterBy;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.product.games.Genre;

import java.util.ArrayList;

public class AdminGameEdit extends Menu {

    private final Game currentGame;

    private final Admin admin;

    private final Store storeDB;

    private AdminGameEditOptions option;

    public AdminGameEdit(Game currentGame, Admin admin, Store storeDB) {
        this.currentGame = currentGame;
        this.admin = admin;
        this.storeDB = storeDB;
    }

    @Override
    public void showMenu() {
        while (showGame() && (option = printMenuOptions("EDIT Games", AdminGameEditOptions.class)) != AdminGameEditOptions.EXIT) {
            if (option != null) {
                switch (option) {
                    case AVAILABLE: {
                        available();
                        break;
                    }
                    case EDIT_NAME: {
                        editName();
                        break;
                    }
                    case EDIT_GENRE: {
                        editGenre();
                        break;
                    }
                    case EDIT_DETAIL: {
                        editDetail();
                        break;
                    }
                    case EDIT_PRICE: {
                        editPrice();
                        break;
                    }
                    case ADD_DEVELOPER: {
                        addDeveloper();
                        break;
                    }
                    case REMOVE_DEVELOPER: {
                        removeDeveloper();
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

    private void available() {
        System.out.println("Y for available N for not available");
        if (GetInputHelper.inputConform().equals("Y")) {
            currentGame.setAvailable(true);
            TerminalColor.green();
            System.out.println("Now is available ");
        } else {
            currentGame.setAvailable(false);
            TerminalColor.yellow();
            System.out.println("Now is not available ");
        }
    }

    private void removeDeveloper() {
        ArrayList<Admin> result = storeDB.gameDevelopers(currentGame, true);
        printUserSearchResult(result);
        if (result.size() != 0) {
            Admin selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            currentGame.removeDeveloper(selectedUser);
            selectedUser.removeAccessProduct(currentGame);
            TerminalColor.yellow();
            System.out.println(selectedUser.getUsername() + " remove from developer for " + currentGame.getName());
        }
    }

    private void addDeveloper() {
        ArrayList<Admin> result = storeDB.gameDevelopers(currentGame, false);
        printUserSearchResult(result);
        if (result.size() != 0) {
            Admin selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            currentGame.addDeveloper(selectedUser);
            selectedUser.addAccessProduct(currentGame);
            TerminalColor.green();
            System.out.println(selectedUser.getUsername() + " added to developer for " + currentGame.getName());
        }

    }

    private boolean showGame() {
        currentGame.showProduct(admin);
        return true;
    }

    private void editPrice() {
        System.out.println("Enter new price : ");
        String input = Scan.getLine();
        if (input.matches("[0-9][0-9.]*")) {
            double newPrice = Double.parseDouble(input);
            currentGame.setPrice(newPrice);
            return;
        }
        TerminalColor.red();
        System.out.println("Enter valid price!");
        TerminalColor.reset();
    }

    private void editName() {
        System.out.println("Enter new name : ");
        String input = Scan.getLine().trim().toUpperCase();
        if (input.length() > 2) {
            currentGame.setName(input);
            return;
        }
        TerminalColor.red();
        System.out.println("Minimum Length 3 character!");
        TerminalColor.reset();
    }

    private void editDetail() {
        System.out.println("Enter new detail : ");
        String input = Scan.getLine().trim().toUpperCase();
        if (input.length() > 2) {
            currentGame.setDetails(input);
            return;
        }
        TerminalColor.red();
        System.out.println("Minimum Length 3 character!");
        TerminalColor.reset();
    }

    private void editGenre() {
        Genre genre = ProductHelper.getInputEnumData(Genre.class);
        currentGame.setGenre(genre);
    }

    private Admin handleSelect(ArrayList<Admin> searchResult) {
        System.out.println("---- chose number : ");
        String input = Scan.getLine();
        if (!input.matches("[0-9]+")) {
            TerminalColor.red();
            System.out.println("Chose valid number!");
            TerminalColor.reset();
        } else {
            int choose = Integer.parseInt(input) - 1;
            if (choose >= searchResult.size() || choose < 0) {
                TerminalColor.red();
                System.out.println("Chose valid number!");
                TerminalColor.reset();
            } else {
                Admin user = searchResult.get(choose);
                return user;
            }
        }
        return null;

    }

    private void printUserSearchResult(ArrayList<Admin> result) {
        if (result.size() == 0) {
            System.out.println("Not found ! :(");
        } else {
            int i = 1;
            for (User user : result) {
                TerminalColor.blue();
                System.out.print(i);
                TerminalColor.yellow();
                System.out.print(" | ");
                TerminalColor.blue();
                System.out.println(user);
                TerminalColor.reset();
                i++;
            }
        }
    }

}
