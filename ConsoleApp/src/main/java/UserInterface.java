import utils.TerminalUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserInterface {
    private final InputHandler handler;
    private BufferedReader reader;
    private boolean status;
    public UserInterface(InputStream input, InputHandler handler) {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.handler = handler;
        this.status = true;
    }

    public int getDifficulty() { //todo: ask user for difficulty
        return 0;
    }

    public void displayUsages() { //todo: print all usages/commands

    }

    public void displayTutorial() { //todo: print tutorial (game explanation and program usage)

    }

    private void listen() {
        Thread listen = new Thread(() -> {
            String message;
            while (status) {
                try {
                    message = reader.readLine();
                    if (message != null) {
                        handler.handel(message);
                    }
                } catch (IOException e) {
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
        TerminalUtils.printWarning("Do not receive any input from the user anymore");
    }
}
