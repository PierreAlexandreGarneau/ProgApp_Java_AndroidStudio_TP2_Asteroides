package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Terre extends SpaceObject {

    private int pointDeVie = 100;

    public boolean DiminuerPointVie(int nbPoint) {
        pointDeVie -= nbPoint;
        if (pointDeVie < 0)
            pointDeVie = 0;
        return pointDeVie > 0;
    }

    public int GetPointVie() {
        return pointDeVie;
    }

    @Override
    public void draw(Canvas canvas) {
        outline.setColor(Color.BLUE);
        outline.setStyle(Paint.Style.FILL);
        outline.setStrokeWidth(1);
        canvas.drawCircle(0, 0, 10, outline);
    }
}
