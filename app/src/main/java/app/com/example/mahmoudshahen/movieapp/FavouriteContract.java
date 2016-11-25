package app.com.example.mahmoudshahen.movieapp;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mahmoud shahen on 8/18/2016.
 */
public class FavouriteContract {

    public static final String AUTHORITY = "com.example.mahmoudshahen.movieapp.app";
    public static final Uri BASE_URL = Uri.parse("content://" + AUTHORITY );

    public  FavouriteContract()
    {}

    public static abstract class favouriteTable implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_URL.buildUpon().appendPath("favourite").build();

        public static final Uri CONTENT_URI_ID = CONTENT_URI.buildUpon().appendPath("id").build();
       // public static final String CONTENT_TYPE =
               // ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + "favourite";
        public static final String CONTENT_TYPE = BASE_URL + "/" + "favourite";

        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ORIGINALTITLE = "originalTitle";
        public static final String COLUMN_NAME_OVERVIEW = "overView";
        public static final String COLUMN_NAME_RELEASEDATE= "releaseDate";
        public static final String COLUMN_NAME_RUNTIME= "runTime";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_BACKGROUND = "background";
        public static final String COLUMN_NAME_IMDB = "IMDb";

        public static  Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }



    }
}
