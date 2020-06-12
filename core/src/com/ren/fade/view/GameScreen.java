package com.ren.fade.view;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ren.fade.Fade;


public class GameScreen extends AbstractScreen implements InputProcessor, ScreenInterface {

    GameField gameField;
    boolean win;
    public Stage stage;
    private SpriteBatch batch;
    private Label score;
    private Label timer;
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private InputProcessor inputProcessor = null;

    public GameScreen(Fade game, SpriteBatch batch) {
        super(game);
        this.batch = batch;
        gameField = new GameField();
        gameField.setSoundEnabled(game.getPreferences().isSoundEffectsEnabled());
        gameField.setSoundVolume(game.getPreferences().getSoundVolume());
        stage = new Stage(new ExtendViewport(640, 480));
    }

    @Override
    public void load(AssetManager assetManager) {
        assetManager = game.manager;
        assetManager.load("16.png", Texture.class);
        gameField.load(assetManager);
    }


    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("shade_skin\\uiskin.json"));

        TextButton returnButton = new TextButton(null, skin);
        returnButton.setColor(Color.GRAY);
        returnButton.setLabel(new Label("Menu", skin.get("title-plain", Label.LabelStyle.class)));
        returnButton.getLabel().setAlignment(Align.center);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.dispose();
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

        timer = new Label("Moves: " + gameField.getMoves() , skin, "title-plain");
        score = new Label("Score: 0", skin, "title-plain");

        Table table1 = new Table();
        table1.setFillParent(true);
        stage.addActor(table1);
        table1.add(timer).width(stage.getWidth() / 5f).height(stage.getHeight() * 0.07f).pad(10);
        table1.add(score).width(stage.getWidth() / 5f).height(stage.getHeight() * 0.07f).pad(10);
        table1.add(restartButton).width(stage.getWidth() / 7f).height(stage.getHeight() * 0.07f).fillX().uniformX().pad(10);
        table1.add(returnButton).width(stage.getWidth() / 7f).height(stage.getHeight() * 0.07f).fillX().uniformX().pad(10);
        table1.top().right();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        int gameFieldSize = Math.min(Gdx.graphics.getWidth(), (int) (0.9f * Gdx.graphics.getHeight()));
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            gameField.resize((Gdx.graphics.getWidth() - gameFieldSize) / 2, 0, gameFieldSize, gameFieldSize);
        } else gameField.resize((Gdx.graphics.getWidth() - gameFieldSize) / 2,
                (Gdx.graphics.getHeight() - gameFieldSize) / 2, gameFieldSize, gameFieldSize);
    }


    @Override
    public void render(float delta) {
        Texture image = game.manager.get("16.png");
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        score.setText("Score: " + gameField.getScore());
        timer.setText("Moves: " + gameField.getMoves());
        gameField.render(delta, this.batch, game.manager);
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
        if (gameField.getMoves() <= 0) {
            stage.dispose();
            this.win = false;
            game.setScreen(new EndScreen(game));
        }
        if (gameField.getScore() >= 1000) {
            stage.dispose();
            this.win = true;
            game.setScreen(new EndScreen(game));
        }
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer != 0)
            return false;
        InputProcessor inputProcessor = gameField.getInputProcessor();
        if (inputProcessor != null && inputProcessor.touchDown(screenX, Gdx.graphics.getHeight() - screenY, pointer, button)) {
            this.inputProcessor = gameField.getInputProcessor();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer != 0 || this.inputProcessor == null)
            return false;
        if (this.inputProcessor.touchUp(screenX, Gdx.graphics.getHeight() - screenY, pointer, button))
            this.inputProcessor = null;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer != 0 || this.inputProcessor == null)
            return false;
        if (this.inputProcessor.touchDragged(screenX, Gdx.graphics.getHeight() - screenY, pointer))
            this.inputProcessor = null;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
