package ly.generalassemb.energize.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrankelinske on 6/28/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Define the database name and version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "energy.db";

    // Define
    public static abstract class DataEntryDrinks implements BaseColumns {
        public static final String TABLE_DRINKS = "drinks";
        public static final String COLUMN_DRINK_NAME = "name";
        public static final String COLUMN_DRINK_DESCRIPTION = "description";
        public static final String COLUMN_DRINK_SIZE = "size";
        public static final String COLUMN_DRINK_PRICE = "price";
        public static final String COLUMN_DRINK_MANUFACTURER = "manufacturer";
    }


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + DataEntryDrinks.TABLE_DRINKS + "(" + DataEntryDrinks._ID  +
            " integer primary key autoincrement, "
            + DataEntryDrinks.COLUMN_DRINK_NAME + " text not null, "
            + DataEntryDrinks.COLUMN_DRINK_DESCRIPTION + " text not null, "
            + DataEntryDrinks.COLUMN_DRINK_SIZE + " double not null, "
            + DataEntryDrinks.COLUMN_DRINK_PRICE + " double not null, "
            + DataEntryDrinks.COLUMN_DRINK_MANUFACTURER + " int not null" +
    ");";

    private static DatabaseHelper INSTANCE;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new DatabaseHelper(context.getApplicationContext());
        return INSTANCE;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Implement onCreate and onUpgrade in SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DataEntryDrinks.TABLE_DRINKS);
        onCreate(db);
    }

    public void insertDrink(String name, String description, double size, double price,
                            int manufacturer){
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();

        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(DataEntryDrinks.COLUMN_DRINK_NAME, name);
        values.put(DataEntryDrinks.COLUMN_DRINK_DESCRIPTION, description);
        values.put(DataEntryDrinks.COLUMN_DRINK_SIZE, size);
        values.put(DataEntryDrinks.COLUMN_DRINK_PRICE, price);
        values.put(DataEntryDrinks.COLUMN_DRINK_MANUFACTURER, manufacturer);

        // Insert the row into the drinks table
        db.insert(DataEntryDrinks.TABLE_DRINKS, null, values);
    }

    public List<Drink> getAllDrinks() {

        List<Drink> drinks = new ArrayList<>();

        // Get a reference to the database
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection, which tells the query to return only the columns mentioned
        // similar to "SELECT column1, column2, column3"
        String[] projection = new String[]{ DataEntryDrinks._ID, DataEntryDrinks.COLUMN_DRINK_NAME,
                DataEntryDrinks.COLUMN_DRINK_DESCRIPTION,
                DataEntryDrinks.COLUMN_DRINK_SIZE, DataEntryDrinks.COLUMN_DRINK_PRICE,
                DataEntryDrinks.COLUMN_DRINK_MANUFACTURER};

        // Make the query, getting the cursor object
        Cursor cursor = db.query(DataEntryDrinks.TABLE_DRINKS, projection, null, null,
                null, null, null, null);

        // With the cursor, create a new game object and return it
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrinks._ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_DESCRIPTION));
            double size = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_SIZE));
            double price = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_PRICE));
            int manufacturerId = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_MANUFACTURER));

            drinks.add(new Drink(id, name, description, size, price, manufacturerId));
            cursor.moveToNext();
        }
        return drinks;
    }

    public Drink getDrink(int id){
        // Get a reference to the database
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection, which tells the query to return only the columns mentioned
        // similar to "SELECT column1, column2, column3"
        String[] projection = new String[]{ DataEntryDrinks._ID, DataEntryDrinks.COLUMN_DRINK_NAME,
                DataEntryDrinks.COLUMN_DRINK_DESCRIPTION,
                DataEntryDrinks.COLUMN_DRINK_SIZE, DataEntryDrinks.COLUMN_DRINK_PRICE,
                DataEntryDrinks.COLUMN_DRINK_MANUFACTURER};

        // Define a selection, which defines the WHERE clause of the query (but not the values for it)
        // similar to "WHERE x < 23", only without the value; "WHERE x < ?"
        String selection = "_id = ?";

        // Define the selection values. The ?'s in the selection
        // The number of values in the following array should equal the number of ? in the where clause
        String[] selectionArgs = new String[]{ String.valueOf(id) };

        // Make the query, getting the cursor object
        Cursor cursor = db.query(DataEntryDrinks.TABLE_DRINKS, projection, selection, selectionArgs,
                null, null, null, null);

        // With the cursor, create a new game object and return it
        cursor.moveToFirst();

        String name = cursor.getString(
                cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_NAME));
        String description = cursor.getString(
                cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_DESCRIPTION));
        double size = cursor.getDouble(
                cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_SIZE));
        double price = cursor.getDouble(
                cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_PRICE));
        int manufacturerId = cursor.getInt(
                cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_MANUFACTURER));

        return new Drink(id, name, description, size, price, manufacturerId);
    }

    public List<Drink> searchDrinks(String searchString){
        List<Drink> drinks = new ArrayList<>();
        // Get a reference to the database
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection, which tells the query to return only the columns mentioned
        // similar to "SELECT column1, column2, column3"
        String[] projection = new String[]{ DataEntryDrinks._ID, DataEntryDrinks.COLUMN_DRINK_NAME,
                DataEntryDrinks.COLUMN_DRINK_DESCRIPTION,
                DataEntryDrinks.COLUMN_DRINK_SIZE, DataEntryDrinks.COLUMN_DRINK_PRICE,
                DataEntryDrinks.COLUMN_DRINK_MANUFACTURER};

        // Define a selection, which defines the WHERE clause of the query (but not the values for it)
        // similar to "WHERE x < 23", only without the value; "WHERE x < ?"
        String selection = DataEntryDrinks.COLUMN_DRINK_NAME + " LIKE ?";
//        + " OR "
//                + DataEntryDrinks.COLUMN_DRINK_DESCRIPTION + " LIKE ?";

        // Define the selection values. The ?'s in the selection
        // The number of values in the following array should equal the number of ? in the where clause
        String[] selectionArgs = new String[]{"'%" + searchString + "%'"};

        // Make the query, getting the cursor object
        Cursor cursor = db.query(DataEntryDrinks.TABLE_DRINKS, projection, selection, selectionArgs,
                null, null, null, null);

        // With the cursor, create a new game object and return it
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrinks._ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_DESCRIPTION));
            double size = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_SIZE));
            double price = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_PRICE));
            int manufacturerId = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrinks.COLUMN_DRINK_MANUFACTURER));

            drinks.add(new Drink(id, name, description, size, price, manufacturerId));
            cursor.moveToNext();
        }
        return drinks;
    }

}