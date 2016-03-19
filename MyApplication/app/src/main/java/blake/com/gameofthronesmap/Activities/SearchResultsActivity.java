package blake.com.gameofthronesmap.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/12/16.
 */
public class SearchResultsActivity extends AppCompatActivity {

    public static final String CONTINENT_KEY = "CONTINENT";
    public static final String SEX_KEY = "SEX";
    public static final String HOUSE_KEY = "HOUSE";

    TextView searchResultsTextView;
    ListView searchResultsListView;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    String characterContinent, characterSex, characterHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        instantiateItems();
        getMainActivityIntent();
        createDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                Intent intent = new Intent(getApplicationContext(), FavoritesListActivity.class);
                startActivity(intent);
                return true;
            case R.id.infoActivity:
                Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(infoIntent);
                return true;
            case R.id.musicActivity:
                themeMediaPlayer.start();
                if (playIsOn) {
                    themeMediaPlayer.pause();
                    playIsOn = false;
                } else {
                    themeMediaPlayer.start();
                    playIsOn = true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void instantiateItems() {
        searchResultsTextView = (TextView) findViewById(R.id.searchResultsText);
        searchResultsListView = (ListView) findViewById(R.id.listView);
    }

    private void getMainActivityIntent() {
        Intent intent = getIntent();
        if (intent.getExtras() != null){ //If the intents are not null get the spinner selection strings
            characterContinent = intent.getExtras().getString(CONTINENT_KEY);
            characterSex = intent.getExtras().getString(SEX_KEY);
            characterHouse = intent.getExtras().getString(HOUSE_KEY);

            Cursor cursor = searchDatabase(characterContinent, characterSex, characterHouse); //Create cursor from selection criteria
            createCursorAdapterForSearchList(cursor);
            setOnListItemClickListerners(searchResultsListView, cursor);
        }
    }

    /*
    Create the instance of the database helper and create the database
     */
    private SQLiteDatabase createDatabase() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(SearchResultsActivity.this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        return db;
    }

    private Cursor searchDatabase(String continent, String sex, String house){
        if (continent.equalsIgnoreCase("No Selection") && sex.equalsIgnoreCase("No Selection") &&
                house.equalsIgnoreCase("No Selection")){
            return createDefaultCursor(); //If no selections use default cursor for all columns and rows
        } else {
            if (continent.equalsIgnoreCase("No Selection")){
                continent = "'%'";//Searches for all amounts of characters in this column
            } else {
                continent = "'"+continent+"'"; //only searches for this keyword in the column
            }
            if (sex.equalsIgnoreCase("No Selection")){
                sex = "'%'";
            } else {
                sex = "'" + sex + "'";
            }
            if (house.equalsIgnoreCase("No Selection")){
                house = "'%'";
            } else {
                house = "'" + house + "'";
            }

            String queryString =  "SELECT * FROM "+DatabaseHelper.CHARACTERS_TABLE_NAME+
                    " WHERE "+DatabaseHelper.COL_CONTINENT+" LIKE "+continent+" AND "
                    +DatabaseHelper.COL_SEX+" LIKE "+sex+
                    " AND "+DatabaseHelper.COL_HOUSE+" LIKE "+house+";";
            Cursor cursor = createDatabase().rawQuery(queryString, null);
            return cursor;
        }
    }

    private Cursor createDefaultCursor() {
        Cursor cursor = createDatabase().query(DatabaseHelper.CHARACTERS_TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    private void createCursorAdapterForSearchList(Cursor cursor) {
        CursorAdapter cursorAdapterForSearchList = new CursorAdapter(SearchResultsActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nameText = (TextView) view.findViewById(R.id.characterNameText);
                TextView badassText = (TextView) view.findViewById(R.id.badassText);
                ImageView iconImage = (ImageView) view.findViewById(R.id.iconImageView);

                nameText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                badassText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BADASS)));
                int drawableID= getDrawableValue(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUSE)));
                //int drawableID= cursor.getColumnIndex(DatabaseHelper.COL_ICON_IMAGE);
                iconImage.setBackgroundResource(drawableID);
            }
        };
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cursorAdapterForSearchList);
    }
//MAKE BETTER PICTURES, EXACT SQUARES!!!!
    private int getDrawableValue(String house){
        switch(house){
            case "Targaryen":
                return R.drawable.targaryen;
            case "Stark":
                return R.drawable.stark;
            case "Lannister":
                return R.drawable.lannister;
            case "Martell":
                return R.drawable.martell;
            case "Mormont":
                return R.drawable.mormont;
            case "Baratheon":
                return R.drawable.baratheon;
            case "None":
                return R.drawable.none;
            default:
                return 0;
        }
    }

    private void setOnListItemClickListerners(ListView listView, final Cursor cursor) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent listItemIntent =  new Intent(SearchResultsActivity.this, LocationActivity.class);
                cursor.moveToPosition(position);
                listItemIntent.putExtra("id", cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                startActivity(listItemIntent);
            }
        });
    }
}
