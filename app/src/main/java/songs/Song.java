package songs;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Daniel on 2014-09-18.
 */
public class Song implements Serializable{
    private String name, subtitle, songFile;
    double[] startTones;
    private int page, id;
    //private File songFile;

    public Song(int id, String name, String subtitle, int page, String songFile, double[] startTones){
        this.id = id;
        this.name = name;
        this.subtitle = subtitle;
        this.page = page;
        this.songFile = songFile;
        this.startTones = startTones;
    }

    public Song(int id, String name, String subtitle, int page, String songFile, double startTone){
        this.id = id;
        this.name = name;
        this.subtitle = subtitle;
        this.page = page;
        this.songFile = songFile;
        this.startTones = new double[]{startTone};
    }

    public Song(int id, String name, int page, String songFile, double[] startTones){
        this.id = id;
        this.name = name;
        this.subtitle = null;
        this.page = page;
        this.songFile = songFile;
        this.startTones = startTones;
    }

    public Song(int id, String name, int page, String songFile, double startTone){
        this.id = id;
        this.name = name;
        this.subtitle = null;
        this.page = page;
        this.songFile = songFile;
        this.startTones = new double[]{startTone};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public double[] getStartTones() {
        return startTones;
    }

    public void setStartTones(double[] startTones) {
        this.startTones = startTones;
    }

    public void setStartTone(double startTones) {
        this.startTones = new double[]{startTones};
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSongFile() {
        return songFile;
    }

    public void setSongFile(String songFile) {
        this.songFile = songFile;
    }
}
