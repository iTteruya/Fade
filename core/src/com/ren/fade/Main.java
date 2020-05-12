package com.ren.fade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ren.fade.view.GameScreen;

public class Main extends Game {

    private Screen GameScreen;

    @Override
    public void create() {
        GameScreen = new GameScreen();
        setScreen(GameScreen);
    }
}
