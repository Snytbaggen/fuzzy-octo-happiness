package com.lkss.sangboksapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Daniel on 2015-01-16.
 */
public class GameView extends SurfaceView {
    private Bitmap fork, player;
    private Point forkPos = new Point();
    private Point playerPos = new Point();
    private SurfaceHolder holder;
    Point displaySize;
    private GameThread gameThread;
    private boolean isMovingLeft, isMovingRight = false;
    private int score = 0;

    public GameView(Context context) {
        super(context);
        gameThread = new GameThread(this);

        holder = getHolder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        displaySize = new Point(display.getWidth(), display.getHeight());


        //display.getSize(displaySize);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameThread.setRunning(false);
                while (retry){
                    try {
                        gameThread.join();
                        retry=false;
                    }catch (InterruptedException e){
                    }
                }
            }
        });
        fork = BitmapFactory.decodeResource(getResources(), R.drawable.gaffel);
        player = BitmapFactory.decodeResource(getResources(), R.drawable.hasse);
        playerPos.x=(displaySize.x-player.getWidth())/2;
        playerPos.y=displaySize.y-120-player.getHeight();
        newForkPos();
    }


    public void newForkPos(){
        Random random = new Random();
        forkPos.x = random.nextInt(displaySize.x-fork.getWidth());
        forkPos.y = 0;
    }

    @Override
    protected void onDraw(Canvas canvas){
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(fork, forkPos.x, forkPos.y, null);
            canvas.drawBitmap(player, playerPos.x, playerPos.y, null);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("Hjälp Hasse hitta stämgaffeln!",20,30, paint);
            canvas.drawText("Poäng: " + score,20,80, paint);
        }
    }

    public boolean intersects(){
        Rect playerRect = new Rect(playerPos.x, playerPos.y, playerPos.x + player.getWidth(), playerPos.y+player.getHeight());
        Rect forkRect = new Rect(forkPos.x, forkPos.y, forkPos.x + fork.getWidth(), forkPos.y+fork.getHeight());
        return playerRect.intersect(forkRect);
    }

    public void moveFork(){
        if ( forkPos.y + fork.getHeight() >= playerPos.y && intersects()){
            score += 100;
            newForkPos();
        }else if (forkPos.y > displaySize.y){
            score -= 100;
            newForkPos();
        }else {
            forkPos.y += displaySize.y / 100;
        }
    }

    public void incPlayerPos(){
        playerPos.x+=displaySize.x/60;
        if (playerPos.x > displaySize.x-player.getWidth())
            playerPos.x = displaySize.x-player.getWidth();

    }

    public void decPlayerPos(){
        playerPos.x-=displaySize.x/50;
        if (playerPos.x < 0)
            playerPos.x = 0;
    }

    public void setMoveRight(boolean move){
        isMovingRight = move;
    }

    public void setMoveLeft(boolean move){
        isMovingLeft = move;
    }

    public boolean isMovingLeft(){
        return isMovingLeft;
    }

    public boolean isMovingRight(){
        return isMovingRight;
    }
}
