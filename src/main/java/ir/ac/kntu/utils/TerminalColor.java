package ir.ac.kntu.utils;

public class TerminalColor {
    public static void cyan() {
        System.out.print("\u001B[36m");
    }

    public static void red() {
        System.out.print("\u001B[31m");
    }

    public static void green() {
        System.out.print("\u001B[32m");
    }

    public static void yellow() {
        System.out.print("\u001B[33m");
    }

    public static void blue() {
        System.out.print("\u001B[34m");
    }

    public static void purple() {
        System.out.print("\u001B[35m");
    }

    public static void reset() {
        System.out.print("\u001B[37m");
    }
}
