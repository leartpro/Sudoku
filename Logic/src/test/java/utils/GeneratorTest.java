package utils;

import domain.Field;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import utils.generator.Generator;
import utils.solver.Solver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorTest extends GridUtils {

    private final Generator generator = new Generator();
        @RepeatedTest(1)
        void completeRandom() { //todo: sometimes tests failed...
            int[][] input = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0}
            };

            Field[][] result = toComparableGrid(input);
            generator.completeRandom(result);
            displaySmall(result);
            assertTrue(allUnique(result));
            assertTrue(isSolved(result));
        }

        @RepeatedTest(1)
        void generate(){
            Field[][] result = generator.generate();
            System.out.flush();
            System.out.println("Puzzle:");
            displaySmall(result);
            System.out.println("Solution:");
            displaySmall(new Solver(result).solve(result));
            try {
                boolean allUnique = allUnique(result);
                assertTrue(allUnique);
            } catch (AssertionFailedError e) {
                throw new AssertionFailedError();
            }
            try {
                boolean isSolvable = isSolvable(result);
                assertTrue(isSolvable);
            } catch (AssertionFailedError e) {
                throw new AssertionFailedError();
            }
            try {
                boolean isSolved = isSolved(result);
                 assertFalse(isSolved);
            } catch (AssertionFailedError e) {
                throw new AssertionFailedError();
            }

        }

    @Test
    void isInFutureSolvable() {
        int[][] input = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        Field[][] result = toComparableGrid(input);
        generator.completeRandom(result);
        displaySmall(result);

    }
}