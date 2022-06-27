package domain;

class RowTest {

    @org.junit.jupiter.api.Test
    void asPoints() {
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                Row row = new Row(x, x ,0, 8);
                System.out.println(row + "\r\n");
                row = new Row(0, 8, y, y);
                System.out.println(row + "\r\n");
            }
        }
    }
}