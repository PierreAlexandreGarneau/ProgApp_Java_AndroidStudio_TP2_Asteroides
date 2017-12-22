package ca.bart.realtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import static ca.bart.realtime.GameView.TAG;

public class MainActivity extends Activity implements Constants {

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mainUpdate();
        }
    };

    private boolean pause = false;
    private boolean gameStarting = false;

    private long lastUpdate = System.nanoTime();
    private double timer = 0;

    private boolean dialogOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameStarting = true;


        setContentView(R.layout.activity_main);

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        lastUpdate = System.nanoTime();
        pause = true;
        if (gameStarting) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("New Game")
                    .setMessage("Start game")
                    .setView(R.layout.test)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick(" + dialog + ", " + which + ")");
                            pause = false;
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Log.d(TAG, "onDismiss(" + dialog + ")");
                            pause = false;
                        }
                    })
                    .show();
            gameStarting = false;
        } else {


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Pause")
                .setMessage("Are you ready?")
                .setView(R.layout.test)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick(" + dialog + ", " + which + ")");
                        pause = false;
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d(TAG, "onDismiss(" + dialog + ")");
                        pause = false;
                    }
                })
                .show();
        }
        mainUpdate();

                }

@Override
protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        }

public void mainUpdate() {

        long nanoSeconds = System.nanoTime();
        double deltaTime = 0.000000001 * (nanoSeconds - lastUpdate);
        lastUpdate = nanoSeconds;

        timer += deltaTime;

        while (timer > DELTA_TIME) {

        timer -= DELTA_TIME;

        GameView gameView = (GameView) findViewById(R.id.gameView);
        if (!pause) {
            gameView.update();
        }
        gameView.invalidate();
        }

        handler.postDelayed(runnable, DELTA_TIME_MILLIS);
        }
        }
