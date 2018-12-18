package com.vaja.game.model.world;


import com.badlogic.gdx.math.GridPoint2;

import com.vaja.game.model.DIRECTION;
import com.vaja.game.model.TileMap;
import com.vaja.game.model.actor.Actor;
import com.vaja.game.model.actor.ActorBehavior;
import com.vaja.game.model.actor.ActorObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author khingbmc
 * this class is contain data in the game world such as
 * player point, object house point, map size map
 */
public class World implements ActorObserver {

    /** Unique name used to refer to this world (it is a name map eg. in red home , town start
     *  boss home)*/
    private String name;
    private int safeX;
    private int safeY;

    private TileMap map;
    private List<Actor> actors;
    private HashMap<Actor, ActorBehavior> brains;
    private List<WorldObj> objects;

    /**
     * @param name		Name of the world for internal model
     * @param width		Size of world in tiles (100*100 tiles)
     * @param height
     * @param safeX		Coord player can stand on
     * @param safeY
     */
    public World(String name, int width, int height, int safeX, int safeY) {
        this.name = name;
        this.map = new TileMap(width, height);
        this.safeX = safeX;
        this.safeY = safeY;
        actors = new ArrayList<Actor>();
        brains = new HashMap<Actor, ActorBehavior>();
        objects = new ArrayList<WorldObj>();
    }

    public void addActor(Actor a) {
        map.getTile(a.getX(), a.getY()).setActor(a);
        actors.add(a);
    }

    public void addActor(Actor a, ActorBehavior b) {
        addActor(a);
        brains.put(a, b);
    }

    public void addObject(WorldObj o) {
        for (GridPoint2 p : o.getTiles()) {
            //System.out.println("\t Adding tile: "+p.x+", "+p.y);
            map.getTile(o.getX()+p.x, o.getY()+p.y).setObject(o);
        }
        objects.add(o);
    }

    public void removeActor(Actor actor) {
        map.getTile(actor.getX(), actor.getY()).setActor(null);
        actors.remove(actor);
        if (brains.containsKey(actor)) {
            brains.remove(actor);
        }
    }

    public void update(float delta) {
        for (Actor a : actors) {
            if (brains.containsKey(a)) {
                brains.get(a).update(delta);
            }
            a.update(delta);
        }
        for (WorldObj o : objects) {
            o.update(delta);
        }
    }


    public TileMap getMap() {
        return map;

    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<WorldObj> getWorldObjects() {
        return objects;
    }

    public String getName() {
        return name;
    }

    public int getSafeX() {
        return safeX;
    }

    public int getSafeY() {
        return safeY;
    }


    @Override
    public void actorMoved(Actor a, DIRECTION direction, int x, int y) {

    }

    @Override
    public void attemptedMove(Actor a, DIRECTION direction) {

    }

    @Override
    public void actorBeforeMoved(Actor a, DIRECTION direction) {

    }
}

