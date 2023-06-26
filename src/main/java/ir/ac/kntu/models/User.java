package ir.ac.kntu.models;

import ir.ac.kntu.HelperClasses.GameHelper;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.accessories.Accessory;
import ir.ac.kntu.models.product.accessories.GamePad;
import ir.ac.kntu.models.product.accessories.Monitor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class User implements Comparable {

    private static int countUser = 0;

    private final String id;

    private String username;

    private String phoneNumber;

    private String email;

    private int hashPassword;

    private double wallet = 0;

    private int score = 0;

    private Date timeEntered = new Date();

    private Date timeExit = new Date();

    public final UserType userType;

    private Map<String, String> library = new HashMap<>();

    private ArrayList<String> friends = new ArrayList<>();

    private ArrayList<String> requests = new ArrayList<>();

    public User(String username, String phoneNumber, String email, String password, UserType type) {
        this.username = username.toUpperCase().trim();
        this.phoneNumber = phoneNumber.trim();
        this.email = email.toLowerCase().trim();
        hashPassword = password.hashCode();
        userType = type;
        if (userType == UserType.USER) {
            id = "USR" + countUser++;
        } else {
            id = "ADM" + countUser++;
        }
    }

    public String getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim().toUpperCase();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public boolean setNewPassword(String newPassword, String oldPassword) {

        if (oldPassword.hashCode() != hashPassword) {
            return false;
        }
        hashPassword = newPassword.hashCode();
        return true;
    }

    public boolean checkPassword(String password) {
        if (password.hashCode() == hashPassword) {
            return true;
        }
        return false;
    }

    public double getWallet() {
        return wallet;
    }

    public void chargeWallet(double value) {
        wallet += value;
    }

    public Map<String, String> getLibrary() {
        return library;
    }

    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        if (product.getClass() == Game.class) {
            return addGame((Game) product);
        }
        if (product.getClass() == GamePad.class || product.getClass() == Monitor.class) {
            return addAccessory((Accessory) product);
        }
        return false;
    }

    private boolean addGame(Game game) {
        double price = GameHelper.applyOffer(game.getPrice(), score);
        if (!doHaveGame(game) && wallet >= price) {
            library.put(game.getId(), game.getName());
            wallet -= price;
            return true;
        }
        return false;
    }

    private boolean addAccessory(Accessory accessory) {
        double price = accessory.getPrice();
        if ((accessory.getAmount() > 0) && wallet >= price) {
            library.put(accessory.getId(), accessory.getName());
            wallet -= price;
            accessory.setAmount(accessory.getAmount() - 1);
            return true;
        }
        return false;
    }

    public void addCostumeProduct(Product accessory) {
        library.put(accessory.getId(), accessory.getName());
    }

    public boolean giftGame(Game game, User friend) {
        double price = GameHelper.applyOffer(game.getPrice(), score);
        if (!friend.getLibrary().containsKey(game.getId()) && wallet >= price) {
            friend.addCostumeProduct(game);
            wallet -= price;
            return true;
        }
        return false;
    }

    public boolean giftAccessory(Accessory accessory, User friend) {
        double price = accessory.getPrice();
        if ((accessory.getAmount() > 0) && wallet >= price) {
            friend.addCostumeProduct(accessory);
            wallet -= price;
            accessory.setAmount(accessory.getAmount() - 1);
            return true;
        }
        return false;
    }

    public boolean doHaveGame(Game game) {
        return library.containsKey(game.getId());
    }

    public boolean isFriend(String userId) {
        return friends.contains(userId);
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<User> getFriendsList(Store storeDB) {
        ArrayList<User> friendsList = new ArrayList<>();
        ArrayList<User> allUsers = storeDB.getUsers();
        for (User user : allUsers) {
            if (friends.contains(user.getId())) {
                friendsList.add(user);
            }
        }

        return friendsList;
    }

    public ArrayList<User> getUserNotFriend(Store storeDB) {
        ArrayList<User> friendsList = new ArrayList<>();
        ArrayList<User> allUsers = storeDB.getUsers();
        for (User user : allUsers) {
            if (!friends.contains(user.getId()) && user.getUserType() == UserType.USER) {
                friendsList.add(user);
            }
        }
        friendsList.remove(this);
        return friendsList;
    }


    public boolean addFriend(User user) {
        if (friends.contains(user.getId())) {
            return false;
        }
        friends.add(user.getId());
        requests.remove(user.getId());
        return true;
    }

    public boolean removeFriend(User user) {
        if (friends.contains(user.getId())) {
            friends.remove(user.getId());
            return true;
        }
        return false;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public boolean addRequest(User someUser) {
        if (friends.contains(someUser.getId())) {
            return false;
        }
        if (requests.contains(someUser.getId())) {
            return false;
        }
        return requests.add(someUser.getId());
    }

    public void removeRequest(User user) {
        requests.remove(user.getId());
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isLogin(String password) {
        if (checkPassword(password)) {
            timeEntered.setTime(System.currentTimeMillis());
            timeExit.setTime(System.currentTimeMillis());
            return true;
        }
        return false;
    }

    public void isLogout() {
        if (timeEntered.equals(timeExit)) {
            timeExit.setTime(System.currentTimeMillis());
            long difference = timeExit.getTime() - timeEntered.getTime();
            difference /= 60000;
            score += difference;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return username.equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


    @Override
    public String toString() {
        return "Username :" + username + " | Phone number : " + phoneNumber + " | Email : " + email + " | Score : " + score;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        User ouser = (User) o;
        if (this.score > ouser.score) {
            return 1;
        } else if (this.score < ouser.score) {
            return -1;
        } else {
            return 0;
        }
    }

    public void removeProduct(Product product) {
        library.remove(product.getId());
    }
}
