package com.ren.fade.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

    public class AppOptions {

        private static final String music_volume = "volume";
        private static final String music_enabled = "music.enabled";
        private static final String sound_volume = "sound";
        private static final String sound_enabled = "sound.enabled";
        private static final String prefs_name = "personal";

        private Preferences preferences;

        private Preferences getPrefs() {
            if (this.preferences == null) {
                this.preferences = Gdx.app.getPreferences(prefs_name);
            }
            return this.preferences;
        }


        public boolean isSoundEffectsEnabled() {
            return getPrefs().getBoolean(sound_enabled, true);
        }

        public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
            getPrefs().putBoolean(sound_enabled, soundEffectsEnabled);
            getPrefs().flush();
        }

        public boolean isMusicEnabled() {
            return getPrefs().getBoolean(music_enabled, true);
        }

        public void setMusicEnabled(boolean musicEnabled) {
            getPrefs().putBoolean(music_enabled, musicEnabled);
            getPrefs().flush();
        }

        public float getMusicVolume() {
            return getPrefs().getFloat(music_volume, 0.5f);
        }

        public void setMusicVolume(float volume) {
            getPrefs().putFloat(music_volume, volume);
            getPrefs().flush();
        }

        public float getSoundVolume() {
            return getPrefs().getFloat(sound_volume, 0.5f);
        }

        public void setSoundVolume(float volume) {
            getPrefs().putFloat(sound_volume, volume);
            getPrefs().flush();
        }
    }
