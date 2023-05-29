package game.component;

import java.util.Random;

/*
    Clasă pentru generarea de numere aleatorii (min ≤ nr ≤ max)
 */

public class RandomNumber {
    public static int randomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}