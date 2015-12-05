package in.vinkrish.quickwash.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by vinkrish on 04/12/15.
 */
public class QuickWashContract {

    public static final String CONTENT_AUTHORITY = "in.vinkrish.quickwash";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_QUICK_WASH = "quickwash";

    public static final class QuickWashEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUICK_WASH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUICK_WASH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_QUICK_WASH;

        public static final String TABLE_NAME = "quickwash";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_ALTERNATE_MOBILE = "alternate_mobile";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PINCODE = "pincode";
        public static final String COLUMN_SERVICE = "service";
        public static final String COLUMN_DATE = "date";

        public static Uri buildQuickWashUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

}
