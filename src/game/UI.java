package game;

import game.character.Character;
import game.character.CharacterManager;
import game.character.Ranks;
import game.graphics.assets.UIAssets;
import game.level.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/*
    Clasa pentru afișarea UI
 */

public class UI {

    public static final int iconSize = 64;

    private static int frames = 0;
    private static int framesToPrint = 60;

    private static long time = System.currentTimeMillis();

    /*
        metoda draw parcurge obiectele ale căror proprietăți trebuie afișate pe ecran și calculează zona în care trebuie
        afișate
     */

    public static void draw(Graphics graphics) {

        if (System.currentTimeMillis() - time >= 1000) {
            time = System.currentTimeMillis();
            framesToPrint = frames;
            frames = 0;
        }

        frames++;

        int fpsPrintSize = 30;

        Font font = UIAssets.font.deriveFont(Font.PLAIN, fpsPrintSize);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        String fps = "FPS: " + framesToPrint;

        graphics.drawString(fps,Game.getGame().getWidth() - fontMetrics.stringWidth(fps) - 10, Game.getGame().getHeight() - 260);

        BufferedImage playerHealthBar;
        if (!CharacterManager.getCharacterManager().getPlayer().isDead()) {
            playerHealthBar = UIAssets.healthBar[CharacterManager.getCharacterManager().getPlayer().getEntity().getHealth()];
        } else {
            playerHealthBar = UIAssets.healthBar[0];
        }

        int y = 10;

        graphics.drawImage(playerHealthBar, 0, 0, (int)(3.5 * iconSize), iconSize, null);

        List<Character> commanding = CharacterManager.getCharacterManager().getPlayer().getCommanding();
        BufferedImage alliedHealthBar;
        BufferedImage alliedRank;
        int tmpY = iconSize;
        int printSize = 50;
        if (commanding != null) for (Character character : commanding) {
            if (character != null && !character.isDead()) {

                alliedHealthBar = UIAssets.healthBar[character.getEntity().getHealth()];
                alliedRank = switch (character.getRank()) {
                    case Oberleutnant -> UIAssets.ranks[3];
                    case Unterfeldwebel -> UIAssets.ranks[2];
                    case Unteroffiziere -> UIAssets.ranks[1];
                    case Obergefreiten -> UIAssets.ranks[0];
                };

                graphics.drawImage(alliedHealthBar, 0, tmpY, (int)(3.5 * printSize), printSize, null);
                graphics.drawImage(alliedRank, (int)(3.5 * printSize) + 5, tmpY, printSize, printSize, null);

                tmpY += printSize;
            }
        }

        printTopBar(graphics, 4 * iconSize, y);
        printWeapon(graphics, y);
        printInventory(graphics, y);
        printMinimap(graphics);
    }

    private static void printMinimap(Graphics graphics) {
        int printSize = 25;
        Font font = UIAssets.font.deriveFont(Font.PLAIN, printSize);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        graphics.setColor(Color.black);
        graphics.fillRect(Game.getGame().getWidth() - 225, Game.getGame().getHeight() - 250, 225, 250);

        graphics.setColor(Color.white);
        graphics.fillRect(Game.getGame().getWidth() - 224, Game.getGame().getHeight() - 24, 223, 23);

        String score = Integer.toString(CharacterManager.getCharacterManager().getPlayer().getScore());

        graphics.setColor(Color.black);
        graphics.drawString(score, Game.getGame().getWidth() - 2 - fontMetrics.stringWidth(score), Game.getGame().getHeight() - (printSize / 2) + 3);

        Minimap.draw(graphics, Game.getGame().getWidth() - 224, Game.getGame().getHeight() - 249, 223);

    }

