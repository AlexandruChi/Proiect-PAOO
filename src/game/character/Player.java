package game.character;

import game.Camera;
import game.Game;
import game.Input;
import game.component.HitBox;
import game.component.position.Position;
import game.component.weapon.MeleeWeapon;
import game.component.weapon.Weapon;
import game.component.weapon.WeaponBase;
import game.component.weapon.WeaponFactory;
import game.entity.Entity;
import game.entity.MakeEntity;

import java.awt.*;

import static game.character.Ranks.Oberleutnant;

public class Player implements Character {
    protected Entity entity;
    private int score;
    private boolean attack;
    private int nrKills;
    private int medal;

    private Ranks rank;

    public static final int scoreForKill = 10;


    public Player(Position position) {
        score = 0;
        entity = MakeEntity.makeEntity(MakeEntity.germanCharacterID, position);
        attack = true;
        nrKills = 0;
        medal = 0;

        Weapon[] weapons = entity.getWeapons();

        weapons[0] = WeaponFactory.makeWeapon(Weapon.Sword);
        weapons[1] = WeaponFactory.makeWeapon(Weapon.GermanSidearm);
        weapons[2] = WeaponFactory.makeWeapon(Weapon.GermanAR);

        weapons[1].addAmmo(weapons[1].getMaxAmmo() * 2);
        weapons[2].addAmmo(weapons[2].getMaxAmmo() * 4);

        setWeapon(2);

        rank = Oberleutnant;
    }

    public void update() {
        entity.setTravelDir(Game.getGame().getInput().getDirection());
        entity.setSprint(Game.getGame().getInput().getSprint());
        entity.setWeapon(Game.getGame().getInput().getCurentWeapon());

        if (entity.getWeapon() != null && Game.getGame().getInput().getReload()) {
            entity.getWeapon().reload();
        }

        if (Game.getGame().getInput().getChangeMode()) {
            if (entity.getWeapon() != null) {
                entity.getWeapon().setMode(!entity.getWeapon().getMode());
            }
        }

        int orientation = 0;
        boolean changeOrientation = false;
        
        if (attack) {
            boolean singleClick = Game.getGame().getInput().getSingleClick();
            Position attackPosition = Game.getGame().getInput().getPosition();
            if (attackPosition != null) {
                Position mapPosition = Camera.getCamera().getMapPosition(attackPosition);
                if (mapPosition.xPX < getPosition().xPX) {
                    changeOrientation = true;
                    orientation = 0;
                } else {
                    changeOrientation = true;
                    orientation = 1;
                }
                if (entity.getWeapon().getMode() || singleClick) {
                    entity.attack(mapPosition);
                }
            }
        }

        // TODO add aim

        entity.update();
        if(changeOrientation) {
                entity.setOrientation(orientation);
        }
    }
    public void draw(Graphics graphics, Camera camera) {
        entity.draw(graphics, camera);
    }

    public Ranks getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public void addKill() {
        nrKills++;
        switch (nrKills) {
            case 5, 10, 20 -> {
                addMedal();
            }
        }
        score += scoreForKill;
    }

    public int getMedal() {
        return medal;
    }

    public void addMedal() {
        if (medal < 9) {
            medal++;
        }
    }

    public int getNrKills() {
        return nrKills;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public HitBox getHitBox() {
        return entity.getHitBox();
    }

    @Override
    public void setWeapon(int weapon) {
        entity.setWeapon(weapon);
    }

    @Override
    public boolean isDead() {
        if (entity == null) {
            return true;
        }
        return entity.isDead();
    }

    public void canAttack(boolean attack) {
        this.attack = attack;
    }

    public void setCharacter(Entity entity) {
        this.entity = entity;
    }

    public Position getPosition() {
        return entity.getPosition();
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void decScore(int score) {
        this.score -= score;
        if (this.score < 0) {
            this.score = 0;
        }
    }
}
