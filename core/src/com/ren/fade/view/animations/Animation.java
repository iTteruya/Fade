package com.ren.fade.view.animations;

public interface Animation {
        void update(float delta);
        void onComplete(Animation animation);
}
