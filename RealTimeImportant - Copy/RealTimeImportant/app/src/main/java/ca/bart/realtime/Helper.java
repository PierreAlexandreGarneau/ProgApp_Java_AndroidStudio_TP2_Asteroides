package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Helper {

    private static Paint greenOutline = new Paint();
    private static Paint redOutline = new Paint();

    static {
        greenOutline.setColor(Color.GREEN);
        greenOutline.setStyle(Paint.Style.STROKE);
        greenOutline.setStrokeWidth(2);
        redOutline.setColor(Color.RED);
        redOutline.setStyle(Paint.Style.STROKE);
        redOutline.setStrokeWidth(2);
    }

    public static void drawAxis(Canvas canvas) {
        canvas.drawLine(0, 0, 100, 0, greenOutline);
        canvas.drawLine(0, 0, 0, 100, redOutline);
    }


    public static int SelectColor(int colorId) {
        switch (colorId) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.GRAY;
            default:
                return Color.CYAN;
        }
    }

}
