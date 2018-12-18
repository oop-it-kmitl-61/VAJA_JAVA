package com.vaja.game.battle.move;

import com.vaja.game.battle.animation.ChargeAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoveDB {
    private List<Move> moves = new ArrayList<Move>();
    private HashMap<String, Integer> mappings = new HashMap<String, Integer>();

    public MoveDB() {
        initializeMoves();
    }

    private void initializeMoves() {
        addMove(new DamageMove(
                new MoveSpecification(
                        MOVE_TYPE.NORMAL,
                        MOVE_CATEGORY.PHYSICAL,
                        25,
                        1f,
                        25,
                        "Tackle",
                        "Charges the foe with a full-body tackle."),
                ChargeAnimation.class));
        addMove(new DamageMove(
                new MoveSpecification(
                        MOVE_TYPE.WATER,
                        MOVE_CATEGORY.SPECIAL,
                        30,
                        1f,
                        30,
                        "Fus Ro Dah!",
                        "Ahhhhhhhhhhhhh"),
                ChargeAnimation.class));
        addMove(new DamageMove(
                new MoveSpecification(
                        MOVE_TYPE.NORMAL,
                        MOVE_CATEGORY.PHYSICAL,
                        50,
                        1f,
                        50,
                        "Ha do Ken!",
                        "Ha do ken"),
                ChargeAnimation.class));
        addMove(new DamageMove(
                new MoveSpecification(
                        MOVE_TYPE.DRAGON,
                        MOVE_CATEGORY.PHYSICAL,
                        70,
                        1f,
                        70,
                        "Khame Khame Ha!",
                        "Boom!"),
                ChargeAnimation.class));
    }

    private void addMove(Move move) {
        moves.add(move);
        mappings.put(move.getName(), moves.size()-1);
    }

    /**
     * @param moveName	Name of the Move you want
     * @return			Clone of the move
     */
    public Move getMove(String moveName) {
        return moves.get(mappings.get(moveName)).clone();
    }

    /**
     * @param index		Index of wanted move
     * @return			Clone of move
     */
    public Move getMove(int index) {
        return moves.get(index).clone();
    }
}
