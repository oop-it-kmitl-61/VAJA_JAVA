package com.vaja.game.model.actor;

import com.badlogic.gdx.math.GridPoint2;
import com.vaja.game.model.DIRECTION;

import java.util.Random;


/**
 * @author khingbmc
 * this class is limited step walk of npc
 */
public class LimitedWalkingBehavior extends ActorBehavior {

    private float moveIntervalMinimum;
    private float moveIntervalMaximum;
    private Random random;

    private float timer;
    private float currentWaitTime;

    private GridPoint2 moveDelta;
    private int limNorth, limSouth, limEast, limWest;

    public LimitedWalkingBehavior(Actor actor, int limNorth, int limSouth, int limEast, int limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random) {
        super(actor);
        this.limNorth = limNorth;
        this.limSouth = limSouth;
        this.limEast = limEast;
        this.limWest = limWest;
        this.moveIntervalMinimum = moveIntervalMinimum;
        this.moveIntervalMaximum = moveIntervalMaximum;
        this.random = random;
        this.timer = 0f;
        this.currentWaitTime = calculateWaitTime();
        this.moveDelta = new GridPoint2();
    }

    @Override
    public void update(float delta) {
        if (getActor().getState() != Actor.ACTOR_STATE.STANDING) {
            return;
        }
        timer += delta;
        if (timer >= currentWaitTime) {
            int directionIndex = random.nextInt(DIRECTION.values().length);
            DIRECTION moveDirection = DIRECTION.values()[directionIndex];
            if (this.moveDelta.x+moveDirection.getDx() > limEast || -(this.moveDelta.x+moveDirection.getDx()) > limWest || this.moveDelta.y+moveDirection.getDy() > limNorth || -(this.moveDelta.y+moveDirection.getDy()) > limSouth) {
                getActor().reface(moveDirection);
                currentWaitTime = calculateWaitTime();
                timer = 0f;
                return;
            }
            boolean moved = getActor().move(moveDirection);
            if (moved) {
                this.moveDelta.x += moveDirection.getDx();
                this.moveDelta.y += moveDirection.getDy();
            }

            currentWaitTime = calculateWaitTime();
            timer = 0f;
        }
    }

    private float calculateWaitTime() {
        return random.nextFloat() * (moveIntervalMaximum - moveIntervalMinimum) + moveIntervalMinimum;
    }
}
