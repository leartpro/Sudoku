package domain;

public record Point(int x, int y) {

    public Point {
        assert (x >= 0 && x < 9);
        assert (y >= 0 && y < 9);
    }

    @Override
    public String toString() {
        return "Point[x="+x+",y="+y+"]";
    }
}
