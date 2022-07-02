package utils;

import domain.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest extends GridUtils{

    private Generator generator = new Generator();

    @Test
    void generate() {
        int[][] input = new int[][]{ //solution
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
        Field[][] result = toComparableGrid(input);
        generator.createPuzzle2(result);
        Solver solver = new Solver(result);
        System.out.println("is solvable: " + solver.isSolvable());
        for() {

        int[][] solution = addAll(input, solver.solve());
        int[][] puzzle = mapComperableGrid(result);
        StringBuilder output = new StringBuilder();
        output.append("Puzzle:              Solution:" + "\r\n");
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                output.append(puzzle[x][y]).append(" ");
            }
            output.append("     ");
            for (int y = 0; y < 9; y++) {
                output.append(solution[x][y]).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }
}