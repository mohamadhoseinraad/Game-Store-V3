package ir.ac.kntu.menu.User.Library;

import ir.ac.kntu.HelperClasses.FeedBacksHelper;
import ir.ac.kntu.models.product.Community;
import ir.ac.kntu.models.product.FeedBack;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.utils.TerminalColor;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.models.User;

public class GameLibraryMenu extends Menu {

    private final User currentUser;

    private final Game currentGame;

    private final Store storeDB;

    public GameLibraryMenu(User currentUser, Game currentGame, Store storeDB) {
        this.currentUser = currentUser;
        this.currentGame = currentGame;
        this.storeDB = storeDB;
    }

    @Override
    public void showMenu() {
        if (!currentGame.isAvailable()) {
            TerminalColor.red();
            System.out.println("Sorry game temporary is not available");
            TerminalColor.reset();
            return;
        }
        SelectedProductMenuOptions option;
        while (printGame() && (option = printMenuOptions(currentGame.getName(), SelectedProductMenuOptions.class)) != SelectedProductMenuOptions.EXIT) {
            switch (option) {
                case RATE: {
                    rate();
                    break;
                }
                case COMMENT: {
                    comment();
                    break;
                }
                case SHOW_COMMENTS: {
                    showComments();
                    break;
                }
                case BACK: {
                    return;
                }
                case FEEDBACK: {
                    feedback();
                    break;
                }
                default: {
                    break;
                }
            }
        }
        System.exit(0);
    }

    private void feedback() {
        System.out.println(storeDB.gameDevelopers(currentGame, true));
        FeedBacksHelper.makeFeedBack(currentUser, currentGame, storeDB);

        FeedBacksHelper.handleFeedBack(currentGame, storeDB.gameDevelopers(currentGame, true));
    }

    private boolean printGame() {
        currentGame.showProduct(currentUser);
        return true;
    }

    private void rate() {
        System.out.println("Enter your rate");
        String rateStr;
        while (!(rateStr = Scan.getLine()).matches("[0-9]|10")) {
            TerminalColor.red();
            System.out.println("Please enter valid rate between 0-10");
            TerminalColor.reset();
        }
        double vote = Double.parseDouble(rateStr);
        currentGame.rating(currentUser, vote);

    }

    private void comment() {
        if (currentGame.isBetaVersion()) {
            TerminalColor.red();
            System.out.println("this is beta version of game. you can't comment for this!");
            TerminalColor.reset();
        } else {
            System.out.println("Enter your comment");
            String userComment;
            while ((userComment = Scan.getLine().trim()).length() < 3) {
                TerminalColor.red();
                System.out.println("Your comment must 3 or more character ! enter your comment again :");
                TerminalColor.reset();
            }
            Community community = new Community(currentUser.getUsername(), userComment);
            currentGame.addCommunity(community);
            TerminalColor.green();
            System.out.println("Successfully submit !");
            TerminalColor.reset();
        }
    }

    private void showComments() {
        if (currentGame.isBetaVersion()) {
            TerminalColor.red();
            System.out.println("this is beta version of game. you can't comment for this!");
            TerminalColor.reset();
        } else {
            currentGame.showAllComment();
        }
    }

}
