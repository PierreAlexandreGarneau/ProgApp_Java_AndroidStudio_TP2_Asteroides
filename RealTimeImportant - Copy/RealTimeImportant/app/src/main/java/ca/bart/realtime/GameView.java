package ca.bart.realtime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static ca.bart.realtime.Constants.DELTA_TIME;

public class GameView extends View {

    public static final String TAG = "GameView";

    private Terre terre;
    private LinkedList<Polygon> asteroides;
    private Player player;
    private Laser laser;
    private boolean gameOver = false;
    private boolean popup = false;
    private Paint outline = new Paint();
    private int Score = 0;

    private int width, height;
    private float cx, cy, scale;

    private float polygoneSpawnTime = 0.0f;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);



        terre = new Terre();
        asteroides = new LinkedList<>();
        laser = new Laser();
        player = new Player(laser);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        cx = 0.5f * width;
        cy = 0.5f * height;
        scale = 0.01f * Math.min(cx, cy);
}

    public void update() {

        polygoneSpawnTime += DELTA_TIME;
        if (polygoneSpawnTime > 0.5f && !gameOver)
        {
            polygoneSpawnTime = 0;
            Random rand = new Random(System.currentTimeMillis());
            int nbCote = rand.nextInt(4) + 5;
            float angle = (float) rand.nextInt(360);
            int color = Helper.SelectColor(rand.nextInt(6) + 1);
            float distance = 1.2f * (float) Math.sqrt(width * width + height * height);
            Polygon poly = new Polygon(nbCote, angle, distance / scale, color);
            asteroides.add(poly);
        }

        CheckCollisionTerre();
        CheckCollisionLaser();

        terre.update();
        player.update();
        for (GameObject asteroide : asteroides) {
            asteroide.update();
        }

        if (gameOver && !popup) {
            popup = true;
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("GAME OVER")
                    .setMessage("Votre score: " + Score)
                    .setView(R.layout.test)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Nouvelle partie", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Score = 0;
                            terre = new Terre();
                            gameOver = false;
                            popup = false;
                            Log.d(TAG, "onClick(" + dialog + ", " + which + ")");
                        }
                    })
                    .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                            Log.d(TAG, "onClick(" + dialog + ", " + which + ")");
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Log.d(TAG, "onDismiss(" + dialog + ")");
                        }
                    })
                    .show();
        }
    }

    private void CheckCollisionTerre() {
        LinkedList<Polygon> asteroidARemove = new LinkedList<>();
        for (Polygon asteroid : asteroides) {
            if (asteroid.GetDistance() <= 0) {
                gameOver = !terre.DiminuerPointVie(asteroid.GetNombreCote());
                asteroidARemove.add(asteroid);
            }
        }
        if (gameOver)
            asteroides.clear();
        else {
            for (Polygon asteroid : asteroidARemove) {
                asteroides.remove(asteroid);
            }
        }
    }

    private void CheckCollisionLaser() {
        if (laser.Actif()){
            LinkedList<Polygon> asteroidARemove = new LinkedList<>();
            for (Polygon asteroid : asteroides) {
                if (CollisionLaser(asteroid)) {
                    asteroidARemove.add(asteroid);
                    Score += 1;
                }
            }
            for (Polygon asteroid : asteroidARemove) {
                asteroides.remove(asteroid);
            }
        }
    }

    private boolean CollisionLaser(Polygon asteroid) {
        if (asteroid.GetDistance() < 20)
            return false;
        float angleBetween = laser.angle - asteroid.angle;
        angleBetween = Math.abs(angleBetween);
        if (angleBetween > 180)
            angleBetween = 360 - angleBetween;
        if (angleBetween > 90)
            return false;
        float distanceAsteroidLaser = asteroid.GetDistance() * (float) Math.sin(Math.toRadians(angleBetween));
        return distanceAsteroidLaser <= asteroid.size;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(cx, cy);
        canvas.scale(scale, scale);

        terre.draw(canvas);
        player.draw(canvas);
        for (GameObject asteroide : asteroides) {
            asteroide.draw(canvas);
        }

        canvas.save();

        canvas.translate(95, 80);
        outline.setColor(Color.RED);
        outline.setStyle(Paint.Style.FILL);
        outline.setStrokeWidth(1);
        canvas.drawRect(0,0,50,10,outline);

        outline.setColor(Color.GREEN);
        outline.setStyle(Paint.Style.FILL);
        outline.setStrokeWidth(1);
        canvas.drawRect(0,0,terre.GetPointVie() / 2,10,outline);

        canvas.restore();

        canvas.save();

        canvas.translate(-120, 85);
        outline.setColor(Color.BLACK);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(1);
        canvas.drawText("Score : " + Score, 0,0,outline);


        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent(" + event + ")");

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        LinkedList<GameObject> gameObjects = new LinkedList<>();
        gameObjects.addAll(asteroides);
        gameObjects.add(terre);
        gameObjects.add(player);

        for (Iterator<GameObject> iterator = gameObjects.descendingIterator(); iterator.hasNext(); ) {
            GameObject gameObject = iterator.next();

            switch (event.getActionMasked()) {

                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (gameObject.onTouchBegin(pointerId, event.getX(pointerIndex), event.getY(pointerIndex))) {
                        return true;
                    }
                    float shotAngle = player.ShotAngle();
                    laser.Shot(shotAngle);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (gameObject.onTouchMove(pointerId, event.getX(pointerIndex), event.getY(pointerIndex))) {
                        return true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if (gameObject.onTouchEnd(pointerId, event.getX(pointerIndex), event.getY(pointerIndex))) {
                        return true;
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    if (gameObject.onTouchCancel(pointerId, event.getX(pointerIndex), event.getY(pointerIndex))) {
                        return true;
                    }
                    break;
            }
        }

        return super.onTouchEvent(event);
    }
}
