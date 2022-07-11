package utils.solver;

import domain.Field;
import utils.generator.Generator;
import utils.GridUtils;

import java.util.ArrayList;
import java.util.List;

public final class Solver extends GridUtils {
    private final Field[][] grid;

    public Solver(Field[][] grid) {
        this.grid = grid;
    }

    public List<Field> solve() {
        Field[][] solution = newInstanceOf(grid);
        if (calculateSolution(solution)) {
            return compareTo(solution, this.grid);
        }
        return null;
    }

    int debugCalculateCount = 0;
    //TODO: to calculate a difficulty for the grid:
    // try first with easy solve-techniques and if there is no solution repeat with more advanced techniques

    //TODO:
    private boolean calculateSolution(Field[][] grid) { //n = grid.length
        //TODO: need sometimes a lot of time
        // display progress in user-interface and find better optimisations
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
                debugCalculateCount++;
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (calculateSolution(grid)) {
                    return true;
                } else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }

    //has debug counter
    public boolean isSolvable() {
        boolean v = calculateSolution(newInstanceOf(grid));
        Generator.totalSteps += debugCalculateCount;
        debugCalculateCount = 0;
        return v;
    }

    /*
    public List<Field[][]> allSolutions() { //todo: find each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        @SuppressWarnings("unchecked") List<Integer>[][] values = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        if (isUnique(grid, new Field(x, y, i))) available.add(i);
                    }
                    values[x][y] = new ArrayList<>(available);
                } else {
                    values[x][y] = new ArrayList<>();
                }
            }
        }
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        for (Field current : available) {
            if(values[current.x()][current.y()].size() == 0) continue;
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (calculateSolution(solution)) {
                if (!inList(solutions, solution)) solutions.add(solution);
                //todo: optimize the values[][][] handle
                // so the same solution is never calculated more than once
            }
        }
        return solutions;
    }
    */

    int debugUniqueCount = 0;

    //TODO: returns as early as possible
    public boolean uniqueSolution() { //todo: find each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        @SuppressWarnings("unchecked") ArrayList<Integer>[][] values = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        if (isUnique(grid, new Field(x, y, i))) available.add(i);
                    }
                    values[x][y] = new ArrayList<>(available);
                } else {
                    values[x][y] = new ArrayList<>();
                }
            }
        }
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        for (Field current : available) { //runs max.81 times
            if (values[current.x()][current.y()].size() == 0) continue;
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (calculateSolution(solution)) {
                debugUniqueCount += debugCalculateCount;
                debugCalculateCount = 0;
                if (!inList(solutions, solution)) solutions.add(solution);
                if (solutions.size() > 1) {
                    Generator.totalSteps += debugUniqueCount;
                    return false;
                }
                //todo: optimize the values[][][] handle
                // so the same solution is never calculated more than once
            } else { //else is only in use for debug print
                debugUniqueCount += debugCalculateCount;
                debugCalculateCount = 0;
            }
        }
        Generator.totalSteps += debugUniqueCount;
        return true;
    }

    //check for each unsolved field  if there is only one valid value to insert
    //if this condition is true insert this value
    public boolean soleCandidates(Field[][] grid, List[][] values) {
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        //first check for each if there is a sole candidate
        //do this again for each only if changes have been made
        //if there were no changes the method returns
        boolean changes = true;
        boolean totalChanges = false;
        while (changes) {
            changes = false;
            for (Field current : available) {
                assert (current.value() == 0);
                //check if there is every number between 1 and 9 and
                //if only one is missing place this one
                values = validValues(grid, values);
                if (values[current.x()][current.y()].size() == 1) {
                    grid[current.x()][current.y()] = new Field(
                            current.x(),
                            current.y(),
                            ((Field)values[current.x()][current.y()].get(0)).value()
                    );
                    changes = true;
                    totalChanges = true;
                }
            }
        }
        return totalChanges;
    }

    //check for each field the row, the column and the square
    // and if there is no other point where the current value is unique
    //then insert this value
    public boolean uniqueCandidates(Field[][] grid, List[][] values) {
        boolean changes = true;
        boolean totalChanges = false;
        while (changes) {
            changes = false;
            for (Field[] constrain : allConstrains(grid)) {
                //check for each constrain for each available point for each value
                //if in the  current constrain in no other point where the current value is unique
                for (int i = 1; i <= 9; i++) {
                    //TODO: values[][] already contains this
                    // iterate the constrain through values and if there is only
                    // one times (i == current.value()) then insert
                    // else continue with the next constrain
                    //TODO: => method -valuesOfConstrain(Field[] constrain): List<Integer>
                    //TODO: => method -pointsWithoutValue(Field[] searchArea, int value): Field
                    List<Field> available = new ArrayList<>(); //stores all field that contain the current point
                    //alternativ: modify values[][] instead ?!
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

    public List<Field> solve2() { //todo
        /*
        first insert all of grid into solution and then add every solid value into solution
        until solution is completed or solver failed t solve
         */
        Field[][] solution = newInstanceOf(grid);
        List[][] values = new List[9][9];
        boolean changes = true;
        while(changes) {
            changes = false;
            values = validValues(solution, values);
            removingCandidates(values);
            if(soleCandidates(solution, values)) {
                changes = true;
            }
            if(uniqueCandidates(solution, values)) {
                changes = true;
            }
        }
        return compareTo(solution, this.grid);
    }

    public void removingCandidates(List[][] values) {

        // e.g. block/column, block/row, block/block - interactions
        // or naked, hidden - subset
        // or x-wing, swordfish or forcing chain
    }

    //TODO: all sub-methods of removingCandidates should modify values[][]
    public void blockLineInteraction(List[][] values) { //TODO

    }

    public void blockBlockInteraction(List[][] values) { //TODO

    }

    public void nakedSubset(List[][] values){

    }

    public void hiddenSubset(List[][] values) {

    }

    public void xWing(List[][] values) {

    }

    public void swordfish(List[][] values) {

    }

    public List[][] validValues(Field[][] grid, List[][] values) { //TODO: make void method because param 'values' is modified
        //TODO: remove instead of add
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Field> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        if (isUnique(grid, new Field(x, y, i))) {
                            available.add(new Field(x, y, i));
                        }
                    }
                    values[x][y] = new ArrayList<>(available);
                } else {
                    values[x][y] = new ArrayList();
                }
            }
        }
        return values;
    }

        //TODO:
        // first store an array with values from 1 to 9 for each point
        // :A
        // then remove all not unique numbers
        // then check for sole candidates //optimize for possible candidates
        //  if changes have been made goto :A
        // then check for unique candidates //optimize for possible candidates
        //  if changes have been made goto :A
        // then repeat this pattern with techniques for removing candidates
        // e.g block/column, block/row, block/block - interactions
        // or naked, hidden - subset
        // or x-wing, swordfish or forcing chain


    }