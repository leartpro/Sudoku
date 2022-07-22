package app;

import controller.Controller;
import utils.TerminalUtils;

import java.io.InputStream;
import java.util.Arrays;

/**
 *
 */
public class Game implements InputHandler {

    private final UserInterface userInterface;
    private final Controller controller;
    private boolean inGame, getDifficulty;
    private final int[] point = {-1, -1, -1}; //[0]=column; [1]=row; [2]=value

    /**
     * @param input
     */
    public Game(InputStream input) {
        userInterface = new UserInterface(input, this);
        controller = new Controller(userInterface);
        inGame = false;
    }

    /**
     * @param input
     */
    @Override
    public void handel(String input) { //todo display used tips and mistakes in solution
        assert (input != null);
        if (input.charAt(0) == '.') handleCommands(input.substring(1));
        else if (inGame) handleGameInput(input);
        else if (getDifficulty) handleDifficultyInput(input);
        else {
            TerminalUtils.printWarning("invalid input");
            userInterface.displayUsages();
        }
    }

    private void handleDifficultyInput(String input) {
        int selected = -1;
        boolean validInput = true;
        try {
            selected = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            validInput = false;
            TerminalUtils.printWarning("input have to be a command or a number");
            userInterface.displayDifficultyUsages();
        }
        if(validInput) {
            if(selected > 0 && selected < 4) {
                this.controller.setDifficulty(selected);
                userInterface.displayDifficultyCompletion();
                this.getDifficulty = false;
            } else {
                TerminalUtils.printWarning("digit have to be between one and three");
            }
        }
    }

    /**
     * @param input
     */
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
        if(validInput) {
            if(
                    selected >= 1 && selected <= 9 ||
                    (point[0] != -1 && point[1] != -1 && point[2] == -1 && selected == 0)
            ) {
                userInterface.clear();
                for (int i = 0; i < point.length; i++) {
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
                if (isCompleted) {
                    if (controller.isAvailable(point[0]-1, point[1]-1)) {
                        controller.insert(point[0]-1, point[1]-1, point[2]);
                    } else {
                        TerminalUtils.printWarning("this point is not available");
                    }
                    //userInterface.displayGameInput(point);
                    Arrays.fill(point, -1);
                }
                userInterface.displayGame(controller.getCurrentGrid(), controller.getGivenGrid(), point);
                userInterface.displayGameInput(point);
            } else {
                TerminalUtils.printWarning("digit have to be between one and nine");
            }
        }
    }

    /**
     * @param input
     */
    private void handleCommands(String input) {
        switch (input.substring(0, input.contains(" ") ? input.indexOf(" ") : input.length())) {
            case "start" -> { //in menu -> to game
                if (!inGame) {
                    controller.generateNewPuzzle();
                    userInterface.clear();
                    userInterface.displayGameIntro(controller.getDifficulty());
                    userInterface.displayGameUsages();
                    userInterface.displayGame(controller.getCurrentGrid(), controller.getGivenGrid(), point);
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
                    userInterface.displayMenu();
                    userInterface.displayUsages();
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
                    if (controller.isSolved()) System.out.println("Your solution is correct!\n");
                    userInterface.displaySolution(
                            controller.getCurrentGrid(),
                            controller.getGivenGrid(),
                            controller.solvedGrid()
                    );
                    this.inGame = false;
                    userInterface.displayMenu();
                    userInterface.displayUsages();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "hint" -> {
                if(inGame) {
                    controller.giveHint();
                    userInterface.clear();
                    userInterface.displayGame(controller.getCurrentGrid(), controller.getGivenGrid(), point);
                    userInterface.displayGameInput(point);
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "difficulty" -> {
                if(!inGame) {
                    this.getDifficulty = true;
                    userInterface.introDifficulty();
                    userInterface.displayDifficultyUsages();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            default -> TerminalUtils.printWarning("the command '" + input + "' was not found");
        }
    }

    /**
     *
     */
    public void init() {
        userInterface.clear();
        userInterface.displayIntro();
        userInterface.displayUsages();
        inGame = false;
        userInterface.listen();
    }
}
