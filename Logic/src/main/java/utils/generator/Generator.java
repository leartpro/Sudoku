package utils.generator;

import domain.Field;
import utils.GridUtils;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        displaySmall(grid);
        createPuzzle(grid);
        displaySmall(grid);
        return grid;
    }


    //TODO: to generate for different difficulties:
    // use different strong solvers for different difficulty values
    public void createPuzzle(Field[][] grid) {
        List<Field> removable = new ArrayList<>(flatGrid(grid));
        for (Field current : removable) {
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (!new Solver(grid).uniqueSolution()) {
                grid[current.x()][current.y()] = current;
            }
        }
    }

    //TODO: optimize this method
    private boolean completeRandom(Field[][] grid) { //todo: requires sometimes a long time to give a valid result
        List<Field> available = new ArrayList<>();
        boolean completed = true;
        for (Field field : flatGrid(grid)) {
            if (field.value() == 0) {
                available.add(field);
                completed = false;
            }
        }
        if (completed) return true;
        Field current = available.get(new Random().nextInt(available.size()));
        int xPos = current.x(), yPos = current.y();
        List<Integer> values = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(values);
        for (int value : values) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (new Solver(grid).isSolvable()) {
                    if (completeRandom(grid)) return true;
                    else grid[xPos][yPos] = new Field(xPos, yPos, 0);
                } else grid[xPos][yPos] = new Field(xPos, yPos, 0);
            }
        }
        return false;
    }
}
