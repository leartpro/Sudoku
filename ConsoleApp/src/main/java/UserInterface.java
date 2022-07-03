import utils.GridUtils;
import utils.ProgressBar;
import utils.TerminalColors;
import utils.TerminalUtils;

import java.io.*;

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

    public void displayUsages() { //todo: print all program usages/commands (currently copy/paste from tic-tac-toe)
        System.out.println("These are the available commands...");
        System.out.println(TerminalUtils.toColorString("  :q(uit)", TerminalColors.blue) + ",        quit the program");
        System.out.println(TerminalUtils.toColorString("  :m(enu)", TerminalColors.blue) + ",        go back to the menu            (only in a game)");
        System.out.println(TerminalUtils.toColorString("  :r(estart)", TerminalColors.blue) + ",     restart the game               (only in a game)");
        System.out.println(TerminalUtils.toColorString("  :s(urrender)", TerminalColors.blue) + ",   restart but keep the opponent  (only in a game)");
        System.out.println(TerminalUtils.toColorString("  :c(commands)", TerminalColors.blue) + ",   list all commands              (only in the main menu)");
        System.out.println(TerminalUtils.toColorString("  :g(game)", TerminalColors.blue) + ",       start a new game               (only in the main menu)");
        System.out.println(TerminalUtils.toColorString("  :o(pponent)", TerminalColors.blue) + ",    play again with same opponent  (only in the main menu, after a game)");
        System.out.println(TerminalUtils.toColorString("  :h(history)", TerminalColors.blue) + ",    show history of last game      (only in the main menu, after a game)");
    }

    public void displayGame(int[][] grid) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
    }

    public void displayMenu() {
        System.out.println("You are in the menu");
    }

    public void displayLoading(String name) {
        ProgressBar progressBar = new ProgressBar(name);
        for(int i = 0; i < 100; i++) {
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
        while(!progressBar2.isCompleted()) {
            progressBar2.addProgress();
            if(i > 30) progressBar2.addProgress(12);
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
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayGameUsages() {
        System.out.println("type at first your row, then your column and finally " +
                "the value you want to insert");
    }

    public void displayGameInput() {
        System.out.print("column " + TerminalUtils.toColorString("X", TerminalColors.cyan) +
                            " row " + TerminalUtils.toColorString("X", TerminalColors.black) +
                            " value " + TerminalUtils.toColorString("X", TerminalColors.black) + "\r");
    }
}
