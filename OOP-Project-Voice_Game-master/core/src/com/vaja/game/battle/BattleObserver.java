package com.vaja.game.battle;

import com.vaja.game.battle.event.BattleEvent;

/**
 * obj is can implemented in this interface
 */

public interface BattleObserver {
        public void queueEvent(BattleEvent event);
}
