package com.vaja.game.model.world.cutscene;


import com.vaja.game.model.world.Door;

/**
 * @author khingbmc
 * this class is event door animations
 * to be change world
 */

public class DoorEvent extends CutsceneEvent {
    private boolean opening;
    private Door door;

    private boolean finished = false;

    /**
     *
     * @param door is object door
     * @param opening is check if door open
     */

    public DoorEvent(Door door, boolean opening) {
        this.door = door;
        this.opening = opening;

    }

    /**
     * is cutscene player change map
     * @param player
     */

    @Override
    public void begin(CutscenePlayer player) {
        super.begin(player);
        if (door.getState() == com.vaja.game.model.world.Door.STATE.OPEN && opening == false) {
            door.close();
        } else if (door.getState() == com.vaja.game.model.world.Door.STATE.CLOSED && opening == true) {
            door.open();
        }
    }

    @Override
    public void update(float delta) {
        if (opening == true && door.getState() == com.vaja.game.model.world.Door.STATE.OPEN) {
            finished = true;
        } else if (opening == false && door.getState() == com.vaja.game.model.world.Door.STATE.CLOSED) {
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void screenShow() {}
}
