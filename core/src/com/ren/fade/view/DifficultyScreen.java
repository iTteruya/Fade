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

public class DifficultyScreen extends AbstractScreen {

    private Stage stage;
    private Image background;
    private Label label;


    DifficultyScreen(Fade game) {
        super(game);
        stage = new Stage(new ExtendViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("shade_skin\\uiskin.json"));

        game.manager.load("backgrounds.atlas", TextureAtlas.class);
        game.manager.finishLoading();
        TextureAtlas atlas = game.manager.get("backgrounds.atlas", TextureAtlas.class);
        background = new Image(atlas.findRegion("11"));
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        label = new Label( "Choose Difficulty Level", skin.get("title-plain", Label.LabelStyle.class));
        TextButton easy = new TextButton(null, skin);
        easy.setLabel(new Label("Easy", skin.get("title-plain", Label.LabelStyle.class)));
        easy.setColor(Color.GRAY);
        easy.getLabel().setAlignment(Align.center);
        TextButton medium = new TextButton(null, skin);
        medium.setLabel(new Label("Medium", skin.get("title-plain", Label.LabelStyle.class)));
        medium.getLabel().setAlignment(Align.center);
        medium.setColor(Color.GRAY);
        TextButton hard = new TextButton(null, skin);
        hard.setLabel(new Label("Hard", skin.get("title-plain", Label.LabelStyle.class)));
        hard.getLabel().setAlignment(Align.center);
        hard.setColor(Color.GRAY);
        TextButton returnButton = new TextButton("Return", skin);
        returnButton.setColor(Color.GRAY);
        returnButton.setLabel(new Label("Return", skin.get("title-plain", Label.LabelStyle.class)));
        returnButton.getLabel().setAlignment(Align.center);

        table.add(label).colspan(2);
        table.row();
        table.add(easy).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(medium).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row();
        table.add(hard).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(returnButton).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX();


        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.difficultyLevel = 1;
                game.setScreen(new LoadingScreen(game));
            }
        });

        medium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.difficultyLevel = 2;
                game.setScreen(new LoadingScreen(game));
            }
        });

        hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.difficultyLevel = 3;
                game.setScreen(new LoadingScreen(game));
            }
        });

        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));

            }
        });
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
    public void hide() {
        game.manager.unload("backgrounds.atlas");
        stage.dispose();
    }

    @Override
    public void dispose() {

    }
}
