package blake.com.gameofthronesmap.otherFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

/**
 * Created by Raiders on 3/14/16.
 * <h1>Help creates the database for each character</h1>
 * Contains Strings for the SQLiteDatabase to use to write the database.
 * Creates methods to manipulate database.
 * @author Blake
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Creates all final strings to be used, written in SQL.
     */
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "characters.db";
    public static final String CHARACTERS_TABLE_NAME = "characters";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_SEX = "sex";
    public static final String COL_CONTINENT = "continent";
    public static final String COL_HOUSE = "house";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_ISLIKED = "isLiked";
    public static final String COL_BADASS = "badAss";
    public static final String COL_LARGE_IMAGE = "largeImage";
    public static final String COL_REVIEWS = "characterReview";
    public static final String SQL_CREATE_CHARACTERS_TABLE = "CREATE TABLE IF NOT EXISTS " + CHARACTERS_TABLE_NAME +
            "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NAME + " TEXT,"
            + COL_SEX + " TEXT,"
            + COL_CONTINENT + " TEXT,"
            + COL_HOUSE + " TEXT,"
            + COL_DESCRIPTION + " TEXT,"
            + COL_ISLIKED + " BOOLEAN,"
            + COL_LARGE_IMAGE + " INTEGER,"
            + COL_BADASS + " TEXT,"
            + COL_REVIEWS + " TEXT)";

    public static final String[] GOT_COLUMNS = {COL_ID,COL_NAME, COL_SEX, COL_CONTINENT, COL_HOUSE, COL_DESCRIPTION, COL_ISLIKED, COL_LARGE_IMAGE, COL_BADASS, COL_REVIEWS};
    public static final String SQL_DROP_CHARACTERS_TABLE = "DROP TABLE IF EXISTS characters";

    private static DatabaseHelper instance;

    /**
     * Creates the instance for the helper once and can be called throughout the application.
     * @param context
     * @return
     */
    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHARACTERS_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_CHARACTERS_TABLE);
        onCreate(db);
    }

    /**
     * Method to create a new character in the database.
     * @param name
     * @param sex
     * @param continent
     * @param house
     * @param description
     * @param isLiked
     * @param largeImage
     * @param badAss
     * @param characterReviews
     */
    public void insert(String name, String sex, String continent, String house, String description,
                       Boolean isLiked, int largeImage, String badAss, String characterReviews) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_SEX, sex);
        values.put(COL_CONTINENT, continent);
        values.put(COL_HOUSE, house);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_ISLIKED, isLiked);
        values.put(COL_LARGE_IMAGE, largeImage);
        values.put(COL_BADASS, badAss);
        values.put(COL_REVIEWS, characterReviews);
        db.insert(CHARACTERS_TABLE_NAME, null, values);
    }

    /**
     * Method to favorite a character and change it in the database.
     * @param id
     */
    public void changeIsLikedColumn(int id) {
        SQLiteDatabase dbWritable = getWritableDatabase();
        SQLiteDatabase dbReadable = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        String[] idArguments = new String[]{String.valueOf(id)};
        Cursor cursor = dbReadable.query(CHARACTERS_TABLE_NAME,
                new String[]{COL_ISLIKED},
                COL_ID+" = ?",
                idArguments,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            boolean colIsLiked = cursor.getInt(cursor.getColumnIndex(COL_ISLIKED)) > 0;
            if (colIsLiked) {
                values.put(COL_ISLIKED, false);
            } else {
                values.put(COL_ISLIKED, true);
            }
        }
        String selection = COL_ID+" = ?";
        dbWritable.update(CHARACTERS_TABLE_NAME, values, selection, idArguments);
    }

    /**
     *
     * @param id
     * @return returns the boolean whether or not the character has been liked
     */
    public boolean getCharacterIsLikedBoolean(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME,
                new String[]{COL_ISLIKED},
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            boolean colIsLiked = cursor.getInt(cursor.getColumnIndex(COL_ISLIKED)) > 0;
            db.close();
            return colIsLiked;
        } else {
            return false;
        }
    }

    /**
     * Gives the user the ability to return all characters in database. Used when no search criteria is specified
     * @return
     */
    public Cursor getCharacter() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CHARACTERS_TABLE_NAME, GOT_COLUMNS, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * Not used in this version but method gives the ability to delete character from database.
     * @param id
     */
    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = COL_ID+" = ?";
        String[] selectionArgs = new String[]{ String.valueOf(id) };
        db.delete(CHARACTERS_TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Method gives character details to put on individual character page.
     * @param id
     * @return Character name and description
     */
    public String[] getCharacterNameAndDescription(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME,
                new String[]{COL_NAME, COL_DESCRIPTION},
                COL_ID+" = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            String colName = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String colDescription = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
            String[] stringsInArray= new String[2];
            stringsInArray[0] = colName;
            stringsInArray[1] = colDescription;
            return stringsInArray;
        } else {
            return null;
        }
    }

    /**
     * Returns  character image for character page.
     * @param id
     * @return image of character
     */
    public int getCharacterImage(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME,
                new String[]{COL_LARGE_IMAGE},
                COL_ID+" = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            int colImage = cursor.getInt(cursor.getColumnIndex(COL_LARGE_IMAGE));
            return colImage;
        } else {
            return -1;
        }
    }

    /**
     * Allows user to search database for character by name
     * @param query
     * @return cursor for search
     */
    public Cursor getCharacterByNameSearch(String query){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME, // a. table
                GOT_COLUMNS, // b. column names
                COL_NAME + " LIKE ?", // c. selections
                new String[]{"%" +query + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursor;
    }


    /**
     * Allows user to search database for character by name
     * @param query
     * @return cursor for search
     */
    public Cursor getFavoriteCharacterCursor(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME, // a. table
                GOT_COLUMNS, // b. column names
                COL_ISLIKED + " = " + 1, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursor;
    }

    /**
     * Allows user to search database for character by name but only for characters that have been favorited
     * @param query
     * @return cursor for search
     */
    public Cursor getCharactersBySearchOfFavorites(String query){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME, // a. table
                GOT_COLUMNS, // b. column names
                COL_NAME + " LIKE ? AND " + COL_ISLIKED + " = " + 1, // c. selections
                new String[]{"%" +query + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursor;
    }

    /**
     * Cursor for choosing based on search criteria from spinners. Uses default search if no search criteria is specified
     * @param continent
     * @param sex
     * @param house
     * @return
     */
    public Cursor searchCriteriaCursor(String continent, String sex, String house) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (continent.equalsIgnoreCase("No Selection") && sex.equalsIgnoreCase("No Selection") &&
                house.equalsIgnoreCase("No Selection")){
            return getCharacter(); //If no selections use default cursor for all columns and rows
        } else {
            if (continent.equalsIgnoreCase("No Selection")) {
                continent = "'%'";//Searches for all amounts of characters in this column
            } else {
                continent = "'" + continent + "'"; //only searches for this keyword in the column
            }
            if (sex.equalsIgnoreCase("No Selection")) {
                sex = "'%'";
            } else {
                sex = "'" + sex + "'";
            }
            if (house.equalsIgnoreCase("No Selection")) {
                house = "'%'";
            } else {
                house = "'" + house + "'";
            }

            Cursor cursor = db.query(CHARACTERS_TABLE_NAME, // a. table
                    GOT_COLUMNS, // b. column names
                    DatabaseHelper.COL_CONTINENT+" LIKE "+continent+" AND "
                            +DatabaseHelper.COL_SEX+" LIKE "+sex+
                            " AND "+DatabaseHelper.COL_HOUSE+" LIKE "+house, // c. selections
                    null, // d. selections args
                    null, // e. group by
                    null, // f. having
                    null, // g. order by
                    null); // h. limit

            return cursor;
        }
    }

    /**
     * Allows user to leave a review of the character and updates the database.
     * @param id
     * @param inputText
     */
    public void addReviewOfCharacter(int id, String inputText) {
        SQLiteDatabase dbWritable = getWritableDatabase();
        SQLiteDatabase dbReadable = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        String[] idArguments = new String[]{String.valueOf(id)};
        Cursor cursor = dbReadable.query(CHARACTERS_TABLE_NAME,
                new String[]{COL_REVIEWS},
                COL_ID+" = ?",
                idArguments,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()) {
            String colReview = cursor.getString(cursor.getColumnIndex(COL_REVIEWS));
            values.put(COL_REVIEWS, inputText + " " + getDateTime() + " aintNuthingButAGThang297 " +  colReview);
        }
        String selection = COL_ID+" = ?";
        dbWritable.update(CHARACTERS_TABLE_NAME, values, selection, idArguments);
    }

    /**
     * Gets the users comments on the characters.
     * @param id
     * @return
     */
    public String getUsersReview(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CHARACTERS_TABLE_NAME,
                new String[]{COL_REVIEWS},
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            String colReview = cursor.getString(cursor.getColumnIndex(COL_REVIEWS));
            cursor.close();
            return colReview;
        } else {
            return "No Reviews Found";
        }
    }

    /**
     * Creates a timestamp. This gets put in the comments section.
     * @return
     */
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
