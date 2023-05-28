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
    boolean singleClick;
    boolean changMode;
    int curentWeapon;
    int change;
    boolean medKit;
    boolean reload;

    Direction direction;

    Position position;

    public Input() {
        input = false;
        direction = Direction.stop;
        validDirections = Set.of(Direction.up, Direction.down, Direction.left, Direction.right);
        position = null;
        singleClick = false;
        changMode = false;
        curentWeapon = 2;
        change = 0;
        medKit = false;
    }

    public Position getPosition() {
        singleClick = false;
        return position;
    }

    public int getChange() {
        if (change != 0) {
            int returnChange = change;
            change = 0;
            return returnChange;
        }
        return change;
    }

    public boolean getSingleClick() {
        return singleClick;
    }

    public boolean getChangeMode() {
        if (changMode) {
            changMode = false;
            return true;
        }
        return false;
    }

    public boolean getMedKit() {
        if (medKit) {
            medKit = false;
            return true;
        }
        return false;
    }

    public boolean getReload() {
        if (reload) {
            reload = false;
            return true;
        }
        return false;
    }

    public int getCurentWeapon() {
        if (curentWeapon != -1) {
            int returnCurentWeapon = curentWeapon;
            curentWeapon = -1;
            return returnCurentWeapon;
        }
        return curentWeapon;
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

            case KeyEvent.VK_M -> medKit = true;

            case KeyEvent.VK_1 -> curentWeapon = 0;
            case KeyEvent.VK_2 -> curentWeapon = 1;
            case KeyEvent.VK_3 -> curentWeapon = 2;

            case KeyEvent.VK_UP -> change = 1;
            case KeyEvent.VK_DOWN -> change = -1;

            case KeyEvent.VK_V -> changMode = true;
            case KeyEvent.VK_R -> reload = true;

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

            case KeyEvent.VK_M -> medKit = false;

            case KeyEvent.VK_1, KeyEvent.VK_3, KeyEvent.VK_2 -> curentWeapon = -1;

            case KeyEvent.VK_V -> changMode = false;
            case KeyEvent.VK_R -> reload = false;

            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> change = 0;

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
        singleClick = true;
        position = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        singleClick = false;
        position = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
