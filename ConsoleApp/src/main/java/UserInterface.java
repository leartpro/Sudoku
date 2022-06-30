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

    public void displayUsages() { //todo: print all program usages/commands

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

    public int getDifficulty(int current) { //todo: ask user for difficulty an show current
        return 0;
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
                    if (message != null) {
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
}
