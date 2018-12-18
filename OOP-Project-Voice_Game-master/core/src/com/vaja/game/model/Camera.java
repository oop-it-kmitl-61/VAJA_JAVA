package com.vaja.game.model;

public class Camera {
    private float cameraX=0f, cameraY=0f;

    public Camera(){

    }

    /**
     * this methos update what this view
     */

    public void update(float newCamX, float newCamY){
        this.cameraX = newCamX;
        this.cameraY = newCamY;
    }

    public float getCameraX() {
        return cameraX;
    }

    public float getCameraY() {
        return cameraY;
    }
}
