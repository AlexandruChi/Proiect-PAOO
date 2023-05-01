package game;

import game.component.Component;
import game.component.Direction;
import game.component.Pair;
import game.component.position.Distance;
import game.component.position.Position;
import game.entity.Player;
import game.graphics.assets.CharacterAssets;

public class Camera extends Component {
    private static final int cameraDistance = 175;
    public static final int screenX = 367;
    public static final int screenY = 270 + CharacterAssets.characterTextureSize / 2;
    private final Player player;

    public Camera(Player player) {
        super(new Position(player.getPosition()));
        this.player = player;
    }

    public void update() {
        if (Distance.calculateDistance(new Pair<>((double)position.xPX, (double)position.yPX), new Pair<>((double)player.getPosition().xPX, (double)player.getPosition().yPX)) > cameraDistance) {
            move(player.getCharacter().getTravelDir());
        }
    }

    private void move(Direction direction) {
        int signX = switch (direction) {
            case right, up_right, down_right -> 1;
            case left, up_left, down_left -> -1;
            default -> 0;
        };

        int signY = switch (direction) {
            case up, up_right, up_left -> -1;
            case down, down_right, down_left -> 1;
            default -> 0;
        };

        position.tmpX += player.getCharacter().getSpeed() * signX;
        position.tmpY += player.getCharacter().getSpeed() * signY;

        position.xPX = (int)position.tmpX;
        position.yPX = (int)position.tmpY;
    }
}
