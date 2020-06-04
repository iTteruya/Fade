package com.ren.fade.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ren.fade.Fade;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class OptionsScreen extends AbstractScreen  {

    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Image background;

    OptionsScreen(Fade game) {
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

        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( game.getPreferences().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });

        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(game.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setTransform(true);
        musicCheckbox.scaleBy(stage.getHeight() / 800);
        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setTransform(true);
        soundEffectsCheckbox.scaleBy(stage.getHeight() / 800);
        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        final TextButton returnButton = new TextButton("Return", skin);
        returnButton.setColor(Color.GRAY);
        returnButton.setLabel(new Label("Return", skin.get("title-plain", Label.LabelStyle.class)));
        returnButton.getLabel().setAlignment(Align.center);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));

            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        titleLabel = new Label( "Options", skin.get("title-plain", Label.LabelStyle.class));
        volumeMusicLabel = new Label( "Music Volume", skin.get("title-plain", Label.LabelStyle.class));
        volumeSoundLabel = new Label( "Sound Volume", skin.get("title-plain", Label.LabelStyle.class));
        musicOnOffLabel = new Label( "Music", skin.get("title-plain", Label.LabelStyle.class));
        soundOnOffLabel = new Label( "Sound Effect", skin.get("title-plain", Label.LabelStyle.class));

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10,0,0,10);
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
