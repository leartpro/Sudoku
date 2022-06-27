package domain;

import java.util.Arrays;

public record Row (int firstX, int lastX, int firstY, int lastY){
    public Row {
        assert (firstX >= 0 && firstX < 9);
        assert (lastX >= 0 && lastX < 9);
        assert (firstY >= 0 && firstY < 9);
        assert (lastY >= 0 && lastY < 9);
        assert (firstX <= lastX);
        assert (firstY <= lastY);
    }

    public Point[] asPoints() {
        Point[] result = new Point[9];
        for(int i = 0; i < 9; i++) {
            if(firstX == lastX) result[i] = new Point(firstX, i);
            if(firstY == lastY) result[i] = new Point(i, firstY);
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.stream(asPoints()).toList().toString();
    }
}
