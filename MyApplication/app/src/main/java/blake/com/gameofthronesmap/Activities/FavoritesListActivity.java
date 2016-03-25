package blake.com.gameofthronesmap.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import blake.com.gameofthronesmap.R;
import blake.com.gameofthronesmap.otherFiles.CustomCursorAdapter;
import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.otherFiles.SongService;

/**
 * Created by Raiders on 3/18/16.
 * <h1>List of favorite characters</h1>
 * Same as SearchResultsActivity but just for favorite characters
 */
public class FavoritesListActivity extends AppCompatActivity{

    //region private variables
    private ListView favoriteCharactersList;
    private CursorAdapter cursorAdapterForSearchList;
    private MusicStateSingleton musicState;
    private Cursor cursor;
    private boolean hasBeenSearched =false;
    //endregion private variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        musicState = MusicStateSingleton.getInstance(); // Creates music state instance in this class
        instantiateItems();
        setCursorAdapterAndListView();
        setOnListItemClickListerners(favoriteCharactersList, cursorForFavorites());
        handleIntent(getIntent());
    }

    /**
     * When the activity is resumed, the cursor is swapped to update if characters have been liked or unliked
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (hasBeenSearched) {
            cursorAdapterForSearchList.swapCursor(cursor); //If I change to cursor search works and updating the list doesn't and vice versa
        } else {
            cursorAdapterForSearchList.swapCursor(cursorForFavorites());
        }
        cursorAdapterForSearchList.notifyDataSetChanged();
    }

    /**
     * Creates menu at the top
     * Search lines allow for user to search by characters by name
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
        favoriteCharactersList = (ListView) findViewById(R.id.listViewFavorite);
    }

    /**
     * Creates database instance in this class. This is for making a raw query.
     * Raw query is not used in this version but I wanted to remember how to do this.
     * @return
     */
//    private SQLiteDatabase getDatabaseForFavorites() {
//        DatabaseHelper dbHelper = DatabaseHelper.getInstance(FavoritesListActivity.this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        return db;
//    }

    /**
     * Creates instance of the database helper in this activity
     * @return
     */
    private DatabaseHelper getDatabaseHelperForFavorites() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(FavoritesListActivity.this);
        return dbHelper;
    }

    /**
     * Creates cursor for searching for favorited characters
     * This is the raw query I wanted to know how to do.
     * @return
     */
//    private Cursor cursorForFavorites() {
//        String queryString =  "SELECT * FROM "+DatabaseHelper.CHARACTERS_TABLE_NAME+
//                " WHERE "+DatabaseHelper.COL_ISLIKED+" LIKE "+1+";";
//        cursor = getDatabaseForFavorites().rawQuery(queryString, null);
//        return cursor;
//    }

    /**
     * Creates database helper instance for this class
     * @return
     */
    private Cursor cursorForFavorites() {
        cursor = getDatabaseHelperForFavorites().getFavoriteCharacterCursor();
        return cursor;
    }

    /**
     * Sets the cursor to the cursor adapter and the adapter to the list view
     */
    private void setCursorAdapterAndListView() {
        cursorAdapterForSearchList = CustomCursorAdapter.getCustomCursorAdapter(FavoritesListActivity.this, cursor);
        favoriteCharactersList.setAdapter(cursorAdapterForSearchList);
    }

    /**
     * Allows user to click on character and go to the characters page with the character details from the database
     * @param listView
     * @param cursor
     */
    private void setOnListItemClickListerners(ListView listView, final Cursor cursor) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent listItemIntent = new Intent(FavoritesListActivity.this, CharacterActivity.class);
                cursor.moveToPosition(position);
                listItemIntent.putExtra(SearchResultsActivity.ID_INTENT_KEY, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                startActivity(listItemIntent);
            }
        });
    }

    /**
     * Allows user to search by name for a character in the database
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            cursor = getDatabaseHelperForFavorites().getCharactersBySearchOfFavorites(query);
            cursorAdapterForSearchList.swapCursor(cursor);
            cursorAdapterForSearchList.notifyDataSetChanged();
            setOnListItemClickListerners(favoriteCharactersList, cursor); //Resets the updated cursor so correct character
            hasBeenSearched =true;
        }
    }

}
