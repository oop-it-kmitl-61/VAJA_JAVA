package com.vaja.game.battle.event;

/**
 * this interface is queqe of battle event eg.
 * player attack after monster attack
 * it is the observer the event in the battle
 */
public interface BattleEventQueuer {
    public void queueEvent(BattleEvent event);
}
