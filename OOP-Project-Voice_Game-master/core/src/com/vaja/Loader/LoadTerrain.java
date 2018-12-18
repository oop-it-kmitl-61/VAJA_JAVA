package com.vaja.Loader;

/**
 * @author khingbmc
 * this class is loadterrain form atlas file
 */
public class LoadTerrain {

    //Name of TextureRegion in tilepack (atlas)
    private String imageName;

    public LoadTerrain(String imageName){
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}
