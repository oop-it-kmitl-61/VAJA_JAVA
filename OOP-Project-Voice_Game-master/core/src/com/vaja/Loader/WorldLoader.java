package com.vaja.Loader;

import com.badlogic.gdx.assets.AssetDescriptor;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import com.badlogic.gdx.utils.Array;
import com.vaja.game.dialogue.Dialogue;
import com.vaja.game.dialogue.LinearDialogueNode;
import com.vaja.game.model.DIRECTION;
import com.vaja.game.model.TeleportTile;
import com.vaja.game.model.Tile;
import com.vaja.game.model.actor.Actor;
import com.vaja.game.model.actor.LimitedWalkingBehavior;
import com.vaja.game.model.world.Door;
import com.vaja.game.model.world.World;
import com.vaja.game.model.world.WorldObj;
import com.vaja.util.AnimationSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

/**
 * it is resource manage class to loadworld into world
 */

public class WorldLoader extends AsynchronousAssetLoader<World, WorldLoader.WorldParameter> {
    private World world;

    private Animation flowerAnimation;
    private Animation doorOpen;
    private Animation doorClose;
    private AnimationSet dragonAnimation, centapideAnimation, wormAnimation, slimeAnimation, birdAnimation;
   


    public WorldLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager asset, String filename, FileHandle file, WorldParameter parameter) {
        TextureAtlas atlas = asset.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureAtlas monAtlas = asset.get("res/graphics_packed/monster/monster_textures.atlas", TextureAtlas.class);

        flowerAnimation = new Animation(0.8f, atlas.findRegions("flowers"), Animation.PlayMode.LOOP_PINGPONG);
        doorOpen = new Animation(0.8f/4f, atlas.findRegions("woodenDoor"), Animation.PlayMode.NORMAL);
        doorClose = new Animation(0.5f/4f, atlas.findRegions("woodenDoor"), Animation.PlayMode.REVERSED);
        dragonAnimation = new AnimationSet(
                new Animation(0.3f/2f, monAtlas.findRegions("dragon_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("dragon_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("dragon_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("dragon_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                monAtlas.findRegion("dragon_stand_north"),
                monAtlas.findRegion("dragon_stand_south"),
                monAtlas.findRegion("dragon_stand_east"),
                monAtlas.findRegion("dragon_stand_west")
        );
        
        centapideAnimation = new AnimationSet(
        		new Animation(0.3f/2f, monAtlas.findRegions("worm_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("worm_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("worm_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("worm_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                monAtlas.findRegion("worm_stand_north"),
                monAtlas.findRegion("worm_stand_south"),
                monAtlas.findRegion("worm_stand_east"),
                monAtlas.findRegion("worm_stand_west")
        		);
        
        wormAnimation = new AnimationSet(
        		new Animation(0.3f/2f, monAtlas.findRegions("centapide_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("centapide_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("centapide_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("centapide_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                monAtlas.findRegion("centapide_stand_north"),
                monAtlas.findRegion("centapide_stand_south"),
                monAtlas.findRegion("centapide_stand_east"),
                monAtlas.findRegion("centapide_stand_west")
        		);
        
        birdAnimation = new AnimationSet(
        		new Animation(0.3f/2f, monAtlas.findRegions("bird_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("bird_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("bird_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, monAtlas.findRegions("bird_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                monAtlas.findRegion("bird_stand_north"),
                monAtlas.findRegion("bird_stand_south"),
                monAtlas.findRegion("bird_stand_east"),
                monAtlas.findRegion("bird_stand_west")
        		);
        		
        		

        BufferedReader reader = new BufferedReader(file.reader());
        int currentLine = 0;
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                currentLine++;

                // header of file
                if (currentLine == 1) {
                    String[] tokens = line.split("\\s+");

                    //World(String name, int width, int height, int safeX, int safeY)
                    world = new World(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                    continue;
                }

                if (line.isEmpty() || line.startsWith("//")) {
                    //it a comment
                    continue;
                }

                // functions
                String[] tokens = line.split("\\s+");//space
                switch (tokens[0]) {
                    case "fillTerrain":
                        fillTerrain(asset, tokens[1]);
                        break;
                    case "setTerrain":
                        setTerrain(asset, tokens[1], tokens[2], tokens[3]);
                        break;
                    case "addFlowers":
                        addFlowers(tokens[1], tokens[2]);
                        break;
                    case "addRug":
                        addRug(asset, tokens[1], tokens[2]);
                        break;
                    case "addObj":
                        addGameWorldObject(asset, tokens[1], tokens[2], tokens[3]);
                        break;
                    case "addTree":
                        addGameWorldObject(asset, tokens[1], tokens[2], "BIG_TREE");
                        break;
                    case "addDoor":
                        addDoor(tokens[1], tokens[2]);
                        break;
                    case "addDragon":
                        addDragon(tokens[1], tokens[2]);

                        break;
                    case "addCentapide":
                    	addCentapide(tokens[1], tokens[2]);
                    	break;
                    	
                    case "addBird":
                    	addBird(tokens[1], tokens[2]);
                    	break;
                    case "addWorm":
                    	addWorm(tokens[1], tokens[2]);
                    	break;
                    case "teleport":
                        teleport(asset, tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);
                        break;


                    case "unwalkable":
                        unwalkable(tokens[1], tokens[2]);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTerrain(AssetManager asset, String terrain) {
        LoadTerrainDB terrainDb = asset.get("res/LTerrain.xml", LoadTerrainDB.class);
        LoadTerrain t = terrainDb.getTerrain(terrain);

        for (int x = 0; x < world.getMap().getWidth(); x++) {
            for (int y = 0; y < world.getMap().getHeight(); y++) {
                world.getMap().setTile(new Tile(t), x, y);
            }
        }
    }

    private void setTerrain(AssetManager asset, String x, String y, String terrain) {
        LoadTerrainDB terrainDb = asset.get("res/LTerrain.xml", LoadTerrainDB.class);
        LoadTerrain t = terrainDb.getTerrain(terrain);

        int ix = Integer.parseInt(x);
        int iy = Integer.parseInt(y);
        world.getMap().getTile(ix, iy).setTerrain(t);
    }

    private void addFlowers(String sx, String sy) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);

        GridPoint2[] gridArray = new GridPoint2[1];
        gridArray[0] = new GridPoint2(0,0);
        WorldObj flowers = new WorldObj(x, y, true, flowerAnimation, 1f, 1f, gridArray);
        world.addObject(flowers);
    }

    private void addDragon(String stringX, String stringY){
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        Actor mon = new Actor(world, x, y, dragonAnimation);
        mon.setLevel(20);
        mon.setName("Dark Dragon");

        LinearDialogueNode node1 = new LinearDialogueNode("Don't translate because it a Dragon Language", 0);

        Dialogue dialogue = new Dialogue();
        dialogue.addNode(node1);


        mon.setDialogue(dialogue);
        mon.setSizeX(2);
        mon.setSizeY(2.5f);

        LimitedWalkingBehavior brain = new LimitedWalkingBehavior(mon, 1, 1, 0, 0, 0.3f, 1f, new Random());
        world.addActor(mon, brain);


    }
    
    private void addBird(String stringX, String stringY) {
    	int x = Integer.parseInt(stringX);
    	int y = Integer.parseInt(stringY);
    	
    	Actor mon = new Actor(world, x, y, birdAnimation);
    	mon.setLevel(5);
    	mon.setName("Bird");
    	LinearDialogueNode node1 = new LinearDialogueNode("Gwak Gwak!!!", 0);

    	 Dialogue dialogue = new Dialogue();
         dialogue.addNode(node1);


         mon.setDialogue(dialogue);
         mon.setSizeX(1);
         mon.setSizeY(1.5f);

         LimitedWalkingBehavior brain = new LimitedWalkingBehavior(mon, 1, 1, 0, 0, 0.3f, 1f, new Random());
         world.addActor(mon, brain);
    }
    
    private void addCentapide(String stringX, String stringY) {
    	int x = Integer.parseInt(stringX);
    	int y = Integer.parseInt(stringY);
    	
    	Actor mon = new Actor(world, x, y, centapideAnimation);
    	mon.setLevel(10);
    	mon.setName("Centapide");
    	LinearDialogueNode node1 = new LinearDialogueNode("Who are you!Pzzz Pzzz", 0);

    	 Dialogue dialogue = new Dialogue();
         dialogue.addNode(node1);


         mon.setDialogue(dialogue);
         mon.setSizeX(2);
         mon.setSizeY(2.5f);

         LimitedWalkingBehavior brain = new LimitedWalkingBehavior(mon, 1, 1, 0, 0, 0.3f, 1f, new Random());
         world.addActor(mon, brain);
    	
    	
    	
    }
    
    private void addWorm(String stringX, String stringY) {
    	int x = Integer.parseInt(stringX);
    	int y = Integer.parseInt(stringY);
    	Actor mon = new Actor(world, x, y, wormAnimation);
    	mon.setLevel(15);
    	mon.setName("Worm");
    	 LinearDialogueNode node1 = new LinearDialogueNode("I believe I can Farmmmm", 0);

    	 Dialogue dialogue = new Dialogue();
         dialogue.addNode(node1);


         mon.setDialogue(dialogue);
         mon.setSizeX(2);
         mon.setSizeY(2.5f);

         LimitedWalkingBehavior brain = new LimitedWalkingBehavior(mon, 1, 1, 0, 0, 0.3f, 1f, new Random());
         world.addActor(mon, brain);
    	
    	
    }


    private void addRug(AssetManager assetManager, String sx, String sy) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);

        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion rugRegion = atlas.findRegion("rug");
        GridPoint2[] gridArray = new GridPoint2[3*2];
        gridArray[0] = new GridPoint2(0,0);
        gridArray[1] = new GridPoint2(0,1);
        gridArray[2] = new GridPoint2(0,2);
        gridArray[3] = new GridPoint2(1,0);
        gridArray[4] = new GridPoint2(1,1);
        gridArray[5] = new GridPoint2(1,2);
        WorldObj rug = new WorldObj(x, y, true, rugRegion, 3f, 2f, gridArray);
        world.addObject(rug);
    }

    private void addGameWorldObject(AssetManager assetManager, String sx, String sy, String stype) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);

        LoadWorldObjDB objDb = assetManager.get("res/LWorldObjects.xml", LoadWorldObjDB.class);
        LoadWorldObj obj = objDb.getLWorldObject(stype);

        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion objRegion = atlas.findRegion(obj.getImgName());

        WorldObj worldObj = new WorldObj(x, y, false, objRegion, obj.getSizeX(), obj.getSizeY(), obj.getTiles());
        world.addObject(worldObj);
    }

    private void teleport(AssetManager asman, String sx, String sy, String sterrain, String stargetWorld, String stargetX, String stargetY, String stargetDir, String stransitionColor) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);

        int targetX = Integer.parseInt(stargetX);
        int targetY = Integer.parseInt(stargetY);

        LoadTerrainDB terrainDb = asman.get("res/LTerrain.xml", LoadTerrainDB.class);
        LoadTerrain t = terrainDb.getTerrain(sterrain);

        DIRECTION targetDir = DIRECTION.valueOf(stargetDir);

        Color transitionColor;
        switch (stransitionColor) {
            case "WHITE":
                transitionColor = Color.WHITE;
                break;
            case "BLACK":
                transitionColor = Color.BLACK;
                break;
            default:
                transitionColor = Color.BLACK;
                break;
        }

        TeleportTile tile = new TeleportTile(t, stargetWorld, targetX, targetY, targetDir, transitionColor);
        world.getMap().setTile(tile, x, y);
    }

    private void unwalkable(String sx, String sy) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);
        world.getMap().getTile(x, y).setWalkable(false);
    }

    private void addDoor(String sx, String sy) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);
        Door door = new Door(x, y, doorOpen, doorClose);
        world.addObject(door);
    }

    @Override
    public World loadSync(AssetManager arg0, String arg1, FileHandle arg2, WorldParameter arg3) {
        return world;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Array<AssetDescriptor> getDependencies(String filename, FileHandle file, WorldParameter parameter) {
        Array<AssetDescriptor> ad = new Array<AssetDescriptor>();
        ad.add(new AssetDescriptor("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class));
        ad.add(new AssetDescriptor("res/LWorldObjects.xml", LoadWorldObjDB.class));
        ad.add(new AssetDescriptor("res/LTerrain.xml", LoadTerrainDB.class));
        return ad;
    }

    static public class WorldParameter extends AssetLoaderParameters<World> {
    }


}
