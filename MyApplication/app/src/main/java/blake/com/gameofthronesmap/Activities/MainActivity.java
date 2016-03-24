package blake.com.gameofthronesmap.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import blake.com.gameofthronesmap.R;
import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.GOTCharacter;
import blake.com.gameofthronesmap.otherFiles.GOTCharactersManager;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.otherFiles.SongService;

/**
 * <h1>Main Activity for GOT Characters App</h1>
 * This is the main page for the application. This activity is the first one that appears when the application starts.
 * From here users can search for various characters in the books and show based on several criteria.
 * Users can also access other activities such as the help activity and favorite characters list from the menu bar.
 * Users can also play music from the menu bar.
 *
 * @author Blake Polidore
 * @version 1.0
 * @since 2016 03 25
 */

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private Spinner continentSpinner;
    private Spinner sexSpinner;
    private Spinner houseSpinner;
    private MusicStateSingleton musicState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicState = MusicStateSingleton.getInstance(); //Creates instance of music state in this class
        startService(new Intent(this, SongService.class)); //Starts the music when the app starts
        intstantiateItems();
        fillSpinners();
        createSQLiteDatabaseHelper();
        toSearchResults();

    }

    /**
     * Creates menu at the top with the options to go to the favorites, the info screen, or play music
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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

    private void intstantiateItems() {
        searchButton = (Button) findViewById(R.id.searchButton);
        continentSpinner = (Spinner) findViewById(R.id.continentSpinner);
        sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        houseSpinner = (Spinner) findViewById(R.id.houseSpinner);
    }

    /**
     * Fills the search spinners with the appropriate search criteria options.
     */
    private void fillSpinners() {
        ArrayAdapter<CharSequence> adapterContinent = ArrayAdapter.createFromResource(this,
                R.array.continent, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterHouse = ArrayAdapter.createFromResource(this,
                R.array.house, android.R.layout.simple_spinner_item);
        adapterContinent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHouse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        continentSpinner.setAdapter(adapterContinent);
        sexSpinner.setAdapter(adapterSex);
        houseSpinner.setAdapter(adapterHouse);
        continentSpinner.setPrompt("Select Continent");
        sexSpinner.setPrompt("Select Gender");
        houseSpinner.setPrompt("Select House");
    }

    /**
     * Grabs the character array list from the GOT Characters Manager and places them in the database.
     * The database is only created once
     */
    private void createSQLiteDatabaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DatabaseHelper.CHARACTERS_TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount<=0) { //If statement ensures the database is created once. It is created when the user first turns on the app
            ArrayList<GOTCharacter> gotCharacters = GOTCharactersManager.getGOTCharacters(this);
            for (GOTCharacter character : gotCharacters){
                databaseHelper.insert(character.getName(), character.getSex(), character.getContinent(), character.getHouse(), character.getDescription(), character.getIsLiked(), character.getLargeImage(), character.getBadAss(), character.getCharacterReviews());
            }
        }
    }

    /**
     * Takes the search criteria in an intent and moves the user and the intent to the search results activity.
     */
    private void toSearchResults() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String continentString = getSpinnerSelections(continentSpinner);
                String sexString = getSpinnerSelections(sexSpinner);
                String houseString = getSpinnerSelections(houseSpinner);
                Intent characterSearchCriteriaIntent = new Intent(MainActivity.this, SearchResultsActivity.class);
                characterSearchCriteriaIntent.putExtra(SearchResultsActivity.CONTINENT_KEY, continentString);
                characterSearchCriteriaIntent.putExtra(SearchResultsActivity.SEX_KEY, sexString);
                characterSearchCriteriaIntent.putExtra(SearchResultsActivity.HOUSE_KEY, houseString);
                startActivity(characterSearchCriteriaIntent);
            }
        });
    }

    /**
     * Get the chosen search criteria from the spinners
     * @param spinner
     * @return
     */
    private String getSpinnerSelections(Spinner spinner) {
        TextView textView = (TextView) spinner.getSelectedView();
        String spinnerText = textView.getText().toString();
        return spinnerText;
    }

}
