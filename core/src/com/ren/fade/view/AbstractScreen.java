package com.ren.fade.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.ren.fade.Fade;

public abstract class AbstractScreen implements Screen {

    Fade game;

    AbstractScreen(Fade game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }


}
