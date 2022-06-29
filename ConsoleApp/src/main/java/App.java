import utils.ProgressBar;

public class App{
    public void main(String[] args) { //todo: args
        ProgressBar progressBar = new ProgressBar("ExampleProgress");
        for(int i = 0; i < 100; i++) {
            progressBar.addProgress();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Game game = new Game(System.in);
        game.start();
    }
}
