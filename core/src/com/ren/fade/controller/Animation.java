package com.ren.fade.controller;

public interface Animation {
        void update(float delta);
        void onComplete(Animation animation);
}
