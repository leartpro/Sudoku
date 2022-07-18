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

    int progress = 0;
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
            System.out.println("completeRandom-Progress: " + progress);
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                progress+=1;
                if (this.isSolvable(newInstanceOf(grid))) {
                    /*
                        TODO:
                        this.isSolvable tries to insert until the grid is completed,
                        a possible alternative would be to check the existing values on uniqueness
                     */
                    if (completeRandom(grid)) return true;
                    else {
                        grid[xPos][yPos] = new Field(xPos, yPos, 0);
                        progress--;
                    }
                } else {
                    grid[xPos][yPos] = new Field(xPos, yPos, 0);
                    progress--;
                }
            }
        }
        return false;
    }

    int subprogress = 0;
    private boolean isSolvable(Field[][] grid) {
        //TODO: need sometimes a lot of time
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
        //backtrack
        for (int value = 1; value <= 9; value++) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                subprogress++;
                if (this.isSolvable(grid)) {
                    return true;
                } else {
                    grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
                    subprogress--;
                }
            }
        }
        return false;
    }
}
