package domain;

/**
 * @param x
 * @param y
 * @param value
 */
@SuppressWarnings("ALL")
public record Field(int x, int y, int value) {
    /**
     * @param x
     * @param y
     * @param value
     */
    public Field {
        assert (value >= 0 && value < 10);
        assert (x >= 0 && x < 9);
        assert (y >= 0 && y < 9);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Field[x="+x+",y="+y+",value="+value+"]";
    }
}
