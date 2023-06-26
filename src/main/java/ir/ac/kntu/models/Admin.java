package ir.ac.kntu.models;

import ir.ac.kntu.HelperClasses.FeedBacksHelper;
import ir.ac.kntu.models.product.FeedBack;
import ir.ac.kntu.models.product.Product;
import ir.ac.kntu.models.product.accessories.Accessory;
import ir.ac.kntu.models.product.games.Game;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Admin extends User implements Comparable, Serializable {
    private final boolean isMastetAdmin;

    private boolean isDeveloper;

    private boolean isSeller;

    private boolean isUserManager;

    private ArrayList<String> productAccessID = new ArrayList<>();

    private ArrayList<FeedBack> scheduledEvent = new ArrayList<>();

    private ArrayList<FeedBack> inbox = new ArrayList<>();

    public Admin(String username, String phoneNumber, String email, String password, boolean isMastetAdmin) {
        super(username, phoneNumber, email, password, UserType.ADMIN);
        this.isMastetAdmin = isMastetAdmin;
    }

    public boolean isMastetAdmin() {
        return isMastetAdmin;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }

    public void setDeveloper(boolean developer) {
        isDeveloper = developer;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public boolean isUserManager() {
        return isUserManager;
    }

    public void setUserManager(boolean userManager) {
        isUserManager = userManager;
    }

    public void addAccessProduct(Product product) {
        productAccessID.add(product.getId());
    }

    public void removeAccessProduct(Product product) {
        productAccessID.remove(product.getId());
    }

    public boolean canAccessProduct(Product product) {
        return productAccessID.contains(product.getId());
    }

    public ArrayList<String> getProductAccessID() {
        return productAccessID;
    }

    public ArrayList<FeedBack> getInbox() {
        return inbox;
    }

    public void setInbox(ArrayList<FeedBack> inbox) {
        this.inbox = inbox;
    }

    public void addInbox(FeedBack feedBack) {
        inbox.add(feedBack);
    }

    public void deAcceptFeedback(FeedBack feedBack, Game game, ArrayList<Admin> developers) {
        inbox.remove(feedBack);
        FeedBacksHelper.updateFeedBackReq(feedBack, game, developers);
    }

    public void acceptFeedback(FeedBack feedBack) {
        inbox.remove(feedBack);
        scheduledEvent.add(feedBack);
        feedBack.setAccept(true);
    }

    public ArrayList<FeedBack> getScheduledEvent() {
        return scheduledEvent;
    }

    public void setScheduledEvent(ArrayList<FeedBack> scheduledEvent) {
        this.scheduledEvent = scheduledEvent;
    }

    public void addScheduledEvent(FeedBack feedBack) {
        scheduledEvent.add(feedBack);
        feedBack.setAccept(true);
    }

    public void solvedScheduledEvent(FeedBack feedBack) {
        scheduledEvent.remove(feedBack);
    }

    @Override
    public double getWallet() {
        return 0;
    }

    @Override
    public void chargeWallet(double value) {
    }

    @Override
    public Map<String, String> getLibrary() {
        return null;
    }

    @Override
    public boolean addProduct(Product product) {
        return true;
    }

    @Override
    public void addCostumeProduct(Product accessory) {
    }

    @Override
    public boolean giftGame(Game game, User friend) {
        return true;
    }

    @Override
    public boolean giftAccessory(Accessory accessory, User friend) {
        return true;
    }

    @Override
    public boolean doHaveGame(Game game) {
        return true;
    }

    @Override
    public boolean isFriend(String userId) {
        return true;
    }

    @Override
    public ArrayList<String> getFriends() {
        return null;
    }

    @Override
    public ArrayList<User> getFriendsList(Store storeDB) {
        return null;
    }

    @Override
    public ArrayList<User> getUserNotFriend(Store storeDB) {
        return null;
    }

    @Override
    public boolean addFriend(User user) {
        return true;
    }

    @Override
    public boolean removeFriend(User user) {
        return true;
    }

    @Override
    public ArrayList<String> getRequests() {
        return null;
    }

    @Override
    public boolean addRequest(User someUser) {
        return true;
    }

    @Override
    public void removeRequest(User user) {
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setScore(int score) {
    }

    @Override
    public boolean isLogin(String password) {
        return checkPassword(password);
    }

    @Override
    public void isLogout() {
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Admin admin = (Admin) o;
        if (this.getScheduledEvent().size() > admin.getScheduledEvent().size()) {
            return 1;
        } else if (this.getScheduledEvent().size() < admin.getScheduledEvent().size()) {
            return -1;
        } else {
            if (this.getInbox().size() > admin.getInbox().size()) {
                return 1;
            } else if (this.getInbox().size() < admin.getInbox().size()) {
                return -1;
            }
            return 0;
        }
    }
}

