package ir.ac.kntu.utils;

import java.util.Scanner;

public class Scan {
    private final static Scanner SCAN = new Scanner(System.in);

    private Scan() {

    }

    public static int getInt() {
        return SCAN.nextInt();
    }

    public static String getLine() {
        return SCAN.nextLine();
    }

    public static String getNext() {
        return SCAN.next();
    }

    public static void scanClose() {
        SCAN.close();
    }
}
