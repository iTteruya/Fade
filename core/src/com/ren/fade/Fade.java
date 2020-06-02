package com.ren.fade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ren.fade.utility.AppOptions;
import com.ren.fade.view.GameScreen;
import com.ren.fade.view.MainMenuScreen;

public class Fade extends Game {

    private com.ren.fade.utility.AppOptions preferences;
    private SpriteBatch batch;
    public int difficultyLevel;
    private Viewport viewport;
    public GameScreen gameScreen;
    public AssetManager manager = new AssetManager();

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.viewport = new ScreenViewport();
        this.manager = new AssetManager();
        this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        preferences = new com.ren.fade.utility.AppOptions();
        setScreen(new MainMenuScreen(this));
        this.gameScreen = new GameScreen(this, this.batch);
        this.gameScreen.load(this.manager);
    }

    public void restart() {
        this.gameScreen = new GameScreen(this, this.batch);
        this.gameScreen.load(this.manager);
        this.setScreen(gameScreen);
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        super.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    @Override
    public void render() {
        this.batch.setProjectionMatrix(this.viewport.getCamera().projection);
        this.batch.setTransformMatrix(this.viewport.getCamera().view);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();
        super.render();
        this.batch.end();
        this.gameScreen.stage.draw();
        this.gameScreen.stage.act();
    }

    public AppOptions getPreferences() {
        return this.preferences;
    }


    @Override
    public void dispose(){
        manager.dispose();
        super.dispose();
    }

}
