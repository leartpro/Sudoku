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
        if (completeRandom(grid)) {
            display(grid);
            System.out.println();
            createPuzzle(grid);
            display(grid);
            System.out.println();
        } else {
            System.err.println("unable to create a valid puzzle");
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
    //displaySmall(grid);

    /*
    notes:
    1. get random field in grid
    2. try to set the value on this point to 0
    3. if the grid has exactly one solution
        call recursion (means repeat from step 1)
        return result
       else undo step 2 and
       return false
     */
    private boolean createPuzzle(Field[][] grid) { //todo: reverse 'completeRandom'-algorithm
        List<Field> removable = flatGrid(grid);
        Field current = removable.get(new Random().nextInt(removable.size()));
        int xPos = current.x(), yPos = current.y();
        grid[xPos][yPos] = new Field(xPos, yPos, 0);
        if(new Solver(grid).countOfAllSolutions() == 1) { //todo: if solver does not find more than one solution
            //repeat
            //call recursion
            return createPuzzle(grid); //todo: this line is wrong!!!!!!!!!!!!!!!!!!!!!
            //return is always false, have to handle result... or similar...
            /*
            if result of recursion-call si false means:
            that this is the latest validate candidate to remove
             */
            //todo: should have list of removals as param?
        } else {
            //return to pre method-call
            grid[xPos][yPos] = current;
            return false;
        }
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
