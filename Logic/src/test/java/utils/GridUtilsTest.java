package utils;

import domain.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridUtilsTest extends GridUtils{
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
        for(Field[] constrain : allConstrains(grid)) {
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
        for(Field[] constrain : constrainsOf(grid, grid[5][5])) {
            System.out.print("Constrain: ");
            for (Field field : constrain) {
                System.out.print(field + " ");
            }
            System.out.println();
        }
    }
}