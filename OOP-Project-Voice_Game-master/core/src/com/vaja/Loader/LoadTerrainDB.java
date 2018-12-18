package com.vaja.Loader;

import java.util.HashMap;

public class LoadTerrainDB {
    private HashMap<String, LoadTerrain> terrain = new HashMap<String, LoadTerrain>();

    public void addTerrain(String name, LoadTerrain obj){
        terrain.put(name, obj);
    }


    public LoadTerrain getTerrain(String name ){
        if(!terrain.containsKey(name)){
            throw new NullPointerException("Loadterrain "+name+"is not found");
        }
        return terrain.get(name);
    }
}
