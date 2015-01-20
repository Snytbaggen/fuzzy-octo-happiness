package com.lkss.sangboksapp;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Daniel on 2014-09-18.
 */
public class SoundPlayer {
    public SoundPlayer(){};
    Thread NotesThread;
    boolean louderTones = true;

    public void playNotes(final double[] notes, final double length, final ImageView v, final Activity a){
        //Most of the code in this function is taken from StackOverflow
        if (NotesThread == null || !NotesThread.isAlive()){
            NotesThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (v != null && a != null) {
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                v.setImageDrawable(v.getResources().getDrawable(R.drawable.gaffel_klang));
                            }
                        });
                    }

                    double duration = length;                // seconds
                    for (int n = 0; n < notes.length; n++) {
                        double freqOfTone = notes[n];           // hz
                        int sampleRate = 9600;//original 8000;  // a number

                        double dnumSamples = duration * sampleRate;
                        dnumSamples = Math.ceil(dnumSamples);
                        int numSamples = (int) dnumSamples;
                        double sample[] = new double[numSamples];
                        byte generatedSnd[] = new byte[2 * numSamples];

                        for (int i = 0; i < numSamples; ++i) { // Fill the sample array
                            if (louderTones) {
                                double a = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
                                if (a > 0)
                                    sample[i] = 1;
                                else
                                    sample[i] = -1;
                                sample[i] += 5 * Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
                                sample[i] += 2 * Math.sin(freqOfTone * 4 * Math.PI * i / (sampleRate));
                                sample[i] += Math.sin(freqOfTone * 8 * Math.PI * i / (sampleRate));
                                sample[i] = sample[i] / 9;
                            }else{
                                sample[i] = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
                            }
                        }

                        // convert to 16 bit pcm sound array
                        // assumes the sample buffer is normalized.
                        // convert to 16 bit pcm sound array
                        // assumes the sample buffer is normalised.
                        int idx = 0;
                        int i = 0;

                        int ramp = numSamples / 20;                                    // Amplitude ramp as a percent of sample count


                        for (i = 0; i < ramp; ++i) {                                     // Ramp amplitude up (to avoid clicks)
                            double dVal = sample[i];
                            // Ramp up to maximum
                            final short val = (short) ((dVal * 32767 * i / ramp));
                            // in 16 bit wav PCM, first byte is the low order byte
                            generatedSnd[idx++] = (byte) (val & 0x00ff);
                            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                        }


                        for (i = i; i < numSamples - ramp; ++i) {                        // Max amplitude for most of the samples
                            double dVal = sample[i];
                            // scale to maximum amplitude
                            final short val = (short) ((dVal * 32767));
                            // in 16 bit wav PCM, first byte is the low order byte
                            generatedSnd[idx++] = (byte) (val & 0x00ff);
                            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                        }

                        for (i = i; i < numSamples; ++i) {                               // Ramp amplitude down
                            double dVal = sample[i];
                            // Ramp down to zero
                            final short val = (short) ((dVal * 32767 * (numSamples - i) / ramp));
                            // in 16 bit wav PCM, first byte is the low order byte
                            generatedSnd[idx++] = (byte) (val & 0x00ff);
                            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                        }

                        AudioTrack audioTrack = null;                                   // Get audio track
                        try {
                            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                    sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                                    AudioFormat.ENCODING_PCM_16BIT, (int) numSamples * 2,
                                    AudioTrack.MODE_STATIC);
                            audioTrack.write(generatedSnd, 0, generatedSnd.length);     // Load the track
                            audioTrack.play();                                          // Play the track
                        } catch (Exception e) {
                            //RunTimeError("Error: " + e);
                            return;
                        }

                        int x = 0;
                        do {                                                     // Montior playback to find when done
                            if (audioTrack != null)
                                x = audioTrack.getPlaybackHeadPosition();
                            else
                                x = numSamples;
                        } while (x < numSamples);

                        if (audioTrack != null)
                            audioTrack.release();           // Track playNote done. Release track.
                    }
                    if (a != null && v != null){
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                v.setImageDrawable(v.getResources().getDrawable(R.drawable.gaffel));
                            }

                        });
                    }
                }
            });
            NotesThread.start();
        }
    }

    public void setLouderTones(boolean isEnabled){
        louderTones = isEnabled;
    }

    public void playNote(final double hz, final double length) {
        double[] notes = {hz};
        playNotes(notes, length, null, null);
    }
}
