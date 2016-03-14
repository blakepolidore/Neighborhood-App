package blake.com.gameofthronesmap;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Raiders on 3/12/16.
 */
public class LocationActivity extends AppCompatActivity {

    ImageButton musicButton3;
    ImageButton infoButton3;
    TextView locationTitleText;
    TextView locationDescription;
    ImageView locationImage;
    EditText reviewEditText;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    private Intent infoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        musicButton3 = (ImageButton) findViewById(R.id.musicButton3);
        infoButton3 = (ImageButton) findViewById(R.id.infoButton3);
        locationTitleText = (TextView) findViewById(R.id.locationTitleText);
        locationDescription = (TextView) findViewById(R.id.locationDescription);
        locationImage = (ImageView) findViewById(R.id.imageViewLocation);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);

        playAudio();
        goToInfoActivity();
    }

    private void playAudio() {
        musicButton3.setOnClickListener(new View.OnClickListener() {
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
        infoButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(infoIntent);
            }
        });
    }
}
