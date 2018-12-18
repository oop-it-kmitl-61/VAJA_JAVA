package com.vaja.game.battle;

import com.badlogic.gdx.math.MathUtils;
import com.vaja.game.Vaja;
import com.vaja.game.battle.move.MOVE_CATEGORY;
import com.vaja.game.battle.move.Move;
import com.vaja.game.model.Monster;

public class BattleMechanics {
    private String message = "";

    private boolean criticalHit(Move move, Monster user, Monster target) {
        float probability = 1f/16f;
        if (probability >= MathUtils.random(1.0f)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return True if the player goes first.
     */
    public boolean goesFirst(Monster player, Monster opponent) {
        if (player.getStat(STAT.SPEED) > opponent.getStat(STAT.SPEED)) {
            return true;
        } else if (opponent.getStat(STAT.SPEED) > player.getStat(STAT.SPEED)) {
            return false;
        } else {
            return MathUtils.randomBoolean();
        }
    }

    public boolean attemptHit(Move move, Monster user, Monster target) {
        float random = MathUtils.random(1.0f);
        if (move.getAccuracy() >= random) {
            return true;
        } else {
            return false;
        }
    }


    public int calculateDamage(Move move, Monster user, Monster target) {
        message = "";

        float attack = 0f;
        if (move.getCategory() == MOVE_CATEGORY.PHYSICAL) {
            attack = user.getStat(STAT.ATTACK);
        } else {
            attack = user.getStat(STAT.SPECIAL_ATTACK);
        }

        float defence = 0f;
        if (move.getCategory() == MOVE_CATEGORY.PHYSICAL) {
            defence = target.getStat(STAT.DEFENCE);
        } else {
            defence = target.getStat(STAT.SPECIAL_DEFENCE);
        }

        boolean isCritical = criticalHit(move, user, target);

        int level = user.getLevel();
        float base = move.getPower();
        float modifier = MathUtils.random(0.85f, 1.00f);
        

        int damage = (int) ((  (2f*level+10f)/250f   *   (float)attack/defence   * base + 2   ) * modifier);

        return damage;
    }

    public boolean hasMessage() {
        return !message.isEmpty();
    }

    public String getMessage() {
        return message;
    }
}
