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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ren.fade.Fade;


public class GameScreen extends AbstractScreen implements InputProcessor, ScreenInterface {

    private GameField gameField;
    public Stage stage;
    private SpriteBatch batch;
    private Table table;
    private Label score;
    private Label timer;
//    private float delayTimer;
//    private final float DELAY_TIMER_INTERVAL = 0.7f;
//    private Timer gameTimer;
//    private float ROUND_TIME_INTERVAL = 0;
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private InputProcessor inputProcessor = null;




    public GameScreen(Fade game, SpriteBatch batch) {
        super(game);
        this.batch = batch;
        stage = new Stage(new ExtendViewport(640, 480));
        gameField = new GameField(7);
//        switch (game.difficultyLevel) {
//            case 1: this.ROUND_TIME_INTERVAL = 500f;
//            case 2: this.ROUND_TIME_INTERVAL = 400f;
//            default: this.ROUND_TIME_INTERVAL = 300f;
//        }
//        this.delayTimer = ROUND_TIME_INTERVAL;
//        this.gameTimer = new Timer();
//        this.gameTimer.scheduleTask(new Timer.Task() {
//            @Override
//            public void run() {
//               delayTimer--;
//            }
//        }, 0.7f);
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

        final TextButton returnButton = new TextButton(null, skin);
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

        final TextButton restartButton = new TextButton(null, skin);
        restartButton.setColor(Color.GRAY);
        restartButton.setLabel(new Label("Restart", skin.get("title-plain", Label.LabelStyle.class)));
        restartButton.getLabel().setAlignment(Align.center);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.restart();
            }
        });

//        timer = new Label("Time: " + delayTimer, skin, "title-plain");
        score = new Label("Score: 0", skin, "title-plain");

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
//        table.add(timer).width(120).pad(10);
        table.add(score).width(120).pad(10);
        table.add(restartButton).width(120).pad(10);
//        table.add(returnButton).width(120).pad(10);
        table.add(returnButton).width(120).pad(10);
        table.top().right();



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
//        timer.setText("Time:" + gameTimer);
        gameField.render(delta, this.batch, game.manager);
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
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
