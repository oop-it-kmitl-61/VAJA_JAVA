package com.vaja.game.model.world.cutscene;

import com.vaja.game.model.actor.Actor;

public class ActorVisibilityEvent extends CutsceneEvent {

    private Actor actor;
    private boolean invisible;

    public ActorVisibilityEvent(Actor a, boolean invisible) {
        this.actor = a;
        this.invisible = invisible;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void screenShow() {

    }



    @Override
    public void begin(CutscenePlayer player) {
        super.begin(player);
        if (invisible) {
            actor.setVisible(false);
        } else {
            actor.setVisible(true);
        }
    }


}
