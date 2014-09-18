package songs;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Daniel on 2014-09-18.
 */
public class Song implements Serializable{
    private String name, subtitle, startTones;
    private int page;
    private File songFile;

    public Song(String name, String subtitle, int page, File songFile, String startTones){
        this.name = name;
        this.subtitle = subtitle;
        this.page = page;
        this.songFile = songFile;
        this.startTones = startTones;
    }

    //maybe a bit ugly, but I couldn't get calling the other constructor to work
    public Song(String name, int page, File songFile, String startTones){
        this.name = name;
        this.subtitle = null;
        this.page = page;
        this.songFile = songFile;
        this.startTones = startTones;
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

    public String getStartTones() {
        return startTones;
    }

    public void setStartTones(String startTones) {
        this.startTones = startTones;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public File getSongFile() {
        return songFile;
    }

    public void setSongFile(File songFile) {
        this.songFile = songFile;
    }
}
