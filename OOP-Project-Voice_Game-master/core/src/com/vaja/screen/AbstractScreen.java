package com.vaja.screen;

import com.badlogic.gdx.Screen;
import com.vaja.game.Vaja;

public abstract class AbstractScreen implements Screen {

    private Vaja app;

    public AbstractScreen(Vaja app){
        this.app = app;
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float v);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    public abstract void update(float delta);

    @Override
    public abstract void hide();

    @Override
    public abstract void dispose();

    public Vaja getApp(){
        return app;
    }
}
