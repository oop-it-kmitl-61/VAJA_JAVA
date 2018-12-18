package com.vaja.game.model;

public enum DIRECTION {
    NORTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    SOUTH(0, -1),
    ;

    private int dx, dy;

    private DIRECTION(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public static DIRECTION getOpposite(DIRECTION direction){
        switch(direction){
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        return null;
    }
}
