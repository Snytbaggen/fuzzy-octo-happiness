package com.lkss.sangboksapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor mSensor;
    private long lastUpdate;
    float last_x, last_y, last_z = 0;
    SoundPlayer player = new SoundPlayer();

    private static final int SHAKE_THRESHOLD = 2000;
    private double note_duration = 0.7;
    private double tune_fork_duration = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
        sensorManager.registerListener(this, mSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            float speed = Math.abs(x+y+z-last_x-last_y-last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                player.playSingleNote(Notes.A4, tune_fork_duration);
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void playNotes(View v){
        double[] notes = {Notes.C4, Notes.G3, Notes.E3, Notes.C3};
        player.playNotes(notes, note_duration);
    }
}
