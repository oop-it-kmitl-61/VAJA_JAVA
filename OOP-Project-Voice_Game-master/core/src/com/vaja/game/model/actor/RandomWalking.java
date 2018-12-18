package com.vaja.game.model.actor;

import com.vaja.game.model.DIRECTION;

import java.util.Random;

public class RandomWalking implements WalkingBehavior {
    private float moveIntervalMin, moveIntervalMax;

    private Random random;

    private float timer;
    private float currentWaitTime;

    private boolean shouldMove;
    private DIRECTION moveDiretion;

    public RandomWalking(float moveIntervalMin, float moveIntervalMax, Random random){
        this.moveIntervalMax = moveIntervalMax;
        this.moveIntervalMin = moveIntervalMin;
        this.random = random;
        this.timer = 0f;
        this.currentWaitTime = calculateWaitTime();
        this.moveDiretion = DIRECTION.NORTH;

    }

    private float calculateWaitTime(){
        return random.nextFloat() * (moveIntervalMax-moveIntervalMin)+moveIntervalMin;
    }


    @Override
    public void update(float delta) {
        this.timer += delta;
        if(timer >= currentWaitTime){
            int directionIndex = random.nextInt(DIRECTION.values().length);
            moveDiretion = DIRECTION.values()[directionIndex];
            shouldMove = true;

            currentWaitTime = calculateWaitTime();
            timer = 0f;
        }else{
            shouldMove = false;
        }

    }

    @Override
    public boolean shouldMove() {
        return shouldMove;
    }

    @Override
    public DIRECTION moveDirection() {
        return moveDiretion;
    }
}
