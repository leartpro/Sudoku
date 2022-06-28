package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {
    private Field[][] grid;

    public Grid() {
        grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
    }

    public List<Field> compareTo(Field[][] grid) {
        List<Field> differences = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            for(int y  =0; y < 9; y++) {
                if(!grid[x][y].equals(this.grid[x][y])) differences.add(grid[x][y]);
            }
        }
        return differences;
    }

    public static List<Field> compareTo(Field[][] key, Field[][] value) { //return the values from key
        List<Field> differences = new ArrayList<>();
        for(int x = 0; x < 9; x++) {
            for(int y  =0; y < 9; y++) {
                if(!key[x][y].equals(value[x][y])) differences.add(key[x][y]);
            }
        }
        return differences;
    }

    @Override
    public String toString() {
        return Arrays.stream(grid).toList().toString();
    }

    public static void display(Field[][] grid) {
        StringBuilder output = new StringBuilder();
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                output.append(grid[x][y]).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }

    public static void displaySmall(Field[][] grid) {
        StringBuilder output = new StringBuilder();
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                output.append(grid[x][y].value()).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }
}