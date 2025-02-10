package com.example.pingponggame;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class BaseObject {
    protected float x, y;
    protected int width, height;
    protected Rect rect;
    protected Bitmap bitmap;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return new Rect((int)this.x,(int)this.y, (int)this.x+this.width, (int)this.y+this.height);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public BaseObject() {
    }

    public BaseObject(float x, float y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
