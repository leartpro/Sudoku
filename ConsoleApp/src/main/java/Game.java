import controller.Controller;

import java.io.InputStream;

public class Game implements InputHandler{

    private final UserInterface userInterface;
    private final Controller controller;

    public Game(InputStream input) {
        this.userInterface = new UserInterface(input, this);
        this.controller = new Controller();
    }

    @Override
    public void handel(String input) {
        
    }

    public void start() {
    }
}
