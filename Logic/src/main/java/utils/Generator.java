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
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
        System.out.println("Generating...");
        System.out.println("Filled Grid:");
        if (completeRandom(grid)) {
            displaySmall(grid);
            System.out.println("\n");
            System.out.println("Generated Puzzle:");
            if(createPuzzle(grid, flatGrid(newInstanceOf(grid)))) { //todo: infinity loop!
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

    /*
    todo:
    return boolean is grid has at least one solution
    for the second part of the algorithm...
    isSolvable():
    -first try with easy techniques than improve step by step
        to validate the difficulty of the given grid
    */
    private boolean createPuzzle(Field[][] grid, List<Field> removable) {
        Collections.shuffle(removable);
        for (Field current : removable) {
            removable.remove(current);
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (new Solver(grid).allSolutions().size() == 1) {
                if (createPuzzle(grid, removable)) {
                    return true;
                }
            } else {
                grid[current.x()][current.y()] = current;
                removable.add(current);
                return false;
            }
        }
        return true;
    }

    private boolean completeRandom(Field[][] grid) {
        List<Field> available = new ArrayList<>();
        boolean completed = true;
        for(Field field : flatGrid(grid)) {
            if(field.value() == 0) {
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


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
