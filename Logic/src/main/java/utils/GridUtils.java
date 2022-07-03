package utils;

import domain.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GridUtils {
    public List<Field> compareTo(Field[][] key, Field[][] value) { //return the values from key
        List<Field> differences = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            for(int y  =0; y < 9; y++) {
                if(!value[x][y].equals(key[x][y])) differences.add(key[x][y]);
            }
        }
        return differences;
    }

    public boolean inList(List<Field[][]> list, Field[][] value) { //todo: work probably wrong
        for(Field[][] current : list) {
            boolean equals = true;
            for(int x = 0; x < 9; x++) {
                for(int y = 0; y < 9; y++) {
                    if (current[x][y].value() != value[x][y].value()) {
                        equals = false;
                        break;
                    }
                }
            }
            if(equals) return true;
        }
        return false;
    }

    public void display(Field[][] grid) {
        StringBuilder output = new StringBuilder();
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                output.append(grid[x][y]).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }

    public void displaySmall(Field[][] grid) {
        StringBuilder output = new StringBuilder();
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                output.append(grid[x][y].value()).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }

    public Field[][] toComparableGrid(int[][] grid) {
        Field[][] result = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = new Field(x, y, grid[x][y]);
            }
        }
        return result;
    }

    public int[][] addAll(int[][] grid, List<Field> inserts) {
        if(inserts == null) return grid;
        inserts.forEach(i -> {
            if(grid[i.x()][i.y()] == 0) grid[i.x()][i.y()] = i.value();
        });
        return grid;
    }

    public int[][] mapComparableGrid(Field[][] grid) {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = grid[x][y].value();
            }
        }
        return result;
    }

    public boolean isUnique(Field[][] grid, Field insert) {
        for (int y = 0; y < 9; y++) {
            if (grid[insert.x()][y].value() == insert.value()) return false;
        }
        for (int x = 0; x < 9; x++) {
            if (grid[x][insert.y()].value() == insert.value()) return false;
        }

        int squareXStart = insert.x() - insert.x() % 3;
        int squareYStart = insert.y() - insert.y() % 3;

        for (int x = squareXStart; x < squareXStart + 3; x++) {
            for (int y = squareYStart; y < squareYStart + 3; y++) {
                if (grid[x][y].value() == insert.value()) return false;
            }
        }
        return true;
    }

    public List<Field> flatGrid(Field[][] grid) {
        List<Field> result = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            result.addAll(Arrays.asList(grid[x]).subList(0, 9)); //higher performance than manual copy
        }
        return result;
    }

    public Field[][] newInstanceOf(Field[][] grid) {
        Field[][] solution = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                solution[x][y] = new Field(x, y, grid[x][y].value());
            }
        }
        return solution;
    }
}
