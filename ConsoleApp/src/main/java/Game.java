import controller.Controller;
import utils.TerminalColors;
import utils.TerminalUtils;

import java.io.InputStream;

public class Game implements InputHandler {

    private final UserInterface userInterface;
    private final Controller controller;
    private boolean inGame;
    private int difficulty;

    public Game(InputStream input) {
        userInterface = new UserInterface(input, this);
        controller = new Controller();
        inGame = false;
        difficulty = 0;
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
        //todo: game input type in grid with rollback (grid)
    }

    private void handleGameInput(String input) {
        System.out.println("handle game input: " + input);
    }

    private void handleCommands(String input) {
        switch (input.substring(0, input.contains(" ") ? input.indexOf(" ") : input.length())) { //todo manage game-specific-input
            case "start" -> { //in menu -> to game
                if (!inGame) {
                    controller.generatePuzzle(difficulty);
                    userInterface.displayGame(controller.getGrid());
                    this.inGame = true;
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "menu" -> { //in game -> to menu
                if (inGame) {
                    //todo print sudoku results
                    this.inGame = false;
                    userInterface.displayMenu();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "commands" -> { //in game&menu -> display all commands
                userInterface.displayUsages();
            }
            case "exit" -> { //in game&menu -> exit program
                System.exit(0);
            }
            case "difficulty" -> { //in menu -> set difficult from 1 to 3
                if (!inGame) {
                    this.difficulty = userInterface.getDifficulty(controller.getDifficulty());
                    controller.setDifficulty(difficulty);
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "help" -> {
                boolean isValid = true;
                if (input.contains(" ")) {
                    if (input.substring(input.indexOf(" ")).isBlank()) {
                        isValid = false;
                    }
                } else {
                    isValid = false;
                }
                if(isValid) {
                    switch (input.substring(input.indexOf(" "))) {
                        case "solution" -> {
                            int[][] grid = controller.solvedGrid();
                            System.out.println("solution...");
                        }
                        case "tip" -> {
                            int[][] grid = controller.solvedGrid(); //compare with current and insert one
                            System.out.println("tip..");
                        }
                        default -> isValid = false;
                    }
                }
                if (!isValid) {
                    TerminalUtils.printWarning("this help command '"
                            + TerminalUtils.toColorString(input, TerminalColors.red) + "' was not found");
                    System.out.println(); //todo help usages
                    //todo improve output message for "help"-only call
                }
            }
            case "solve" -> { //in game -> finish game, return to menu, sout game states, mistakes and solution...
                this.inGame = false;
                if (controller.isSolved()) System.out.println();
            }
            default -> {
                TerminalUtils.printWarning("the command '" + input + "' was not found");
            }
        }

    }

    public void init() {
        userInterface.displayIntro();
        userInterface.displayUsages();
        difficulty = userInterface.getDifficulty(0);
        inGame = false;
        userInterface.listen();

    }
}
