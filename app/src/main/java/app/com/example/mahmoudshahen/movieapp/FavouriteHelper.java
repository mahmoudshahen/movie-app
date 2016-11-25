package app.com.example.mahmoudshahen.movieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mahmoud shahen on 8/18/2016.
 */
public class FavouriteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FAVOURITE.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ FavouriteContract.favouriteTable.TABLE_NAME + " ( "+
            FavouriteContract.favouriteTable.COLUMN_NAME_ID + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_ORIGINALTITLE + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_RELEASEDATE + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_RUNTIME + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_RATING + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_IMAGE + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_BACKGROUND + TEXT_TYPE + COMA_SEP +
            FavouriteContract.favouriteTable.COLUMN_NAME_IMDB + TEXT_TYPE + " ); ";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXIST " + FavouriteContract.favouriteTable.TABLE_NAME;

    public FavouriteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

   /* public long insert(String id, String originalTitle, String overView,
                           String releaseDate, String runTime, String rating, String image,
                           String background, String imdb)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_ID, id);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_ORIGINALTITLE, originalTitle);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_OVERVIEW, overView);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_RELEASEDATE, releaseDate);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_RUNTIME, runTime);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_RATING, rating);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_IMAGE, image);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_BACKGROUND, background);
        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_IMDB, imdb);

        long newRowID;
        newRowID = db.insert(FavouriteContract.favouriteTable.TABLE_NAME, null, values);
        return newRowID;
    }*/

    /*public Cursor getALlData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        /*String Projection [] = {
                FavouriteContract.favouriteTable.COLUMN_NAME_ID,
                FavouriteContract.favouriteTable.COLUMN_NAME_ORIGINALTITLE,
                FavouriteContract.favouriteTable.COLUMN_NAME_OVERVIEW,
                FavouriteContract.favouriteTable.COLUMN_NAME_RELEASEDATE,
                FavouriteContract.favouriteTable.COLUMN_NAME_RUNTIME,
                FavouriteContract.favouriteTable.COLUMN_NAME_RATING,
                FavouriteContract.favouriteTable.COLUMN_NAME_IMAGE,
                FavouriteContract.favouriteTable.COLUMN_NAME_BACKGROUND,
                FavouriteContract.favouriteTable.COLUMN_NAME_IMDB
        };

        Cursor cs = db.rawQuery("select* from " + FavouriteContract.favouriteTable.TABLE_NAME, null);

        if(cs.isAfterLast())
            return null;

        return cs;
    }*/


  /*  public void delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteContract.favouriteTable.TABLE_NAME, FavouriteContract.favouriteTable.COLUMN_NAME_ID +"="+ id,null);
    }
     public boolean search (String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("select* from " + FavouriteContract.favouriteTable.TABLE_NAME + " WHERE " +
                FavouriteContract.favouriteTable.COLUMN_NAME_ID + " = " + id, null);
        if(cs.isAfterLast())
            return false;

        return true;
    }
    */
}
