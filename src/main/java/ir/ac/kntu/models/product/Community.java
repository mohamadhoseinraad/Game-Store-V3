package ir.ac.kntu.models.product;

import ir.ac.kntu.utils.TerminalColor;

public class Community {
    private String username;

    private String comment;

    public Community(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "User: " + username + " - " + comment;
    }

    public void showComment() {
        TerminalColor.yellow();
        System.out.println("        User " + username + " say :");
        TerminalColor.reset();
        System.out.println("                " + comment);
        TerminalColor.reset();
    }
}
