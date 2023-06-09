package game;

import game.component.Component;
import game.component.Pair;
import game.component.position.Position;
import game.character.Player;
import game.component.position.RelativeCoordinates;
import game.graphics.assets.CharacterAssets;

/*
    Clasa pentru ajustarea poziției camerei;
 */

public class Camera extends Component {
    private static Camera camera;
    private static final int cameraDistance = (int) (100 * ((double)Game.height / 540));

    /*
        constante pentru definirea poziției camerei pe ecran;
     */

    public static final int screenX = (int) (367 * ((double)Game.height / 540));
    public static final int screenY = (int) (270 * ((double)Game.height / 540) + CharacterAssets.characterTextureSize / 2);
    private final Player player;

    public Camera(Player player) {
        super(new Position(player.getPosition()));
        this.player = player;
        camera = this;
    }

    public static Camera getCamera() {
        return camera;
    }

    public void update() {
        int difX;
        int difY;

        if (!player.isDead()) {
            difX = position.xPX - player.getPosition().xPX;
            difY = position.yPX - player.getPosition().yPX;
        } else {
            difX = 0;
            difY = 0;
        }

        if (Math.abs(difX) > cameraDistance || Math.abs(difY) > cameraDistance) {
            followPlayer();
        } else {
            position.tmpY = position.yPX;
            position.tmpX = position.xPX;
        }
    }

    /*
        Dacă distanța dintre cameră și jucător pe ecran este mai mare decât cameraDistance, camera se mișcă în direcția
        jucătorului cu viteza acestuia.
     */

    private void followPlayer() {
        double speedX = player.getEntity().getSpeed();
        double speedY = player.getEntity().getSpeed();
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

    public Position getMapPosition(Position position) {
        Pair<Integer, Integer> relativePosition = RelativeCoordinates.getRelativeCoordinates(new Pair<>(screenX, screenY), new Pair<>(position.xPX, position.yPX));
        return new Position(getPosition().xPX + relativePosition.getLeft(), getPosition().yPX + relativePosition.getRight());
    }
}
