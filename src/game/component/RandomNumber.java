package game.component;

import java.util.Random;

public class RandomNumber {
    public static int randomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}