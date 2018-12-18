package com.vaja.game.model.actor;

import com.vaja.game.model.DIRECTION;

public interface WalkingBehavior {


    // update state of the behavior
    public void update(float delta);

    public boolean shouldMove();

    public DIRECTION moveDirection();
}
