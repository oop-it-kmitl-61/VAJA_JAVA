package com.vaja.game.model;

/**
 * this class is contain position point in map
 * it look like grid
 * @author khingbmc
 */

public class TileMap {

    private Tile[][] tile;

    private int width, height;



    public TileMap(int width, int height){
        this.width = width;
        this.height = height;

        tile = new Tile[this.width][this.height];

//                if(y >= 22 && y <=32){
//                    if(x == 49 && y == 22) this.tile[x][y] = new Tile(TERRAIN.DIRTDOWN1);
//                    else if(x == 50 && y == 22) this.tile[x][y] = new Tile(TERRAIN.DIRTDOWN2);
//                    else if(x== 51 && y == 22) this.tile[x][y] = new Tile(TERRAIN.DIRTDOWN3);
//                    else if(x == 49 &&(y>=23 && y <= 31)) this.tile[x][y] = new Tile(TERRAIN.DIRTMID1);
//                    else if(x == 50 &&(y>=23 && y <= 31)) this.tile[x][y] = new Tile(TERRAIN.DIRTMID2);
//                    else if(x == 51 &&(y>=23 && y <= 31)) this.tile[x][y] = new Tile(TERRAIN.DIRTMID3);
//                    else if(x == 49 && y == 32) this.tile[x][y] = new Tile(TERRAIN.DIRTUP1);
//                    else if(x == 50 && y == 32) this.tile[x][y] = new Tile(TERRAIN.DIRTUP2);
//                    else if(x == 51 && y == 32) this.tile[x][y] = new Tile(TERRAIN.DIRTUP3);
//                }
    }

    public Tile getTile(int x, int y) {
        return tile[x][y];
    }

    public void setTile(Tile tile, int x, int y) {
        this.tile[x][y] = tile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
