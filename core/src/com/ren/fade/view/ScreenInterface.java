package com.ren.fade.view;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public interface ScreenInterface extends Screen {
    void load(AssetManager assetManager);
}
