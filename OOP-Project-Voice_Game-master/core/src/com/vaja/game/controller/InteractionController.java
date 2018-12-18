package com.vaja.game.controller;

/**
 * control what interact in front of player
 */

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.vaja.game.dialogue.Dialogue;
import com.vaja.game.model.DIRECTION;
import com.vaja.game.model.Tile;
import com.vaja.game.model.actor.Actor;

public class InteractionController extends InputAdapter {

    private Actor a;
    private DialogueController dialogueController;

    public InteractionController(Actor a, DialogueController dialogueController) {
        this.a = a;
        this.dialogueController = dialogueController;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {

            Tile target = a.getWorld().getMap().getTile(a.getX()+a.getFacing().getDx(), a.getY()+a.getFacing().getDy());
            if (target.getActor() != null) {
                Actor targetActor = target.getActor();
                if (targetActor.getDialogue() != null) {

                    if (targetActor.refaceWithoutAnimation(DIRECTION.getOpposite(a.getFacing()))){
                        Dialogue dialogue = targetActor.getDialogue();
                        dialogueController.startDialogue(dialogue);


                    }
                }
            }

            return false;
        }
        return false;
    }
}

