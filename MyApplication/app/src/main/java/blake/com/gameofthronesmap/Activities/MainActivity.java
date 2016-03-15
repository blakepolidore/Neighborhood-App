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
        createSQLiteDatabase();
        toSearchResults();
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

    private void createSQLiteDatabase() {
        DatabaseHelper database = new DatabaseHelper(this);

        database.insert(1, "Jon Snow", "Male", "Westeros", "Stark");
        database.insert(2, "Arya Stark", "Female", "Both", "Stark");
        database.insert(3, "Daenerys Targaryen", "Female", "Essos", "Targaryen");
        database.insert(4, "Jamie Lannister", "Male", "Westeros", "Lannister");
        database.insert(5, "Cersei Lannister", "Female", "Westeros", "Lannister");
        database.insert(6, "Oberyn Martell", "Male", "Westeros", "Martell");
        database.insert(7, "Joffery Baratheon", "Male", "Westeros", "Baratheon");
        database.insert(8, "Robert Baratheon", "Male", "Westeros", "Baratheon");
        database.insert(9, "Jorah Mormont", "Male", "Essos", "Mormont");
        database.insert(10, "Tyrion Lannister", "Male", "Both", "Lannister");
        database.insert(11, "Petyr Baelish", "Male", "Westeros", "None");
        database.insert(12, "Lord Varys", "Male", "Both", "None");
        database.insert(13, "Jeor Mormont", "Male", "Westeros", "Mormont");
        database.insert(14, "Doran Martell", "Male", "Westeros", "Martell");
        database.insert(15, "Aegon Targaryen", "Male", "Westeros", "Targaryen");
        database.insert(16, "Eddard Stark", "Male", "Westeros", "Stark");
        database.insert(17, "Lady Melissandre", "Female", "Westeros", "None");
        database.insert(18, "Bran Stark", "Male", "Westeros", "Stark");
        database.insert(19, "Tywin Lannister", "Male", "Westeros", "Lannister");
        database.insert(20, "Viserys Targaryen", "Male", "Essos", "Targaryen");
        database.insert(21, "Jaqen H'ghar", "Male", "Both", "None");
    }

    private void toSearchResults() {
        final Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(searchResultsIntent,SEARCH_RESULTS_REQUEST_CODE);
            }
        });
    }
}
