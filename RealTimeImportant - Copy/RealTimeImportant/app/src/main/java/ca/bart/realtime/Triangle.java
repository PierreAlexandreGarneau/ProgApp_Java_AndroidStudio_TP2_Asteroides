package ca.bart.realtime;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Triangle {

    public static void drawIsoceleTriangle(Canvas canvas, float size, float angle, Paint paint) {
        drawIsoceleTriangle(canvas, size, angle, paint, true);
    }

    public static void drawIsoceleTriangle(Canvas canvas, float size, float angle, Paint paint, boolean drawInnerSides) {

        canvas.save();
        {
            for (int i = 0; i < 2; ++i) {

                canvas.save();
                {
                    canvas.rotate(180 - angle / 2);
                    if (drawInnerSides) {
                        canvas.drawLine(0, 0, size, 0, paint);
                    }
                    canvas.translate(size, 0);
                    canvas.rotate(90 + angle / 2);
                    if (angle < 60) {
                        canvas.drawLine(0, 0, size / 2, 0, paint);
                    } else {
                        canvas.drawLine(0, 0, size, 0, paint);
                    }
                }
                canvas.restore();

                canvas.scale(1, -1);
            }
        }
        canvas.restore();
    }
}
