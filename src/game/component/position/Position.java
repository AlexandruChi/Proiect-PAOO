package game.component.position;

public class Position {
    public int x, y;
    public int xPX, yPX;
    public double tmpX, tmpY;

    public Position() {
        x = y = xPX = yPX = 0;
        tmpX = tmpY = 0;
    }

    public Position(Position position) {
        x = position.x;
        y = position.y;
        xPX = position.xPX;
        yPX = position.yPX;
        tmpX = position.tmpX;
        tmpY = position.tmpY;
    }
}
