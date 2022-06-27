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

    public String toString() {
        return Arrays.stream(grid).toList().toString();
    }
}