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
 * Created by Raiders on 3/18/16.
 * <h1>List of favorite characters</h1>
 * Same as SearchResultsActivity but just for favorite characters
 */
public class FavoritesListActivity extends AppCompatActivity{

    private ListView favoriteCharactersList;
    private CursorAdapter cursorAdapterForSearchList;
    private MusicStateSingleton musicState;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        musicState = MusicStateSingleton.getInstance(); // Creates music state instance in this class
        instantiateItems();
        createCursorAdapterForSearchList(cursorForFavorites());
        setOnListItemClickListerners(favoriteCharactersList, cursorForFavorites());
        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cursorAdapterForSearchList.swapCursor(cursorForFavorites());
        cursorAdapterForSearchList.notifyDataSetChanged();
    }

    /**
     * Creates menu at the top
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
     * Custom cursor adapter for list view
     * @param cursor
     */
    private void createCursorAdapterForSearchList(Cursor cursor) {
        cursorAdapterForSearchList = new CursorAdapter(FavoritesListActivity.this, cursor, 0) {
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
        ListView listView = (ListView) findViewById(R.id.listViewFavorite);
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
            case "None":
                return R.drawable.none;
            default:
                return 0;
        }
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
        }
    }

}
