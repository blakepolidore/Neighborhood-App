package blake.com.gameofthronesmap.Activities;

import android.app.SearchManager;
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
 * Created by Raiders on 3/18/16.
 * <h1>List of favorite characters</h1>
 * Same as SearchResultsActivity but just for favorite characters
 */
public class FavoritesListActivity extends AppCompatActivity{

    TextView searchResultsTextView;
    ListView searchResultsListView;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    private CursorAdapter cursorAdapterForSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        instantiateItems();
        createCursorAdapterForSearchList(cursorForFavorites());
        setOnListItemClickListerners(searchResultsListView, cursorForFavorites());
        handleIntent(getIntent());
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
        searchResultsTextView = (TextView) findViewById(R.id.searchResultsTextFavorite1);
        searchResultsListView = (ListView) findViewById(R.id.listViewFavorite);
    }

    /**
     * Creates database instance in this class
     * @return
     */
    private SQLiteDatabase getDatabaseForFavorites() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(FavoritesListActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db;
    }

    /**
     * Creates cursor for searching for favorited characters
     * @return
     */
    private Cursor cursorForFavorites() {
        String queryString =  "SELECT * FROM "+DatabaseHelper.CHARACTERS_TABLE_NAME+
                " WHERE "+DatabaseHelper.COL_ISLIKED+" LIKE "+1+";";
        Cursor cursor = getDatabaseForFavorites().rawQuery(queryString, null);
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
                //int drawableID= cursor.getColumnIndex(DatabaseHelper.COL_ICON_IMAGE);
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

    /**
     * Allows user to click on character and go to the characters page with more descriptions
     * @param listView
     * @param cursor
     */
    private void setOnListItemClickListerners(ListView listView, final Cursor cursor) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent listItemIntent =  new Intent(FavoritesListActivity.this, FavoriteCharacterActivity.class);
                cursor.moveToPosition(position);
                listItemIntent.putExtra("idFavorite", cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
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
            Cursor cursor = DatabaseHelper.getInstance(FavoritesListActivity.this).getCharactersBySearchOfFavorites(query);
            cursorAdapterForSearchList.swapCursor(cursor);
            cursorAdapterForSearchList.notifyDataSetChanged();
        }
    }

}
