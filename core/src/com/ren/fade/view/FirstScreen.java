//package com.ren.fade.view;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.ai.btree.Task;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.utils.Timer;
//import com.badlogic.gdx.utils.viewport.ExtendViewport;
//import com.ren.fade.Fade;
//
//public class FirstScreen extends AbstractScreen {
//
//    private Stage stage;
//    private Image background;
//
//    public FirstScreen(Fade game) {
//        super(game);
//        stage = new Stage(new ExtendViewport(640, 480));
//    }
//
//    @Override
//    public void show() {
//        Texture backgroundT = new Texture(Gdx.files.internal("background_edit.jpg"));
//        background = new Image(backgroundT);
//        stage.addActor(background);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
//        background.setSize(stage.getWidth(), stage.getHeight());
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
////        if (game.manager.update()) {
////            Timer.schedule(new Timer.Task() {
////                @Override
////                public void run() {
////                    game.setScreen(new MainMenuScreen(game));
////                }
////            }, 0.3f);
////        }
//
//        if (game.manager.update()) {
//            game.setScreen(new MainMenuScreen(game));
//        }
//
//        stage.act();
//        stage.draw();
//    }
//
//
//    @Override
//    public void hide() {
//        background.remove();
//        stage.dispose();
//    }
//}
