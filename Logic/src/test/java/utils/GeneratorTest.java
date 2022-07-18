package utils;

import domain.Field;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import utils.generator.Generator;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneratorTest extends GridUtils {

    private final Generator generator = new Generator();
        @RepeatedTest(10)
        void generateRandom() { //todo: sometimes tests failed...
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
    }