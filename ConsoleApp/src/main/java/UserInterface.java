import utils.ProgressBar;
import utils.TerminalColors;
import utils.TerminalUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserInterface {
    private final InputHandler handler;
    private final BufferedReader reader;
    private boolean status;

    public UserInterface(InputStream input, InputHandler handler) {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.handler = handler;
        this.status = true;
    }

    public void displayIntro() {
        TerminalUtils.printStatus("Welcome to Sudoku!" + "\n");
    }

    public void displayUsages() {
        System.out.println("These are the available commands...");
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
                        "(only in a game)" + "\n"
        );
    }

    public void displayGame(int[][] grid) { //TODO: improve the design
        StringBuilder builder = new StringBuilder();
        int j = 3, n = 0;
        for (int i = 0; i < 3; i++) {
            builder.append("+ - - - + - - - + - - - +\n");
            for (int row = n; row < j; row++) {
                builder.append("| ");
                for (int column = 0; column < 9; column++) {
                    builder.append(grid[row][column]).append(" ");
                    if(column == 2 || column == 5 || column == 8) builder.append("| ");
                }
                builder.append("\n");
            }
            j += 3;
            n += 3;
        }
        builder.append("+ - - - + - - - + - - - +");
        System.out.println(builder);
    }

    public void displayMenu() {
        System.out.println("You are in the menu");
    }

    public void displayLoading(String name) {
        ProgressBar progressBar = new ProgressBar(name);
        for (int i = 0; i < 100; i++) {
            progressBar.addProgress();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void displayLoading(String name, int totalSteps) {
        ProgressBar progressBar2 = new ProgressBar(name, totalSteps);
        int i = 0;
        while (!progressBar2.isCompleted()) {
            progressBar2.addProgress();
            if (i > 30) progressBar2.addProgress(12);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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

    public void close() {
        TerminalUtils.printWarning("Do not receive any input from the user anymore");
        status = false;
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayGameUsages() {
        System.out.println("Game Usages\n" +
                "type at first your row, then your column and finally " +
                "the value you want to insert");
    }

    public void displayGameInput(int[] inGameProgress) {
        //TODO: first 'X' in a different signal color to display the current step
        assert (inGameProgress.length == 3);
        System.out.println(
                "row " + (inGameProgress[0] == -1 ?
                        TerminalUtils.toColorString("X", TerminalColors.cyan) :
                        TerminalUtils.toColorString(String.valueOf(inGameProgress[0]), TerminalColors.green)) +
                        " column " + (inGameProgress[1] == -1 ?
                        TerminalUtils.toColorString("X", TerminalColors.cyan) :
                        TerminalUtils.toColorString(String.valueOf(inGameProgress[1]), TerminalColors.green)) +
                        " value " + (inGameProgress[2] == -1 ?
                        TerminalUtils.toColorString("X", TerminalColors.cyan) :
                        TerminalUtils.toColorString(String.valueOf(inGameProgress[2]), TerminalColors.green))
        );
    }

    public void displayGameIntro() {
        System.out.println("Your are now in a game");
    }

    public void clear() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public void displaySolution(int[][] solvedGrid) {
        System.out.println("Solution: ");
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(solvedGrid[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
