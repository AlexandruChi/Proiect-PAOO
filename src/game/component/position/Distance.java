package game.component.position;

import game.component.Pair;

public class Distance {
    public static double calculateDistance(Pair<Double, Double> point1, Pair<Double, Double> point2) {
        return Math.sqrt(Math.pow(point2.getLeft() - point1.getLeft(), 2) + Math.pow(point2.getRight() - point1.getRight(), 2));
    }
}

