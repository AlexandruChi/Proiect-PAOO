package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
    Clasa pentru fereastra principală.
    Conține un obiect de tim JFrame pentru fereastră și un obiect Canvas pentru desenare;
 */

public class Window {

    /*
        constanta objectScale poste fi modificată pentru a mări sau micșora distanța dintre cameră și jucător
     */

    private static final int objectScale = 23;
    public static final int objectSize = (int) (objectScale * ((double)Game.height / 540));
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

        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public int getWidth() {
        return getCanvas().getWidth();
    }

    public int getHeight() {
        return getCanvas().getHeight();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getJFrame() {return jFrame;}

    public void setInput(Input input) {
        canvas.addKeyListener(input);
        canvas.addMouseListener(input);
    }

    public void setSize(int width, int height) {
        jFrame.setSize(new Dimension(width, height));
        canvas.setSize(new Dimension(width, height));
    }
}