package com.lkss.sangboksapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import songs.Notes;
import songs.Song;
import songs.SongList;
import songs.SongNames;


public class MainActivity extends Activity implements SensorEventListener{
    private static final int SHAKE_THRESHOLD = 2000;
    private static final double NOTE_DURATION = 0.7;
    private static final double TUNE_FORK_DURATION = 5;
    private static final String APP_DATA_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath()+"/LKSS";

    private SensorManager sensorManager;
    private Sensor mSensor;
    private long lastUpdate;
    float last_x, last_y, last_z = 0;
    boolean isActive;
    SongList songList = new SongList(APP_DATA_DIRECTORY);
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

        //Read an example song
        //f = new File("005 Entrésång.pdf");
        //f = new File(this.getCacheDir(), "008 Studentsången.pdf");
        //songList.addSong(new Song(0,"Entrésång",5,"005 Entrésång.pdf",new double[]{Notes.C5, Notes.G4, Notes.E4, Notes.C4}));
        //songList.save(Environment.getExternalStorageDirectory().getAbsolutePath()+"/songlist");
        songList.loadList(APP_DATA_DIRECTORY + "/songlist");

        final SongListAdapter adapter = new SongListAdapter(this, R.layout.activity_main, songList.getList());

        ListView listView = (ListView) findViewById(R.id.song_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Song song = (Song) adapterView.getItemAtPosition(i);
                openSongFile(song);
            }
        });
    }

    private  boolean fileExists(File[] files, String name){
        for (File file : files){
            if (file.getName().equals(name))
                return true;
        }
        return false;
    }

    public void loadSongFiles(){
        AssetManager assetManager = getAssets();
        SongNames songNames = new SongNames();
        String[] assetFilesNames = null;
        try{
           assetFilesNames = assetManager.list("");
        }catch (IOException e){
            Log.e("tag", "Failed to get asset file list.", e);
        }

        File filesDir = new File(APP_DATA_DIRECTORY);
        if (!filesDir.exists())
            filesDir.mkdir();

        File[] existingFiles = filesDir.listFiles();
        String filename;
        for (int i=0; i<109; i++){
            filename = songNames.getNameFromId(Integer.parseInt(assetFilesNames[i]));

            if (!fileExists(existingFiles, filename)){
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void openSongFile(Song song){
        f = new File(APP_DATA_DIRECTORY, song.getSongFile());

        //android.R.layout.simple_expandable_list_item_2
        Intent target = new Intent(Intent.ACTION_VIEW);
		target.setDataAndType(Uri.fromFile(f),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		try {
		    startActivity(target);
		} catch (ActivityNotFoundException e) {
		    // Instruct the user to install a PDF reader here, or something
		}
    }
}
