package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SpaceObject implements GameObject {

    protected float angle = 0;
    protected Paint outline = new Paint();

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public boolean onTouchBegin(int id, float x, float y) {
        return false;
    }

    @Override
    public boolean onTouchMove(int id, float x, float y) {
        return false;
    }

    @Override
    public boolean onTouchEnd(int id, float x, float y) {
        return false;
    }

    @Override
    public boolean onTouchCancel(int id, float x, float y) {
        return false;
    }
}
