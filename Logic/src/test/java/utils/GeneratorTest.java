package utils;

import domain.Field;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneratorTest extends GridUtils {

    private final Generator generator = new Generator();

    @Test
    void generate2() { //todo: removes to much
        int[][] input = new int[][]{ //todo solver.calculateOneSolution() is very inefficient, lists and arrays copy also very inefficient
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
        int[][] expected = new int[][]{ //unsolved
                {4, 0, 0, 0, 9, 1, 0, 0, 0},
                {0, 0, 9, 0, 0, 7, 4, 2, 5},
                {0, 5, 8, 3, 4, 0, 1, 9, 0},
                {6, 9, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 9, 6, 4, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 9, 6, 3},
                {0, 8, 7, 0, 2, 6, 5, 3, 0},
                {3, 1, 5, 8, 0, 0, 6, 0, 0},
                {0, 0, 0, 1, 5, 0, 0, 0, 9}
        };
        Field[][] result = toComparableGrid(input);
        generator.createPuzzle2(result);
        Solver solver = new Solver(result);
        int[][] puzzle = mapComparableGrid(result);

        boolean success = true;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                try {
                    assertEquals(expected[x][y], puzzle[x][y]);
                } catch (AssertionFailedError e) {
                    System.err.println("Unexpected value=" + puzzle[x][y] + " on x=" + x + ", y=" + y + ". Should be value=" + expected[x][y]);
                    success = false;
                }
            }
        }
        //print grids
        System.out.println("is solvable: " + solver.isSolvable());
        List<Field[][]> solutions = new ArrayList<>(solver.allSolutions2());
        if (solutions.size() != 1 || !success) {
            System.out.println("invalid result: ");
            StringBuilder output = new StringBuilder();
            output.append("""
                    \r
                    Puzzle:                Solution:\r
                    """);
            int count = 0;
            for (Field[][] solution : solutions) {
                for (int x = 0; x < 9; x++) {
                    if (count == 0) {
                        for (int y = 0; y < 9; y++) {
                            output.append(puzzle[x][y]).append(" ");
                        }
                    } else {
                        output.append("  ".repeat(9));
                    }
                    output.append("     ");
                    for (int y = 0; y < 9; y++) {
                        output.append(solution[x][y].value()).append(" ");
                    }
                    output.append("\r\n");
                }
                output.append("\r\n");
                count++;
            }
            System.out.println(output);
        }
        if (success) System.out.println("test successfully finished");
        else throw new AssertionFailedError();
    }

    @Test
    void generate3() { //todo: removes to much
        int[][] input = new int[][]{ //todo solver.calculateOneSolution() is very inefficient, lists and arrays copy also very inefficient
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
        int[][] expected = new int[][]{ //unsolved
                {4, 0, 0, 0, 9, 1, 0, 0, 0},
                {0, 0, 9, 0, 0, 7, 4, 2, 5},
                {0, 5, 8, 3, 4, 0, 1, 9, 0},
                {6, 9, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 9, 6, 4, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 9, 6, 3},
                {0, 8, 7, 0, 2, 6, 5, 3, 0},
                {3, 1, 5, 8, 0, 0, 6, 0, 0},
                {0, 0, 0, 1, 5, 0, 0, 0, 9}
        };

        Field[][] result = generator.createPuzzle3(toComparableGrid(input));
        Solver solver = new Solver(result);
        int[][] puzzle = mapComparableGrid(result);

        StringBuilder output = new StringBuilder();
        output.append("""
                \r
                Actual:                Expected:\r
                """);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                output.append(puzzle[x][y]).append(" ");
            }
            output.append("     ");
            for (int y = 0; y < 9; y++) {
                output.append(expected[x][y]).append(" ");
            }
            output.append("\r\n");
        }
        output.append("\r\n");
        System.out.println(output);

        boolean success = true;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                try {
                    assertEquals(expected[x][y], puzzle[x][y]);
                } catch (AssertionFailedError e) {
                    System.err.println("Unexpected value=" + puzzle[x][y] + " on x=" + x + ", y=" + y + ". Should be value=" + expected[x][y]);
                    success = false;
                }
            }
        }
        //print grids
        System.out.println("is solvable: " + solver.isSolvable());
        List<Field[][]> solutions = new ArrayList<>(solver.allSolutions2());
        if (solutions.size() != 1 || !success) {
            System.out.println("invalid result: ");
            System.out.print("Solutions-List: ");
            solutions.forEach(System.out::print);
            output = new StringBuilder();
            output.append("""
                    \r
                    Puzzle:                Solution:\r
                    """);
            int count = 0;
            for (Field[][] solution : solutions) {
                for (int x = 0; x < 9; x++) {
                    if (count == 0) {
                        for (int y = 0; y < 9; y++) {
                            output.append(puzzle[x][y]).append(" ");
                        }
                    } else {
                        output.append("  ".repeat(9));
                    }
                    output.append("     ");
                    for (int y = 0; y < 9; y++) {
                        output.append(solution[x][y].value()).append(" ");
                    }
                    output.append("\r\n");
                }
                output.append("\r\n");
                count++;
            }
            System.out.println(output);
        }
        if (success) System.out.println("test successfully finished");
        else throw new AssertionFailedError();
    }
}