package utils;

import domain.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridUtilsTest extends GridUtils {
    Field[][] grid;

    @BeforeEach
    void setUp() {
        grid = toComparableGrid(
                new int[][]{ //unsolved grid
                        {2, 9, 5, 7, 4, 3, 8, 6, 1},
                        {4, 3, 1, 8, 6, 5, 9, 0, 0},
                        {8, 7, 6, 1, 9, 2, 5, 4, 3},
                        {3, 8, 7, 4, 5, 9, 2, 1, 6},
                        {6, 1, 2, 3, 8, 7, 4, 9, 5},
                        {5, 4, 9, 2, 1, 6, 7, 3, 8},
                        {7, 6, 3, 5, 3, 4, 1, 8, 9},
                        {9, 2, 8, 6, 7, 1, 3, 5, 4},
                        {1, 5, 4, 9, 3, 8, 6, 0, 0}
                }
        );
    }

    @Test
    void compareTo() {
    }

    @Test
    void inList() {
        List<Field[][]> list = new ArrayList<>();
        list.add(
                toComparableGrid(
                        new int[][]{ //index 0
                                {2, 9, 5, 7, 4, 3, 8, 6, 1},
                                {4, 3, 1, 8, 6, 5, 9, 2, 7},
                                {8, 7, 6, 1, 9, 2, 5, 4, 3},
                                {3, 8, 7, 4, 5, 9, 2, 1, 6},
                                {6, 1, 2, 3, 8, 7, 4, 9, 5},
                                {5, 4, 9, 2, 1, 6, 7, 3, 8},
                                {7, 6, 3, 5, 3, 4, 1, 8, 9},
                                {9, 2, 8, 6, 7, 1, 3, 5, 4},
                                {1, 5, 4, 9, 3, 8, 6, 7, 2}
                        }
                )
        );
        list.add(
                toComparableGrid(
                        new int[][]{ //index 1
                                {2, 9, 5, 7, 4, 3, 8, 6, 1},
                                {4, 3, 1, 8, 6, 5, 9, 7, 2},
                                {8, 7, 6, 1, 9, 2, 5, 4, 3},
                                {3, 8, 7, 4, 5, 9, 2, 1, 6},
                                {6, 1, 2, 3, 8, 7, 4, 9, 5},
                                {5, 4, 9, 2, 1, 6, 7, 3, 8},
                                {7, 6, 3, 5, 3, 4, 1, 8, 9},
                                {9, 2, 8, 6, 7, 1, 3, 5, 4},
                                {1, 5, 4, 9, 3, 8, 6, 2, 7}
                        }
                )
        );
        Field[][] insert = toComparableGrid(
                new int[][]{ //insert equals to index 1 in list
                        {2, 9, 5, 7, 4, 3, 8, 6, 1},
                        {4, 3, 1, 8, 6, 5, 9, 2, 7},
                        {8, 7, 6, 1, 9, 2, 5, 4, 3},
                        {3, 8, 7, 4, 5, 9, 2, 1, 6},
                        {6, 1, 2, 3, 8, 7, 4, 9, 5},
                        {5, 4, 9, 2, 1, 6, 7, 3, 8},
                        {7, 6, 3, 5, 3, 4, 1, 8, 9},
                        {9, 2, 8, 6, 7, 1, 3, 5, 4},
                        {1, 5, 4, 9, 3, 8, 6, 7, 2}
                }
        );
        assertTrue(inList(list, insert)); //'list' already contains 'insert'
    }

    @Test
    void display() {
    }

    @Test
    void displaySmall() {
    }

    @Test
    void toComparableGrid() {
    }

    @Test
    void addAll() {
    }

    @Test
    void mapComperableGrid() {
    }

    @Test
    void isUnique() {
    }

    @Test
    void flatGrid() {
    }

    @Test
    void newInstanceOf() {
    }

    @Test
    void testMapComparableGrid() {
    }

    @Test
    void testAllConstrains() {
        for (Field[] constrain : allConstrains(grid)) {
            System.out.print("Constrain: ");
            for (Field field : constrain) {
                System.out.print(field + " ");
            }
            System.out.println();
        }
    }

    @Test
    void testMapComparableGrid1() {
    }

    @Test
    void testConstrainsOf() {
        for (Field[] constrain : constrainsOf(grid, grid[5][5])) {
            System.out.print("Constrain: ");
            for (Field field : constrain) {
                System.out.print(field + " ");
            }
            System.out.println();
        }
    }

    @Test
    void testSquares() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //TODO: invalid Grid ->2x 3 in x=7 y=2,4 value=3
                        {2, 9, 5, 7, 4, 3, 8, 6, 1},
                        {4, 3, 1, 8, 6, 5, 9, 2, 7},
                        {8, 7, 6, 1, 9, 2, 5, 4, 3},
                        {3, 8, 7, 4, 5, 9, 2, 1, 6},
                        {6, 1, 2, 3, 8, 7, 4, 9, 5},
                        {5, 4, 9, 2, 1, 6, 7, 3, 8},
                        {7, 6, 3, 5, 3, 4, 1, 8, 9},
                        {9, 2, 8, 6, 7, 1, 3, 5, 4},
                        {1, 5, 4, 9, 3, 8, 6, 7, 2}
                }
        );
        displaySmall(grid);
        List<Field[][]> squares = new ArrayList<>(squares(grid));
        for (Field[][] square : squares) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    System.out.print(square[x][y].value() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    @Test
    void testAllUnique() {
        Field[][] grid = toComparableGrid(
                new int[][]{
                        {4, 0, 0, 0, 9, 1, 0, 0, 0},
                        {0, 0, 9, 0, 0, 7, 4, 2, 5},
                        {0, 5, 8, 3, 4, 0, 1, 9, 0},
                        {6, 9, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 9, 6, 4, 7, 0, 0},
                        {0, 0, 0, 0, 0, 0, 9, 6, 3},
                        {0, 8, 7, 0, 2, 6, 5, 3, 0},
                        {3, 1, 5, 8, 0, 0, 6, 0, 0},
                        {0, 0, 0, 1, 5, 0, 0, 0, 9}
                }
        );
        assertTrue(allUnique(grid));
    }

    @Test
    void testUniqueSolution() {
        Field[][] grid = toComparableGrid(
                new int[][]{
                        {0/*7*/, 8, 5, 3, 6, 1, 4, 2, 9},
                        {9, 1, 4, 7, 8, 2, 6, 5, 3},
                        {3, 6, 2, 9, 4, 5, 8, 1, 7},
                        {1, 2, 8, 4, 7, 6, 5, 3, 0},
                        {4, 3, 9, 5, 2, 8, 1, 6, 0},
                        {5, 7, 6, 1, 3, 9, 2, 8, 4},
                        {8, 9, 7, 6, 1, 4, 3, 0, 5},
                        {6, 4, 3, 2, 5, 7, 9, 0, 1},
                        {2, 5, 1, 8, 9, 3, 7, 4, 6}
                }
        );
        displaySmall(new Solver(grid).solve(grid));
        //var v = uniqueSolution(grid);
        //assertTrue(v);
    }

    @Test
    void testIsSolvable() {
        Field[][] grid = toComparableGrid(
                new int[][]{
                        {7/*7*/, 8, 5, 3, 6, 1, 4, 2, 9},
                        {9, 1, 4, 7, 8, 2, 6, 5, 3},
                        {3, 6, 2, 9, 4, 5, 8, 1, 7},
                        {1, 2, 8, 4, 7, 6, 5, 3, 0/*9*/},
                        {4, 3, 9, 5, 2, 8, 1, 6, 0/*7*/},
                        {5, 7, 6, 1, 3, 9, 2, 8, 4},
                        {8, 9, 7, 6, 1, 4, 3, 0/*2*/, 5},
                        {6, 4, 3, 2, 5, 7, 9, 0/*8*/, 1},
                        {2, 5, 1, 8, 9, 3, 7, 4, 6}
                }
        );
        displaySmall(new Solver(grid).solve(grid));
        var v = isSolvable(grid);
        assertTrue(v);
    }
}