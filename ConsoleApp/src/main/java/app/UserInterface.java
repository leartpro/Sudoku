package app;

import controller.ProgressMonitor;
import utils.ProgressBar;
import utils.TerminalColors;
import utils.TerminalUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Stores print methods and a listen-thread on the given InputStream
 */
public class UserInterface implements ProgressMonitor {
    private final InputHandler handler;
    private final BufferedReader reader;
    private boolean status;
    private ProgressBar progressBar;

    /**
     * @param input the InputStream to listen to
     * @param handler the Handler to handle the input
     */
    public UserInterface(InputStream input, InputHandler handler) {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.handler = handler;
        this.status = true;
    }

    /**
     * displays the game intro
     */
    public void displayIntro() {
        TerminalUtils.printStatus("Welcome to Sudoku!");
    }

    /**
     * displays all usages
     */
    public void displayUsages() {
        TerminalUtils.printStatus("\nThese are the available commands:");
        System.out.println(
                TerminalUtils.toColorString(".start", TerminalColors.blue) + " ".repeat(11) +
                        "start a game" + " ".repeat(18) +
                        "(only in the main menu)" + "\n" +
                        TerminalUtils.toColorString(".menu", TerminalColors.blue) + " ".repeat(12) +
                        "go back to the menu" + " ".repeat(11) +
                        "(only in a game)" + "\n" +
                        TerminalUtils.toColorString(".commands", TerminalColors.blue) + " ".repeat(8) +
                        "list all commands" + " ".repeat(13) +
                        "(everywhere)" + "\n" +
                        TerminalUtils.toColorString(".exit", TerminalColors.blue) + " ".repeat(12) +
                        "exit the program" + " ".repeat(14) +
                        "(everywhere)" + "\n" +
                        TerminalUtils.toColorString(".solve", TerminalColors.blue) + " ".repeat(11) +
                        "validate your solution" + " ".repeat(8) +
                        "(only in a game)" + "\n" +
                        TerminalUtils.toColorString(".hint", TerminalColors.blue) + " ".repeat(12) +
                        "solve one field" + " ".repeat(15) +
                        "(only in a game)" + "\n" +
                        TerminalUtils.toColorString(".difficulty", TerminalColors.blue) + " ".repeat(6) +
                        "set the difficulty" + " ".repeat(12) +
                        "(only in the main menu)" + "\n"
        );
    }

