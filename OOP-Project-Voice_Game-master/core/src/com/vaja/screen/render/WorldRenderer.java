package com.vaja.screen.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vaja.game.Setting;
import com.vaja.game.Vaja;
import com.vaja.game.model.Camera;
import com.vaja.game.model.YSortable;
import com.vaja.game.model.actor.Actor;
import com.vaja.game.model.world.World;
import com.vaja.game.model.world.WorldObj;
import com.vaja.screen.BattleScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khingbmc
 * world render is class is render anything in the world (tiles
 *  terrain, object, player)
 */
public class WorldRenderer {
    private AssetManager assetManager;
    private World world;

    private TextureAtlas atlas;

    private List<Integer> renderedObjects = new ArrayList<Integer>();
    private List<YSortable> forRendering = new ArrayList<YSortable>();

    private Vaja app;

    public WorldRenderer(AssetManager assetManager, World world, Vaja app) {
        this.assetManager = assetManager;
        this.world = world;
        this.app = app;

        atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
    }

    public void render(SpriteBatch batch, Camera camera) {
        float worldStartX = Gdx.graphics.getWidth()/2 - camera.getCameraX()* Setting.SCALE_TILE_S;
        float worldStartY = Gdx.graphics.getHeight()/2 - camera.getCameraY()* Setting.SCALE_TILE_S;


        /* render tile terrains */
        for (int x = 0; x < world.getMap().getWidth(); x++) {
            for (int y = 0; y < world.getMap().getHeight(); y++) {
                String imageName = world.getMap().getTile(x, y).getTerrain().getImageName();

                TextureRegion render = null;
                if (!imageName.isEmpty()) { // Terrain NONE has no image
                    render = atlas.findRegion(world.getMap().getTile(x, y).getTerrain().getImageName());
                }

                if (render != null) {
                    batch.draw(render,
                            (int)(worldStartX+x* Setting.SCALE_TILE_S),
                            (int)(worldStartY+y* Setting.SCALE_TILE_S),
                            (int)( Setting.SCALE_TILE_S),
                            (int)( Setting.SCALE_TILE_S));
                }
            }
        }

        /* collect objects and actors */
        for (int x = 0; x < world.getMap().getWidth(); x++) {
            for (int y = 0; y < world.getMap().getHeight(); y++) {
                if (world.getMap().getTile(x, y).getActor() != null) {
                    Actor actor = world.getMap().getTile(x, y).getActor();

                    forRendering.add(actor);
                    if(x == 7 && y == 14){

                    }
//                    if(actor.getX() == 31){
//
//                        app.setScreen(app.getBattleScreen());
//                        actor.setX(32);
//                        app.setScreen(app.getGameScreen());
//
//                    }


                }
                if (world.getMap().getTile(x, y).getObject() != null) {
                    WorldObj object = world.getMap().getTile(x, y).getObject();

                    if (renderedObjects.contains(object.hashCode())) { // test if it's already drawn
                        continue;
                    }
                    if (object.isWalkable()) {  		// if it's walkable, draw it right away
                        batch.draw(object.getSprite(),	// chances are it's probably something on the ground
                                worldStartX+object.getWorldX()* Setting.SCALE_TILE_S,
                                worldStartY+object.getWorldY()* Setting.SCALE_TILE_S,
                                 Setting.SCALE_TILE_S*object.getSizeX(),
                                 Setting.SCALE_TILE_S*object.getSizeY());
                        continue;
                    } else {	// add it to the list of YSortables
                        forRendering.add(object);
                        renderedObjects.add(object.hashCode());
                    }
                }
            }
        }

        Collections.sort(forRendering, new WorldObjectYComparator());
        Collections.reverse(forRendering);


        for (YSortable loc : forRendering) {

            if (loc instanceof Actor) {
                Actor a = (Actor)loc;
                if (!a.isVisible()) {
                    continue;
                }
            }
            batch.draw(loc.getSprite(),
                    worldStartX+loc.getWorldX()* Setting.SCALE_TILE_S,
                    worldStartY+loc.getWorldY()* Setting.SCALE_TILE_S,
                     Setting.SCALE_TILE_S*loc.getSizeX(),
                     Setting.SCALE_TILE_S*loc.getSizeY());
        }

        renderedObjects.clear();
        forRendering.clear();
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
