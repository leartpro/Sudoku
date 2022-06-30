import controller.Controller;
import utils.TerminalUtils;

import java.io.InputStream;

public class Game implements InputHandler{

    private final UserInterface userInterface;
    private final Controller controller;
    private boolean inGame = false;
    private int difficulty = 0;


    public Game(InputStream input) {
        this.userInterface = new UserInterface(input, this);
        this.controller = new Controller();
    }

    @Override
    public void handel(String input) { //todo count time needed to solve and used tips and mistakes in solution
        assert (input != null);
        System.out.println("your input is: " + input);
        if(input.charAt(0) == '.') handleCommands(input.substring(1));
        else if(inGame) handleGameInput(input);
        else TerminalUtils.printWarning("invalid input");
        //todo: game input type in grid with rollback (grid)
    }

    private void handleGameInput(String input) {

    }

    private void handleCommands(String input) {
        switch(input) { //todo manage game-specific-input
            case "start" -> { //in menu -> to game
                if (!inGame) {
                    controller.generatePuzzle(difficulty);
                    controller.start();
                    userInterface.displayGame(controller.getGrid());
                    this.inGame = true;
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "menu" -> { //in game -> to menu
                if(inGame) {
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
                if(!inGame) {
                    this.difficulty = userInterface.getDifficulty();
                    controller.setDifficulty(difficulty);
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "help solution" -> { //in game -> print solution
                System.out.println();
            }
            case "help tip" -> { //in game -> complete one random point
                System.out.println();
            }
            case "solve" -> { //in game -> finish game, return to menu, sout game states, mistakes and solution...
                this.inGame = false;
                System.out.println();
            }
            case "pause" -> { //in game -> pause game and blur grid
                if(inGame) {
                    controller.pause();
                    userInterface.pause();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
            case "continue" -> { //in game -> available if game is paused
                if(inGame) {
                    controller.resume();
                    userInterface.resume();
                } else {
                    TerminalUtils.printWarning("can not use that command here");
                }
            }
        }
    }

    public void init() {
        userInterface.displayUsages();
        userInterface.listen();
        this.difficulty = userInterface.getDifficulty();
        inGame = false;
    }
}
