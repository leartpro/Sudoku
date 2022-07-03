package utils;

import domain.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator extends GridUtils {
    private int difficulty;

    public Generator() {
        this.difficulty = 0;
    }

    public Field[][] generate() {
        assert difficulty > 0;
        Field[][] grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
        System.out.println("Generating...");
        System.out.println("Filled Grid:");
        if (completeRandom(grid)) {
            displaySmall(grid);
            System.out.println("\n");
            System.out.println("Generated Puzzle:");
            List<Field> removable = flatGrid(newInstanceOf(grid));
            Collections.shuffle(removable);
            //if (createPuzzle(grid, removable)) { //todo: infinity loop!
            if(createPuzzle2(grid)) {
                System.out.println("success!!!!!!!");
            } else {
                System.err.println("unable to create a valid puzzle");
            }
            displaySmall(grid);
        } else {
            System.err.println("unable to create a valid pattern");
        }
        return grid;
    }

    private boolean createPuzzle(Field[][] grid, List<Field> removable) { //todo: infinity-loop
        for (Field current : new ArrayList<>(removable)) {
            removable.remove(current);
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (new Solver(grid).allSolutions().size() == 1) { //todo: this method call throws the infinity-loop
                if (createPuzzle(grid, removable)) return true;
            } else {
                grid[current.x()][current.y()] = current;
                removable.add(current);
                return false;
            }
        }
        return true;
    }

    public Field[][] createPuzzle3(Field[][] grid) { //todo: countofallsolutions is not working
        List<Field> removable = new ArrayList<>();
        for (Field field : flatGrid(grid)) {
            if (field.value() != 0) {
                removable.add(field);
            }
        }
        Collections.shuffle(removable);
        System.out.println(removable.size());
        for (Field current : removable) {
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            int countOfSolutions = new Solver(grid).allSolutions().size();
            System.out.println("count of solutions: " + countOfSolutions);
            displaySmall(grid);
            System.out.println();
            int countOfValues = 0;
            for(int x = 0; x < 9; x++) {
                for(int y = 0; y < 9; y++) {
                    if(grid[x][y].value() != 0) countOfValues++;
                }
            }
            System.out.println("count of values: " + countOfValues);
            if (countOfSolutions == 0) {
                return null;
            } else if (countOfSolutions == 2) {
                grid[current.x()][current.y()] = current;
                assert (new Solver(grid).allSolutions().size() == 1);
                return grid;
            }
        }
        return grid;
    }

    public boolean createPuzzle2(Field[][] grid) {
        List<Field> removable = new ArrayList<>();
        for (Field field : flatGrid(grid)) {
            if (field.value() != 0) {
                removable.add(field);
            }
        }
        Field current = removable.get(new Random().nextInt(removable.size()));
        int xPos = current.x(), yPos = current.y();
        assert (grid[xPos][yPos].value() != 0);
        grid[xPos][yPos] = new Field(xPos, yPos, 0);
        if (new Solver(grid).allSolutions().size() == 1) {
            if (createPuzzle2(grid)) return true;
            else grid[xPos][yPos] = new Field(xPos, yPos, 0);
        } else {
            grid[xPos][yPos] = new Field(xPos, yPos, 0);
            return false;
        }
        return false;
    }

    private boolean completeRandom(Field[][] grid) {
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
                if (new Solver(grid).isSolvable()) { //todo: is necessary?
                    if (completeRandom(grid)) return true;
                    else grid[xPos][yPos] = new Field(xPos, yPos, 0);
                } else grid[xPos][yPos] = new Field(xPos, yPos, 0);
            }
        }
        return false;
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
