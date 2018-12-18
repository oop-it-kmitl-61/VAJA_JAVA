package com.vaja.game.model;

import com.badlogic.gdx.graphics.Color;
import com.vaja.Loader.LoadTerrain;
import com.vaja.game.model.actor.Actor;
import com.vaja.game.model.actor.PlayerActor;
import com.vaja.game.model.world.Door;
import com.vaja.game.model.world.World;
import com.vaja.game.model.world.WorldObj;
import com.vaja.game.model.world.cutscene.*;

public class TeleportTile extends Tile {
    /* destination */
    private String worldName;
    private int x, y;
    private DIRECTION facing;

    /* transition color */
    private Color color;

    public TeleportTile(LoadTerrain terrain, String worldName, int x, int y, DIRECTION facing, Color color) {
        super(terrain);
        this.worldName = worldName;
        this.x= x;
        this.y=y;
        this.facing=facing;
        this.color=color;
    }

    @Override
    public void actorStep(Actor a) {

    }

    @Override
    public boolean actorBeforeStep(Actor a) {
        //System.out.println("beforestep");
        if (a instanceof PlayerActor) {
            PlayerActor playerActor = (PlayerActor) a;
            CutscenePlayer cutscenes = playerActor.getCutscenePlayer();

            if (this.getObject() != null) { // the teleport tile has an object
                if (this.getObject() instanceof Door) { // entering af door
                    //System.out.println("GONNA STEP ON A DOOR");
                    Door door = (Door)this.getObject();
                    cutscenes.queueEvent(new DoorEvent(door, true));
                    cutscenes.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
                    cutscenes.queueEvent(new ActorVisibilityEvent(a, true));
                    cutscenes.queueEvent(new DoorEvent(door, false));
                    cutscenes.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
                    cutscenes.queueEvent(new ActorVisibilityEvent(a, false));
                    cutscenes.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
                    return false;
                }
            } else { // // the teleport tile does not have an object
                System.out.println("Initiating teleport to "+worldName);

                World nextWorld = cutscenes.getWorld(worldName);

                if (nextWorld.getMap().getTile(x, y).getObject() != null) { // the target tile has an object
                    WorldObj targetObj = nextWorld.getMap().getTile(x, y).getObject();
                    //System.out.println("Teleporting onto a "+targetObj.getClass().getName());
                    if (targetObj instanceof Door) {
                        Door targetDoor = (Door) targetObj;
                        cutscenes.queueEvent(new ActorWalkEvent(a, DIRECTION.SOUTH));
                        cutscenes.queueEvent(new ActorVisibilityEvent(a, true));
                        cutscenes.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
                        cutscenes.queueEvent(new DoorEvent(targetDoor, true));
                        cutscenes.queueEvent(new WaitEvent(0.2f));
                        cutscenes.queueEvent(new ActorVisibilityEvent(a, false));
                        cutscenes.queueEvent(new WaitEvent(0.2f));
                        cutscenes.queueEvent(new ActorWalkEvent(a, DIRECTION.SOUTH)); // we know we're exiting a door
                        cutscenes.queueEvent(new DoorEvent(targetDoor, false));
                        return false;
                    }
                } else { // the target tile does not have an object
                    cutscenes.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
                    cutscenes.queueEvent(new ActorVisibilityEvent(a, true));
                    cutscenes.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
                    cutscenes.queueEvent(new ActorVisibilityEvent(a, false));
                    return false;
                }
            }
        }
        return true;
    }
}
