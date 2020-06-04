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

public class MainMenuScreen extends AbstractScreen {

    private Stage stage;
    private Image background;


    public MainMenuScreen(Fade game) {
        super(game);
        stage = new Stage(new ExtendViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        game.manager.load("backgrounds.atlas", TextureAtlas.class);
        game.manager.finishLoading();
        TextureAtlas atlas = game.manager.get("backgrounds.atlas", TextureAtlas.class);
        background = new Image(atlas.findRegion("17"));
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("shade_skin\\uiskin.json"));
        TextButton play = new TextButton(null, skin);
        play.setLabel(new Label("Play", skin.get("title-plain", Label.LabelStyle.class)));
        play.setColor(Color.GRAY);
        play.getLabel().setAlignment(Align.center);
        TextButton options = new TextButton(null, skin);
        options.setLabel(new Label("Options", skin.get("title-plain", Label.LabelStyle.class)));
        options.getLabel().setAlignment(Align.center);
        options.setColor(Color.GRAY);
        TextButton rules = new TextButton(null, skin);
        rules.setLabel(new Label("Rules", skin.get("title-plain", Label.LabelStyle.class)));
        rules.getLabel().setAlignment(Align.center);
        rules.setColor(Color.GRAY);

        table.add(play).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(options).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row();
        table.add(rules).height(stage.getHeight() * 0.1f).fillX().uniformX();

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadingScreen(game));
            }
        });

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        rules.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new RulesScreen(game));
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
    public void dispose() {
        game.manager.unload("backgrounds.atlas");
        stage.dispose();
    }
}
