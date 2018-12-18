package com.vaja.game.battle.event;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Texture;
import com.vaja.game.battle.BATTLE_PARTY;
import com.vaja.game.battle.animation.BattleAnimation;
import com.vaja.game.ui.DialogueBox;
import com.vaja.game.ui.StatusBox;

public interface BattleEventPlayer {
    public void playBattleAnimation(BattleAnimation animation, BATTLE_PARTY party);

    public void setPokemonSprite(Texture region, BATTLE_PARTY party);

    public DialogueBox getDialogueBox();

    public StatusBox getStatusBox(BATTLE_PARTY party);

    public BattleAnimation getBattleAnimation();

    public TweenManager getTweenManager();

    public void queueEvent(BattleEvent event);
}
