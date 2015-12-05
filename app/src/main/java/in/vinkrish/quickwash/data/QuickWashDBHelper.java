package in.vinkrish.quickwash.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import in.vinkrish.quickwash.data.QuickWashContract.QuickWashEntry;

/**
 * Created by vinkrish on 04/12/15.
 */
public class QuickWashDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "quickwash.db";

    public QuickWashDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_QUICKWASH_TABLE = "CREATE TABLE " + QuickWashEntry.TABLE_NAME + " (" +
                QuickWashEntry._ID + " INTEGER PRIMARY KEY," +
                QuickWashEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                QuickWashEntry.COLUMN_MOBILE + " INTEGER NOT NULL, " +
                QuickWashEntry.COLUMN_ALTERNATE_MOBILE + " INTEGER NOT NULL, " +
                QuickWashEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                QuickWashEntry.COLUMN_ADDRESS + " TEXT NOT NULL, " +
                QuickWashEntry.COLUMN_PINCODE + " INTEGER NOT NULL, " +
                QuickWashEntry.COLUMN_SERVICE + " TEXT NOT NULL, " +
                QuickWashEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                " );";

        db.execSQL(SQL_CREATE_QUICKWASH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuickWashEntry.TABLE_NAME);
        onCreate(db);
    }
}
