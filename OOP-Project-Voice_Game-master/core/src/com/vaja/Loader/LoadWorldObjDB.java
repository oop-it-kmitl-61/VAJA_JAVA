package com.vaja.Loader;

import java.util.HashMap;

/**
 * @author khingbmc
 * this class is database of every object in a world
 * it has referense to import object easier
 */

public class LoadWorldObjDB {

    private HashMap<String, LoadWorldObj> knownObj = new HashMap<String, LoadWorldObj>();

    public void addObject(String name, LoadWorldObj obj) {
        knownObj.put(name, obj);
    }

    public LoadWorldObj getLWorldObject(String name) {
        if (!knownObj.containsKey(name)) {
            throw new NullPointerException("Could not find LWorldObject of name "+name);
        }
        return knownObj.get(name);
    }
}
