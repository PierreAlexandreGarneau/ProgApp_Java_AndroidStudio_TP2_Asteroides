package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Player extends SpaceObject {

    private Matrix matrix = new Matrix();
    private float[] points = new float[2];
    private float size = 10;
    private Laser laser;

    public Player(Laser pLaser) {
        outline.setColor(Color.BLACK);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(1);
        laser = pLaser;
    }

    public float ShotAngle() {
        return angle;
    }

    @Override
    public void update() {
        laser.update();
    }

    public void Move() {
        if (points[0] > 20 || points[0] < -20 || points[1] > 20 || points[1] < -20)
        {
            angle = (float) Math.toDegrees(Math.atan2(points[1], points[0]));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.getMatrix().invert(matrix);
        canvas.save();
        canvas.rotate(angle);
        canvas.translate(20, 0);
        Triangle.drawIsoceleTriangle(canvas, size, 60, outline);
        canvas.restore();
        laser.draw(canvas);
    }


    @Override
    public boolean onTouchBegin(int id, float x, float y) {
        points[0] = x;
        points[1] = y;
        matrix.mapPoints(points);

        Move();
        laser.Shot(angle);
        return true;
    }

    @Override
    public boolean onTouchMove(int id, float x, float y) {
        points[0] = x;
        points[1] = y;
        matrix.mapPoints(points);

        Move();

        return true;
    }
}
