package com.lkss.sangboksapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import songs.FileNames;
import songs.Notes;
import songs.SongList;


public class MainActivity extends FragmentActivity implements SensorEventListener{
    private boolean tune_fork_enabled = true;
    private int tune_fork_hardness = 2000;
    private double note_duration = 0.7;
    private double tune_fork_duration = 5;
    private static final String APP_DATA_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath()+"/LKSS";

    private SongListFragment currentFragment;

    private SensorManager sensorManager;
    private Sensor mSensor;
    private long lastUpdate;
    float last_x, last_y, last_z = 0;
    boolean isActive, pdfEnabled;
    SongList songListNumerical = new SongList(APP_DATA_DIRECTORY);
    SongList songListAlphabetical = new SongList(APP_DATA_DIRECTORY);
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

    ViewPager tab;
    TabPagerAdapter tabAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //updateSettings();

        //Fetch sensors and set sensor listener
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mSensor, sensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();

        //Set up lists
        loadSongFiles();
        songListNumerical.loadList(APP_DATA_DIRECTORY + "/songlist.lkss");
        songListAlphabetical.loadList(APP_DATA_DIRECTORY + "/songlist.lkss");
        songListAlphabetical.sortAlphabetical();

        //Set up tabs
        tabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabAdapter.setData(APP_DATA_DIRECTORY, songListAlphabetical, songListNumerical, player, note_duration);
        tab = (ViewPager)findViewById(R.id.pager);
        tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                actionBar = getActionBar();
                actionBar.setSelectedNavigationItem(position);
            }
        });
        tab.setAdapter(tabAdapter);
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabReselected(android.app.ActionBar.Tab t,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTabSelected(ActionBar.Tab t, FragmentTransaction ft) {
                tab.setCurrentItem(t.getPosition());
                currentFragment = (SongListFragment)tabAdapter.getItem(t.getPosition());
            }
            @Override
            public void onTabUnselected(android.app.ActionBar.Tab t,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }};
        actionBar.addTab(actionBar.newTab().setText("0-9").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("A-Z").setTabListener(tabListener));
    }

    private  boolean fileExists(File[] files, String name){
        for (File file : files){
            if (file.getName().equals(name))
                return true;
        }
        return false;
    }

    //Checks that all files exists in APP_DATA_DIRECTORY and if not copies them there
    /********************************************************************
    * IMPORTANT                                                         *
    * Due to copyright issues this code has been repurposed to only     *
    * load a single "songfile", the user will have to add the PDF-      *
    * files manually to the memory card                                 *
    **********************************************************************/
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

        /*CODE BELOW IS FOR THE COPYRIGHT "FIX"*/
        File[] existingFiles = filesDir.listFiles();
        String filename = fileNames.getNameFromId(109);
        if (!fileExists(existingFiles, filename)){ //If it doesn't exists we copy the file and rename it to the proper name
            InputStream in;
            OutputStream out ;
            try{
                in = assetManager.open("109");
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
        filename = fileNames.getNameFromId(110);
        if (!fileExists(existingFiles, filename)){ //If it doesn't exists we copy the file and rename it to the proper name
            InputStream in;
            OutputStream out ;
            try{
                in = assetManager.open("110");
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

        /*CODE BELOW IS FOR AUTOMATICALLY ADDING THE SONG
        * FILES TO THE MEMORY CARD AND IS CURRENTLY DISABLED*/

        /*File[] existingFiles = filesDir.listFiles();
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
        }*/
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void  updateSettings(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tune_fork_duration = Integer.parseInt(preferences.getString("fork_duration_list", "5"));
        tune_fork_enabled = preferences.getBoolean("fork_enabled", true);
        tune_fork_hardness = Integer.parseInt(preferences.getString("fork_hardness_list", "2000"));

        note_duration = Double.parseDouble(preferences.getString("note_duration_list", "7"))/10;
        player.setLouderTones(preferences.getBoolean("note_louder_tones", true));

        boolean firststart = preferences.getBoolean("firststart", true);
        if (firststart){
            Intent intent = new Intent();
            intent.setClass(this, GuideActivity.class);
            startActivityForResult(intent, 0);
        }

        pdfEnabled = preferences.getBoolean("pdfenabled", true);

        if (tabAdapter != null){
            tabAdapter.updateDuration(note_duration);
            //tabAdapter.setData(APP_DATA_DIRECTORY, songListAlphabetical, songListNumerical, player, note_duration);
            //currentFragment = (SongListFragment)tabAdapter.getItem(0);
        }
    }

    @Override
    public void onResume(){
        updateSettings();
        super.onResume();
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
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SetPreferenceActivity.class);
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isActive && tune_fork_enabled){
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

                if (speed > tune_fork_hardness) {
                    player.playNote(Notes.A4, tune_fork_duration);
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
