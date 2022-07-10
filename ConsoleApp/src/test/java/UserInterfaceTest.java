import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    private UserInterface userInterface;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.userInterface = new UserInterface(System.in, new Game(System.in));
        //TODO: better initialisation
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        //userInterface.clear();
        userInterface.close();
    }

    @org.junit.jupiter.api.Test
    void displayIntro() {
    }

    @org.junit.jupiter.api.Test
    void displayUsages() {
    }

    @org.junit.jupiter.api.Test
    void displayGame() {
        int[][] input = new int[][]{
                {4, 6, 2, 5, 9, 1, 3, 8, 7},
                {1, 3, 9, 6, 8, 7, 4, 2, 5},
                {7, 5, 8, 3, 4, 2, 1, 9, 6},
                {6, 9, 1, 7, 3, 8, 2, 5, 4},
                {5, 2, 3, 9, 6, 4, 7, 1, 8},
                {8, 7, 4, 2, 1, 5, 9, 6, 3},
                {9, 8, 7, 4, 2, 6, 5, 3, 1},
                {3, 1, 5, 8, 7, 9, 6, 4, 2},
                {2, 4, 6, 1, 5, 3, 8, 7, 9}
        };
        userInterface.displayGame(input);
    }

    @org.junit.jupiter.api.Test
    void displayMenu() {
    }

    @org.junit.jupiter.api.Test
    void displayLoading() {
    }

    @org.junit.jupiter.api.Test
    void testDisplayLoading() {
    }

    @org.junit.jupiter.api.Test
    void displayGameUsages() {
    }

    @org.junit.jupiter.api.Test
    void displayGameInput() {
    }

    @org.junit.jupiter.api.Test
    void displayGameIntro() {
    }

    @org.junit.jupiter.api.Test
    void displaySolution() {
    }
}