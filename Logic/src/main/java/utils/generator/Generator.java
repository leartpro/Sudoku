package utils.generator;

import domain.Field;
import utils.GridUtils;
import utils.solver.Solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generator extends GridUtils {

    //todo: returns sometimes completed grids
    public Field[][] generate() {
        Field[][] grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
        completeRandom(grid);
        assert allUnique(grid);
        assert isSolved(grid);
        createPuzzle(grid);
        assert !isSolved(grid);
        //assert isSolvable(grid);
        return grid;
    }

    //TODO: to generate for different difficulties:
    // use different strong solvers for different difficulty values
    public void createPuzzle(Field[][] grid) { //TODO: removes to much
        List<Field> removable = new ArrayList<>(flatGrid(grid));
        Collections.shuffle(removable);
        for (Field current : removable) {
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (!uniqueSolvable(grid)) {
                System.out.println("cant remove " + current);
                grid[current.x()][current.y()] = current;
                //System.exit(0);
            }
        }
    }

    private boolean uniqueSolvable(Field[][] grid) {
        ArrayList<Integer>[][] values = new ArrayList[9][9];
        cleanupValues(grid, values);
        if(!allPossible(grid, values)) return false;
        List<Field> available = new ArrayList<>(flatGrid(grid));
        available.removeIf(f -> f.value() != 0);
        if(81 - available.size() < 17) return false;
        List<Field[][]> solutions = new ArrayList<>();
        for(Field f : available) {
            assert  (values[f.x()][f.y()].size() != 0);
            List<Integer> possibleValues = new ArrayList<>(values[f.x()][f.y()]);
            for(int value : possibleValues) {
                Field[][] solution = newInstanceOf(grid);
                solution[f.x()][f.y()] = new Field(f.x(), f.y(), value);
                assert allUnique(solution);
                values[f.x()][f.y()].remove(Integer.valueOf(value));
                solution = new Solver(solution).solve(solution);
                if(isSolved(solution)/*completeRandom(solution)*/) { //TODO: use Solver.solve() instead
                    if (!inList(solutions, solution)) solutions.add(solution);
                }
            }
            if(solutions.size() > 1) return false;
        }
        return solutions.size() > 0;
    }

    public boolean completeRandom(Field[][] grid) {
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
        List<Integer> values = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
        Collections.shuffle(values);
        for (int value : values) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (this.completeRandom(grid)) return true;  //backtracking
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }

    private void cleanupValues(Field[][] grid, ArrayList<Integer>[][] values) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> temp = new ArrayList<>();
                    for (int j = 1; j <= 9; j++) if (isUnique(grid, new Field(x, y, j))) temp.add(j);
                    values[x][y] = new ArrayList<>(temp);
                } else values[x][y] = new ArrayList<>();
            }
        }
    }

    private boolean allPossible(Field[][] grid, ArrayList<Integer>[][] values) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0 && values[x][y].size() == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
