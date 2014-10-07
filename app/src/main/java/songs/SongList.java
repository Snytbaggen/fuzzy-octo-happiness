package songs;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Daniel on 2014-09-19.
 */
public class SongList{
    private List<Song> songList = new ArrayList<Song>();
    private String root_directory;

    public SongList(String root_directory){
        this.root_directory = root_directory;
    }

    public SongList(String root_directory, String path){
        this.root_directory = root_directory;
        loadList(path);
    }

   public void loadList(String path){
       try{
           final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
           Object obj = ois.readObject();
           if (obj instanceof ArrayList){
               ArrayList l = (ArrayList) obj;
               if (l.size() > 0 && l.get(0) instanceof Song){
                   //All is OK
                   songList = l;
               }else{
                   //List empty or not song list
                   throw new ClassNotFoundException();
               }
           }else{
               //File not arraylist
               throw new ClassNotFoundException();
           }
       }catch (FileNotFoundException e){
           //File not found
       }catch (StreamCorruptedException e){
           //Error reading file
       }catch (IOException e){
           //Error reading file
       } catch (ClassNotFoundException e) {
           //File not list or bad list
       }
   }
    public void save(String path){
        try{final ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(path));
            fos.writeObject(songList);
            fos.close();
            Log.w("save", "Save successful");
        } catch (IOException e) {
            System.out.println("Couldn't save scenariolist");  //To change body of catch statement use File | Settings | File Templates.
            Log.w("save", "Save not successful");
        }
    }

    public Song getSong(int id){
        for (Song s : songList){
            if (s.getId() == id)
                return s;
        }
        return null;
    }

    public void addSong(Song s){
        songList.add(s);
    }

    public List<Song> getList(){
        return songList;
    }

    public void sortAlphabetical(){
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song song, Song song2) {
                return song.getName().compareTo(song2.getName());
            }
        });
    }

    public void sortNumerical(){
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song song, Song song2) {
                return Integer.compare(song.getPage(), song2.getId());
            }
        });
    }
}
