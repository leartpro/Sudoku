import controller.Controller;
import utils.TerminalUtils;

import java.io.InputStream;
import java.util.Arrays;

public class Game implements InputHandler {

    private final UserInterface userInterface;
    private final Controller controller;
    private boolean inGame;
    private final int[] point = {-1, -1, -1}; //[0]=column; [1]=row; [2]=value
    public Game(InputStream input) {
        userInterface = new UserInterface(input, this);
        controller = new Controller();
        inGame = false;
    }

    @Override
    public void handel(String input) { //todo display used tips and mistakes in solution
        assert (input != null);
        if (input.charAt(0) == '.') handleCommands(input.substring(1));
        else if (inGame) handleGameInput(input);
        else {
            TerminalUtils.printWarning("invalid input");
            userInterface.displayUsages();
        }
    }

    private void handleGameInput(String input) {
        int selected = -1;
        boolean validInput = true;
        try {
            selected = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            validInput = false;
            TerminalUtils.printWarning("input have to be a command or a number");
            userInterface.displayGameUsages();
        }
        if(validInput) { //TODO: point should reset
                for(int i = 0; i < point.length; i++) {
                    if (point[i] == -1) {
                        point[i] = selected;
                        break;
                    }
                }
            boolean isCompleted = true;
            for (int i : point) {
                if (i == -1) {
                    isCompleted = false;
                    break;
                }
            }
            if(isCompleted) {
                //TODO: set value on position in controller and display

                Arrays.fill(point, -1);
            }
            //todo: valid inputs are row, column and value all other inputs are commands
            userInterface.displayGame(controller.getGrid()); //todo: insert value by controller
            userInterface.displayGameInput(point);
        }
    }

    private void handleCommands(String input) {
        switch (input.substring(0, input.contains(" ") ? input.indexOf(" ") : input.length())) {
            case "start" -> { //in menu -> to game
                if (!inGame) {
                    controller.generateNewPuzzle();
                    userInterface.clear();
                    userInterface.displayGameIntro();
                    userInterface.displayGameUsages();
                    userInterface.displayGame(controller.getGrid());
                    userInterface.displayGameInput(point);
                    this.inGame = true;
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "menu" -> { //in game -> to menu
                if (inGame) {
                    //todo warn that all progress will be lost
                    this.inGame = false;
                    userInterface.clear();
                    userInterface.displayUsages();
                    userInterface.displayMenu();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "commands" -> //in game&menu -> display all commands
                    userInterface.displayUsages();
            case "exit" -> {//in game&menu -> exit program
                userInterface.clear();
                userInterface.close();
                System.exit(0);
            }
            case "solve" -> { //in game -> finish game, return to menu,
                // print game states, mistakes and solution...
                if(inGame) {
                    this.inGame = false;
                    if (controller.isSolved()) System.out.println("your solution is correct!");
                    else userInterface.displaySolution(controller.solvedGrid());
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            default -> TerminalUtils.printWarning("the command '" + input + "' was not found");
        }
    }

    public void init() {
        userInterface.clear();
        userInterface.displayIntro();
        userInterface.displayUsages();
        inGame = false;
        userInterface.listen();
    }
}
