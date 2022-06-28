package domain;

public record Field(int x, int y, int value) {
    public Field {
        assert (value >= 0 && value < 10);
        assert (x >= 0 && x < 9);
        assert (y >= 0 && y < 9);
    }

    @Override
    public String toString() {
        return "Field[x="+x+",y="+y+",value="+value+"]";
    }
}
