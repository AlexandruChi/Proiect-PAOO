package game;

import game.component.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

public class Input implements KeyListener {
    private final Set<Direction> validDirections;
    boolean input;
    boolean sprint;
    Direction direction;

    public Input() {
        input = false;
        direction = Direction.stop;
        validDirections = Set.of(Direction.up, Direction.down, Direction.left, Direction.right);
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

        System.out.println(direction);
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
}
