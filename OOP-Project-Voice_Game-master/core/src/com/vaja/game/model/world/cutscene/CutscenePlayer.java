package com.vaja.game.model.world.cutscene;

import com.badlogic.gdx.graphics.Color;
import com.vaja.game.model.DIRECTION;
import com.vaja.game.model.world.World;

/**
 * this interface is cutscene change map
 * (smooth white color)
 */

public interface CutscenePlayer {

    public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color);

    /**
     * Load world form name
     * @param worldName
     * @return
     */
    public World getWorld(String worldName);

    public void queueEvent(CutsceneEvent event);
}
