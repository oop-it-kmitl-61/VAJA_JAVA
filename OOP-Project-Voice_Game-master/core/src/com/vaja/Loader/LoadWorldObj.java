package com.vaja.Loader;


import com.badlogic.gdx.math.GridPoint2;

/**
 * this class is get object form map.txt
 */
public class LoadWorldObj {
    private String imgName;
    private float sizeX, sizeY;
    private GridPoint2[] tiles;

    public LoadWorldObj(String imgName, float sizeX, float sizeY, GridPoint2... tiles){
        this.imgName = imgName;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = tiles;
    }

    public String getImgName() {
        return imgName;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public GridPoint2[] getTiles() {
        return tiles;
    }
}
