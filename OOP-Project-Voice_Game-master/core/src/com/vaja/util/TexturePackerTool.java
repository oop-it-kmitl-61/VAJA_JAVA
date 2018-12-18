package com.vaja.util;


import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * this class is pack group of png file to atlas file
 */
public class TexturePackerTool {

    public static void main(String[] args) {
        TexturePacker.process("res/graphics/monster"
                            ,"res/graphics_packed/monster"
                            , "monster_textures");
    }
}
