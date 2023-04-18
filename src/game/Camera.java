package game;

import game.component.Pair;
import game.component.position.Distance;
import game.component.position.Position;
import game.entity.Player;
import game.graphics.assets.CharacterAssets;

public class Camera {
    private static final int cameraDistance = 200;
    private static final int screenX = 367;
    private static final int screenY = 270 + +CharacterAssets.characterTextureSize;
    public Position position;
    private Player player;

    public Camera(Player player) {
        this.player = player;
        position = player.getPosition();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void update() {
        if (Distance.calculateDistance(new Pair<>((double)position.xPX, (double)position.yPX), new Pair<>((double)player.getPosition().xPX, (double)player.getPosition().yPX)) > cameraDistance) {
            position = player.getPosition();
        }
    }
}
