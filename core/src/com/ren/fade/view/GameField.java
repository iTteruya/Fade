package com.ren.fade.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ren.fade.controller.DisappearanceAnimation;
import com.ren.fade.controller.FallAnimation;
import com.ren.fade.controller.GameFieldController;
import com.ren.fade.controller.GameInputProcessor;
import com.ren.fade.controller.Animation;
import com.ren.fade.controller.SwapAnimation;
import com.ren.fade.model.GameFieldLogic;
import com.ren.fade.model.Rune;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameField extends Actor implements GameFieldController, Animation {

    private static final String[] normalRunes = {"blue_1.png", "red_1.png", "green_1.png", "yellow_1.png", "purple_1.png", "white_1.png"};
    private static final String[] specialRunes = {"blue_2.png", "red_2.png", "green_2.png", "yellow_2.png", "purple_2.png", "white_2.png"};
//    private static final String[] sounds = {"knock.wav", "mystery3_3.wav", "mystery3_4.wav"};

    private GameFieldLogic fieldLogic;

    private List<Rune> runes = new ArrayList<>();
    private List<Animation> animations = new ArrayList<>();

    Stage stage;
    private GameInputProcessor gameInputProcessor;
    private int boardSize;
    private static final float blockSize = 1;
    private static final float runeSize = 1;


    private boolean canMove = false;
//    private boolean playSound = false;

    GameField(int boardSize) {
        this.gameInputProcessor = new GameInputProcessor(this);
        this.fieldLogic = new GameFieldLogic(this, boardSize);
    }

    @Override
    public void load(AssetManager assetManager) {
        assetManager.load("field.jpg", Texture.class);
        for (String name : GameField.normalRunes) {
            assetManager.load(name, Texture.class);
        }
        for (String name : GameField.specialRunes) {
            assetManager.load(name, Texture.class);
        }
//        for (String name : GameField.sounds) {
//            assetManager.load(name, Sound.class);
//        }
    }

    @Override
    public void resize(int x, int y, int width, int height) {
        this.boardSize = width;
        int fieldSize = this.fieldLogic.getSize();
        int cellSize = (int) (0.96 * width / fieldSize);
        int boardOffset = (height - cellSize * fieldSize) / 2;
        this.gameInputProcessor.resize(cellSize, x, y, boardOffset);
    }

    @Override
    public void render(float delta, SpriteBatch batch, AssetManager assetManager) {
        for (int index = 0; index < this.animations.size(); ++index) {
            this.animations.get(index).update(delta);
        }
        this.updateFieldState(assetManager);
        Texture boardImage = assetManager.get("field.jpg");
        batch.draw(boardImage, this.gameInputProcessor.getOffset(true),
                this.gameInputProcessor.getOffset(false), this.boardSize, this.boardSize);
        for (int index = 0; index < this.runes.size(); ++index) {
            this.drawObject(batch, assetManager, this.runes.get(index));
        }
    }

    private void drawImage(SpriteBatch batch, Texture image, float x, float y, float width, float height) {
        batch.draw(image, this.gameInputProcessor.indexToCoord(x, true), this.gameInputProcessor.indexToCoord(y, false),
                this.gameInputProcessor.sizeToCoord(width), this.gameInputProcessor.sizeToCoord(height));
    }

    private void drawObject(SpriteBatch batch, AssetManager assetManager, Rune rune) {
        Texture image;
        float minX = rune.posX - 0.5f * rune.sizeX;
        float minY = rune.posY - 0.5f * rune.sizeY;
        if (rune.kind != -1) {
            if (rune.type == Rune.Type.NORMAL) {
                image = assetManager.get(GameField.normalRunes[rune.kind], Texture.class);
                this.drawImage(batch, image, minX, minY, rune.sizeX, rune.sizeY);
            } else if (rune.type == Rune.Type.SPECIAL) {
                image = assetManager.get(GameField.specialRunes[rune.kind], Texture.class);
                this.drawImage(batch, image, minX, minY, rune.sizeX, rune.sizeY);
            }
        }
    }


    @Override
    public InputProcessor getInputProcessor() {
        return this.gameInputProcessor;
    }


    @Override
    public Rune newRune(int i, int j) {
        int kind = (int) (Math.random() * GameField.normalRunes.length);
        Rune newRune = new Rune(kind, i, j, GameField.runeSize, GameField.runeSize);
        this.runes.add(newRune);
        return newRune;
    }

    @Override
    public boolean canMove() {
        return this.canMove;
    }

    @Override
    public boolean checkIndex(int index) {
        return this.fieldLogic.checkIndex(index);
    }

    int getScore() {
        return fieldLogic.getScore();

    }


    private void updateFieldState(AssetManager assetManager) {
        if (this.animations.size() > 0)
            return;

        Set<Rune> runesToFall = this.fieldLogic.findRunesToFall();
        if (runesToFall.size() > 0) {
//            this.playSound = true;
            this.animations.add(new FallAnimation(runesToFall, GameField.blockSize, this));
            return;
        }
//        if (this.playSound) {
//            this.playSound = false;
//            assetManager.get(GameFieldControl.soundNames[0], Sound.class).play();
//        }
        Set<Rune> matchedAll = this.fieldLogic.findMatches();
        if (matchedAll.size() > 0) {
            this.animations.add(new DisappearanceAnimation(matchedAll, GameField.runeSize, this));
            return;
        }
        if (this.fieldLogic.findMatches() == null) {
            this.animations.add(new DisappearanceAnimation(this.fieldLogic.getAllRunes(), GameField.runeSize, this));
            return;
        }
        this.canMove = true;
    }

    @Override
    public void swap(int i1, int j1, int i2, int j2) {
        boolean success = this.fieldLogic.tryToSwap(i1, j1, i2, j2);
        this.animations.add(new SwapAnimation(this.fieldLogic.getRune(i1, j1), this.fieldLogic.getRune(i2, j2), !success, this));
        if (success) {
            this.canMove = false;
        }
    }

    @Override
    public void update(float delta) { }

    @Override
    public void onComplete(Animation animation) {
        if (animation.getClass() == DisappearanceAnimation.class) {
            DisappearanceAnimation disappearAnimation = (DisappearanceAnimation) animation;
            Set<Rune> chained = this.fieldLogic.deleteRunes(disappearAnimation.runes);
            if (chained.size() > 0) {
                this.animations.add(new DisappearanceAnimation(chained, GameField.runeSize, this));
            }
            for (Rune rune : disappearAnimation.runes) {
                this.runes.remove(rune);
            }
        }
        this.animations.remove(animation);
    }
}
