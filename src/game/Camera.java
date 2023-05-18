package game;

import game.component.Component;
import game.component.position.Position;
import game.character.Player;
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
        int difX = position.xPX - player.getPosition().xPX;
        int difY = position.yPX - player.getPosition().yPX;

        // TODO enter camera when changing character

        if (Math.abs(difX) > cameraDistance || Math.abs(difY) > cameraDistance) {
            followPlayer();
        }
    }

    private void followPlayer() {
        double speedX = player.getCharacter().getSpeed();
        double speedY = player.getCharacter().getSpeed();
        int difX = position.xPX - player.getPosition().xPX;
        int difY = position.yPX - player.getPosition().yPX;

        int signX, signY;

        if (difX != 0) {
            signX = difX / Math.abs(difX);
        } else {
            signX = 0;
        }

        if (difY != 0) {
            signY = difY / Math.abs(difY);
        } else {
            signY = 0;
        }

        if (Math.abs(difX) < speedX) {
            speedX = Math.abs(difX);
        }

        if (Math.abs(difY) < speedY) {
            speedY = Math.abs(difY);
        }

        position.tmpX -= speedX * signX;
        position.tmpY -= speedY * signY;

        position.xPX = (int)position.tmpX;
        position.yPX = (int)position.tmpY;
    }
}
