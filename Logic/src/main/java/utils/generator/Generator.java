package utils.generator;

import controller.ProgressMonitor;
import domain.Field;
import utils.GridUtils;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Generator extends GridUtils {

    private final ProgressMonitor progressMonitor;
    private int difficulty;

    /**
     * @param progressMonitor
     */
    public Generator(ProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
        this.difficulty = 1;
    }

    /**
     * @return
     */
    public Field[][] generate(int difficulty) {
        assert (difficulty > 0 && difficulty < 4);
        this.difficulty = difficulty;
        /*
            worst cases: //TODO: not calculated correctly
            - 2888 runs for noob
            - 3183 runs for easy
            - 3348 runs for medium or higher
         */
        progressMonitor.displayLoading(
                "Generating the Grid",
                ((difficulty == 1) ? 1290 : (difficulty == 2) ? 1815 : 3348)/*5265*/
        );
        Field[][] grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
        completeRandom(grid); //progress max. 81times
        assert allUnique(grid);
        assert isSolved(grid);
        createPuzzle(grid);
        assert !isSolved(grid);
        progressMonitor.completeProgress();
        return grid;
    }

    /**
     * @param grid
     */
    //TODO: to generate for different difficulties:
    // use different strong solvers for different difficulty values
    private void createPuzzle(Field[][] grid) {
        List<Field> removable = new ArrayList<>(flatGrid(grid));
        Collections.shuffle(removable);
        for (Field current : removable) { //runs 81 times for one generation
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (!uniqueSolvable(grid)) { //TODO: this method call requires a lot of time
                grid[current.x()][current.y()] = current;
            } else {
                assert isSolved(new Solver(this.difficulty).solve(grid));
            }
            progressMonitor.increaseProgress();
        }
    }

    /**
     * @param grid
     * @return
     */
    private boolean uniqueSolvable(Field[][] grid) { //is called 81times for one generation
        if(!isSolved(new Solver(this.difficulty).solve(grid))) return false;
        @SuppressWarnings("unchecked") ArrayList<Integer>[][] values = new ArrayList[9][9];
        cleanupValues(grid, values);
        if(!allPossible(grid, values)) return false;
        List<Field> available = new ArrayList<>(flatGrid(grid));
        available.removeIf(f -> f.value() != 0);
        if(81 - available.size() < ((difficulty == 1) ? 34 : (difficulty == 2) ? 24 : 17)) return false;
        List<Field[][]> solutions = new ArrayList<>();
        for(Field f : available) { //runs int worst case 1081 times for one generation
            assert  (values[f.x()][f.y()].size() != 0);
            List<Integer> possibleValues = new ArrayList<>(values[f.x()][f.y()]);
            for(int value : possibleValues) {
                Field[][] solution = newInstanceOf(grid);
                solution[f.x()][f.y()] = new Field(f.x(), f.y(), value);
                assert allUnique(solution);
                values[f.x()][f.y()].remove(Integer.valueOf(value));
                solution = new Solver(this.difficulty).solve(solution);
                if(isSolved(solution)) {
                    if (!inList(solutions, solution)) solutions.add(solution);
                }
            }
            progressMonitor.increaseProgress(); //runs worst 3168 times for one generation
            if(solutions.size() > 1) return false;
        }
        return solutions.size() != 0;
    }

    /**
     * @param grid
     * @return
     */
    private boolean completeRandom(Field[][] grid) { //runs 81times for one generation
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
                if (this.completeRandom(grid)) {
                    progressMonitor.increaseProgress();
                    return true;
                }
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }

    /**
     * @param grid
     * @param values
     */
    private void cleanupValues(Field[][] grid, ArrayList<Integer>[][] values) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> temp = new ArrayList<>();
                    for (int j = 1; j <= 9; j++) if (isUnique(grid, new Field(x, y, j))) temp.add(j);
                    values[x][y] = new ArrayList<>(temp);
                } else values[x][y] = new ArrayList<>();
            }
        }
    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean allPossible(Field[][] grid, ArrayList<Integer>[][] values) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0 && values[x][y].size() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param grid
     * @return
     */
    private boolean allUnique(Field[][] grid) {
        for (Field current : flatGrid(grid)) {
            if (current.value() == 0) continue;
            for (Field[] constrain : constrainsOf(grid, current)) {
                for (Field value : constrain) {
                    if (value.value() == 0 || value.equals(current)) continue;
                    if (value.value() == current.value()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param list
     * @param value
     * @return
     */
    private boolean inList(List<Field[][]> list, Field[][] value) {
        for (Field[][] current : list) {
            boolean equals = true;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (current[x][y].value() != value[x][y].value()) {
                        equals = false;
                        break;
                    }
                }
            }
            if (equals) return true;
        }
        return false;
    }

    /**
     * @param grid
     * @param field
     * @return
     */
    private List<Field[]> constrainsOf(Field[][] grid, Field field) { //TODO: use this for solver
        List<Field[]> constrains = new ArrayList<>();
        Field[] column = new Field[9];
        System.arraycopy(grid[field.x()], 0, column, 0, 9);
        constrains.add(column);
        Field[] row = new Field[9];
        for (int x = 0; x < 9; x++) row[x] = grid[x][field.y()];
        constrains.add(row);
        List<Field> square = new ArrayList<>();
        int squareXStart = field.x() - field.x() % 3;
        int squareYStart = field.y() - field.y() % 3;
        for (int x = squareXStart; x < squareXStart + 3; x++)
            square.addAll(Arrays.asList(grid[x]).subList(squareYStart, squareYStart + 3));
        constrains.add(square.toArray(new Field[9]));
        return constrains;
    }
}
