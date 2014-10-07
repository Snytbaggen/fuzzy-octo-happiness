package com.lkss.sangboksapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import songs.FileNames;
import songs.Notes;
import songs.SongList;


public class MainActivity extends Activity implements SensorEventListener{
    private static final int SHAKE_THRESHOLD = 2000;
    private static final double NOTE_DURATION = 0.7;
    private static final double TUNE_FORK_DURATION = 5;
    private static final String APP_DATA_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath()+"/LKSS";

    private SongListFragment fragmentNumerical, fragmentAlphabetical, currentFragment;

    private SensorManager sensorManager;
    private Sensor mSensor;
    private long lastUpdate;
    float last_x, last_y, last_z = 0;
    boolean isActive;
    SongList songListNumerical = new SongList(APP_DATA_DIRECTORY);
    SongList songListAlphabetical = new SongList(APP_DATA_DIRECTORY);
    File f;
    SoundPlayer player = new SoundPlayer();

    @Override
    public void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fetch sensors and set sensor listener
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mSensor, sensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();

        loadSongFiles();
        songListNumerical.loadList(APP_DATA_DIRECTORY + "/songlist.lkss");
        songListAlphabetical.loadList(APP_DATA_DIRECTORY + "/songlist.lkss");
        songListAlphabetical.sortAlphabetical();

        fragmentNumerical = new SongListFragment();
        fragmentNumerical.setData(APP_DATA_DIRECTORY, songListNumerical, player, NOTE_DURATION);

        fragmentAlphabetical = new SongListFragment();
        fragmentAlphabetical.setData(APP_DATA_DIRECTORY, songListAlphabetical, player, NOTE_DURATION);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                if (tab.getText().toString().equalsIgnoreCase("0-9")) {
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragmentNumerical).commit();
                    currentFragment = fragmentNumerical;
                }
                else {
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragmentAlphabetical).commit();
                    currentFragment = fragmentAlphabetical;
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                //hide the given tab
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                //probably ignore this event
            }
        };
        actionBar.addTab(actionBar.newTab().setText("0-9").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("A-Z").setTabListener(tabListener));

        //getFragmentManager().beginTransaction().add(R.id.main_fragment_container, fragmentNumerical).commit();
    }

    private  boolean fileExists(File[] files, String name){
        for (File file : files){
            if (file.getName().equals(name))
                return true;
        }
        return false;
    }

    //Checks that all files exists in APP_DATA_DIRECTORY and if not copies them there
    private void loadSongFiles(){
        //Get list of all assets, most of them files
        AssetManager assetManager = getAssets();
        FileNames fileNames = new FileNames();
        String[] assetFilesNames = null;
        try{
           assetFilesNames = assetManager.list(""); //Get all the file names
        }catch (IOException e){
            Log.e("tag", "Failed to get asset file list.", e);
        }

        File filesDir = new File(APP_DATA_DIRECTORY);
        if (!filesDir.exists())
            filesDir.mkdir(); //Create dir if it doesn't exist

        File[] existingFiles = filesDir.listFiles();
        String filename;
        //The array existingFiles will have more file than ours, but there's 109
        //of our files in the assets folder and they come first in the array,
        //so we use this to loop through the files we need
        for (int i=0; i<109; i++){
            filename = fileNames.getNameFromId(Integer.parseInt(assetFilesNames[i])); //Get name associated with file

            if (!fileExists(existingFiles, filename)){ //If it doesn't exists we copy the file and rename it to the proper name
                InputStream in;
                OutputStream out ;
                try{
                    in = assetManager.open(assetFilesNames[i]);
                    File outFile = new File(APP_DATA_DIRECTORY, filename);
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                    in.close();
                    out.flush();
                    out.close();
                    Log.w("song", "Wrote song "+filename);
                }catch (IOException e){
                    Log.e("tag", "Failed to copy asset file: "+filename, e);
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
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
        if (isActive){
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float[] values = sensorEvent.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    player.playNote(Notes.A4, TUNE_FORK_DURATION);
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

        public void clickListener(View v) {
            currentFragment.clickListener(v);
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
