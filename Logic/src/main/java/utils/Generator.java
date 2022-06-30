package utils;

import domain.Field;

public class Generator extends GridUtils{
    private int difficulty;
    public Generator() {
        this.difficulty = 0;
    }

    public Field[][] generate() {
        assert difficulty > 0;
        int[][] input = new int[][]{ //unsolved
                {4, 0, 0, 0, 9, 1, 0, 0, 0},
                {0, 0, 9, 0, 0, 7, 4, 2, 5},
                {0, 5, 8, 3, 4, 0, 1, 9, 0},
                {6, 9, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 9, 6, 4, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 9, 6, 3},
                {0, 8, 7, 0, 2, 6, 5, 3, 0},
                {3, 1, 5, 8, 0, 0, 6, 0, 0},
                {0, 0, 0, 1, 5, 0, 0, 0, 9}
        };
        return toComparableGrid(input);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
