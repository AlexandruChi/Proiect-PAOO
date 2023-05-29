package game.component.position;

import game.component.Pair;

/*
    Clasă pentru calcularea coordonatelor unui punt relativ cu un alt punct.
    Este folosită pentru a obține poziția pe ecran a unui obiect de pe hartă
 */
public class RelativeCoordinates {
    public static Pair<Integer, Integer> getRelativeCoordinates(Pair<Integer, Integer> base, Pair<Integer, Integer> coords) {
        return new Pair<>(coords.getLeft() - base.getLeft(), coords.getRight() - base.getRight());
    }
}