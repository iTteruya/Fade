package com.ren.fade.view.animations;

import com.ren.fade.model.Rune;

import java.util.Set;

public class FallAnimation implements Animation {

    private static final float totalDuration = 0.0666666f;
    private static final float totalDurationInv = 15.0f;
    private Animation handler;
    private Rune[] runesArray;
    private float currentDuration;
    private float fallLength;

    public FallAnimation(Set<Rune> runes, float fallLength, Animation handler) {
        this.handler = handler;
        this.runesArray = runes.toArray(new Rune[0]);
        for (Rune rune : this.runesArray) {
            rune.activity = 0;
        }
        this.fallLength = fallLength;
        this.currentDuration = 0;
    }

    @Override
    public void update(float delta) {
        float currentDelta = Math.min(FallAnimation.totalDuration - this.currentDuration, delta);
        float deltaLength = this.fallLength * currentDelta * FallAnimation.totalDurationInv;
        for (Rune rune : this.runesArray) {
            rune.posY -= deltaLength;
        }
        this.currentDuration += delta;
        if (this.currentDuration >= FallAnimation.totalDuration) {
            for (Rune rune : this.runesArray) {
                rune.activity = -1;
            }
            this.handler.onComplete(this);
        }
    }

    @Override
    public void onComplete(Animation animation) {

    }
}