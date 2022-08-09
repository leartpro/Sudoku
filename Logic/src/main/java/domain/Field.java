package domain;

/**
 * @param x the row value
 * @param y the column value
 * @param value the value
 */
@SuppressWarnings("ALL")
public record Field(int x, int y, int value) {
    /**
     * @param x a row value from zero to eight
     * @param y a column value from zero to eight
     * @param value a value from one to nine
     */
    public Field {
        assert (value >= 0 && value < 10);
        assert (x >= 0 && x < 9);
        assert (y >= 0 && y < 9);
    }

    /**
     * @return the field as string
     */
    @Override
    public String toString() {
        return "Field[x="+x+",y="+y+",value="+value+"]";
    }
}
