package utils;

import domain.Field;

import java.util.ArrayList;
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

    public int[][] mapComperableGrid(Field[][] grid) {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = grid[x][y].value();
            }
        }
        return result;
    }
}
