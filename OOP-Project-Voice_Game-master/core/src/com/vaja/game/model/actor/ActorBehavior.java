package com.vaja.game.model.actor;

/***
 * @author khingbmc
 * this class for npc behavior
 */
public abstract class ActorBehavior {
    private Actor actor;

    public ActorBehavior(Actor actor){
        this.actor = actor;
    }
    /**
     * this method update state of the behavior
     * @param delta is time in second since last update
     */
    public abstract void update(float delta);

    public Actor getActor() {
        return actor;
    }
}
