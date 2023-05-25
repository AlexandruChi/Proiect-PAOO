package game;

import game.character.*;
import game.character.Character;
import game.component.Collectable;
import game.graphics.assets.UIAssets;
import game.level.Map;

import java.awt.*;

public class Minimap {

    private static int iconSize = 10;

    public static void draw(Graphics graphics, int x, int y, int size) {
        graphics.drawImage(UIAssets.minimap[Map.getMap().curentLevel() - 1], x, y, size, size, null);

        for (Character character : CharacterManager.getCharacterManager().getCharacters()) {
            if (character != null && !character.isDead()) {
                double cX = character.getPosition().tmpX / (double) Map.widthPX;
                double cY = character.getPosition().tmpY / (double) Map.heightPX;

                cX *= size;
                cY *= size;

                if (character instanceof UndeadEnemy) {
                    graphics.drawImage(UIAssets.unknownEnemy, (int) (x + cX) - iconSize / 2, (int) (y + cY) - iconSize / 2, iconSize, iconSize, null);
                }

                if (character instanceof NormalEnemy) {
                    graphics.drawImage(UIAssets.enemy, (int) (x + cX) - iconSize / 2, (int) (y + cY) - iconSize / 2, iconSize, iconSize, null);
                }

                if (character instanceof Player || character instanceof Unit) {
                    graphics.drawImage(switch (character.getRank()) {
                        case Oberleutnant -> UIAssets.friendly[3];
                        case Unterfeldwebel -> UIAssets.friendly[2];
                        case Unteroffiziere -> UIAssets.friendly[1];
                        case Obergefreiten -> UIAssets.friendly[0];
                    }, (int) (x + cX) - (iconSize * 2) / 2, (int) (y + cY) - (iconSize * 2) / 2, iconSize * 2, iconSize * 2, null);
                }
            }
        }

        for (Collectable objective : Map.getMap().getObjectives()) {
            if (objective != null) {
                double cX = objective.getPosition().tmpX / (double) Map.widthPX;
                double cY = objective.getPosition().tmpY / (double) Map.heightPX;

                cX *= size;
                cY *= size;

                graphics.drawImage(UIAssets.friendlyLocation, (int) (x + cX) - (iconSize * 2) / 2, (int) (y + cY) - (iconSize * 2) / 2, iconSize * 2, iconSize * 2, null);
            }
        }

        if (Map.getMap().getExit() != null) {
            double cX = Map.getMap().getExit().tmpX / (double) Map.widthPX;
            double cY = Map.getMap().getExit().tmpY / (double) Map.heightPX;

            cX *= size;
            cY *= size;

            graphics.drawImage(UIAssets.enemyLocation, (int) (x + cX) - (iconSize * 2) / 2, (int) (y + cY) - (iconSize * 2) / 2, iconSize * 2, iconSize * 2, null);
        }
    }
}
