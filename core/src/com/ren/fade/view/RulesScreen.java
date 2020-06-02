package com.ren.fade.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ren.fade.Fade;

public class RulesScreen extends AbstractScreen {

    private Stage stage;
    private Image background;
    private Label text;

    RulesScreen(Fade game) {
        super(game);
        stage = new Stage(new ExtendViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        Skin skin = new Skin(Gdx.files.internal("shade_skin\\uiskin.json"));

        game.manager.load("loading.atlas", TextureAtlas.class);
        game.manager.finishLoading();
        TextureAtlas atlas = game.manager.get("loading.atlas", TextureAtlas.class);
        background = new Image(atlas.findRegion("01"));
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        text = new Label( "Rules\nTo win in this game you must score 1000 points\n" +
                "before time on the timer runs out. You do this\nby combining 3 or more blocks\n" +
                "of the same type." + " Difficulty you select\nwill determine how much time you will have:\n" +
                "Easy - 5 minutes\nMedium - 3 minutes\nHard - 1 minute",
                skin.get("title-plain", Label.LabelStyle.class));
        text.setAlignment(Align.center);

        TextButton returnButton = new TextButton("Return", skin);
        returnButton.setColor(Color.GRAY);
        returnButton.setLabel(new Label("Return", skin.get("title-plain", Label.LabelStyle.class)));
        returnButton.getLabel().setAlignment(Align.center);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));

            }
        });

        table.add(text).align(Align.center);
        table.row().pad(10,0 , 0, 10);
        table.add(returnButton).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX().colspan(2);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        background.setSize(stage.getWidth(), stage.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        game.manager.unload("loading.atlas");
        stage.dispose();
    }
}
