package blake.com.gameofthronesmap.OtherFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Raiders on 3/14/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

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
    public static final String SQL_CREATE_CHARACTERS_TABLE = "CREATE TABLE IF NOT EXISTS " + CHARACTERS_TABLE_NAME +
            "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_NAME + " TEXT," + COL_SEX + " TEXT," + COL_CONTINENT + " TEXT," + COL_HOUSE + " TEXT," + COL_DESCRIPTION + " TEXT,"
            + COL_ISLIKED + " BOOLEAN," + COL_LARGE_IMAGE + " INTEGER," + COL_BADASS + " TEXT)";
    public static final String[] GOT_COLUMNS = {COL_ID,COL_NAME, COL_SEX, COL_CONTINENT, COL_HOUSE, COL_DESCRIPTION, COL_ISLIKED, COL_LARGE_IMAGE, COL_BADASS};
    public static final String SQL_DROP_CHARACTERS_TABLE = "DROP TABLE IF EXISTS characters";

    private static DatabaseHelper instance;

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

    public void insert(String name, String sex, String continent, String house, String description, Boolean isLiked, int largeImage, String badAss) {
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
        db.insert(CHARACTERS_TABLE_NAME, null, values);
    }

    public Cursor getCharacter(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CHARACTERS_TABLE_NAME, GOT_COLUMNS, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = "_id = ?";
        String[] selectionArgs = new String[]{ String.valueOf(id) };
        db.delete(CHARACTERS_TABLE_NAME, selection, selectionArgs);
    }

}
