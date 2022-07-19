package utils.generator;

import domain.Field;
import utils.GridUtils;

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
        assert allUnique(grid) == true;
        assert isSolved(grid) == true;
        createPuzzle(grid);
        return grid;
    }

    //TODO: to generate for different difficulties:
    // use different strong solvers for different difficulty values
    public void createPuzzle(Field[][] grid) {
        List<Field> removable = new ArrayList<>(flatGrid(grid));
        Collections.shuffle(removable);
        for (Field current : removable) {
            grid[current.x()][current.y()] = new Field(current.x(), current.y(), 0);
            if (!uniqueSolution(grid)) {
                System.out.println("cant remove " + current);
                grid[current.x()][current.y()] = current;
            }
        }
    }

    /* TODO:
     * for each point
     * cleanupValues()
     * check allPossible()
     * on true continue with the current point
     * otherwise undo until allPossible() becomes true
     * for all possible values of this point try to insert the current value
     * if after insert all is possible continue with the next point
     * else continue with the next possible value
     * assert the value of the current point is not zero and continue
     * */
    public void completeRandom(Field[][] grid) {
        ArrayList<Integer>[][] values = new ArrayList[9][9];
        List<Field> available = new ArrayList<>();
        for (Field field : flatGrid(grid)) {
            if (field.value() == 0) {
                available.add(field);
            }
        }
        Collections.shuffle(available);
        iteration: while(!isSolved(grid)) {
            available.removeIf(f -> f.value() != 0);
            for (int i = 0; i < available.size(); i++) {
                //displaySmall(grid);
                //System.out.println("Progress: " + available.size());
                //System.out.println("step " + i + ":");
                this.cleanupValues(grid, values);
                int j = 0;
                while (!allPossible(newInstanceOf(grid), values)) {
                    //System.out.println("        invalid values for at least one point...");
                    Field f = available.get(i - j);
                    available.remove(i - j);
                    //System.out.println("        undo the last insert and remove value " + f.value() + " from " + f);
                    grid[f.x()][f.y()] = new Field(f.x(), f.y(), 0);
                    j++;
                    available.add(new Field(f.x(), f.y(), 0));
                    this.cleanupValues(grid, values);
                }
                if(j != 0) continue iteration;
                Field current = available.get(i);
                int xPos = current.x(), yPos = current.y();
                List<Integer> v = new ArrayList<>(values[xPos][yPos]);
                //System.out.println("        insert values(" + v + ") into " + current);
                //Collections.shuffle(v);
                for (int value : v) {
                    assert isUnique(grid, new Field(xPos, yPos, value));
                    grid[xPos][yPos] = new Field(xPos, yPos, value);
                    if (!allPossible(newInstanceOf(grid), values)) grid[xPos][yPos] = new Field(xPos, yPos, 0);
                }
                //System.out.println("        result is: " + grid[xPos][yPos]);
                assert grid[xPos][yPos].value() != 0;
            }
        }
    }
    /*TODO:
    *  recursive method...
    * select random available point
    * get all available values to this point
    * try to insert a value and call recursion
    * if recursion fails undo and continue with different value
    * if no value is possible return false
    * if is solved return true
    * o.ä. //TODO: implement recursion and use help methods
    * */

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
                    //System.out.println("        no values available for point " + grid[x][y]);
                    return false;
                }
            }
        }
        return true;
    }
}
