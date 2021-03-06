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


public class EndScreen extends AbstractScreen {

    private Stage stage;
    private Image background;
    private Label gameOver;

    EndScreen(Fade game) {
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

        TextButton returnButton = new TextButton(null, skin);
        returnButton.setColor(Color.GRAY);
        returnButton.setLabel(new Label("Menu", skin.get("title-plain", Label.LabelStyle.class)));
        returnButton.getLabel().setAlignment(Align.center);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton restartButton = new TextButton(null, skin);
        restartButton.setColor(Color.GRAY);
        restartButton.setLabel(new Label("Restart", skin.get("title-plain", Label.LabelStyle.class)));
        restartButton.getLabel().setAlignment(Align.center);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.restart();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        gameOver = new Label("Game Over", skin, "title-plain");
        gameOver.setAlignment(Align.center);

        table.add(gameOver);
        table.row().pad(10,0,0,10);
        table.add(restartButton).width(stage.getWidth() / 3f).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row().pad(10,0,0,10).row();
        table.add(returnButton).height(stage.getHeight() * 0.1f).fillX().uniformX();
        table.row();

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
        if (game.gameScreen.win) {
            gameOver.setText("Congratulations!\nYou Win");
        } else gameOver.setText("Game Over\nScore: " + game.gameScreen.gameField.getScore());
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        game.manager.unload("backgrounds.atlas");
        stage.dispose();
    }
}
