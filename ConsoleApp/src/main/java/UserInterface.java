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
        displayIntro();
    }

    private void displayIntro() {
        TerminalUtils.printStatus("Welcome to Sudoku!" + "\n");
    }

    public int getDifficulty() { //todo: ask user for difficulty
        return 0;
    }

    public void displayUsages() { //todo: print all program usages/commands

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

    public void displayGame(int[][] grid) {
    }

    public void displayMenu() {
    }

    public void pause() {
    }

    public void resume() {
    }
}
