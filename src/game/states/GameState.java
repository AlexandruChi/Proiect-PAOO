package game.states;

import game.Camera;
import game.Game;
import game.UI;
import game.character.Character;
import game.character.CharacterManager;
import game.character.Player;
import game.level.LevelManager;
import game.level.Map;

import java.awt.*;

/*
    State-ul principal al jocului.
    Creează obiectele pentru hartă, cameră și caractere;
    Salvează în baza de date datele despre începutul nivelului pentru fi identice la reluarea nivelului
    (obiectele din nivele fiind generate aleatoriu la fiecare pornire a jocului)
    Datele despre hartă care nu se schimbă la fiecare pornire a jocului sunt stocate în fișierul campaign.blwk
    Starea curentă a jocului este salvată la apăsarea taster ESC
 */

public class GameState extends State {
    private final Map map;
    private final Camera camera;
    private final CharacterManager characterManager;

    public GameState() {
        LevelManager.readFile("campaign.blwk");
        map = new Map();
        characterManager = new CharacterManager(map);
        camera = new Camera(characterManager.getPlayer());

        LevelManager.saveLevelStart();
    }

    @Override
    public void update() {
        characterManager.update();
        camera.update();

        if (Game.getGame().getInput().getEscape()) {
            LevelManager.saveGame();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        map.draw(graphics, camera, characterManager);
        UI.draw(graphics);
    }
}
