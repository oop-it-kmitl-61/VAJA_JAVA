package com.vaja.game.model.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.vaja.game.dialogue.Dialogue;
import com.vaja.game.model.*;
import com.vaja.game.model.world.World;
import com.vaja.game.model.world.WorldObj;
import com.vaja.util.AnimationSet;

public class Actor implements YSortable {

    private int level;
    private float sizeX, sizeY;
    private boolean battle;
    private String name;
    private World world;
    private int x;
    private int y;
    private DIRECTION facing;
    private boolean visible = true;

    private float worldX, worldY;

    // For callbacks to World
    private ActorObserver observer;

    /* state specific */
    private int srcX, srcY;
    private int destX, destY;
    private float animTimer;
    private float WALK_TIME_PER_TILE = 0.15f;
    private float REFACE_TIME = 0.1f;
    private boolean noMoveNotifications = false;

    private float walkTimer;
    private boolean moveRequestThisFrame;

    private ACTOR_STATE state;

    private AnimationSet animations;

    private Dialogue dialogue;

    public Actor(World world, int x, int y, AnimationSet animations) {
        this.observer = world;
        this.world = world;
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        this.animations = animations;
        this.state = ACTOR_STATE.STANDING;
        this.facing = DIRECTION.SOUTH;
    }

    public enum ACTOR_STATE {
        WALKING,
        STANDING,
        REFACING,
        BATTLE,

        ;
    }

    public void update(float delta) {

        if (state == ACTOR_STATE.WALKING) {
            animTimer += delta;
            walkTimer += delta;
            worldX = Interpolation.linear.apply(srcX, destX, animTimer / WALK_TIME_PER_TILE);
            worldY = Interpolation.linear.apply(srcY, destY, animTimer / WALK_TIME_PER_TILE);
            if (animTimer > WALK_TIME_PER_TILE) {
                float leftOverTime = animTimer - WALK_TIME_PER_TILE;
                finishMove();
                if (moveRequestThisFrame) { // keep walking using the same animation time
                    if (move(facing)) {
                        animTimer += leftOverTime;
                        worldX = Interpolation.linear.apply(srcX, destX, animTimer / WALK_TIME_PER_TILE);
                        worldY = Interpolation.linear.apply(srcY, destY, animTimer / WALK_TIME_PER_TILE);
                    }
                } else {
                    walkTimer = 0f;
                }
            }
        }
        if (state == ACTOR_STATE.REFACING) {
            animTimer += delta;
            if (animTimer > REFACE_TIME) {
                state = ACTOR_STATE.STANDING;
            }
        }
        moveRequestThisFrame = false;
    }

    public boolean reface(DIRECTION dir) {
        if (state != ACTOR_STATE.STANDING) { // can only reface when standing
            return false;
        }
        if (facing == dir) { // can't reface if we already face a direction
            return true;
        }
        facing = dir;
        state = ACTOR_STATE.REFACING;
        animTimer = 0f;
        return true;
    }

    /**
     * Changes the Players facing direction, without the walk-frame animation.
     * This is used when loading maps, and in dialogue.
     */
    public boolean refaceWithoutAnimation(DIRECTION dir) {
        if (state != ACTOR_STATE.STANDING) { // can only reface when standing
            return false;
        }
        this.facing = dir;
        return true;
    }

