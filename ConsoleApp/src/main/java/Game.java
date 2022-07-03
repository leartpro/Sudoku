import controller.Controller;
import utils.GridUtils;
import utils.TerminalUtils;

import java.io.InputStream;

public class Game implements InputHandler {

    private final UserInterface userInterface;
    private final Controller controller;
    private boolean inGame;

    public Game(InputStream input) {
        userInterface = new UserInterface(input, this);
        controller = new Controller();
        inGame = false;
    }

    @Override
    public void handel(String input) { //todo count time needed to solve and used tips and mistakes in solution
        assert (input != null);
        if (input.charAt(0) == '.') handleCommands(input.substring(1));
        else if (inGame) handleGameInput(input);
        else {
            TerminalUtils.printWarning("invalid input");
            userInterface.displayUsages();
        }
    }

    private void handleGameInput(String input) {
        int selected = Integer.parseInt(input);
        //todo: valid inputs are row, column and value all other inputs are commands
        userInterface.displayGameInput();
    }

    private void handleCommands(String input) {
        switch (input.substring(0, input.contains(" ") ? input.indexOf(" ") : input.length())) {
            case "start" -> { //in menu -> to game
                if (!inGame) {
                    controller.generateNewPuzzle();
                    userInterface.displayGameUsages();
                    userInterface.displayGame(controller.getGrid());
                    this.inGame = true;
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "menu" -> { //in game -> to menu
                if (inGame) {
                    //todo warn that all progress will be lost
                    this.inGame = false;
                    userInterface.displayMenu();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "commands" -> //in game&menu -> display all commands
                    userInterface.displayUsages();
            case "exit" -> {//in game&menu -> exit program
                userInterface.close();
                System.exit(0);
            }
            case "solve" -> { //in game -> finish game, return to menu,
                // print game states, mistakes and solution...
                this.inGame = false;
                if (controller.isSolved()) System.out.println();
            }
            default -> TerminalUtils.printWarning("the command '" + input + "' was not found");
        }
    }

    public void init() {
        userInterface.displayIntro();
        userInterface.displayUsages();
        inGame = false;
        userInterface.listen();
    }
}
