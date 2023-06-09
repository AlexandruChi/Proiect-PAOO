package game.component.position;

/*
    Clasa Position reprezintă sistemul de bază de coordonate a jocului.
    Conține poziția în pixei și o valoare double pentru a permite mutarea unui obiect cu valori double
 */

public class Position {
    public int xPX, yPX;
    public double tmpX, tmpY;

    public Position() {
        xPX = yPX = 0;
        tmpX = tmpY = 0;
    }

    public Position(int xPX, int yPX) {
        this.xPX = xPX;
        tmpX = xPX;
        this.yPX = yPX;
        tmpY = yPX;
    }

    public Position(Position position) {
        xPX = position.xPX;
        yPX = position.yPX;
        tmpX = position.tmpX;
        tmpY = position.tmpY;
    }
}
