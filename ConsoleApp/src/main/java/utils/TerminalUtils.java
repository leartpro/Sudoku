package utils;

/**
 * contains methods for colorizing
 */
public abstract class TerminalUtils {
    /**
     * @param s string to colorize
     * @param color the color to use
     * @return the given string in the given color
     */
    public static String toColorString(String s, String color) {
        return color + s + TerminalColors.reset;
    }

    /**
     * @param message the status message to print
     */
    public static void printStatus(String message) {
        System.out.println(toColorString(message, TerminalColors.cyan));
    }

    /**
     * @param message the warning to print
     */
    public static void printWarning(String message) {
        System.out.println(toColorString(message, TerminalColors.yellow));
    }
}
