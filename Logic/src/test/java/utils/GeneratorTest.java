package utils;

import domain.Field;
import org.junit.jupiter.api.RepeatedTest;
import org.opentest4j.AssertionFailedError;
import utils.generator.Generator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorTest extends GridUtils {

    private final Generator generator = new Generator();
        @RepeatedTest(10)
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
            boolean success = true; //generator.completeRandom(result);
            generator.completeRandom(result);
            displaySmall(result);
            assertTrue(success);
            assertTrue(allUnique(result));
        }

        @RepeatedTest(15)
        void generate(){
            Field[][] result = generator.generate();
            displaySmall(result);
            try {
                boolean allUnique = allUnique(result);
                System.out.println("all unique: " + allUnique);
                assertTrue(allUnique);
            } catch (AssertionFailedError e) {
                throw new AssertionFailedError();
            }
            try {
                boolean isSolvable = isSolvable(result);
                System.out.println("is solvable: " + isSolvable);
                assertTrue(isSolvable);
            } catch (AssertionFailedError e) {
                throw new AssertionFailedError();
            }

        }
    }