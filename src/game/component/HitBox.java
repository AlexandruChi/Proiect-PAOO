package game.component;

/*
    Conține zona pe care se poate selecta cu mausul de pe ecran un anumit obiect
 */

public class HitBox {
    public int hitBoxDifX, hitBoxDifY;

    public HitBox(int x, int y) {
        hitBoxDifX = x;
        hitBoxDifY = y;
    }
}