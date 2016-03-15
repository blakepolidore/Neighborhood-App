package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/12/16.
 */
public class SearchResultsActivity extends AppCompatActivity {

    ImageButton musicButton2;
    ImageButton infoButton2;
    TextView searchResultsTextView;
    ListView searchResultsListView;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    private Intent infoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        musicButton2 = (ImageButton) findViewById(R.id.musicButton2);
        infoButton2 = (ImageButton) findViewById(R.id.infoButton2);
        searchResultsTextView = (TextView) findViewById(R.id.searchResultsText);
        searchResultsListView = (ListView) findViewById(R.id.listView);
        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);

        playAudio();
        goToInfoActivity();
    }

    private void playAudio() {
        musicButton2.setOnClickListener(new View.OnClickListener() {
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
        infoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(infoIntent);
            }
        });
    }
}
