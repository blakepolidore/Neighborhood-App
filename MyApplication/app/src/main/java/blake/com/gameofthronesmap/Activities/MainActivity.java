package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.R;

public class MainActivity extends AppCompatActivity {

    ImageButton musicButton;
    ImageButton infoButton;
    Button searchButton;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    private Intent infoIntent;
    private static final int SEARCH_RESULTS_REQUEST_CODE = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicButton = (ImageButton) findViewById(R.id.musicButton);
        infoButton = (ImageButton) findViewById(R.id.infoButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);

        playAudio();
        goToInfoActivity();
        toSearchResults();
        createSQLiteDatabaseHelper();
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

    private void toSearchResults() {
        final Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(searchResultsIntent, SEARCH_RESULTS_REQUEST_CODE);
            }
        });
    }

    private void createSQLiteDatabaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
        //Add Tyrell, Stannis
        databaseHelper.insert("Jon Snow", "Male", "Westeros", "Stark", getString(R.string.jonSnow), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Arya Stark", "Female", "Both", "Stark", getString(R.string.aryaStark), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Daenerys Targaryen", "Female", "Essos", "Targaryen", getString(R.string.danyTargaryen), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Jamie Lannister", "Male", "Westeros", "Lannister", getString(R.string.jaimeLannister), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Cersei Lannister", "Female", "Westeros", "Lannister", getString(R.string.cerseiLannister), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Oberyn Martell", "Male", "Westeros", "Martell", getString(R.string.oberynMartell), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Joffery Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.jofferyBaratheon), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Robert Baratheon", "Male", "Westeros", "Baratheon", getString(R.string.robertBaratheon), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Jorah Mormont", "Male", "Essos", "Mormont", getString(R.string.jorahMormont), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Tyrion Lannister", "Male", "Both", "Lannister", getString(R.string.tyrionLannister), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Petyr Baelish", "Male", "Westeros", "None", getString(R.string.petyrBaelish), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Lord Varys", "Male", "Both", "None", getString(R.string.varys), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Jeor Mormont", "Male", "Westeros", "Mormont", getString(R.string.jeorMormont), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Doran Martell", "Male", "Westeros", "Martell", getString(R.string.doranMartell), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Aegon Targaryen", "Male", "Westeros", "Targaryen", getString(R.string.aegonTargaryen), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Eddard Stark", "Male", "Westeros", "Stark", getString(R.string.eddardStark), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Lady Melissandre", "Female", "Westeros", "None", getString(R.string.melissandre), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Bran Stark", "Male", "Westeros", "Stark", getString(R.string.branStark), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Tywin Lannister", "Male", "Westeros", "Lannister", getString(R.string.tywinLannister), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Viserys Targaryen", "Male", "Essos", "Targaryen", getString(R.string.viserysTargaryen), false, R.drawable.got, R.drawable.got);
        databaseHelper.insert("Jaqen H'ghar", "Male", "Both", "None", getString(R.string.jaqenHghar), false, R.drawable.got, R.drawable.got);

    }
}
