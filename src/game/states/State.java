package game.states;

import java.awt.*;

public abstract class State {
    private static State state = null;

    public static void setState(State state) {
        State.state = state;
    }

    public static State getState() {
        return state;
    }

    public abstract void update();
    public abstract void draw(Graphics graphics);
}
