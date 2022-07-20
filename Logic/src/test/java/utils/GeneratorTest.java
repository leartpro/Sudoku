package utils;

import domain.Field;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import utils.generator.Generator;

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

        @RepeatedTest(10)
        void generate(){
            Field[][] result = generator.generate();
            displaySmall(result);
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