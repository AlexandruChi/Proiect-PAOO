package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Window {
    private final JFrame jFrame;
    private final Canvas canvas;

    public Window(String title, int width, int height) {

        jFrame = new JFrame(title);
        jFrame.setSize(width, height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        jFrame.add(canvas);
        jFrame.pack();
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getJFrame() {return jFrame;}

    public void setInput(KeyListener keyListener) {
        jFrame.addKeyListener(keyListener);
    }
}