    private static void printTopBar(Graphics graphics, int x, int y) {

        int printSize = 50;

        Font font = UIAssets.font.deriveFont(Font.PLAIN, printSize);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        graphics.drawImage(UIAssets.friendlyLocation, x, y, printSize, printSize, null);

        x += printSize + 10;
        String objectives = Map.getMap().getNrFinishedObjectives() + " / " + Map.getMap().getNrObjectives();
        graphics.drawString(objectives, x, y + (printSize / 2) + 10);

        x += fontMetrics.stringWidth(objectives) + 10;
        graphics.drawImage(UIAssets.nrAllies, x, y, printSize, printSize, null);

        x += printSize + 10;
        String nrAllies = Integer.toString(CharacterManager.getCharacterManager().getNrSoldiers());
        graphics.drawString(nrAllies, x, y + (printSize / 2) + 10);

        x += fontMetrics.stringWidth(nrAllies) + 10;
        graphics.drawImage(UIAssets.nrEliminated, x, y, printSize, printSize, null);

        x += printSize + 10;
        String nrEliminated = Integer.toString(CharacterManager.getCharacterManager().getPlayer().getNrKills());
        graphics.drawString(nrEliminated, x, y + (printSize / 2) + 10);

        x += fontMetrics.stringWidth(nrEliminated) + iconSize / 2;
        if (!CharacterManager.getCharacterManager().getPlayer().isDead()) {
            Ranks rank = CharacterManager.getCharacterManager().getPlayer().getRank();
            BufferedImage rankTexture = switch (rank) {
                case Oberleutnant -> UIAssets.ranks[3];
                case Unterfeldwebel -> UIAssets.ranks[2];
                case Unteroffiziere -> UIAssets.ranks[1];
                case Obergefreiten -> UIAssets.ranks[0];
            };

            graphics.drawImage(rankTexture, x, 6, iconSize, iconSize, null);
        }
    }

    private static void printWeapon(Graphics graphics, int y) {
        BufferedImage weaponTexture;
        String ammo;

        int printSize = 50;

        Font font = UIAssets.font.deriveFont(Font.PLAIN, printSize);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        int x = Game.getGame().getWidth() - iconSize * 2 - 15;

        if (CharacterManager.getCharacterManager().getPlayer().getEntity() != null && CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon() != null) {
            weaponTexture = UIAssets.weapons[CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().getID()];
            if (CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().hasAmmo()) {
                ammo = CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().getNrAmmo() + " / " + CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().getMaxAmmo();
                int ammoX = Game.getGame().getWidth() - fontMetrics.stringWidth(ammo) - printSize - 20;
                if (x < ammoX) {
                    ammoX = x;
                }

                if (CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().isSelectFire()) {
                    String fireMode;
                    if (CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().getMode()) {
                        fireMode = "F";
                    } else {
                        fireMode = "E";
                    }

                    graphics.drawString(fireMode, ammoX - 10 - fontMetrics.stringWidth(fireMode), y + (printSize / 2) + 10);
                }

                graphics.drawImage(UIAssets.ammo, ammoX, y, printSize, printSize, null);
                ammoX += printSize + 10;
                graphics.drawString(ammo, ammoX, y + (printSize / 2) + 10);

                y += printSize + 10;
            }
            graphics.drawImage(weaponTexture, x, y, iconSize * 2, iconSize, null);
        }
    }

    private static void printInventory(Graphics graphics, int y) {
        int printSize = 50;

        int tmpX = 10;
        int tmpY = Game.getGame().getHeight() - y + 3 - printSize;

        Font font = UIAssets.font.deriveFont(Font.PLAIN, printSize);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        if (CharacterManager.getCharacterManager().getPlayer().getMedal() != 0) {
            graphics.drawImage(UIAssets.medals[CharacterManager.getCharacterManager().getPlayer().getMedal() - 1], tmpX, tmpY, 3 * printSize, printSize, null);
            tmpX += 3 * printSize + 10;
        }

        if (CharacterManager.getCharacterManager().getPlayer().getEntity() != null && CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon() != null && CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().hasAmmo()) {
            double nrClips = (double) CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().getTotalAmmo() / CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapon().getMaxAmmo();
            int clips = (int) nrClips;
            if ((int)nrClips != nrClips) {
                clips++;
            }

            if (clips != 0) {
                graphics.drawImage(UIAssets.clip, tmpX, tmpY, printSize, printSize, null);
                tmpX += printSize + 10;
                if (clips > 1) {
                    String nrClipsString = "X " + clips;
                    graphics.drawString(nrClipsString, tmpX, tmpY + (printSize / 2) + y - 3);
                    tmpX += fontMetrics.stringWidth(nrClipsString) + 10;
                }
            }
        }

        if (CharacterManager.getCharacterManager().getPlayer().getEntity() != null && CharacterManager.getCharacterManager().getPlayer().getEntity().getNrMedKits() > 0) {
            graphics.drawImage(UIAssets.medKit, tmpX, tmpY, printSize, printSize, null);
            tmpX += printSize + 10;
            if (CharacterManager.getCharacterManager().getPlayer().getEntity().getNrMedKits() > 1) {
                String nrMedKitsString = "X " + CharacterManager.getCharacterManager().getPlayer().getEntity().getNrMedKits();
                graphics.drawString(nrMedKitsString, tmpX, tmpY + (printSize / 2) + y - 3);
                tmpX += fontMetrics.stringWidth(nrMedKitsString) + 10;
            }
        }
    }
}
