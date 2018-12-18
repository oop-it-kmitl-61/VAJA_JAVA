package com.vaja.Loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

public class LoadTerrainLoader extends AsynchronousAssetLoader<LoadTerrainDB, LoadTerrainLoader.LoadTerraintDBParameter> {

    private LoadTerrainDB terrainDb = new LoadTerrainDB();

    public LoadTerrainLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager asman, String filename, FileHandle file, LoadTerraintDBParameter parameter) {
        XmlReader xr = new XmlReader();

        XmlReader.Element root = null;
        try {
            root = xr.parse(file.reader());
        } catch (IOException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }

        if (!root.getName().equals("LTerrain")) {
            System.err.println("Root node in "+filename+" is "+root.getName()+" expected LTerrain");
            Gdx.app.exit();
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            XmlReader.Element obj = root.getChild(i);
            if (!obj.getName().equals("terrain")) {
                System.err.println("Found " + obj.getName() +"-element where expected terrain-element in "+filename);
                Gdx.app.exit();
            }
            String name = obj.get("name");

            XmlReader.Element imageName = obj.getChild(0);
            if (!imageName.getName().equals("imageName")) {
                System.err.println("Found " + imageName.getName() +"-element where expected imageName-element in "+filename);
                Gdx.app.exit();
            }
            String imageNameString;
            if (imageName.getText() != null) {
                imageNameString = imageName.getText();
            } else {
                imageNameString = "";
            }

            LoadTerrain justLoaded = new LoadTerrain(imageNameString);
            terrainDb.addTerrain(name, justLoaded);
        }
    }

    @Override
    public LoadTerrainDB loadSync(AssetManager arg0, String arg1, FileHandle arg2, LoadTerraintDBParameter arg3) {
        return terrainDb;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Array<AssetDescriptor> getDependencies(String filename, FileHandle file, LoadTerraintDBParameter parameter) {
        Array<AssetDescriptor> ad = new Array<AssetDescriptor>();
        return ad;
    }

    static public class LoadTerraintDBParameter extends AssetLoaderParameters<LoadTerrainDB> {
    }
}
