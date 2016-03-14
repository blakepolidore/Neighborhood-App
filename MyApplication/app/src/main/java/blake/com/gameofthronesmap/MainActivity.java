package blake.com.gameofthronesmap;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton musicButton;
    ImageButton infoButton;
    Button searchButton;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    private Intent infoIntent;

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
}
