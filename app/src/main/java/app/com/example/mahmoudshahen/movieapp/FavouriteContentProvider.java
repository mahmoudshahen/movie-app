package app.com.example.mahmoudshahen.movieapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by mahmoud shahen on 8/24/2016.
 */
public class FavouriteContentProvider extends ContentProvider {


    private static final UriMatcher uriMatcher = buildUriMatcher();
    private  FavouriteHelper mOpenHelper;

    static final int movies = 100;
    static final int search = 110;

    private static UriMatcher buildUriMatcher() {
         UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(FavouriteContract.AUTHORITY, "favourite", movies);
        matcher.addURI(FavouriteContract.AUTHORITY, "favourite/id", search);
        return matcher;
    }

    public FavouriteContentProvider()
    {
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new FavouriteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {

            case movies:
            {
                retCursor =  db.rawQuery("select* from " + FavouriteContract.favouriteTable.TABLE_NAME, null);
                break;
            }
            case search:
            {
                retCursor = db.rawQuery("select* from " + FavouriteContract.favouriteTable.TABLE_NAME + " WHERE " +
                        FavouriteContract.favouriteTable.COLUMN_NAME_ID + " = " + selection, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        if(retCursor.isAfterLast())
            return null;
        db.close();
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = uriMatcher.match(uri);

        switch (match) {
            case movies:
                return FavouriteContract.favouriteTable.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (uriMatcher.match(uri)){
            case movies:{
                long newRowID;
                newRowID = db.insert(FavouriteContract.favouriteTable.TABLE_NAME, null, values);

                if(newRowID > 0)
                {
                    returnUri = FavouriteContract.favouriteTable.buildUri(newRowID);
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = db.delete(FavouriteContract.favouriteTable.TABLE_NAME, FavouriteContract.favouriteTable.COLUMN_NAME_ID +"="+ selection,null);
        db.close();
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}