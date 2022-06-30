public class App{
    public static void main(String[] args) { //todo: args
        /*ProgressBar progressBar = new ProgressBar("ExampleProgress");
        for(int i = 0; i < 100; i++) {
            progressBar.addProgress();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ProgressBar progressBar2 = new ProgressBar("ExampleProgress", 5000);
        int i = 0;
        while(!progressBar2.isCompleted()) {
            progressBar2.addProgress();
            if(i > 30) progressBar2.addProgress(12);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }*/
        Game game = new Game(System.in);
        game.init();
    }
}
