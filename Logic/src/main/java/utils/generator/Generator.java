package utils.generator;

import domain.Field;
import utils.GridUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generator extends GridUtils {

    //todo: returns sometimes completed grids
    public Field[][] generate() {
        Field[][] grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
        completeRandom(grid);
        assert allUnique(grid);
        assert isSolved(grid);
        createPuzzle(grid);
        return grid;
    }

    //TODO: to generate for different difficulties:
    // use different strong solvers for different difficulty values
    public void createPuzzle(Field[][] grid) {
        List<Field> removable = new ArrayList<>(flatGrid(grid));
        Collections.shuffle(removable);
        for (Field current : removable) {
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (!uniqueSolution(grid)) {
                //System.out.println("cant remove " + current);
                grid[current.x()][current.y()] = current;
            }
        }
    }

    public boolean completeRandom(Field[][] grid) {
        int xPos = -1;
        int yPos = -1;
        boolean completed = true;
        int x = 0;
        while (x < 9 && completed) {
            int y = 0;
            while (y < 9 && completed) {
                if (grid[x][y].value() == 0) {
                    completed = false;
                    xPos = x;
                    yPos = y;
                }
                y++;
            }
            x++;
        }
        if (completed) return true;
        List<Integer> values = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
        Collections.shuffle(values);
        for (int value : values) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (this.completeRandom(grid)) return true;  //backtracking
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }
}
