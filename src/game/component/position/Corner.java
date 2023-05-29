package game.component.position;

import game.component.Pair;

/*
    Clasă pentru a verifica dacă un set de coordonate se află într-o anumită poziție față de diagonala principală sau
    secundară (dată de parametrul corner);
 */

public class Corner {
    public static boolean isInCorner(int squareSize, Pair<Integer, Integer> coordinates, int corner) {
        int y = coordinates.getRight();
        int x = coordinates.getLeft();

        return switch (corner) {
            case 1 -> x > y;
            case 2 -> x < squareSize - y;
            case 3 -> x < y;
            case 4 -> x > squareSize - y;
            default -> false;
        };
    }
}
