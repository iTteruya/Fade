package com.ren.fade.view;

import com.badlogic.gdx.Screen;
import com.ren.fade.Fade;

public abstract class AbstractScreen implements Screen {

    protected Fade game;

    public AbstractScreen(Fade game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }


}
