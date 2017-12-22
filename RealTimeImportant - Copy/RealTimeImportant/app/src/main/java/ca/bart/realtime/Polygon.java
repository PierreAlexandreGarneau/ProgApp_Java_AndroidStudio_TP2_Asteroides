package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Paint;

import static ca.bart.realtime.Constants.DELTA_TIME;

public class Polygon implements GameObject {

    private float distance = 0.0f;
    public float size = 5.0f;
    private int sides = 5;
    public float angle;

    private float x;
    private float y;

    private float speed = 100;

    private float rotate = 0.0f;

    private Paint blueOutline = new Paint();

    public Polygon(int pSides, float pAngle, float pDistance, int pCouleur) {
        sides = pSides;
        angle = pAngle;
        distance = pDistance;
        blueOutline.setColor(pCouleur);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(1);
    }


    @Override
    public void update() {
        if (distance > 0)
            distance -= speed * DELTA_TIME;

        rotate += (2 * 360 * DELTA_TIME);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();

        canvas.rotate(angle);
        canvas.translate(distance, 0);
        canvas.rotate(rotate);

        for (int i = 0; i < sides; ++i) {

            Triangle.drawIsoceleTriangle(canvas, size, 360 / sides, blueOutline, false);
            canvas.rotate( 360 / sides);
        }
        canvas.restore();
    }

    public float GetDistance(){
        return distance;
    }

    public int GetNombreCote() {
        return sides;
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
