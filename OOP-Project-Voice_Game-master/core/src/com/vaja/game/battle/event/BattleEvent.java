package com.vaja.game.battle.event;

public abstract class BattleEvent {
    private BattleEventPlayer player;

    public void begin(BattleEventPlayer player) {
        this.player = player;
        //texture is come form for BattleEventPlayer
    }

    public abstract void update(float delta);

    public abstract boolean finished();

    protected BattleEventPlayer getPlayer() {
        return player;
    }
}
