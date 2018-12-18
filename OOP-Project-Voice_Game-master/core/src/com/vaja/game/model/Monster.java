package com.vaja.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.vaja.game.battle.STAT;
import com.vaja.game.battle.move.Move;
import com.vaja.game.battle.move.MoveDB;
import com.vaja.game.battle.move.MoveSpecification;

import java.util.HashMap;
import java.util.Map;

public class Monster {
    private String name;

    private int level;

    private Map<STAT, Integer> stats;
    private int currentHitpoints;

    /*
     * This array holds prototypes. Use #clone to get an instance.
     */
    private Move[] moves = new Move[4];

    private Texture image;

    public Monster(String name, Texture image, int hp) {
        this.name = name;
        this.image = image;
        if(name.equals("Brian")) {
        	this.level = 7;
        }else if(name.equals("Bird")) {
        	this.level = 5;
        }else if(name.equals("Centapide")){
        	this.level = 20;
        }else if(name.equals("Worm")) {
        	this.level = 50;
        }else {
        	this.level = 100;
        }
        
        

        /* init all stats to 1 */
        stats = new HashMap<STAT, Integer>();
        for (STAT stat : STAT.values()) {
            stats.put(stat, hp);
        }
        stats.put(STAT.HITPOINTS, 10);
        currentHitpoints = stats.get(STAT.HITPOINTS);
    }

    public Texture getSprite() {
        return image;
    }

    public void setLevel(int level) {
		this.level = level;
	}

	public int getCurrentHitpoints() {
        return currentHitpoints;
    }

    public void setCurrentHitpoints(int currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    public String getName() {
        return name;
    }

    /**
     * @param index		Position of the move in the move-array. 0 - 3 allowed.
     * @param move		Move to put in
     */
    public void setMove(int index, Move move) {
        moves[index] = move;
    }

    /**
     * NOTE: This should deplete PP.
     * @param index		Move to use, 0 - 3 allowed
     * @return			Clone (safe) of move at index
     */
    public Move getMove(int index) {
        return moves[index].clone();
    }

    public MoveSpecification getMoveSpecification(int index) {
        return moves[index].getMoveSpecification();
    }

    public int getStat(STAT stat) {
        return stats.get(stat);
    }

    public void setStat(STAT stat, int value) {
        stats.put(stat, value);
    }

    public int getLevel() {
        return level;
    }

    public void applyDamage(int amount) {
        currentHitpoints -= amount;
        if (currentHitpoints < 0) {
            currentHitpoints = 0;
        }
    }


    public boolean isFainted() {
        return currentHitpoints == 0;
    }

    public static Monster generatePokemon(String name, Texture sprite, MoveDB moveDatabase, int hp) {
        Monster generated = new Monster(name, sprite, hp);
        generated.setMove(0, moveDatabase.getMove(0));
        generated.setMove(1, moveDatabase.getMove(1));
        generated.setMove(2, moveDatabase.getMove(2));
        generated.setMove(3, moveDatabase.getMove(3));

        return generated;
    }
}
