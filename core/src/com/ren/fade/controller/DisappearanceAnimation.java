package com.ren.fade.controller;

import com.ren.fade.model.Rune;

import java.util.Set;

public class DisappearanceAnimation implements Animation {

        private static final float totalDuration = 0.1666666f;
        private static final float totalDurationInv = 6.0f;
        private Animation handler;
        public Set<Rune> runes;
        private Rune[] runesArray;
        private float currentDuration;
        private float runeSize;

	public DisappearanceAnimation(Set<Rune> runes, float runeSize, Animation handler) {
	    this.handler = handler;
	    this.runes = runes;
	    this.runesArray = runes.toArray(new Rune[0]);
		for (Rune rune : this.runesArray) {
			rune.activity = 1;
		}
	    this.runeSize = runeSize;
	    this.currentDuration = 0;
	}

        @Override
        public void update(float delta) {
	    this.currentDuration += delta;
	    if (this.currentDuration >= DisappearanceAnimation.totalDuration) {
			for (Rune rune : this.runesArray) {
				rune.activity = -1;
			}
	        this.handler.onComplete(this);
	    }
	    else {
	        float newSize = this.runeSize * (1 - this.currentDuration * DisappearanceAnimation.totalDurationInv);
			for (Rune rune : this.runesArray) {
				rune.sizeY = rune.sizeX = newSize;
			}
	    }
	}

	@Override
	public void onComplete(Animation animation) {

	}
}