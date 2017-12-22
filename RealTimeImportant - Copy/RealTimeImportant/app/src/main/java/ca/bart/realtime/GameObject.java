package ca.bart.realtime;

import android.graphics.Canvas;

public interface GameObject {

    void update();

    void draw(Canvas canvas);

    boolean onTouchBegin(int id, float x, float y);
    boolean onTouchMove(int id, float x, float y);
    boolean onTouchEnd(int id, float x, float y);
    boolean onTouchCancel(int id, float x, float y);
}
