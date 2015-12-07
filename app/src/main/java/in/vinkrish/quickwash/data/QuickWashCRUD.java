package in.vinkrish.quickwash.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinkrish on 05/12/15.
 */
public class QuickWashCRUD {


    public static void inertOrder (Context context, ContentValues values) {
        QuickWashDBHelper dbHelper = new QuickWashDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(QuickWashContract.QuickWashEntry.TABLE_NAME, null, values);
    }

    public static List<Order> getOrder (Context context) {
        QuickWashDBHelper dbHelper = new QuickWashDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                QuickWashContract.QuickWashEntry.TABLE_NAME,
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        List<Order> orderList = new ArrayList<>();

        int nameColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_NAME);
        int mobileColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_MOBILE);
        int alternateMobileColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_ALTERNATE_MOBILE);
        int emailColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_EMAIL);
        int addressColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_ADDRESS);
        int pincodeColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_PINCODE);
        int serviceColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_SERVICE);
        int dateColumn = cursor.getColumnIndex(QuickWashContract.QuickWashEntry.COLUMN_DATE);

        while (cursor.moveToNext()){
            Order o = new Order();
            o.setName(cursor.getString(nameColumn));
            o.setMobile(cursor.getString(mobileColumn));
            o.setAlternateMobile(cursor.getString(alternateMobileColumn));
            o.setEmail(cursor.getString(emailColumn));
            o.setAddress(cursor.getString(addressColumn));
            o.setPincode(cursor.getString(pincodeColumn));
            o.setService(cursor.getString(serviceColumn));
            o.setDate(cursor.getString(dateColumn));
            orderList.add(o);
        }
        cursor.close();

        return orderList;
    }

}