    /**
     * displays the game
     * @param current the grid edited by the user
     * @param given generated grid
     */
    public void displayGame(int[][] current, int[][] given, int[] selected) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        int j = 3, n = 0;
        for (int i = 1; i <= 9; i++) {
            if (i == 1) builder.append("  | ");
            if (i == 4 || i == 7) builder.append("| ");
            builder.append(
                    TerminalUtils.toColorString(
                            i + " ",
                            (selected[1] == i ? TerminalColors.red : TerminalColors.purple)
                    )
            );
        }
        builder.append("|\n");
        for (int i = 0; i < 3; i++) {
            builder.append("- + - - - + - - - + - - - +\n");
            for (int row = n; row < j; row++) {
                builder.append(
                                TerminalUtils.toColorString(
                                        String.valueOf(row + 1),
                                        (selected[0] == (row + 1) ? TerminalColors.red : TerminalColors.purple)
                                )
                        )
                        .append(" | ");
                for (int column = 0; column < 9; column++) {
                    builder.append(
                                    TerminalUtils.toColorString(
                                            String.valueOf(current[row][column]),
                                            given[row][column] == 0 ?
                                                    (
                                                            (selected[0] == (row+1) && selected[1] == (column+1)) ?
                                                                    TerminalColors.blue :
                                                                    (
                                                                            current[row][column] == 0 ?
                                                                                    TerminalColors.white :
                                                                                    TerminalColors.cyan
                                                                    )
                                                    ) : TerminalColors.black
                                    )
                            )
                            .append(" ");
                    if (column == 2 || column == 5 || column == 8) builder.append("| ");
                }
                builder.append("\n");
            }
            j += 3;
            n += 3;
        }
        builder.append("- + - - - + - - - + - - - +");
        System.out.println(builder);
    }

    /**
     * displays the menu intro
     */
    public void displayMenu() {
        TerminalUtils.printStatus("\nYou are in the menu!");
    }

    /**
     * @param name the name of the progress
     * @param totalSteps the count of steps to complete the progress
     */
    @Override
    public void displayLoading(String name, int totalSteps) {
        this.progressBar = new ProgressBar(name, totalSteps);
        this.progressBar.display();
    }

    /**
     * increase the progress by one
     */
    @Override
    public void increaseProgress() {
        this.progressBar.addProgress();
    }

    /**
     * completes the progress
     */
    @Override
    public void completeProgress() {
        if (!this.progressBar.isCompleted()) this.progressBar.complete();
    }

    /**
     * listen in a thread to the input from the given InputStream
     * on receive calls the handle method
     */
    public void listen() {
        Thread listen = new Thread(() -> {
            String message;
            while (status) {
                System.out.println("Type your input:");
                try {
                    message = reader.readLine();
                    if (message != null && !message.isBlank()) {
                        handler.handel(message);
                    }
                } catch (IOException e) {
                    TerminalUtils.printWarning("Do not receive any input from the user anymore");
                    status = false;
                    e.printStackTrace();
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        listen.start();
    }

    /**
     * stops the listen thread
     */
    public void close() {
        TerminalUtils.printWarning("Do not receive any input from the user anymore");
        status = false;
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * display the game usages
     */
    public void displayGameUsages() {
        System.out.println("""
                Game Usages:
                 - Type at first your row
                 - Then your column
                 - And finally the value you want to insert""");
    }

    /**
     * @param inGameProgress the value to edit
     */
    public void displayGameInput(int[] inGameProgress) {
        //TODO: first 'X' in a different signal color to display the current step
        assert (inGameProgress.length == 3);
        System.out.println(
                "\nrow " + (inGameProgress[0] == -1 ?
                        TerminalUtils.toColorString("X", TerminalColors.blue) :
                        TerminalUtils.toColorString(String.valueOf(inGameProgress[0]), TerminalColors.green)) +
                        " column " + (inGameProgress[1] == -1 ?
                        TerminalUtils.toColorString("X", (inGameProgress[0] == -1 ? TerminalColors.cyan : TerminalColors.blue)) :
                        TerminalUtils.toColorString(String.valueOf(inGameProgress[1]), TerminalColors.green)) +
                        " value " + (inGameProgress[2] == -1 ?
                        TerminalUtils.toColorString("X", (inGameProgress[1] == -1 ? TerminalColors.cyan : TerminalColors.blue)) :
                        TerminalUtils.toColorString(String.valueOf(inGameProgress[2]), TerminalColors.green))
        );
    }

    /**
     * @param difficulty the current difficulty
     */
    public void displayGameIntro(int difficulty) {
        TerminalUtils.printStatus("Your are now in a game");
        System.out.println("Your difficulty is " +
                (
                        difficulty == 1 ? "Noob" :
                                difficulty == 2 ? "Easy" :
                                        difficulty == 3 ? "Medium" :
                                                TerminalUtils.toColorString("UNSET", TerminalColors.red)
                )
        );
    }

    /**
     * clears the console
     */
    public void clear() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * @param current the grid edited by the user
     * @param given the generated grid
     * @param solved the solved grid
     */
    public void displaySolution(int[][] current, int[][] given, int[][] solved) {
        System.out.println("Solution:\n");
        StringBuilder builder = new StringBuilder();
        int j = 3, n = 0;
        builder.append("  | ").append(TerminalUtils.toColorString("1 2 3", TerminalColors.purple))
                .append(" | ").append(TerminalUtils.toColorString("4 5 6", TerminalColors.purple))
                .append(" | ").append(TerminalUtils.toColorString("7 8 9", TerminalColors.purple)).append(" |\n");
        for (int i = 0; i < 3; i++) {
            builder.append("- + - - - + - - - + - - - +\n");
            for (int row = n; row < j; row++) {
                builder.append(TerminalUtils.toColorString(String.valueOf(row + 1), TerminalColors.purple)).append(" | ");
                for (int column = 0; column < 9; column++) {
                    builder.append(
                                    TerminalUtils.toColorString
                                            (
                                                    String.valueOf(solved[row][column]),
                                                    given[row][column] == 0 ?
                                                            (current[row][column] == solved[row][column] ?
                                                                    TerminalColors.green :
                                                                    TerminalColors.red) :
                                                            TerminalColors.black
                                            )
                            )
                            .append(" ");
                    if (column == 2 || column == 5 || column == 8) builder.append("| ");
                }
                builder.append("\n");
            }
            j += 3;
            n += 3;
        }
        builder.append("- + - - - + - - - + - - - +\n");
        System.out.println(builder);
    }

    /**
     * displays the difficulty intro
     */
    public void introDifficulty() {
        TerminalUtils.printStatus("Choose your difficulty you want to play on");
    }

    /**
     * displays the difficulty usages
     */
    public void displayDifficultyUsages() {
        System.out.println("""
                Please enter one of the following digits:
                1 - Noob
                2 - Easy
                3 - Medium
                """);
    }

    /**
     * displays on difficulty input completion
     */
    public void displayDifficultyCompletion() {
        System.out.println(
                TerminalUtils.toColorString(
                        "Your difficulty was set.",
                        TerminalColors.green
                )
        );
    }
}
