package com.vaja.game.model.world.cutscene;

import com.vaja.game.model.DIRECTION;
import com.vaja.game.model.actor.Actor;

public class ActorWalkEvent extends CutsceneEvent {
    private Actor actor;
    private DIRECTION direction;

    private int targetX, targetY;
    private boolean finished = false;

    public ActorWalkEvent(Actor actor, DIRECTION direction){
        this.actor = actor;
        this.direction = direction;
    }

    @Override
    public void begin(CutscenePlayer player) {
        super.begin(player);
        this.targetX = actor.getX()+direction.getDx();
        this.targetY = actor.getY()+direction.getDy();
    }

    @Override
    public void update(float delta) {
        if(actor.getX() != targetX || actor.getY() != targetY){
            if(actor.getState() == Actor.ACTOR_STATE.STANDING){
                actor.moveWithoutNotifications(direction);
            }
        }else{
            if(actor.getState() == Actor.ACTOR_STATE.STANDING){
                finished = true;
            }
        }

    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public void screenShow() {

    }
}
