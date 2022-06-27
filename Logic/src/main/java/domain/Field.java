package domain;

public record Field(Point point, int value) {
    public Field {
        assert (value > 0 && value < 10);
    }

    public Field(int x, int y, int value) {
        this(new Point(x,y), value);
    }
}
