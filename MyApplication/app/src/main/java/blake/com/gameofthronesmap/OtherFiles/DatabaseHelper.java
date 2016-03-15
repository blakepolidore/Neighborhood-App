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

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "characters.db";
    public static final String SQL_CREATE_CHARACTERS_TABLE = "CREATE TABLE characters (id INTEGER PRIMARY KEY, name TEXT, sex TEXT, continent TEXT, house TEXT)";
    public static final String SQL_DROP_CHARACTERS_TABLE = "DROP TABLE IF EXISTS characters";

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

    public void insert(int id, String name, String sex, String continent, String house) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("sex", sex);
        values.put("continent", continent);
        values.put("house", house);
        db.insert("characters", null, values);
    }

    public GOTCharacter getCharacter(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = new String[]{ "id", "name", "sex", "continent", "house" };
        String selection = "id = ?";
        String[] selectionArgs = new String[]{ String.valueOf(id) };
        Cursor cursor = db.query("characters", projection, selection, selectionArgs, null, null, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String sex = cursor.getString(cursor.getColumnIndex("sex"));
        String continent = cursor.getString( cursor.getColumnIndex("continent") );
        String house = cursor.getString( cursor.getColumnIndex("house") );

        return new GOTCharacter(id, name, sex, continent, house);
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = new String[]{ String.valueOf(id) };
        db.delete("characters", selection, selectionArgs);
    }
}
