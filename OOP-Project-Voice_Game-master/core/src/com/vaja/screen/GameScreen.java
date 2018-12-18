package com.vaja.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import com.vaja.game.Vaja;
import com.vaja.game.controller.ActorMovementControl;
import com.vaja.game.controller.DialogueController;
import com.vaja.game.controller.InteractionController;
import com.vaja.game.dialogue.Dialogue;
import com.vaja.game.model.*;
import com.vaja.game.model.actor.Actor;
import com.vaja.game.model.actor.PlayerActor;
import com.vaja.game.model.world.World;
import com.vaja.game.model.world.WorldObj;
import com.vaja.game.model.world.cutscene.CutsceneEvent;
import com.vaja.game.model.world.cutscene.CutscenePlayer;
import com.vaja.game.ui.DialogueBox;
import com.vaja.game.ui.OptionBox;
import com.vaja.screen.render.EventQueueRenderer;
import com.vaja.screen.render.TileInfoRenderer;
import com.vaja.screen.render.WorldRenderer;
import com.vaja.screen.transition.Action;
import com.vaja.screen.transition.FadeInTransition;
import com.vaja.screen.transition.FadeOutTransition;
import com.vaja.util.AnimationSet;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class GameScreen extends AbstractScreen implements CutscenePlayer {

    private InputMultiplexer multiplexer;
    private DialogueController dialogueController;
    private ActorMovementControl playerController;
    private InteractionController interactionController;

    private Vaja app;

    private HashMap<String, World> worlds = new HashMap<String, World>();
    private World world;
    
    private String state = "first";
    private int number;
    
    private PlayerActor player;
    private Camera camera;
    private Dialogue dialogue;

    private Actor monster;

    
    private BattleScreen battleScreen;

    /* cutscenes */
    private Queue<CutsceneEvent> eventQueue = new ArrayDeque<CutsceneEvent>();
    private CutsceneEvent currentEvent;

    private SpriteBatch batch;

    private Viewport gameViewport;

    private WorldRenderer worldRenderer;
    private EventQueueRenderer queueRenderer; // renders cutscenequeue
    private TileInfoRenderer tileInfoRenderer;
    private boolean renderTileInfo = false;

    private int uiScale = 2;

    private Stage uiStage;
    private Table root;
    private DialogueBox dialogueBox;
    private OptionBox optionsBox;

    public GameScreen(Vaja app) {
        super(app);
        gameViewport = new ScreenViewport();
        batch = new SpriteBatch();

        TextureAtlas atlas = app.getAssetManager().get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);

        AnimationSet animations = new AnimationSet(
                new Animation(0.3f/2f, atlas.findRegions("brendan_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, atlas.findRegions("brendan_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, atlas.findRegions("brendan_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, atlas.findRegions("brendan_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("brendan_stand_north"),
                atlas.findRegion("brendan_stand_south"),
                atlas.findRegion("brendan_stand_east"),
                atlas.findRegion("brendan_stand_west") 
        );

        Array<World> loadWorlds = app.getAssetManager().getAll(World.class, new Array<World>());
        for (World w : loadWorlds) {
            worlds.put(w.getName(), w);
        }
        if (state.equals("save1")) {
            world = worlds.get("save1");

            camera = new Camera();
            player = new PlayerActor(world, world.getSafeX(), world.getSafeY(), animations, this);
            player.setSizeX(1);
           
            player.setSizeY(1.5f);
            player.setLevel(10);
            world.addActor(player);

            initUI();

            multiplexer = new InputMultiplexer();

            playerController = new ActorMovementControl(player);
            dialogueController = new DialogueController(dialogueBox, optionsBox);
            interactionController = new InteractionController(player, dialogueController);
            multiplexer.addProcessor(0, dialogueController);
            multiplexer.addProcessor(1, playerController);
            multiplexer.addProcessor(2, interactionController);

            worldRenderer = new WorldRenderer(getApp().getAssetManager(), world, getApp());
            queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);
            tileInfoRenderer = new TileInfoRenderer(world, camera);
        }

        if (state.equals("save2")) {
            world = worlds.get("save2");

            camera = new Camera();
            player = new PlayerActor(world, world.getSafeX(), world.getSafeY(), animations, this);
            player.setSizeX(1);
            player.setSizeY(1.5f);
            player.setLevel(15);
            world.addActor(player);

            initUI();

            multiplexer = new InputMultiplexer();

            playerController = new ActorMovementControl(player);
            dialogueController = new DialogueController(dialogueBox, optionsBox);
            interactionController = new InteractionController(player, dialogueController);
            multiplexer.addProcessor(0, dialogueController);
            multiplexer.addProcessor(1, playerController);
            multiplexer.addProcessor(2, interactionController);

            worldRenderer = new WorldRenderer(getApp().getAssetManager(), world, getApp());
            queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);
            tileInfoRenderer = new TileInfoRenderer(world, camera);
        }

        if (state.equals("save3")) {
            world = worlds.get("save3");

            camera = new Camera();
            player = new PlayerActor(world, world.getSafeX(), world.getSafeY(), animations, this);
            player.setSizeX(1);
            player.setSizeY(1.5f);
            player.setLevel(15);
            world.addActor(player);

            initUI();

            multiplexer = new InputMultiplexer();

            playerController = new ActorMovementControl(player);
            dialogueController = new DialogueController(dialogueBox, optionsBox);
            interactionController = new InteractionController(player, dialogueController);
            multiplexer.addProcessor(0, dialogueController);
            multiplexer.addProcessor(1, playerController);
            multiplexer.addProcessor(2, interactionController);

            worldRenderer = new WorldRenderer(getApp().getAssetManager(), world, getApp());
            queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);
            tileInfoRenderer = new TileInfoRenderer(world, camera);
        }
        if (state.equals("first")) {
            world = worlds.get("first_town");

            camera = new Camera();
            player = new PlayerActor(world, world.getSafeX(), world.getSafeY(), animations, this);
            player.setSizeX(1);
            player.setSizeY(1.5f);
            player.setLevel(15);
            world.addActor(player);

            initUI();

            multiplexer = new InputMultiplexer();

            playerController = new ActorMovementControl(player);
            dialogueController = new DialogueController(dialogueBox, optionsBox);
            interactionController = new InteractionController(player, dialogueController);
            multiplexer.addProcessor(0, dialogueController);
            multiplexer.addProcessor(1, playerController);
            multiplexer.addProcessor(2, interactionController);

            worldRenderer = new WorldRenderer(getApp().getAssetManager(), world, getApp());
            queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);
            tileInfoRenderer = new TileInfoRenderer(world, camera); 
        }
    }

    
    public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
        System.out.println(this.state);

	}


	@Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
            renderTileInfo = !renderTileInfo;
        }

        while (currentEvent == null || currentEvent.isFinished()) { // no active event
            if (eventQueue.peek() == null) { // no event queued up
                currentEvent = null;
                break;
            } else {					// event queued up
                currentEvent = eventQueue.poll();
                currentEvent.begin(this);
            }
        }

        if (currentEvent != null) {
            currentEvent.update(delta);
        }

        if (currentEvent == null) {
            playerController.update(delta);
        }

        dialogueController.update(delta);

        if (!dialogueBox.isVisible()) {

            camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
            world.update(delta);

            if(player.isBattle()){
                player.setBattle(false);
                System.out.println(monster.getName());

                battleScreen = new BattleScreen(getApp(), monster, player);

                getApp().setScreen(battleScreen);

            }
        }
        if(dialogueBox.isVisible()){
            if (dialogueBox.isFinished()){
           
                monster = this.world.getMap().getTile(player.getX()+this.player.getFacing().getDx(),
                        player.getY()+this.player.getFacing().getDy()).getActor();
                monster.setSizeX(2);
                monster.setSizeY(2.5f);
                monster.setName(monster.getName());
                player.setBattle(true);

            }
        }
        uiStage.act(delta);
    }

    @Override
    public void render(float delta) {

        gameViewport.apply();
        batch.begin();
        worldRenderer.render(batch, camera);
        queueRenderer.render(batch, currentEvent);
        if (renderTileInfo) {
            tileInfoRenderer.render(batch, Gdx.input.getX(), Gdx.input.getY());
        }
        batch.end();

        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        uiStage.getViewport().update(width/uiScale, height/uiScale, true);
        gameViewport.update(width, height);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
        if (currentEvent != null) {
            currentEvent.screenShow();
        }
    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update(Gdx.graphics.getWidth()/uiScale, Gdx.graphics.getHeight()/uiScale, true);
        //uiStage.setDebugAll(true);

        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);

        dialogueBox = new DialogueBox(getApp().getSkin());
        dialogueBox.setVisible(false);

        optionsBox = new OptionBox(getApp().getSkin());
        optionsBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(optionsBox)
                .expand()
                .align(Align.right)
                .space(8f)
                .row();
        dialogTable.add(dialogueBox)
                .expand()
                .align(Align.bottom)
                .space(8f)
                .row();


        root.add(dialogTable).expand().align(Align.bottom);
    }

    public void changeWorld(World newWorld, int x, int y, DIRECTION face) {
        player.changeWorld(newWorld, x, y);
        this.world = newWorld;
        player.refaceWithoutAnimation(face);
        this.worldRenderer.setWorld(newWorld);
        this.camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
    }



    @Override
    public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color) {
        getApp().startTransition(
                this,
                this,
                new FadeOutTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
                new FadeInTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
                new Action() {
                    @Override
                    public void action() {
                        changeWorld(newWorld, x, y, facing);
                    }
                });

    }

    @Override
    public World getWorld(String worldName) {
        return worlds.get(worldName);
    }

    @Override
    public void queueEvent(CutsceneEvent event) {
        eventQueue.add(event);
    }
}
