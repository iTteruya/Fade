package com.ren.fade.model;

public class Rune {

    public enum Type {NORMAL, SPECIAL}
    public Type type = Type.NORMAL;
    public int activity = -1;
    public int kind;
    public float posX;
    public float posY;
    public float sizeX;
    public float sizeY;

    public Rune(int kind, float posX, float posY, float sizeX, float sizeY) {
        this.kind = kind;
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
}
