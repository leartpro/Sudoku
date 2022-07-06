package utils.generator;

import domain.Field;
import utils.GridUtils;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator extends GridUtils {
    public static int totalSteps = 0;
    //TODO: give progress staus information of grid completion and puzzle completion

    //todo: returns sometimes completed grids
    public Field[][] generate() {
        Field[][] grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
        System.out.println("generating the grid...");
            completeRandom(grid);
        System.out.println("generating the puzzle...");
            createPuzzle(grid);
        System.out.println("Needed " + totalSteps + " to generate");
        return grid;
    }


    //TODO: to generate for different difficulties:
    // use different strong solvers for different difficulty values
    //int debugPuzzleCount = 0;
    public void createPuzzle(Field[][] grid) {
        List<Field> removable = new ArrayList<>(flatGrid(grid));
        for (Field current : removable) {
            //debugPuzzleCount++;
            //System.out.println("createPuzzle in step: " + debugPuzzleCount + " / 81"); //Todo: give progress to user-interface
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            //int countOfSolutions = new Solver(grid).allSolutions().size();
            //todo: optimized allSolution() should return either true
            // or false if there are more than one solution
            if (!new Solver(grid).uniqueSolution()) {
                grid[current.x()][current.y()] = current;
                //assert (new Solver(grid).uniqueSolution());
            }
        }
    }

   // int debugCompleteCount = 0;
    //TODO: performance tests
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
                //debugCompleteCount++;
                //System.out.println("completeRandom in step: " + debugCompleteCount + " / max.729"); //Todo: give progress to user-interface
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (new Solver(grid).isSolvable()) { //todo: is necessary?
                    if (completeRandom(grid)) return true;
                    else grid[xPos][yPos] = new Field(xPos, yPos, 0);
                } else grid[xPos][yPos] = new Field(xPos, yPos, 0);
            }
        }
        return false;
    }
}
