package com.vaja.game.model.actor;

import com.vaja.game.model.world.World;
import com.vaja.game.model.world.cutscene.CutscenePlayer;
import com.vaja.util.AnimationSet;

public class PlayerActor extends Actor {
    private CutscenePlayer cutscenePlayer;

    public PlayerActor(World world, int x, int y, AnimationSet animations, CutscenePlayer cutscenePlayer) {
        super(world, x, y, animations);
        this.cutscenePlayer = cutscenePlayer;
    }

    public CutscenePlayer getCutscenePlayer() {
        return cutscenePlayer;
    }

    @Override
    public int getLevel() {
        return super.getLevel();
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
    }
    
}
