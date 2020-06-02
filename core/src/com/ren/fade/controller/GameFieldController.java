package com.ren.fade.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ren.fade.model.Rune;

public interface GameFieldController {
    Rune newRune(int i, int j);
    boolean canMove();
    boolean checkIndex(int index);
    void swap(int i1, int j1, int i2, int j2);
    void load(AssetManager assetManager);
    void resize(int x, int y, int width, int height);
    void render(float delta, SpriteBatch batch, AssetManager assetManager);
    InputProcessor getInputProcessor();
}
