package game.entity;

public class NormalEnemy extends Enemy{
    @Override
    public void update() {
        character.update();
    }
}
