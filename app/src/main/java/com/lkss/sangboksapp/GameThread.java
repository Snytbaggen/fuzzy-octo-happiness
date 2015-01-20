package com.lkss.sangboksapp;

import android.graphics.Canvas;

/**
 * Created by Daniel on 2015-01-16.
 */
public class GameThread extends Thread {
    private GameView view;
    boolean running = false;

    public GameThread(GameView view){
        this.view = view;
    }

    public void setRunning(boolean run){
        running = run;
    }

    @Override
    public void run(){
        while (running){
            Canvas c = null;
            try{
                tick();
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.onDraw(c);
                }
            }finally {
                if (c != null){
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }

    private void tick(){
        if (view.isMovingLeft())
            view.decPlayerPos();

        if (view.isMovingRight())
            view.incPlayerPos();

        view.moveFork();
    }
}
