package game;

import game.component.Direction;
import game.component.position.Position;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

public class Input implements KeyListener, MouseListener {
    private final Set<Direction> validDirections;
    boolean input;
    boolean sprint;
    Direction direction;

    Position position;

    public Input() {
        input = false;
        direction = Direction.stop;
        validDirections = Set.of(Direction.up, Direction.down, Direction.left, Direction.right);
        position = null;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        input = true;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> addMovement(Direction.up);
            case KeyEvent.VK_S -> addMovement(Direction.down);
            case KeyEvent.VK_A -> addMovement(Direction.left);
            case KeyEvent.VK_D -> addMovement(Direction.right);

            case KeyEvent.VK_SHIFT -> sprint = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> subMovement(Direction.up);
            case KeyEvent.VK_S -> subMovement(Direction.down);
            case KeyEvent.VK_A -> subMovement(Direction.left);
            case KeyEvent.VK_D -> subMovement(Direction.right);

            case KeyEvent.VK_SHIFT -> sprint = false;
        }
    }

    private void addMovement(Direction direction) {
        if (!validDirections.contains(direction)) {
            return;
        }
        switch (this.direction) {
            case stop -> this.direction = direction;
            case up -> {
                switch (direction) {
                    case down -> this.direction = Direction.down;
                    case left -> this.direction = Direction.up_left;
                    case right -> this.direction = Direction.up_right;
                    default -> {
                    }
                }
            }
            case down -> {
                switch (direction) {
                    case up -> this.direction = Direction.up;
                    case left -> this.direction = Direction.down_left;
                    case right -> this.direction = Direction.down_right;
                    default -> {
                    }
                }
            }
            case left -> {
                switch (direction) {
                    case up -> this.direction = Direction.up_left;
                    case down -> this.direction = Direction.down_left;
                    case right -> this.direction = Direction.right;
                    default -> {
                    }
                }
            }
            case right -> {
                switch (direction) {
                    case up -> this.direction = Direction.up_right;
                    case down -> this.direction = Direction.down_right;
                    case left -> this.direction = Direction.left;
                    default -> {
                    }
                }
            }
            case up_left -> {
                switch (direction) {
                    case down -> this.direction = Direction.down_left;
                    case right -> this.direction = Direction.up_right;
                }
            }
            case up_right -> {
                switch (direction) {
                    case down -> this.direction = Direction.down_right;
                    case left -> this.direction = Direction.up_left;
                }
            }
            case down_left -> {
                switch (direction) {
                    case up -> this.direction = Direction.up_left;
                    case right -> this.direction = Direction.down_right;
                }
            }
            case down_right -> {
                switch (direction) {
                    case up -> this.direction = Direction.up_right;
                    case left -> this.direction = Direction.down_left;
                }
            }
        }
    }

    private void subMovement(Direction direction) {
        if (!validDirections.contains(direction)) {
            return;
        }

        if (this.direction == direction) {
            this.direction = Direction.stop;
            return;
        }

        switch (this.direction) {
            case up_left -> {
                switch (direction) {
                    case up -> this.direction = Direction.left;
                    case left -> this.direction = Direction.up;
                }
            }
            case up_right -> {
                switch (direction) {
                    case up -> this.direction = Direction.right;
                    case right -> this.direction = Direction.up;
                }
            }
            case down_left -> {
                switch (direction) {
                    case down -> this.direction = Direction.left;
                    case left -> this.direction = Direction.down;
                }
            }
            case down_right -> {
                switch (direction) {
                    case down -> this.direction = Direction.right;
                    case right -> this.direction = Direction.down;
                }
            }
        }
    }

    public boolean hadInput() {
        if (input) {
            input = false;
            return true;
        }
        return false;
    }

    public Boolean getSprint() {
        return sprint;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        position = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        position = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
