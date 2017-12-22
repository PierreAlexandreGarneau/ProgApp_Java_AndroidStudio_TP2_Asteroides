package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static ca.bart.realtime.Constants.DELTA_TIME;

public class Laser extends SpaceObject {

    private float lazerTime = 0.f;

    public Laser() {
        outline.setColor(Color.RED);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(1);
    }

    public boolean Actif() {
        return lazerTime > 0;
    }

    public boolean Shot(float pAngle) {
        if (lazerTime <= 0) {
            lazerTime += 0.3;
            angle = pAngle;
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (lazerTime > 0) {
            lazerTime -= DELTA_TIME;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        if (lazerTime > 0) {
            canvas.rotate(angle);
            canvas.translate(20,  0);
            canvas.drawLine(0, 0, 200, 0, outline);
        }
        canvas.restore();
    }
}
