package com.vaja.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainGame {
    public static void RunGame() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Vaja_Java";
        config.height = 800;
        config.width = 1024;
        config.vSyncEnabled = true;
        new LwjglApplication(new Vaja(), config);
    }
}
