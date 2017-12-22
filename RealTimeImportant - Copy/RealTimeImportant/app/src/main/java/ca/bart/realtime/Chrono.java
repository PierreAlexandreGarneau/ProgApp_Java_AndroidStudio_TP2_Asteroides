package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Chrono implements GameObject, Constants {

    private double angleVaisseau = 0;

    private double angleLazer = 0;

    private Paint outline = new Paint();

    private int touchId = -1;

    private float lazerTime = 0.f;

    private Matrix matrix = new Matrix();
    private float[] points = new float[2];

    public Chrono() {
        outline.setColor(Color.BLACK);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(1);
    }

    @Override
    public void update() {
        if (touchId != -1) {
            return;
        }
    }

    @Override
    public void draw(Canvas canvas) {

        //dessine le vaisseau
        canvas.save();
        canvas.rotate(-90);

        canvas.getMatrix().invert(matrix);

        canvas.rotate((float) angleVaisseau);

        canvas.translate(10,  0);
        Triangle.drawIsoceleTriangle(canvas, 5, 60, outline);

        canvas.restore();

        //dessine le lazer
        canvas.save();
        canvas.rotate(-90);

        if (lazerTime > 0) {
            canvas.rotate((float) angleLazer);
            canvas.translate(10,  0);
            canvas.drawLine(0, 0, 200, 0, outline);
            lazerTime -= DELTA_TIME;
        }
        canvas.restore();
    }

    private void updateTime(float x, float y) {

        points[0] = x;
        points[1] = y;
        matrix.mapPoints(points);

        if (lazerTime <= 0) {
            lazerTime += 0.3;
            angleLazer = Math.toDegrees(Math.atan2(points[1], points[0]));
        }


        if (points[0] > 10 || points[0] < -10 ||
                points[1] > 10 || points[1] < -10)
        {
            angleVaisseau = Math.toDegrees(Math.atan2(points[1], points[0]));
        }
    }

    @Override
    public boolean onTouchBegin(int id, float x, float y) {

        if (touchId != -1) {
            return false;
        }

        touchId = id;
        updateTime(x, y);
        return true;
    }

    @Override
    public boolean onTouchMove(int id, float x, float y) {

        if (touchId != id) {
            return false;
        }
        updateTime(x, y);
        return true;
    }

    @Override
    public boolean onTouchEnd(int id, float x, float y) {

        if (touchId != id) {
            return false;
        }

        touchId = -1;
        return true;
    }

    @Override
    public boolean onTouchCancel(int id, float x, float y) {

        if (touchId == -1) {
            return false;
        }

        touchId = -1;
        return true;
    }
}
