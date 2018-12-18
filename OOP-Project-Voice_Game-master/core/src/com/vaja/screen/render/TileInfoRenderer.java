package com.vaja.screen.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.vaja.game.Setting;
import com.vaja.game.model.Camera;
import com.vaja.game.model.world.World;

public class TileInfoRenderer {
    private BitmapFont font = new BitmapFont();

    private World world;
    private Camera cam;

    public TileInfoRenderer(World world, Camera cam) {
        this.world = world;
        this.cam = cam;
    }

    public void render(SpriteBatch batch, float mouseX, float mouseY) {
        float worldStartX = Gdx.graphics.getWidth()/2 - cam.getCameraX()* Setting.SCALE_TILE_S;
        float worldStartY = Gdx.graphics.getHeight()/2 - cam.getCameraY()*Setting.SCALE_TILE_S;

        float worldFloatX = mouseX-worldStartX;
        float worldFloatY = (Gdx.graphics.getHeight()-mouseY)-worldStartY;

        int tileX = (int) (worldFloatX/ Setting.SCALE_TILE_S);
        int tileY = (int) (worldFloatY/Setting.SCALE_TILE_S);

        font.draw(batch, "X="+tileX+", Y="+tileY, 10f, Gdx.graphics.getHeight()-10f);


    }
}
