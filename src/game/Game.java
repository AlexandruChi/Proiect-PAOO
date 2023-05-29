package game;

import game.graphics.AssetManager;
import game.level.Map;
import game.states.GameState;
import game.states.StartState;
import game.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

/*
    Clasa principală a jocului.
 */

public class Game implements Runnable {
    private static Game game = null;
    private boolean isRunning;
    private Thread thread;
    private final Window window;
    private final Input input;


    /*
        Constante pentru mărimea ferestrei jocului.
        Dimensiunea texturilor și poziția pe hartă a obiectelor este calculată în funcție de înălțimea ferestre.
     */

    public static final int height = 700;   // default height 540
    public static final int width = (int) (height * (16.0/9));

    public Input getInput() {
        return input;
    }

    public static Game getGame() {
        if (game == null) {
            game = new Game("Beim letzten Weltkrieg", width, height);
        }
        return game;
    }

    private Game(String title, int width, int height) {
        isRunning = false;
        window = new Window(title, width, height);
        input = new Input();
        window.setInput(input);

        AssetManager.load();

        State.setState(new StartState());
    }

    @Override
    public void run() {
        long oldTime = System.nanoTime();
        long newTime;

        final double frameTime = (double) 1000000000 / 60;
        while (isRunning) {
            newTime = System.nanoTime();
            if ((newTime - oldTime) > frameTime) {
                update();
                draw();
                oldTime = newTime;
            }
        }
    }

    public synchronized void start() {
        if (!isRunning) {
            thread = new Thread(this);
            thread.start();
            isRunning = true;
        }
    }

    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (State.getState() != null) {
            State.getState().update();
        }
    }

    private void draw() {
        BufferStrategy bufferStrategy = window.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, window.getWidth(), window.getHeight());

        if (State.getState() != null) {
            State.getState().draw(graphics);
        }

        bufferStrategy.show();
        graphics.dispose();
    }

    public Window getWindow() {
        return window;
    }

    public int getWidth() {
        return getWindow().getWidth();
    }

    public int getHeight() {
        return getWindow().getHeight();
    }

    public void loadGame() {
        State.setState(new GameState());
    }

    public void startScreen() {
        State.setState(new StartState());
    }

    public boolean isRunning() {
        return isRunning;
    }
}
