package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.R;

public class MainActivity extends AppCompatActivity {

    ImageButton musicButton;
    ImageButton infoButton;
    Button searchButton;
    MediaPlayer themeMediaPlayer;
    Spinner continentSpinner;
    Spinner sexSpinner;
    Spinner houseSpinner;
    boolean playIsOn = false;
    private Intent infoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        intstantiateItems();
        fillSpinners();
        playAudio();
        goToInfoActivity();
        createSQLiteDatabaseHelper();
        toSearchResults();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void intstantiateItems() {
        musicButton = (ImageButton) findViewById(R.id.musicButton);
        infoButton = (ImageButton) findViewById(R.id.infoButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        continentSpinner = (Spinner) findViewById(R.id.continentSpinner);
        sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        houseSpinner = (Spinner) findViewById(R.id.houseSpinner);
    }

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

    private void playAudio() {
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeMediaPlayer.start();
                if (playIsOn) {
                    themeMediaPlayer.pause();
                    playIsOn = false;
                } else {
                    themeMediaPlayer.start();
                    playIsOn = true;
                }
            }
        });
    }

    private void goToInfoActivity() {
        infoIntent = new Intent(this, InfoActivity.class);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(infoIntent);
            }
        });
    }

    private void createSQLiteDatabaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
        //Add Tyrell, Stannis
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DatabaseHelper.CHARACTERS_TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount<=0) {
            databaseHelper.insert("Jon Snow", "Male", "Westeros", "Stark", getString(R.string.jonSnow), false, R.drawable.got, "Brooding Badass");
            databaseHelper.insert("Arya Stark", "Female", "Essos", "Stark", getString(R.string.aryaStark), false, R.drawable.got, "Badass with a needle");
            databaseHelper.insert("Daenerys Targaryen", "Female", "Essos", "Targaryen", getString(R.string.danyTargaryen), false, R.drawable.got, "Badass with Dragons");
            databaseHelper.insert("Jamie Lannister", "Male", "Westeros", "Lannister", getString(R.string.jaimeLannister), false, R.drawable.got, "Recent Badass");
            databaseHelper.insert("Cersei Lannister", "Female", "Westeros", "Lannister", getString(R.string.cerseiLannister), false, R.drawable.got, "She's the worst");
            databaseHelper.insert("Oberyn Martell", "Male", "Westeros", "Martell", getString(R.string.oberynMartell), false, R.drawable.got, "Most Badass");
            databaseHelper.insert("Joffery Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.jofferyBaratheon), false, R.drawable.got, "Worst Person Ever");
            databaseHelper.insert("Robert Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.robertBaratheon), false, R.drawable.got, "Was a Badass");
            databaseHelper.insert("Jorah Mormont", "Male", "Essos", "Mormont", getString(R.string.jorahMormont), false, R.drawable.got, "Badass Voice");
            databaseHelper.insert("Tyrion Lannister", "Male", "Essos", "Lannister", getString(R.string.tyrionLannister), false, R.drawable.got, "Mega Badass");
            databaseHelper.insert("Petyr Baelish", "Male", "Westeros", "None", getString(R.string.petyrBaelish), false, R.drawable.got, "Sneaky Badass");
            databaseHelper.insert("Lord Varys", "Male", "Essos", "None", getString(R.string.varys), false, R.drawable.got, "Unich Badass");
            databaseHelper.insert("Jeor Mormont", "Male", "Westeros", "Mormont", getString(R.string.jeorMormont), false, R.drawable.got, "Badass-ish");
            databaseHelper.insert("Doran Martell", "Male", "Westeros", "Martell", getString(R.string.doranMartell), false, R.drawable.got, "Ehh");
            databaseHelper.insert("Aegon Targaryen", "Male", "Westeros", "Targaryen", getString(R.string.aegonTargaryen), false, R.drawable.got, "Original Badass");
            databaseHelper.insert("Eddard Stark", "Male", "Westeros", "Stark", getString(R.string.eddardStark), false, R.drawable.got, "Loyal Badass");
            databaseHelper.insert("Lady Melissandre", "Female", "Westeros", "None", getString(R.string.melissandre), false, R.drawable.got, "Black Magic Badass");
            databaseHelper.insert("Bran Stark", "Male", "Westeros", "Stark", getString(R.string.branStark), false, R.drawable.got, "Maybe a Badass");
            databaseHelper.insert("Tywin Lannister", "Male", "Westeros", "Lannister", getString(R.string.tywinLannister), false, R.drawable.got, "Disliked Badass");
            databaseHelper.insert("Viserys Targaryen", "Male", "Essos", "Targaryen", getString(R.string.viserysTargaryen), false, R.drawable.got, "Sucks");
            databaseHelper.insert("Jaqen H'ghar", "Male", "Essos", "None", getString(R.string.jaqenHghar), false, R.drawable.got, "Many-faced Badass");
        }
    }

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
    /*
    Put in spinners and receive selection text from each spinner
     */
    private String getSpinnerSelections(Spinner spinner) {
        TextView textView = (TextView) spinner.getSelectedView();
        String spinnerText = textView.getText().toString();
        return spinnerText;
    }
}
