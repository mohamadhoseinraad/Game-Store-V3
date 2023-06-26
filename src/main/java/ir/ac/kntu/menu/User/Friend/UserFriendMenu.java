package ir.ac.kntu.menu.User.Friend;

import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.models.User;

import java.util.ArrayList;

public class UserFriendMenu extends Menu {
    private final Store storeDB;

    private ArrayList<User> notFriend;

    private ArrayList<User> friends;

    private final User currentUser;

    public UserFriendMenu(Store storeDB, User currentUser) {
        this.storeDB = storeDB;
        this.currentUser = currentUser;
        updateList();
    }

    private void updateList() {
        notFriend = currentUser.getUserNotFriend(storeDB);
        friends = currentUser.getFriendsList(storeDB);
    }

    private ArrayList<User> usernameSearch(ArrayList<User> notFriend) {
        System.out.println("Search User by username : ");
        String name = Scan.getLine().trim().toUpperCase();
        ArrayList<User> result = new ArrayList<>();
        for (User user : notFriend) {
            if (user.getUsername().startsWith(name)) {
                result.add(user);
            }
        }
        return result;
    }


    private User handleSelect(ArrayList<User> searchResult) {
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
                return searchResult.get(choose);
            }
        }
        return null;

    }

    private void allFriends() {
        ArrayList<User> result = friends;
        printUserSearchResult(result);
        if (result.size() != 0) {
            User selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            FriendProfileMenu friendProfileMenu = new FriendProfileMenu(selectedUser, currentUser, storeDB);
            friendProfileMenu.showMenu();
        }
    }

    private void sendRequest(User user) {
        TerminalColor.yellow();
        System.out.println("Send request to " + user.getUsername() + " ? (Y/N)");
        String input;
        while (!(input = Scan.getLine().toUpperCase()).matches("[Y N]")) {
            System.out.println("Send request to " + user.getUsername() + " ? (Y/N)");
        }
        TerminalColor.reset();
        if (input.equals("Y")) {
            if (user.addRequest(currentUser)) {
                TerminalColor.green();
                System.out.println("Requested !");
            } else if (currentUser.isFriend(user.getId())) {
                TerminalColor.red();
                System.out.println("this username is your friend already!");
            } else {
                TerminalColor.red();
                System.out.println("You sent request before this time!");
            }
        } else {
            TerminalColor.yellow();
            System.out.println("Send request cancelled !");
        }
        TerminalColor.reset();
    }

    private void addFriend() {
        ArrayList<User> result = usernameSearch(notFriend);
        printUserSearchResult(result);
        if (result.size() != 0) {
            User selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            sendRequest(selectedUser);
        }
    }

    private void printUserSearchResult(ArrayList<User> result) {
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

    public void showMenu() {
        UserFriendMenuOption option;
        while ((option = printMenuOptions("Search User", UserFriendMenuOption.class)) != UserFriendMenuOption.EXIT) {
            if (option != null) {
                switch (option) {
                    case ALL_FRIEND: {
                        allFriends();
                        break;
                    }
                    case ADD_FRIEND: {
                        addFriend();
                        break;
                    }
                    case YOUR_REQUESTS: {
                        requests();
                        break;
                    }
                    case BACK: {
                        return;
                    }
                    default:
                        System.out.println("Invalid choose");
                }
            }
            updateList();

        }
        System.exit(0);

    }

    private void requests() {
        ArrayList<User> result = getUserRequested();
        printUserSearchResult(result);
        if (result.size() != 0) {
            User selectedUser = handleSelect(result);
            if (selectedUser == null) {
                return;
            }
            handleFriendRequest(selectedUser);
        }
    }

    private void handleFriendRequest(User user) {
        TerminalColor.yellow();
        System.out.println(user);
        System.out.println("1- Accept");
        System.out.println("2- Delete");
        String input;
        while (!(input = Scan.getLine().toUpperCase().trim()).matches("[1 2]")) {
            TerminalColor.red();
            System.out.println("Wrong choose!");
            TerminalColor.yellow();
            System.out.println(user);
            System.out.println("1- Accept");
            System.out.println("2- Delete");
        }
        if (input.equals("1")) {
            currentUser.addFriend(user);
            user.addFriend(currentUser);
            friends = currentUser.getFriendsList(storeDB);
            notFriend.remove(user);
            TerminalColor.green();
            System.out.println(user.getUsername() + " now is your friend!");
            TerminalColor.reset();
        } else {
            currentUser.removeRequest(user);
            TerminalColor.green();
            System.out.println(user.getUsername() + " remove from request list");
            TerminalColor.reset();
        }

    }


    private ArrayList<User> getUserRequested() {
        ArrayList<User> result = new ArrayList<>();
        for (String userId : currentUser.getRequests()) {
            result.add(storeDB.findUserById(userId));
        }
        return result;
    }

}