    /**
     * Initializes a move. If you want to move an Actor, use this method.
     * @param dir	Direction to move
     * @return		If the move can be performed
     */
    public boolean move(DIRECTION dir) {
        System.out.println(srcX+":"+srcY);
        if (state == ACTOR_STATE.WALKING) {
            if (facing == dir) {
                moveRequestThisFrame = true;
            }
            return false;
        }
        
        // edge of world test
        if (x+dir.getDx() >= world.getMap().getWidth() || x+dir.getDx() < 0 || y+dir.getDy() >= world.getMap().getHeight() || y+dir.getDy() < 0) {
            reface(dir);
            return false;
        }
        // unwalkable tile test
        if (!world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).walkable()) {
            reface(dir);
            return false;
        }
        // actor test
        if (world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).getActor() != null) {
            reface(dir);
            return false;
        }
        // object test
        if (world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).getObject() != null) {
            WorldObj o = world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).getObject();
            if (!o.isWalkable()) {
                reface(dir);
                return false;
            }
        }
        if (world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).actorBeforeStep(this) == true) {
            initializeMove(dir);
            world.getMap().getTile(x, y).setActor(null);
            x += dir.getDx();
            y += dir.getDy();
            world.getMap().getTile(x, y).setActor(this);
            return true;
        }
        return false;
    }



    /**
     * Same as #move() but doesn't notify receiving Tile.
     * Doesn't obey Tile#actorBeforeStep and Tile#actorStep
     */
    public boolean moveWithoutNotifications(DIRECTION dir) {
        noMoveNotifications = true;
        if (state == ACTOR_STATE.WALKING) {
            if (facing == dir) {
                moveRequestThisFrame = true;
            }
            return false;
        }
        // edge of world test
        if (x+dir.getDx() >= world.getMap().getWidth() || x+dir.getDx() < 0 || y+dir.getDy() >= world.getMap().getHeight() || y+dir.getDy() < 0) {
            reface(dir);
            return false;
        }
        // unwalkable tile test
        if (!world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).walkable()) {
            reface(dir);
            return false;
        }
        // actor test
        if (world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).getActor() != null) {
            reface(dir);
            return false;
        }
        // object test
        if (world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).getObject() != null) {
            WorldObj o = world.getMap().getTile(x+dir.getDx(), y+dir.getDy()).getObject();
            if (!o.isWalkable()) {
                reface(dir);
                return false;
            }
        }
        initializeMove(dir);
        world.getMap().getTile(x, y).setActor(null);
        x += dir.getDx();
        y += dir.getDy();
        world.getMap().getTile(x, y).setActor(this);
        return true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private void initializeMove(DIRECTION dir) {
        this.facing = dir;
        this.srcX = x;
        this.srcY = y;
        this.destX = x+dir.getDx();
        this.destY = y+dir.getDy();
        this.worldX = x;
        this.worldY = y;
        animTimer = 0f;
        state = ACTOR_STATE.WALKING;
    }

    private void finishMove() {
        state = ACTOR_STATE.STANDING;
        this.worldX = destX;
        this.worldY = destY;
        this.srcX = 0;
        this.srcY = 0;
        this.destX = 0;
        this.destY = 0;
        if (!noMoveNotifications) {
            world.getMap().getTile(x, y).actorStep(this);
        } else {
            noMoveNotifications = false;
        }
    }

    /**
     * Changes the Players position internally.
     */
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getWorldX() {
        return worldX;
    }

    public float getWorldY() {
        return worldY;
    }

    public TextureRegion getSprite() {
        if (state == ACTOR_STATE.WALKING) {
            return animations.getWalking(facing).getKeyFrame(walkTimer);
        } else if (state == ACTOR_STATE.STANDING) {
            return animations.getStanding(facing);
        } else if (state == ACTOR_STATE.REFACING) {
            return animations.getWalking(facing).getKeyFrames()[0];
        }
        return animations.getStanding(DIRECTION.SOUTH);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public float getSizeX() {
        return sizeX;
    }

    @Override
    public float getSizeY() {
        return sizeY;
    }

    public void setSizeX(float sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(float sizeY) {
        this.sizeY = sizeY;
    }

    public void changeWorld(World world, int newX, int newY) {
        this.world.removeActor(this);
        this.teleport(newX, newY);
        this.world = world;
        this.world.addActor(this);
    }

    public void loser(){
        this.world.removeActor(this);
    }

    public ACTOR_STATE getState() {
        return state;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DIRECTION getFacing() {
        return facing;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public World getWorld() {
        return world;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isBattle() {
        return battle;
    }

    public void setBattle(boolean battle) {
        this.battle = battle;
    }
}