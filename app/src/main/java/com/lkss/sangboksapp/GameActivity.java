package com.lkss.sangboksapp;

import android.app.ActionBar;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Daniel on 2015-01-16.
 */
public class GameActivity extends Activity {

    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a SurfaceView instance and set it
        // as the ContentView for this Activity.
        gameView = new GameView(this);

        ActionBar bar = getActionBar();
        bar.hide();
        setContentView(R.layout.gameview);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.gamelayout);
        if (layout != null)
            layout.addView(gameView);

        TextView left = (TextView) layout.findViewById(R.id.game_touch);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    gameView.setPlayerPos(motionEvent.getX());
                    return true;
                }
                /*if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    gameView.setMoveLeft(true);
                    return true;
                }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    gameView.setMoveLeft(false);
                    return true;
                }*/
                return false;
            }
        });

        /*TextView right = (TextView) layout.findViewById(R.id.game_right);
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //rightPress();
                    gameView.setMoveRight(true);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //rightRelease();
                    gameView.setMoveRight(false);
                    return true;
                }
                return false;
            }
        });*/
    }
}
