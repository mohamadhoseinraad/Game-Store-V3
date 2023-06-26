package ir.ac.kntu.models.product;

import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.Store;
import ir.ac.kntu.models.User;
import ir.ac.kntu.models.product.games.Game;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class FeedBack {

    private Period baseperiod = Period.of(0, 0, 5);

    private final String massage;

    private final String userFeedBackId;

    private final String gameId;

    private String lastReqDevId;

    private String adminId;

    private boolean isAccept = false;

    private boolean isSolved = false;

    private LocalDate timeReq;


    public FeedBack(String massage, User user, Game game, ArrayList<Admin> developers) {
        this.massage = massage;
        this.userFeedBackId = user.getId();
        this.gameId = game.getId();
        timeReq = java.time.LocalDate.now();
        game.addFeedBack(this, developers);

    }

    public void updateTimeReq() {
        timeReq = java.time.LocalDate.now();
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public String getLastReqDevId() {
        return lastReqDevId;
    }

    public void setLastReqDevId(String lastReqDevId) {
        this.lastReqDevId = lastReqDevId;
    }

    public Period getBaseperiod() {
        return baseperiod;
    }

    public void incrementBasePeriod() {
        baseperiod.multipliedBy(2);
    }

    public String getMassage() {
        return massage;
    }

    public String getUserFeedBackId() {
        return userFeedBackId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void solvedFeedback(Admin admin, Game game) {
        isSolved = true;
        admin.solvedScheduledEvent(this);
        game.removeFeedBack(this);
    }

    public void adminAccept(Admin admin) {
        isAccept = true;
        isSolved = false;
        adminId = admin.getId();
        admin.addScheduledEvent(this);
    }

    public boolean isExpired() {
        if (!isAccept) {
            LocalDate now = LocalDate.now();
            Period diff = Period.between(timeReq, now);
            return !(diff.getYears() <= baseperiod.getYears() &&
                    diff.getMonths() <= baseperiod.getMonths() &&
                    diff.getDays() <= baseperiod.getDays());
        }
        return false;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "massage='" + massage + '\'' +
                ", userFeedBackId='" + userFeedBackId + '\'' +
                ", gameId='" + gameId + '\'' +
                ", lastReqDevId='" + lastReqDevId + '\'' +
                ", adminId='" + adminId + '\'' +
                ", isAccept=" + isAccept +
                ", isSolved=" + isSolved +
                ", timeReq=" + timeReq +
                ", isExpired=" + isExpired() +
                '}';
    }
}
