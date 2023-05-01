package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

public class Window {

    // TODO make objectSize depend on screen size;
    public static final int objectSize = 32;
    private final JFrame jFrame;
    private final Canvas canvas;

    //TODO better resolution

    public Window(String title, int width, int height) {

        jFrame = new JFrame(title);
        jFrame.setSize(width, height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        //jFrame.addComponentListener(new AspectRatio(this));

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        jFrame.add(canvas);
        jFrame.pack();
    }

    public int getWidth() {
        return jFrame.getWidth();
    }

    public int getHeight() {
        return jFrame.getHeight();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getJFrame() {return jFrame;}

    public void setInput(KeyListener keyListener) {
        jFrame.addKeyListener(keyListener);
    }

    public void setSize(int width, int height) {
        jFrame.setSize(new Dimension(width, height));
        canvas.setSize(new Dimension(width, height));
    }
}

class AspectRatio extends ComponentAdapter {
    private final Window window;

    public AspectRatio(Window window) {
        this.window = window;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int ratioWidth = 16;
        int ratioHeight = 9;

        int width = window.getWidth();
        int height = window.getHeight();

        if (width / ratioWidth < height / ratioHeight) {
            height = width * ratioHeight / ratioWidth;
        } else {
            width = height * ratioWidth / ratioHeight;
        }

        if (window.getJFrame().getRootPane().getWindowDecorationStyle() == JRootPane.NONE) {
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int screenWidth = graphicsDevice.getDisplayMode().getWidth();
            int screenHeight = graphicsDevice.getDisplayMode().getHeight();
            int x = (screenWidth - width) / 2;
            int y = (screenHeight - height) / 2;
            window.getJFrame().setLocation(x, y);
        }

        window.setSize(width, height);
    }
}