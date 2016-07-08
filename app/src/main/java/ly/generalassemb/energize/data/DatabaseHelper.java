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

    // Define Database Constants
    public static abstract class DataEntryManufacturer implements BaseColumns {
        public static final String TABLE_MANUFACTURER = "manufacturer";
        public static final String COLUMN_MANUFACTURER_NAME = "name";
        public static final String COLUMN_MANUFACTURER_LOCATION = "manufacturer";
        public static final String COLUMN_MANUFACTURER_DESCRIPTION = "description";
    }

    public static abstract class DataEntryDrink implements BaseColumns {
        public static final String TABLE_DRINK = "drink";
        public static final String COLUMN_DRINK_NAME = "name";
        public static final String COLUMN_DRINK_DESCRIPTION = "description";
        public static final String COLUMN_DRINK_SIZE = "size";
        public static final String COLUMN_DRINK_PRICE = "price";
        public static final String COLUMN_DRINK_IMAGE = "image";
        public static final String COLUMN_DRINK_MANUFACTURER = "manufacturer";
    }

    // Define Database Creation SQL
    private static final String MANUFACTURER_DATABASE_CREATE = "create table "
            + DataEntryManufacturer.TABLE_MANUFACTURER + "(" + DataEntryManufacturer._ID  +
            " integer primary key autoincrement, "
            + DataEntryManufacturer.COLUMN_MANUFACTURER_NAME + " text not null, "
            + DataEntryManufacturer.COLUMN_MANUFACTURER_LOCATION + " text not null, "
            + DataEntryManufacturer.COLUMN_MANUFACTURER_DESCRIPTION + " double not null" +
            ");";

    private static final String DRINK_DATABASE_CREATE = "create table "
            + DataEntryDrink.TABLE_DRINK + "(" + DataEntryDrink._ID  +
            " integer primary key autoincrement, "
            + DataEntryDrink.COLUMN_DRINK_NAME + " text not null, "
            + DataEntryDrink.COLUMN_DRINK_DESCRIPTION + " text not null, "
            + DataEntryDrink.COLUMN_DRINK_SIZE + " double not null, "
            + DataEntryDrink.COLUMN_DRINK_PRICE + " double not null, "
            + DataEntryDrink.COLUMN_DRINK_IMAGE + " text not null, "
            + DataEntryDrink.COLUMN_DRINK_MANUFACTURER + " int not null" +
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
        db.execSQL(MANUFACTURER_DATABASE_CREATE);
        db.execSQL(DRINK_DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DataEntryDrink.TABLE_DRINK);
        db.execSQL("DROP TABLE IF EXISTS " + DataEntryManufacturer.TABLE_MANUFACTURER);
        onCreate(db);
    }

    public void cleanDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        String manufacturerCleanQuery = "DELETE FROM "+ DataEntryManufacturer.TABLE_MANUFACTURER +";";
        String drinkCleanQuery = "DELETE FROM "+ DataEntryDrink.TABLE_DRINK +";";
        db.execSQL(manufacturerCleanQuery);
        db.execSQL(drinkCleanQuery);

    }

    public void insertDrink(String name, String description, double size, double price,
                            String image, long manufacturer){
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();

        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(DataEntryDrink.COLUMN_DRINK_NAME, name);
        values.put(DataEntryDrink.COLUMN_DRINK_DESCRIPTION, description);
        values.put(DataEntryDrink.COLUMN_DRINK_SIZE, size);
        values.put(DataEntryDrink.COLUMN_DRINK_PRICE, price);
        values.put(DataEntryDrink.COLUMN_DRINK_IMAGE, image);
        values.put(DataEntryDrink.COLUMN_DRINK_MANUFACTURER, manufacturer);

        // Insert the row into the drinks table
        db.insert(DataEntryDrink.TABLE_DRINK, null, values);
    }

    public long insertManufacturer(String name, String location, String description){
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();

        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(DataEntryManufacturer.COLUMN_MANUFACTURER_NAME, name);
        values.put(DataEntryManufacturer.COLUMN_MANUFACTURER_LOCATION, location);
        values.put(DataEntryManufacturer.COLUMN_MANUFACTURER_DESCRIPTION, description);

        // Insert the row into the drinks table
        return db.insert(DataEntryManufacturer.TABLE_MANUFACTURER, null, values);
    }

    public List<Drink> getAllDrinks() {

        List<Drink> drinks = new ArrayList<>();

        // Get a reference to the database
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection, which tells the query to return only the columns mentioned
        // similar to "SELECT column1, column2, column3"
        String[] projection = new String[]{ DataEntryDrink._ID, DataEntryDrink.COLUMN_DRINK_NAME,
                DataEntryDrink.COLUMN_DRINK_DESCRIPTION,
                DataEntryDrink.COLUMN_DRINK_SIZE, DataEntryDrink.COLUMN_DRINK_PRICE,
                DataEntryDrink.COLUMN_DRINK_IMAGE, DataEntryDrink.COLUMN_DRINK_MANUFACTURER};

        // Make the query, getting the cursor object
        Cursor cursor = db.query(DataEntryDrink.TABLE_DRINK, projection, null, null,
                null, null, null, null);

        // With the cursor, create a new game object and return it
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrink._ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_DESCRIPTION));
            double size = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_SIZE));
            double price = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_PRICE));
            String image = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_IMAGE));
            int manufacturerId = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_MANUFACTURER));

            drinks.add(new Drink(id, name, description, size, price, image, manufacturerId));
            cursor.moveToNext();
        }
        return drinks;
    }

    public Drink getDrink(int id){
        // Get a reference to the database
        SQLiteDatabase db = getReadableDatabase();

        String selectDrinkQuery = "SELECT * FROM " + DataEntryDrink.TABLE_DRINK +
                " INNER JOIN " + DataEntryManufacturer.TABLE_MANUFACTURER +
                " ON " + DataEntryDrink.TABLE_DRINK + "." +DataEntryDrink.COLUMN_DRINK_MANUFACTURER
                + "=" + DataEntryManufacturer.TABLE_MANUFACTURER + "." +DataEntryManufacturer._ID
                + " WHERE " + DataEntryDrink.TABLE_DRINK + "." +DataEntryDrink._ID +"= ?";

        // Define the selection values. The ?'s in the selection
        // The number of values in the following array should equal the number of ? in the where clause
        String[] selectionArgs = new String[]{ String.valueOf(id) };

        // Make the query, getting the cursor object
        Cursor cursor = db.rawQuery(selectDrinkQuery, selectionArgs);

        // With the cursor, create a new game object and return it
        cursor.moveToFirst();

        String name = cursor.getString(
                cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_NAME));
        String description = cursor.getString(
                cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_DESCRIPTION));
        double size = cursor.getDouble(
                cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_SIZE));
        double price = cursor.getDouble(
                cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_PRICE));
        String image = cursor.getString(
                cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_IMAGE));
        int manufacturerId = cursor.getInt(
                cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_MANUFACTURER));

        return new Drink(id, name, description, size, price, image, manufacturerId);
    }

    public List<Drink> searchDrinks(String searchString){
        List<Drink> drinks = new ArrayList<>();
        // Get a reference to the database
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection, which tells the query to return only the columns mentioned
        // similar to "SELECT column1, column2, column3"
        String[] projection = new String[]{ DataEntryDrink._ID, DataEntryDrink.COLUMN_DRINK_NAME,
                DataEntryDrink.COLUMN_DRINK_DESCRIPTION,
                DataEntryDrink.COLUMN_DRINK_SIZE, DataEntryDrink.COLUMN_DRINK_PRICE,
                DataEntryDrink.COLUMN_DRINK_IMAGE, DataEntryDrink.COLUMN_DRINK_MANUFACTURER};

        // Define a selection, which defines the WHERE clause of the query (but not the values for it)
        // similar to "WHERE x < 23", only without the value; "WHERE x < ?"
//        String selection = DataEntryDrinks.COLUMN_DRINK_NAME + " LIKE ? OR "
//         + DataEntryDrinks.COLUMN_DRINK_DESCRIPTION + " LIKE ?";

        String selection = "Select * from "+DataEntryDrink.TABLE_DRINK + " where "
                + DataEntryDrink.COLUMN_DRINK_NAME + " LIKE ? OR "
                + DataEntryDrink.COLUMN_DRINK_DESCRIPTION + " LIKE ? OR "
                + DataEntryDrink.COLUMN_DRINK_SIZE + " LIKE ?";

        // Define the selection values. The ?'s in the selection
        // The number of values in the following array should equal the number of ? in the where clause
        String[] selectionArgs = new String[]{"%" + searchString + "%", "%" + searchString + "%",
                "%" + searchString + "%"};

        // Make the query, getting the cursor object
        Cursor cursor = db.rawQuery(selection, selectionArgs);

        // With the cursor, create a new game object and return it
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrink._ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_DESCRIPTION));
            double size = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_SIZE));
            double price = cursor.getDouble(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_PRICE));
            String image = cursor.getString(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_IMAGE));
            int manufacturerId = cursor.getInt(
                    cursor.getColumnIndex(DataEntryDrink.COLUMN_DRINK_MANUFACTURER));

            drinks.add(new Drink(id, name, description, size, price, image, manufacturerId));
            cursor.moveToNext();
        }
        return drinks;
    }

    public void populateDatabase() {

        long redBullManufacturerId = insertManufacturer("Red Bull", "Austria",
                "Red Bull is an energy drink sold by Austrian company Red Bull GmbH, " +
                        "created in 1987. In terms of market share, " +
                        "Red Bull is the highest-selling energy drink in the world, " +
                        "with 5.387 billion cans sold in 2013.");

        long monsterManufacturerId = insertManufacturer("Monster Energy", "United States",
                "Monster Energy is an energy drink introduced by Hansen Natural Corp. " +
                        "(HANS) in April 2002.");

        long bawlsManufacturerId = insertManufacturer("Bawls", "United States",
                "BAWLS Guarana is a soda manufactured by Solvi Acquisition. Headquartered in " +
                        "Twinsburg, OH, BAWLS Guarana beverages are available at supermarkets, " +
                        "convenience stores and electronic retailers across the United States.");

        long rockstarManufacturerId = insertManufacturer("Rockstar", "United States",
                "Rockstar (branded ROCKST★R) is an Energy Drink created in 2001.[1] " +
                        "With 14% of the US market in 2008, Rockstar is a leading energy" +
                        " drink brand.");

        long fivehourManufacturerId = insertManufacturer("Rockstar", "United States",
                "5-hour Energy (stylized as 5-hour ENERGY) is an American made \"energy shot\" " +
                        "manufactured by Living Essentials LLC. The company was founded by CEO " +
                        "Manoj Bhargava and launched in 2003.");

        insertDrink("Red Bull", "The original", 8.0, 1.97, "red_bull", redBullManufacturerId);
        insertDrink("Monster", "Energy!", 16.0, 1.84, "monster_energy", monsterManufacturerId);
        insertDrink("Bawls", "Gamerz", 12.0, 2.77, "bawls", bawlsManufacturerId);
        insertDrink("Rockstar", "Party like a...", 12.0, 1.84, "rockstar", rockstarManufacturerId);
        insertDrink(
                "5-Hour Energy", "Hours of energy now", 12.0, 3.77, "five_hour_energy",
                fivehourManufacturerId);
    }

}