package com.ren.fade.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ren.fade.Fade;

public class LoadingScreen extends AbstractScreen {


    private Stage stage;

    private Image backgroundBar;
    private Image background;
    private Image borderBar;
    private Image bar;
    private Image loadingBg;


    private int loadComplete = 0;
    private Label label;

    private float startX, endX;
    private float percent;


    LoadingScreen(Fade game) {
        super(game);
    }

    @Override
    public void show() {

        game.manager.load("loading.atlas", TextureAtlas.class);
        game.manager.finishLoading();

        stage = new Stage(new ExtendViewport(640, 480));
        TextureAtlas atlas = game.manager.get("loading.atlas", TextureAtlas.class);

        background = new Image(atlas.findRegion("14"));
        backgroundBar = new Image(atlas.findRegion("BackgroundBar"));
        borderBar = new Image(atlas.findRegion("BorderBar"));
        bar = new Image(atlas.findRegion("Bar"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        stage.addActor(background);
        stage.addActor(borderBar);
        stage.addActor(backgroundBar);
        stage.addActor(bar);
        stage.addActor(loadingBg);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        if (loadComplete == 1) {
            label.setPosition(stage.getWidth() / 2 - 65, 10);
        }
        background.setSize(stage.getWidth(), stage.getHeight());

        borderBar.setSize(stage.getWidth() * 0.7f, stage.getHeight() * 0.03f);
        borderBar.setX((stage.getWidth() - borderBar.getWidth()) / 2);
        borderBar.setY((stage.getHeight() - borderBar.getHeight()) / 2);

        bar.setSize(borderBar.getWidth() - 10, borderBar.getHeight() - 10);
        bar.setX(borderBar.getX() + 5);
        bar.setY(borderBar.getY() + 5);

        backgroundBar.setSize(borderBar.getWidth(), borderBar.getHeight());
        backgroundBar.setX(borderBar.getX());
        backgroundBar.setY(borderBar.getY());


        loadingBg.setSize(bar.getWidth(), bar.getHeight());
        loadingBg.setX(bar.getX());
        loadingBg.setY(bar.getY());
        startX = bar.getX() + 35;
        endX = bar.getWidth() - 20;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.manager.update()) {
            if (game.manager.getProgress() == 1) {
                if (loadComplete == 0) {
                    Skin skin = new Skin(Gdx.files.internal("shade_skin\\uiskin.json"));
                    label = new Label("Press to Start", skin.get("title-plain", Label.LabelStyle.class));
                    label.setAlignment(Align.center);
                    label.setFontScale(1);
                    label.setPosition(stage.getWidth() / 2 - 65, 10);
                    stage.addActor(label);
                    loadComplete++;
                }
                if (Gdx.input.isTouched()) {
                    game.restart();
                }
            }
        }

        percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);

        loadingBg.setX(startX + endX * percent);
        loadingBg.setWidth( bar.getWidth() - bar.getWidth() * percent);
        loadingBg.invalidate();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        game.manager.unload("loading.atlas");
        stage.dispose();
    }
}

