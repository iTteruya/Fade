package com.ren.fade.controller;

import com.ren.fade.model.Rune;

public class SwapAnimation implements Animation {

        private static final float totalDuration = 0.1f;
        private static final float totalDurationInv = 10.0f;
        private Animation handler;
        private Rune rune1;
        private Rune rune2;
        private float currentDuration;
        private float distanceX;
        private float distanceY;
        private boolean swapBack;

    public SwapAnimation(Rune rune1, Rune rune2, boolean swapBack, Animation handler) {
        this.handler = handler;
        this.rune1 = rune1;
        this.rune2 = rune2;
        this.rune1.activity = 2;
        this.rune2.activity = 2;
        this.swapBack = swapBack;
        this.distanceX = this.rune2.posX - this.rune1.posX;
        this.distanceY = this.rune2.posY - this.rune1.posY;
        this.currentDuration = 0;
    }

    @Override
    public void update(float delta) {
        float currentDelta = Math.min(SwapAnimation.totalDuration - this.currentDuration, delta);
        float deltaX = this.distanceX * currentDelta * SwapAnimation.totalDurationInv;
        float deltaY = this.distanceY * currentDelta * SwapAnimation.totalDurationInv;
        this.rune1.posX += deltaX;
        this.rune1.posY += deltaY;
        this.rune2.posX -= deltaX;
        this.rune2.posY -= deltaY;
        this.currentDuration += delta;
        if (this.currentDuration >= SwapAnimation.totalDuration) {
            if (this.swapBack) {
                this.swapBack = false;
                this.currentDuration = 0;
                this.distanceX = -this.distanceX;
                this.distanceY = -this.distanceY;
                this.update(delta - currentDelta);
            }
            else {
                this.rune1.activity = -1;
                this.rune2.activity = -1;
                this.handler.onComplete(this);
            }
        }
    }

    @Override
    public void onComplete(Animation animation) {

    }
}

