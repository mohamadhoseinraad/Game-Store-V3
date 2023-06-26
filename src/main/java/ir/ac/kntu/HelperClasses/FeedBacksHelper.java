package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.FeedBack;
import ir.ac.kntu.models.product.games.Game;
import ir.ac.kntu.utils.Scan;
import ir.ac.kntu.utils.TerminalColor;

import java.util.ArrayList;
import java.util.Collections;

public class FeedBacksHelper {

    public static FeedBack makeFeedBack(User user, Game game, Store storeDB) {
        System.out.println("sent your massage :");
        String input;
        do {
            input = Scan.getLine();

        } while (!checkInputMassage(input));
        FeedBack feedBack = new FeedBack(input, user, game, storeDB.gameDevelopers(game, true));
        return feedBack;
    }


    private static boolean checkInputMassage(String input) {
        if (input.length() < 5) {
            TerminalColor.red();
            System.out.println("write more than 5 character!");
            TerminalColor.reset();
            return false;
        } else {
            return true;
        }
    }

    public static void updateAllFeedBack(Store store){
        for (Game game : store.getGames()){
            FeedBacksHelper.handleFeedBack(game, store.gameDevelopers(game, true));
        }
    }

    public static void handleFeedBack(Game game, ArrayList<Admin> developers) {
        for (FeedBack feedBack : game.getFeedBacks()) {
            if (!feedBack.isAccept()) {
                if (feedBack.isExpired() || feedBack.getLastReqDevId() == null) {
                    updateFeedBackReq(feedBack, game, developers);
                }
            }
        }
    }

    public static void updateFeedBackReq(FeedBack feedBack, Game game, ArrayList<Admin> developers) {
        Collections.sort(developers);
        int index = 0;
        if (feedBack.getLastReqDevId() == null) {
            feedBack.setLastReqDevId(developers.get(index).getId());
            feedBack.updateTimeReq();
            developers.get(index).addInbox(feedBack);
            feedBack.setAccept(false);
            return;
        } else {
            if (developers.get(index).getId().equals(feedBack.getLastReqDevId())) {
                index++;
            }
            if (index > developers.size() - 1) {
                index = 0;
                feedBack.incrementBasePeriod();
            }
        }
        feedBack.setLastReqDevId(developers.get(index).getId());
        feedBack.updateTimeReq();
        developers.get(index).addInbox(feedBack);
        feedBack.setAccept(false);
    }
}
