package utils;

import domain.Field;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GridUtils {
    public List<Field> compareTo(Field[][] key, Field[][] value) { //return the values from key
        List<Field> differences = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (!value[x][y].equals(key[x][y])) differences.add(key[x][y]);
            }
        }
        return differences;
    }

    public boolean inList(List<Field[][]> list, Field[][] value) { //todo: work probably wrong
        for (Field[][] current : list) {
            boolean equals = true;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (current[x][y].value() != value[x][y].value()) {
                        equals = false;
                        break;
                    }
                }
            }
            if (equals) return true;
        }
        return false;
    }

    public void display(Field[][] grid) {
        StringBuilder output = new StringBuilder();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                output.append(grid[x][y]).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }

    public void displaySmall(Field[][] grid) {
        StringBuilder output = new StringBuilder();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                output.append(grid[x][y].value()).append(" ");
            }
            output.append("\r\n");
        }
        System.out.println(output);
    }

    public Field[][] toComparableGrid(int[][] grid) {
        Field[][] result = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = new Field(x, y, grid[x][y]);
            }
        }
        return result;
    }

    public int[][] mapComparableGrid(Field[][] grid) {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = grid[x][y].value();
            }
        }
        return result;
    }

    public boolean isUnique(Field[][] grid, Field insert) { //TODO: make sure the result is 100% valid
        // make tests for row, column and square
        for (int y = 0; y < 9; y++) {
            if (grid[insert.x()][y].value() == insert.value()) return false;
        }
        for (int x = 0; x < 9; x++) {
            if (grid[x][insert.y()].value() == insert.value()) return false;
        }

        int squareXStart = insert.x() - insert.x() % 3;
        int squareYStart = insert.y() - insert.y() % 3;

        for (int x = squareXStart; x < squareXStart + 3; x++) {
            for (int y = squareYStart; y < squareYStart + 3; y++) {
                if (grid[x][y].value() == insert.value()) return false;
            }
        }
        return true;
    }

    public boolean allUnique(Field[][] grid) {
        for (Field current : flatGrid(grid)) {
            if (current.value() == 0) continue;
            for (Field[] constrain : constrainsOf(grid, current)) {
                for (Field value : constrain) {
                    if (value.value() == 0 || value.equals(current)) continue;
                    if (value.value() == current.value()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Field[]> allConstrains(Field[][] grid) { //TODO: use this for solver
        List<Field[]> constrains = new ArrayList<>();
        //rows
        for (int x = 0; x < 9; x++) {
            constrains.add(grid[x]);
        }
        //columns
        for (int y = 0; y < 9; y++) {
            Field[] constrain = new Field[9];
            for (int x = 0; x < 9; x++) {
                constrain[x] = grid[x][y];
            }
            constrains.add(constrain);
        }
        //squares
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

    //TODO: split in squares
    //TODO: (for the test-method) complete squares to one grid
    public List<Field[][]> squares(Field[][] grid) {
        List<Field[][]> constrains = new ArrayList<>();
        for (int x = 1; x < 9; x += 3) {
            for (int y = 1; y < 9; y += 3) {
                constrains.add(
                        new Field[][]{
                                new Field[]{
                                        new Field(x - 1, y - 1, grid[x - 1][y - 1].value()),
                                        new Field(x - 1, y, grid[x - 1][y].value()),
                                        new Field(x - 1, y + 1, grid[x - 1][y + 1].value()),
                                },
                                new Field[]{
                                        new Field(x, y - 1, grid[x][y - 1].value()),
                                        new Field(x, y, grid[x][y].value()),
                                        new Field(x, y + 1, grid[x][y + 1].value()),
                                },
                                new Field[]{
                                        new Field(x + 1, y - 1, grid[x + 1][y - 1].value()),
                                        new Field(x + 1, y, grid[x + 1][y].value()),
                                        new Field(x + 1, y + 1, grid[x + 1][y + 1].value()),
                                }
                        }
                );
            }
        }
        return constrains;
    }

    public List<Field[]> constrainsOf(Field[][] grid, Field field) { //TODO: use this for solver
        List<Field[]> constrains = new ArrayList<>();
        Field[] column = new Field[9];
        for (int y = 0; y < 9; y++) {
            column[y] = grid[field.x()][y];
        }
        constrains.add(column);
        Field[] row = new Field[9];
        for (int x = 0; x < 9; x++) {
            row[x] = grid[x][field.y()];
        }
        constrains.add(row);
        List<Field> square = new ArrayList<>();
        int squareXStart = field.x() - field.x() % 3;
        int squareYStart = field.y() - field.y() % 3;

        for (int x = squareXStart; x < squareXStart + 3; x++) {
            for (int y = squareYStart; y < squareYStart + 3; y++) {
                square.add(grid[x][y]);
            }
        }
        constrains.add(square.toArray(new Field[9])); //... null null null null null null null null Field[x=3,y=3,value=4] -
        // - Field[x=3,y=4,value=5] Field[x=3,y=5,value=9] null null null null ...
        return constrains;
    }

    public List<Field> flatGrid(Field[][] grid) {
        List<Field> result = new ArrayList<>();
        for (Field[] array : grid) {
            result.addAll(Arrays.asList(array));
        }
        assert result.size() == 81;
        return result;
    }

    public Field[][] newInstanceOf(Field[][] grid) {
        Field[][] solution = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                solution[x][y] = new Field(x, y, grid[x][y].value());
            }
        }
        return solution;
    }

    public int[][] newInstanceOf(int[][] grid) {
        int[][] solution = new int[9][9];
        for (int x = 0; x < 9; x++) {
            System.arraycopy(grid[x], 0, solution[x], 0, 9);
        }
        return solution;
    }

    public boolean isSolved(Field[][] grid) { //TODO: validate method result
        for (Field current : flatGrid(grid)) {
            if (current.value() == 0) {
                return false;
            }

            //check column for current
            for (int i = 0; i < 9; i++) {
                if (i == current.y()) {
                    continue;
                }
                if (grid[current.x()][i].value() == current.value()) {
                    return false;
                }
            }

            //check row for current
            for (int i = 0; i < 9; i++) {
                if (i == current.x()) {
                    continue;
                }
                if (grid[i][current.y()].value() == current.value()) {
                    return false;
                }
            }
            //check square for current
            int squareXStart = current.x() - current.x() % 3;
            int squareYStart = current.y() - current.y() % 3;
            for (int i = squareXStart; i < squareXStart + 3; i++) {
                for (int j = squareYStart; j < squareYStart + 3; j++) {
                    if (i == current.x()) continue;
                    if (grid[i][j].value() == current.value()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean uniqueSolution(Field[][] grid) { //todo: finds each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        List<Integer>[][] values = new ArrayList[9][9];

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) if (isUnique(grid, new Field(x, y, i))) available.add(i);
                    values[x][y] = new ArrayList<>(available);
                } else values[x][y] = new ArrayList<>();
            }
        }

        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) if (current.value() == 0) available.add(current);

        if(81 - available.size() < 17) return false;

        for (Field current : available) { //runs max.81 times
            if (values[current.x()][current.y()].size() == 0) continue;
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (this.isSolvable(solution)) {
                if (!inList(solutions, solution)) solutions.add(solution);
                if (solutions.size() > 1) return false;
            } else return false;
        }

        return true;
    }

    //solve the grid and returns true if success else return false
    public boolean isSolvable(Field[][] grid) {
        grid = new Solver(grid).solve(grid);
        return isSolved(grid);
    }
}
