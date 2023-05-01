package game.component.position;

import game.component.Pair;

public class RelativeCoordinates {
    public static Pair<Integer, Integer> getRelativeCoordinates(Pair<Integer, Integer> base, Pair<Integer, Integer> coords) {
        return new Pair<>(coords.getLeft() - base.getLeft(), coords.getRight() - base.getRight());
    }
}