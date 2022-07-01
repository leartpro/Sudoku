package utils;

import domain.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generator extends GridUtils{
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
        solveRandom(grid);
        return grid;
    }

    private boolean solveRandom(Field[][] grid) { //n = grid.length
        List<Field> available = new ArrayList<>();
        boolean completed = true;
        for(Field field : flatGrid(grid)) {
            if(field.value() == 0) {
                available.add(field);
                completed = false;
            }
        }
        if (completed) return true;
        //Collections.shuffle(available);
        int xPos = available.get(0).x(), yPos = available.get(0).y();
        List<Integer> values = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(values);
        for (int value = 1; value <= 9; value++) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (solveRandom(grid)) return true;
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
