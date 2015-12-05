package in.vinkrish.quickwash.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by vinkrish on 04/12/15.
 */
public class QuickWashProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final int QUICKWASH = 100;
    private QuickWashDBHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = QuickWashContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, QuickWashContract.PATH_QUICK_WASH, QUICKWASH);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new QuickWashDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case QUICKWASH: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        QuickWashContract.QuickWashEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case QUICKWASH:
                return QuickWashContract.QuickWashEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case QUICKWASH: {
                normalizeDate(values);
                long _id = db.insert(QuickWashContract.QuickWashEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = QuickWashContract.QuickWashEntry.buildQuickWashUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void normalizeDate(ContentValues values) {
        if (values.containsKey(QuickWashContract.QuickWashEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(QuickWashContract.QuickWashEntry.COLUMN_DATE);
            values.put(QuickWashContract.QuickWashEntry.COLUMN_DATE, QuickWashContract.normalizeDate(dateValue));
        }
    }
}
