package game.component;

import game.Window;
import game.component.position.Position;
import game.component.texture.MakeTexture;
import game.graphics.assets.UIAssets;

/*
    Clasă pentru implementarea obiectelor care pot fi colectate de către jucător.
    Tipul obiectelor este generate aleatoriu la încărcarea nivelului.
    Conținutul unor obiecte este generat la aleatoriu la colectarea acestora.
 */

public class Collectable extends TextureComponent {

    public static final int medKit = 1;
    public static final int ammo = 2;
    public static final int toolBox = 3;
    public static final int map = 4;

    public static final int textureSize = 2 * Window.objectSize;

    private final int type;

    private boolean collected;

    public Collectable(Position position) {
        super(position, null, new PrintBox(textureSize / 2, textureSize));
        collected = false;

        type = RandomNumber.randomNumber(1, 4);

        texture = switch (type) {
            case medKit, ammo -> MakeTexture.make(UIAssets.collectable, textureSize);
            case toolBox -> MakeTexture.make(UIAssets.toolBox, textureSize);
            case map -> MakeTexture.make(UIAssets.map, textureSize);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public Collectable(Position position, int type) {
        super(position, null, new PrintBox(textureSize / 2, textureSize));
        collected = false;

        this.type = type;

        texture = switch (this.type) {
            case medKit, ammo -> MakeTexture.make(UIAssets.collectable, textureSize);
            case toolBox -> MakeTexture.make(UIAssets.toolBox, textureSize);
            case map -> MakeTexture.make(UIAssets.map, textureSize);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    /*
        la colectarea unui obiect se returnează un Pair cu tipul obiectului și valoarea obiectului
     */

    public Pair<Integer, Integer> collect() {
        collected = true;
        return new Pair<>(type, switch (type) {
            case medKit -> RandomNumber.randomNumber(1, 2);
            case ammo -> RandomNumber.randomNumber(1, 3);
            case toolBox -> 100;
            case map -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        });
    }

    public boolean isCollected() {
        return collected;
    }

    public int getType() {
        return type;
    }
}
