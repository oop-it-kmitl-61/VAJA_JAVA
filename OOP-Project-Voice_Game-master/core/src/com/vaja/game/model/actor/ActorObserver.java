package com.vaja.game.model.actor;

import com.vaja.game.model.DIRECTION;

/***
 * @author khingbmc
 * this interface is call if player move
 */
public interface ActorObserver {
    /**
     * Called from an Actor is finished moving.
     *
     * @param a				Actor in question
     * @param direction		The direction of the move
     * @param x				Location after the move
     * @param y
     */
    public void actorMoved(Actor a, DIRECTION direction, int x, int y);

    /**
     * Called from an Actor when move unsuccessful
     * @param a				Actor in question
     * @param direction		The direction of the move
     */
    public void attemptedMove(Actor a, DIRECTION direction);

    public void actorBeforeMoved(Actor a, DIRECTION direction);
}
