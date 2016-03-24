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

import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.otherFiles.SongService;
import blake.com.gameofthronesmap.R;

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
     * Creates the database and fills it with the characters.
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
            databaseHelper.insert("Jon Snow", "Male", "Westeros", "Stark", getString(R.string.jonSnow), false, R.drawable.jon_snow, "Brooding Badass", "");
            databaseHelper.insert("Arya Stark", "Female", "Essos", "Stark", getString(R.string.aryaStark), false, R.drawable.arya, "Badass with a needle", "");
            databaseHelper.insert("Daenerys Targaryen", "Female", "Essos", "Targaryen", getString(R.string.danyTargaryen), false, R.drawable.daenerys, "Badass with Dragons", "");
            databaseHelper.insert("Jamie Lannister", "Male", "Westeros", "Lannister", getString(R.string.jaimeLannister), false, R.drawable.jaime, "Recent Badass", "");
            databaseHelper.insert("Cersei Lannister", "Female", "Westeros", "Lannister", getString(R.string.cerseiLannister), false, R.drawable.cersei, "She's the worst", "");
            databaseHelper.insert("Oberyn Martell", "Male", "Westeros", "Martell", getString(R.string.oberynMartell), false, R.drawable.oberyn, "Most Badass", "");
            databaseHelper.insert("Joffery Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.jofferyBaratheon), false, R.drawable.joffrey, "Worst Person Ever", "");
            databaseHelper.insert("Robert Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.robertBaratheon), false, R.drawable.robert, "Was a Badass", "");
            databaseHelper.insert("Jorah Mormont", "Male", "Essos", "Mormont", getString(R.string.jorahMormont), false, R.drawable.jorah, "Badass Voice", "");
            databaseHelper.insert("Tyrion Lannister", "Male", "Essos", "Lannister", getString(R.string.tyrionLannister), false, R.drawable.tyrion_crossbow, "Mega Badass", "");
            databaseHelper.insert("Petyr Baelish", "Male", "Westeros", "None", getString(R.string.petyrBaelish), false, R.drawable.baelish, "Sneaky Badass", "");
            databaseHelper.insert("Lord Varys", "Male", "Essos", "None", getString(R.string.varys), false, R.drawable.varys, "Eunuch Badass", "");
            databaseHelper.insert("Jeor Mormont", "Male", "Westeros", "Mormont", getString(R.string.jeorMormont), false, R.drawable.jeor, "Badass-ish", "");
            databaseHelper.insert("Doran Martell", "Male", "Westeros", "Martell", getString(R.string.doranMartell), false, R.drawable.doran, "Ehh", "");
            databaseHelper.insert("Aegon Targaryen", "Male", "Westeros", "Targaryen", getString(R.string.aegonTargaryen), false, R.drawable.aegon, "Original Badass", "");
            databaseHelper.insert("Eddard Stark", "Male", "Westeros", "Stark", getString(R.string.eddardStark), false, R.drawable.eddard, "Loyal Badass", "");
            databaseHelper.insert("Lady Melissandre", "Female", "Westeros", "None", getString(R.string.melissandre), false, R.drawable.melissandre, "Black Magic Badass", "");
            databaseHelper.insert("Bran Stark", "Male", "Westeros", "Stark", getString(R.string.branStark), false, R.drawable.bran, "Maybe a Badass", "");
            databaseHelper.insert("Tywin Lannister", "Male", "Westeros", "Lannister", getString(R.string.tywinLannister), false, R.drawable.tywin, "Disliked Badass", "");
            databaseHelper.insert("Viserys Targaryen", "Male", "Essos", "Targaryen", getString(R.string.viserysTargaryen), false, R.drawable.viserys, "Sucks", "");
            databaseHelper.insert("Jaqen H'ghar", "Male", "Essos", "None", getString(R.string.jaqenHghar), false, R.drawable.jaqen, "Many-faced Badass", "");
            databaseHelper.insert("Margaery Tyrell", "Female", "Westeros", "Tyrell", getString(R.string.margaery), false, R.drawable.margaery, "Badass Queen", "");
            databaseHelper.insert("Loras Tyrell", "Male", "Westeros", "Tyrell", getString(R.string.loras), false, R.drawable.loras, "Flowery Badass", "");
            databaseHelper.insert("Stannis Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.stannis), false, R.drawable.stannis, "Stern Badass", "");
            databaseHelper.insert("Sansa Stark", "Female", "Westeros", "Stark", getString(R.string.sansa), false, R.drawable.sansa, "Unfortunate Badass", "");
            databaseHelper.insert("Ramsay Snow/Bolton", "Male", "Westeros", "Bolton", getString(R.string.ramsay), false, R.drawable.ramsay, "Pure Evil", "");
            databaseHelper.insert("Roose Bolton", "Male", "Westeros", "Bolton", getString(R.string.roose), false, R.drawable.roose, "Backstabber", "");
            databaseHelper.insert("Davos Seaworth", "Male", "Westeros", "None", getString(R.string.davos), false, R.drawable.davos, "Captain Badass", "");
            databaseHelper.insert("Robb Stark", "Male", "Westeros", "Stark", getString(R.string.robb), false, R.drawable.robb, "Northern Badass", "");
            databaseHelper.insert("Bronn", "Male", "Westeros", "None", getString(R.string.bronn), false, R.drawable.bronn, "Sellsword Badass", "");
            databaseHelper.insert("Briene of Tarth", "Female", "Westeros", "None", getString(R.string.brienne), false, R.drawable.brienne, "Badass Knight", "");
            databaseHelper.insert("Hodor", "Male", "Westeros", "Stark", getString(R.string.hodor), false, R.drawable.hodor, "Badass Hodor", "");
            databaseHelper.insert("Khal Drogo", "Male", "Essos", "None", getString(R.string.khal), false, R.drawable.khal, "Badass Horselord", "");
            databaseHelper.insert("Olenna Tyrell", "Female", "Westeros", "Tyrell", getString(R.string.olenna), false, R.drawable.olenna, "Badass Grandma", "");
            databaseHelper.insert("Tommen Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.tommen), false, R.drawable.tommen, "Chode", "");
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
