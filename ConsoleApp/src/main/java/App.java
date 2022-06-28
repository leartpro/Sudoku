import controller.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) { //todo: args
        UserInterface userInterface = new UserInterface(System.in);
        startGame(userInterface);
    }

    private static void startGame(UserInterface userInterface) { //todo: complete method
        userInterface.displayUsages();
        int difficulty = userInterface.getDifficulty();
        Controller controller = new Controller(difficulty);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean status = true;
        while (status) {
            try {
                input = reader.readLine();
                if (input != null) process(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            status = userInterface.getStatus(); //todo: always possible to type exit e.g
        }
    }

    private static void process(String input) { //TODO: complete method

    }
}
