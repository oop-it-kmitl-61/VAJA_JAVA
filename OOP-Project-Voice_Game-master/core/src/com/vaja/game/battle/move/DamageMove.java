package com.vaja.game.battle.move;

import com.badlogic.gdx.Gdx;
import com.vaja.game.battle.BATTLE_PARTY;
import com.vaja.game.battle.BattleMechanics;
import com.vaja.game.battle.STAT;
import com.vaja.game.battle.animation.BattleAnimation;
import com.vaja.game.battle.animation.BlinkingAnimation;
import com.vaja.game.battle.event.AnimationBattleEvent;
import com.vaja.game.battle.event.BattleEventQueuer;
import com.vaja.game.battle.event.HPAnimationEvent;
import com.vaja.game.battle.event.TextEvent;
import com.vaja.game.model.Monster;

import java.lang.reflect.InvocationTargetException;

public class DamageMove extends Move {
    public DamageMove(MoveSpecification spec, Class<? extends BattleAnimation> clazz) {
        super(spec, clazz);
    }

    @Override
    public BattleAnimation animation() {
        try {
            return animationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.err.println(animationClass.getName()+" does not seem to have a constructor");
            e.printStackTrace();
            Gdx.app.exit();
        }
        return null;
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public boolean isDamaging() {
        return true;
    }

    @Override
    public int useMove(BattleMechanics mechanics, Monster user, Monster target, BATTLE_PARTY party, BattleEventQueuer broadcaster) {
        int hpBefore = target.getCurrentHitpoints();
        int damage = super.useMove(mechanics, user, target, party, broadcaster);

        /* Broadcast animations */
        broadcaster.queueEvent(new AnimationBattleEvent(party, animation()));

        /* Broadcast blinking */
        broadcaster.queueEvent(new AnimationBattleEvent(BATTLE_PARTY.getOpposite(party), new BlinkingAnimation(1f, 5)));

        //float hpPercentage = ((float)target.getCurrentHitpoints())/(float)target.getStat(STAT.HITPOINTS);

        /* Broadcast HP change */
        broadcaster.queueEvent(
                new HPAnimationEvent(
                        BATTLE_PARTY.getOpposite(party),
                        hpBefore,
                        target.getCurrentHitpoints(),
                        target.getStat(STAT.HITPOINTS),
                        0.5f));

        if (mechanics.hasMessage()) {
            broadcaster.queueEvent(new TextEvent(mechanics.getMessage(), 0.5f));
        }
        return damage;
    }

    @Override
    public Move clone() {
        return new DamageMove(spec, animationClass);
    }
}
