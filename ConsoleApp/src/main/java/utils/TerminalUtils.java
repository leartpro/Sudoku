package utils;

import java.security.Timestamp;

public abstract class TerminalUtils {
    public static String toColorString(String s, String color) {
        return color + s + TerminalColors.reset;
    }

    public static void printStatus(String message) {
        System.out.println(toColorString(message, TerminalColors.cyan));
    }

    public static void printError(String message) {
        System.out.println(toColorString(message, TerminalColors.red));
    }

    public static void printWarning(String message) {
        System.out.println(toColorString(message, TerminalColors.yellow));
    }


    private static void inputMarker() {
        System.out.print(toColorString("> ", TerminalColors.green));
    }
}
