package utils;

public abstract class TerminalUtils {
    /**
     * @param s
     * @param color
     * @return
     */
    public static String toColorString(String s, String color) {
        return color + s + TerminalColors.reset;
    }

    /**
     * @param message
     */
    public static void printStatus(String message) {
        System.out.println(toColorString(message, TerminalColors.cyan));
    }

    /**
     * @param message
     */
    public static void printWarning(String message) {
        System.out.println(toColorString(message, TerminalColors.yellow));
    }
}
