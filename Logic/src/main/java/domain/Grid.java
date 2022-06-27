package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Grid {
    private Field[][] grid;

    public Grid() {
        grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
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
        for(Field[][] square : getSquares()) {
            if(!isSquareSolved(square)) return false;
        }
        return true;
    }

    private boolean isSquareSolved(Field[][] square) {
        //map to 1d array
        Field[] flat = new Field[square.length * square[0].length];
        for(int i = 0; i < square.length; i++) {
            for(int j = 0; j < square[i].length; j++) {
                flat[i + (j * square.length)] = square[i][j];
            }
        }
        return isRowCompleted(flat);
    }



    private List<Field[][]> getSquares() {
        List<Field[][]> squares = new ArrayList<>();
        for(int x = 1; x < 9; x+=3) {
            for(int y = 1; y < 9; y+=3) {
                //TODO: get surroundings of this point and map it to a two dimensional field array
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
        for(int i = 0; i < row.length; i++) {
            for(int j = i +1; j < row.length; j++) {
                if(row[i].equals(row[j])) {
                    duplications.add(row[i]);
                }
            }
        }
        if(duplications.size() == 0) return null;
        return duplications;
    }

    public List<Field> solve() {
        List<Field> changes = new ArrayList<>();

        return changes;
    }

    public List<Field> compareTo(Grid grid) {
        List<Field> differences = new ArrayList<>();

        return differences;
    }

    public String toString() {
        return Arrays.stream(grid).toList().toString();
    }
}