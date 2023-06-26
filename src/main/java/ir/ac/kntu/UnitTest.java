package ir.ac.kntu;

import ir.ac.kntu.HelperClasses.DefaultData;
import ir.ac.kntu.HelperClasses.FeedBacksHelper;
import ir.ac.kntu.HelperClasses.GameHelper;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.FeedBack;
import ir.ac.kntu.models.product.games.Game;
import org.testng.annotations.Test;
//import static org.junit.Assert.*;
import java.util.Collections;

import static org.testng.AssertJUnit.assertEquals;

public class UnitTest {

    @Test
    public void feedBackAssignTest() {
        Store storeDB = DefaultData.addDefaultData();
        User user = storeDB.findUserByUsername("1");
        Game game = storeDB.getGames().get(2);
        Admin admin = (Admin) storeDB.findUserByUsername("DEV");
        game.addDeveloper(admin);
        FeedBack feedBack = new FeedBack("input", user, game, storeDB.gameDevelopers(game, true));
        FeedBacksHelper.handleFeedBack(game, storeDB.gameDevelopers(game, true));
        assertEquals(feedBack.getLastReqDevId(),game.creatorId);
        Collections.sort(storeDB.gameDevelopers(game, true));
        storeDB.gameDevelopers(game, true).get(0).deAcceptFeedback(feedBack, game, storeDB.gameDevelopers(game, true));
        assertEquals(feedBack.getLastReqDevId(),admin.getId());
    }

    @Test
    public void applyPriceForUserLevel() {
        Store storeDB = DefaultData.addDefaultData();
        User user = storeDB.findUserByUsername("1");
        user.setScore(30);
        Game game = storeDB.getGames().get(2);
        double originalPrice = game.getPrice();
        assertEquals(originalPrice * 0.9, GameHelper.applyOffer(originalPrice, 30));
    }


}
