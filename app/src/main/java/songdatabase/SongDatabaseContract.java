package songdatabase;

import android.provider.BaseColumns;

/**
 * Created by Daniel on 2015-01-18.
 */
public class SongDatabaseContract {

    public SongDatabaseContract() {}

    public static abstract class SongDatabaseEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_FILENAME = "filename";
        public static final String COLUMN_NAME_STARTTONES = "starttones";
        public static final String COLUMN_NAME_PAGE = "page";
    }
}
