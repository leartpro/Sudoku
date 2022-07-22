package utils.solver;

import domain.Field;
import utils.GridUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@SuppressWarnings("rawtypes")
public final class Solver extends GridUtils {

    private final int difficulty;

    public Solver(int difficulty) {
        assert (difficulty > 0 && difficulty < 4);
        this.difficulty = difficulty;
    }

    /**
     * @param grid
     * @return
     */
    @SuppressWarnings("unchecked")
    public Field[][] solve(Field[][] grid) { //todo order methods
        Field[][] solution = newInstanceOf(grid);
        ArrayList<Field>[][] values = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[x][y] = new ArrayList();
                for (int i = 1; i < 10; i++) values[x][y].add(new Field(x, y, i));
            }
        }
        boolean changes = true;
        while (changes) {
            changes = false;
            validValues(solution, values);
            if (soleCandidates(solution, values)) changes = true;
            if (difficulty > 1) if (uniqueCandidates(solution, values)) changes = true;
            if (difficulty > 2) removingCandidates(solution, values);
        }
        return solution;
    }

    /**
     * @param grid
     * @param values
     * @return
     */
    //check for each unsolved field  if there is only one valid value to insert
    //if this condition is true insert this value
    private boolean soleCandidates(Field[][] grid, ArrayList<Field>[][] values) {
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        validValues(grid, values);
        boolean changes = true;
        boolean totalChanges = false;
        while (changes) {
            changes = false;
            for (Field current : available) {
                assert (current.value() == 0);
                validValues(grid, values);
                if (values[current.x()][current.y()].size() == 1) {
                    grid[current.x()][current.y()] = new Field(
                            current.x(),
                            current.y(),
                            values[current.x()][current.y()].get(0).value()
                    );
                    changes = true;
                    totalChanges = true;
                }
            }
        }
        return totalChanges;
    }

    /**
     * @param grid
     * @param values
     * @return
     */
    //check for each field the row, the column and the square
    // and if there is no other point where the current value is unique
    //then insert this value
    private boolean uniqueCandidates(Field[][] grid, List[][] values) {
        boolean changes = true;
        boolean totalChanges = false;
        while (changes) {
            changes = false;
            for (Field[] constrain : allConstrains(grid)) {
                for (int i = 1; i <= 9; i++) {
                    List<Field> available = new ArrayList<>();
                    for (Field current : constrain) {
                        if (current.value() != 0) continue; //if not available skip
                        if (isUnique(grid, new Field(current.x(), current.y(), i))) {
                            available.add(new Field(current.x(), current.y(), i));
                        }
                    }
                    if (available.size() == 1) {
                        grid[available.get(0).x()][available.get(0).y()] = available.get(0);
                        changes = true;
                        totalChanges = true;
                    }
                }
            }
        }
        return totalChanges;
    }

    /**
     * @param grid
     * @param values
     */
    private void removingCandidates(Field[][] grid, ArrayList<Field>[][] values) {
        boolean changes = true;
        while (changes) {
            changes = false;
            validValues(grid, values);
            if (blockLineInteraction(grid, values)) changes = true;
            if (blockInteractions(grid, values)) changes = true;
            if (nakedSubset(grid, values)) changes = true;
            if (hiddenSubset(grid, values)) changes = true;
            if (xWing(grid, values)) changes = true;
            if (swordfish(grid, values)) changes = true;
            if (forcingChain(grid, values)) changes = true;
            changes = false; //TODO: this line of code is only for test cases
            // (suppressing infinity-loop)
            // => remove this line to make method work
        }
    }

    /**
     * @param grid
     * @param values
     * @return
     */
    //TODO: all sub-methods of removingCandidates should modify values[][]
    private boolean blockLineInteraction(Field[][] grid, List[][] values) { //TODO
        /*
        for each square for each value from one to nine
            if
         */
        return true;
    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean blockInteractions(Field[][] grid, List[][] values) { //TODO
        return true;

    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean nakedSubset(Field[][] grid, List[][] values) {
        return true;

    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean hiddenSubset(Field[][] grid, List[][] values) {
        return true;

    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean xWing(Field[][] grid, List[][] values) {
        return true;

    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean swordfish(Field[][] grid, List[][] values) {
        return true;

    }

    /**
     * @param grid
     * @param values
     * @return
     */
    private boolean forcingChain(Field[][] grid, List[][] values) {
        return true;

    }

    /**
     * @param grid
     * @param values
     */
    private void validValues(Field[][] grid, ArrayList<Field>[][] values) {
        ArrayList<Field>[][] removable = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Field> available = new ArrayList<>();
                    for (int i = 1; i <= 9; i++) {
                        if (isUnique(grid, new Field(x, y, i))) {
                            available.add(new Field(x, y, i));
                        }
                    }
                    removable[x][y] = new ArrayList<>(available);
                } else {
                    removable[x][y] = new ArrayList();
                }
            }
        }
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++)
            ((List<Field>) values[x][y]).retainAll(((List<Field>) removable[x][y]));
        }
    }

    /**
     * @param grid
     * @return
     */
    private List<Field[]> allConstrains(Field[][] grid) {
        List<Field[]> constrains = new ArrayList<>(Arrays.asList(grid).subList(0, 9));
        for (int y = 0; y < 9; y++) {
            Field[] constrain = new Field[9];
            for (int x = 0; x < 9; x++) {
                constrain[x] = grid[x][y];
            }
            constrains.add(constrain);
        }
        for (int x = 1; x < 9; x += 3) {
            for (int y = 1; y < 9; y += 3) {
                constrains.add(
                        new Field[]{
                                new Field(x - 1, y + 1, grid[x - 1][y + 1].value()),
                                new Field(x, y + 1, grid[x][y + 1].value()),
                                new Field(x + 1, y + 1, grid[x + 1][y + 1].value()),
                                new Field(x - 1, y, grid[x - 1][y].value()),
                                new Field(x, y, grid[x][y].value()),
                                new Field(x + 1, y, grid[x + 1][y].value()),
                                new Field(x - 1, y - 1, grid[x - 1][y - 1].value()),
                                new Field(x, y - 1, grid[x][y - 1].value()),
                                new Field(x + 1, y - 1, grid[x + 1][y - 1].value())
                        }
                );
            }
        }
        return constrains;
    }
}