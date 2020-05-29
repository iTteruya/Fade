package com.ren.fade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.ren.fade.controller.AppOptions;
//import com.ren.fade.view.FirstScreen;
import com.ren.fade.view.MainMenuScreen;

public class Fade extends Game {

    private AppOptions preferences;
    public AssetManager manager = new AssetManager();

    @Override
    public void create() {
        preferences = new AppOptions();
        setScreen(new MainMenuScreen(this));
    }

    public AppOptions getPreferences() {
        return this.preferences;
    }

    @Override
    public void dispose(){
        manager.dispose();
    }

}
