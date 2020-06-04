package com.ren.fade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ren.fade.utility.AppOptions;
import com.ren.fade.view.GameScreen;
import com.ren.fade.view.MainMenuScreen;

public class Fade extends Game {

    private com.ren.fade.utility.AppOptions options;
    private SpriteBatch batch;
    private Music music;
    private Viewport viewport;
    public GameScreen gameScreen;
    private float currentVolume;
    public AssetManager manager = new AssetManager();

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.viewport = new ScreenViewport();
        this.manager = new AssetManager();
        this.music = Gdx.audio.newMusic(Gdx.files.internal("A Stroll Through Lost Dreams.mp3"));
        this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        options = new com.ren.fade.utility.AppOptions();
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
        this.currentVolume = music.getVolume();
        this.batch.setProjectionMatrix(this.viewport.getCamera().projection);
        this.batch.setTransformMatrix(this.viewport.getCamera().view);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!this.options.isMusicEnabled() && music.isPlaying()) {
            music.stop();
        }
        if (this.options.isMusicEnabled() && !music.isPlaying() && currentVolume != 0 ||
                (this.options.getMusicVolume() != 0 && currentVolume == 0)) {
            music.setLooping(true);
            music.setVolume(this.options.getMusicVolume());
            this.currentVolume = music.getVolume();
            music.play();
        }
        if (this.options.isMusicEnabled() && music.isPlaying() && this.options.getMusicVolume() != this.currentVolume) {
            music.setVolume(this.options.getMusicVolume());
            if (this.currentVolume == 0) music.stop();
        }
        this.batch.begin();
        super.render();
        this.batch.end();
        this.gameScreen.stage.draw();
        this.gameScreen.stage.act();
    }

    public AppOptions getPreferences() {
        return this.options;
    }

    @Override
    public void dispose(){
        music.dispose();
        manager.dispose();
        super.dispose();
    }
}
