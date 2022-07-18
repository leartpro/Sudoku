package utils.solver;

import domain.Field;
import utils.GridUtils;

import java.util.ArrayList;
import java.util.List;

public final class Solver extends GridUtils {
    private final Field[][] grid;

    public Solver(Field[][] grid) {
        this.grid = grid;
    }

    //TODO: returns as early as possible
    public boolean uniqueSolution() { //todo: finds each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        List<Integer>[][] values = new ArrayList[9][9];
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
        if(81 - available.size() < 17) return false; //TODO: check this condition
        for (Field current : available) { //runs max.81 times
            if (values[current.x()][current.y()].size() == 0) continue;
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (isSolvable(solution)) {
                if (!inList(solutions, solution)) solutions.add(solution);
                if (solutions.size() > 1) return false;
                } else {
                return false;
            }
                //todo: optimize the values[][][] handle
                // so the same solution is never calculated more than once
            }
        return true;
    }

    //solve the grid and returns true if success else return false
    private boolean isSolvable(Field[][] grid) {
        grid = addAll(grid, solve(grid));
        return isSolved(grid);
    }

    //check for each unsolved field  if there is only one valid value to insert
    //if this condition is true insert this value
    public boolean soleCandidates(Field[][] grid, List[][] values) {
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        validValues(grid, values);
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
                validValues(grid, values);
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

    public List<Field> solve(Field[][] grid) { //todo
        /*
        first insert all of grid into solution and then add every solid value into solution
        until solution is completed or solver failed t solve
         */
        Field[][] solution = newInstanceOf(grid);
        List[][] values = new ArrayList[9][9];
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                values[x][y] = new ArrayList();
                for(int i = 1; i < 10; i++) {
                    values[x][y].add(new Field(x, y, i));
                }
            }
        }
        boolean changes = true;
        while(changes) {
            changes = false;
            validValues(solution, values);
            //removingCandidates(solution, values);
            if(soleCandidates(solution, values)) {
                changes = true;
            }
            if(uniqueCandidates(solution, values)) {
                changes = true;
            }
        }
        return compareTo(solution, this.grid);
    }

    public void removingCandidates(Field[][] grid, List[][] values) {
        boolean changes = true;
        while(changes) {
            changes = false;
            validValues(grid, values);
            if(blockLineInteraction(grid, values)) {
                changes = true;
            }
            if(blockBlockInteraction(grid, values)) {
                changes = true;
            }
            if(nakedSubset(grid, values)) {
                changes = true;
            }
            if(hiddenSubset(grid, values)) {
                changes = true;
            }
            if(xWing(grid, values)) {
                changes = true;
            }
            if(swordfish(grid, values)) {
                changes = true;
            }
        }
        // e.g. block/column, block/row, block/block - interactions
        // or naked, hidden - subset
        // or x-wing, swordfish or forcing chain
    }

    // all sub-methods of removingCandidates should modify values[][]
    public boolean blockLineInteraction(Field[][] grid, List[][] values) { //TODO
        /*
        for each square for each value from one to nine
            if
         */
        return true;
    }

    public boolean blockBlockInteraction(Field[][] grid, List[][] values) { //TODO
        return true;

    }

    public boolean nakedSubset(Field[][] grid, List[][] values){
        return true;

    }

    public boolean hiddenSubset(Field[][] grid, List[][] values) {
        return true;

    }

    public boolean xWing(Field[][] grid, List[][] values) {
        return true;

    }

    public boolean swordfish(Field[][] grid, List[][] values) {
        return true;

    }

    public void validValues(Field[][] grid, List[][] values) {
        List[][] removable = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Field> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
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
            for (int y = 0; y < 9; y++) {
                ((List<Field>)values[x][y]).retainAll(((List<Field>)removable[x][y]));
            }
        }
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
