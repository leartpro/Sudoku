package utils;

import domain.Field;
import domain.Grid;
import domain.Point;
import domain.Row;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private Field[][] grid;
    public Solver(Field[][] grid) {
        this.grid=grid;
    }
    public boolean isSolved() {
        for (int x = 0; x < 9; x++) {
            Point[] horizontal = new Row(x, x, 0, 8).asPoints();
            if (!isRowSolved(horizontal)) return false;
        }
        for (int y = 0; y < 9; y++) {
            Point[] vertical = new Row(0, 8, y, y).asPoints();
            if (!isRowSolved(vertical)) return false;
        }
        for (Field[][] square : getSquares()) {
            if (!isSquareSolved(square)) return false;
        }
        return true;
    }

    private boolean isSquareSolved(Field[][] square) {
        //map to 1d array
        Field[] flat = new Field[square.length * square[0].length];
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                flat[i + (j * square.length)] = square[i][j];
            }
        }
        return isRowCompleted(flat);
    }


    private List<Field[][]> getSquares() {
        List<Field[][]> squares = new ArrayList<>();
        for (int x = 1; x < 9; x += 3) {
            for (int y = 1; y < 9; y += 3) {
                squares.add(new Field[][]{
                        new Field[]{
                                new Field(x - 1, y + 1, grid[x-1][y+1].value()),
                                new Field(x, y + 1, grid[x][y+1].value()),
                                new Field(x + 1, y + 1, grid[x+1][y+1].value())
                        },
                        new Field[]{
                                new Field(x - 1, y, grid[x-1][y].value()),
                                new Field(x, y, grid[x][y].value()),
                                new Field(x + 1, y, grid[x+1][y].value())
                        },
                        new Field[]{
                                new Field(x - 1, y - 1, grid[x-1][y-1].value()),
                                new Field(x, y - 1, grid[x][y-1].value()),
                                new Field(x + 1, y - 1, grid[x+1][y-1].value())
                        },
                });
            }
        }
        return squares;
    }

    private boolean isRowSolved(Point[] vertical) {
        Field[] VRow = new Field[9];
        int i = 0;
        for (Point p : vertical) {
            VRow[i] = grid[p.x()][p.y()];
            i++;
        }
        return isRowCompleted(VRow);
    }

    private boolean isRowCompleted(Field[] row) {
        for (Field f : row) {
            if (f.value() <= 0 || f.value() > 9) return false;
        }
        return getDuplications(row) == null;
    }

    private List<Field> getDuplications(Field[] row) {
        List<Field> duplications = new ArrayList<>();
        for (int i = 0; i < row.length; i++) {
            for (int j = i + 1; j < row.length; j++) {
                if (row[i].equals(row[j])) {
                    duplications.add(row[i]);
                }
            }
        }
        if (duplications.size() == 0) return null;
        return duplications;
    }

    public List<Field> solve() {
        Field[][] solution = new Field[9][9];
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                solution[x][y] = new Field(x, y, grid[x][y].value());
            }
        }
        if(calculateSolution(solution, solution.length)) {
            return Grid.compareTo(solution, grid);
        }
        return null;
    }

    private boolean isUnique(Field insert) {
        for (int i = 0; i < grid.length; i++) {
            if(grid[insert.point().x()][i].value() == insert.value()) return false;
        }
        for (int i = 0; i < grid.length; i++) {
            if(grid[i][insert.point().y()].value() == insert.value()) return false;
        }
        int sqrt = (int)Math.sqrt(grid.length);
        int squareXStart = insert.point().x() - insert.point().x() % sqrt;
        int squareYStart = insert.point().y() - insert.point().y() % sqrt;

        for (int x = squareXStart; x < squareXStart + sqrt; x++) {
            for (int y = squareYStart; y < squareYStart + sqrt; y++){
                if (grid[x][y].value() == insert.value()) return false;
            }
        }
        return true;
    }

    private boolean calculateSolution(Field[][] grid, int n) { //n = grid.length
        int xPos = -1;
        int yPos = -1;
        boolean completed = true;
        int x = 0;
        while (x < n && completed) {
            int y = 0;
            while (y < n && completed) {
                if (grid[x][y].value() == 0) {
                    completed = false;
                    xPos = x;
                    yPos = y;
                }
                y++;
            }
            x++;
        }
        if(completed) return true;
        // else backtrack
        for (int value = 1; value <= n; value++) {
            if (isUnique(new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (calculateSolution(grid, n)) return true;
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }

    private boolean isCompleted(Field[][] grid, int n) {
        boolean completed = true;
        int x = 0;
        while (x < n && completed) {
            int y = 0;
            while (y < n && completed) {
                if (grid[x][y].value() == 0) {
                    completed = false;
                }
                y++;
            }
            x++;
        }
        return completed;
    }
}
