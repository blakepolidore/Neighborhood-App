package blake.com.gameofthronesmap.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import blake.com.gameofthronesmap.R;
import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.otherFiles.SongService;

/**
 * Created by Raiders on 3/12/16.
 * <h1>Search Results</h1>
 * Shows the results of the user's search in a list view
 * User can select a character in the list by clicking on the row.
 * The user will be sent to this character's activity
 */
public class SearchResultsActivity extends AppCompatActivity {

    //region Public Keys For Intents
    public static final String CONTINENT_KEY = "CONTINENT";
    public static final String SEX_KEY = "SEX";
    public static final String HOUSE_KEY = "HOUSE";
    public static final String ID_INTENT_KEY = "id";
    //endregion Public Keys For Intents

    //region private variables
    private ListView searchResultsListView;
    private String characterContinent, characterSex, characterHouse;
    private CursorAdapter cursorAdapterForSearchList;
    private MusicStateSingleton musicState;
    private Cursor cursor;
    //endregion private variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        instantiateItems();
        getMainActivityIntent();
        createCursorAndPutInCursorAdapter();
        setOnListItemClickListerners(searchResultsListView, cursor); //Set on item click listener for each character
        getDatabaseHelper();
        handleIntent(getIntent());
        musicState = MusicStateSingleton.getInstance(); //Creates music state instance
    }

    /**
     * Creates menu at the top
     * Search lines allow user to search for characters by name
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_with_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    /**
     * Allows you to click on options in the menu bar.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                Intent intent = new Intent(getApplicationContext(), FavoritesListActivity.class);
                startActivity(intent);
                return true;
            case R.id.infoActivity:
                Intent infoIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(infoIntent);
                return true;
            case R.id.musicActivity:
                if (musicState.isPlaying()) {
                    stopService(new Intent(this, SongService.class));
                } else {
                    startService(new Intent(this, SongService.class));
                }
                return true;
            case R.id.search:
                handleIntent(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void instantiateItems() {
        searchResultsListView = (ListView) findViewById(R.id.listView);
    }

    /**
     * Gets the intent from the main activity.
     * The intent includes the three search criteria strings
     */
    private void getMainActivityIntent() {
        Intent intent = getIntent();
        if (intent.getExtras() != null){ //If the intents are not null get the spinner selection strings
            characterContinent = intent.getExtras().getString(CONTINENT_KEY);
            characterSex = intent.getExtras().getString(SEX_KEY);
            characterHouse = intent.getExtras().getString(HOUSE_KEY);
        }
    }

    /**
     * Cursor searches the database with the criteria from the main activity.
     * The cursor is put into the cursor adapter
     */
    private void createCursorAndPutInCursorAdapter() {
        cursor = getDatabaseHelper().searchCriteriaCursor(characterContinent, characterSex, characterHouse); //Creates cursor from selection criteria
        createCursorAdapterForSearchList(cursor); //Puts cursor in custom cursor adapter

    }

    /**
     * Creates database instance in this class
     * @return
     */
    private DatabaseHelper getDatabaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(SearchResultsActivity.this);
        return databaseHelper;
    }

    /**
     * Custom cursor adapter for list view.
     * Takes in cursor and sets the appropriate fields in the list based off the cursor
     * @param cursor
     */
    private void createCursorAdapterForSearchList(Cursor cursor) {
        cursorAdapterForSearchList = new CursorAdapter(SearchResultsActivity.this, cursor, 0) {
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
                iconImage.setBackgroundResource(drawableID);
            }
        };
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(cursorAdapterForSearchList);
    }
    /**
     * Gets icon for listview depending on the house of the favorited characters
     * @param house
     * @return
     */
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
            case "Tyrell":
                return R.drawable.tyrell;
            case "Bolton":
                return R.drawable.bolton;
            case "None":
                return R.drawable.none;
            default:
                return 0;
        }
    }

    /**
     * Allows user to click on character and go to the CharacterActivity
     * @param listView
     * @param cursor
     */
    private void setOnListItemClickListerners(ListView listView, final Cursor cursor) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent listItemIntent =  new Intent(SearchResultsActivity.this, CharacterActivity.class);
                cursor.moveToPosition(position);
                listItemIntent.putExtra(ID_INTENT_KEY, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                startActivity(listItemIntent);
            }
        });
    }

    /**
     * Allows user to search by name for a character in the database.
     * This action is done in the menu bar at the top of the activity
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            cursor = getDatabaseHelper().getCharacterByNameSearch(query);
            cursorAdapterForSearchList.swapCursor(cursor);
            cursorAdapterForSearchList.notifyDataSetChanged();
            setOnListItemClickListerners(searchResultsListView, cursor); //Resets the updated cursor so correct character activity appears when clicked
        }
    }

}
