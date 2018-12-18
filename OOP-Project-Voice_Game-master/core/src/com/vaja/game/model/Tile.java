package com.vaja.game.model;

import com.vaja.Loader.LoadTerrain;
import com.vaja.game.model.actor.Actor;
import com.vaja.game.model.world.WorldObj;

public class Tile {

    private Actor actor;



    private WorldObj object;

    private boolean walkable = true;
    private LoadTerrain terrain;

    public Tile(LoadTerrain terrain) {
        this.terrain = terrain;
    }

    public void setTerrain(LoadTerrain terrain) {
        this.terrain = terrain;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public LoadTerrain getTerrain() {
        return terrain;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public WorldObj getObject() {
        return object;
    }

    public void setObject(WorldObj object) {
        this.object = object;
    }

    public boolean walkable() {
        return walkable;
    }



    public void actorStep(Actor a) {

    }

    public boolean actorBeforeStep(Actor a) {
        return true;
    }


}
