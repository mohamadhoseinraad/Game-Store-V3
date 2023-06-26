package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.*;
import ir.ac.kntu.models.product.Community;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.product.games.Genre;
import ir.ac.kntu.models.product.games.Level;
import ir.ac.kntu.models.product.accessories.Connection;
import ir.ac.kntu.models.product.accessories.Device;
import ir.ac.kntu.models.product.accessories.GamePad;

public class DefaultData {
    public static Store addDefaultData() {
        Store store = new Store();
        User user1 = new User("1", "09934140117", "mh.shbanirad@icloud.com", "1", UserType.USER);
        User user2 = new User("mo.gamer", "09934140117", "mogamer@gmail.com", "12341234", UserType.USER);
        Admin amin2 = new Admin("adminDe", "", "", "admin", false);
        Admin amin3 = new Admin("dev", "", "", "admin", false);
        Game game1 = new Game("Fortnite", "Battle royall action game", 0, Genre.SHOOTING, Level.LEVEL_1, null);
        Game g2 = new Game("Rainbow six", "Action shooter game", 20, Genre.SHOOTING, Level.LEVEL_1, null);
        Game g3 = new Game("GTA V", "Story mode game form al life of a person", 35, Genre.SHOOTING, Level.LEVEL_1, amin2);
        Game g4 = new Game("Bomb", "Strategy game ", 0, Genre.STRATEGY, Level.LEVEL_4, amin2);
        GamePad gp1 = new GamePad("Ps4GP", "For PS4 , PS5", 100, 3, Connection.WIRELESS, Device.PLAY_STATION, null);
        store.addUser(user1);
        g2.setBetaVersion(true);
        store.addUser(user2);
        store.addUser(amin2);
        store.addUser(amin3);
        amin2.setDeveloper(true);
        amin3.setDeveloper(true);
        amin2.addAccessProduct(g2);
        store.addProduct(game1);
        store.addProduct(g2);
        store.addProduct(g3);
        store.addProduct(g4);
        store.addProduct(gp1);
        user1.chargeWallet(100);
        Community community = new Community(user1.getUsername(), "Awlliii");
        game1.addCommunity(community);
        user1.addProduct(game1);
        game1.addBuy();
        user1.addFriend(user2);
        user2.addFriend(user1);
        user1.setScore(30);
        return store;
    }
}
