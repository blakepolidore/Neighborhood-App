package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.R;

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
    String characterNameText;
    boolean playIsOn = false;
    private Intent infoIntent;
    FloatingActionButton isLikedButton;
    ImageView likedIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        instantiateItems();
        playAudio();
        goToInfoActivity();
        getCharacterDetails();
        setIsLikedButton();
    }

    private void instantiateItems() {
        musicButton3 = (ImageButton) findViewById(R.id.musicButton3);
        infoButton3 = (ImageButton) findViewById(R.id.infoButton3);
        locationTitleText = (TextView) findViewById(R.id.locationTitleText);
        locationDescription = (TextView) findViewById(R.id.locationDescription);
        locationImage = (ImageView) findViewById(R.id.imageViewLocation);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        isLikedButton = (FloatingActionButton) findViewById(R.id.fabButton);
        likedIcon = (ImageView) findViewById(R.id.likedImage);
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

    private DatabaseHelper databaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(LocationActivity.this);
        return databaseHelper;
    }

    private void getCharacterDetails() {
        int id = getIntent().getIntExtra("id", -1);
        if(id >= 0){
            characterNameText = databaseHelper().getCharacterStringDetails(id)[0];
            TextView nameText = (TextView)findViewById(R.id.locationTitleText);
            nameText.setText(characterNameText);
            String characterDescriptionText = databaseHelper().getCharacterStringDetails(id)[1];
            TextView descriptionText = (TextView)findViewById(R.id.locationDescription);
            descriptionText.setText(characterDescriptionText);
            int characterPicture = databaseHelper().getCharacterImage(id);
            ImageView characterImage = (ImageView)findViewById(R.id.imageViewLocation);
            characterImage.setBackgroundResource(characterPicture);
        }
    }

    private void setIsLikedButton() {
        final int id = getIntent().getIntExtra("id", -1);
        isLikedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id >= 0) {
                    databaseHelper().changeIsLikedColumn(id);
                    boolean isLiked = databaseHelper().getCharacterIsLikedBoolean(id);
                    String characterLiked;
                    if (isLiked) {
                        characterLiked = "You liked " + characterNameText;
                        Toast.makeText(LocationActivity.this , characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setImageAlpha(1);
                        likedIcon.startAnimation(AnimationUtils.loadAnimation(LocationActivity.this, android.R.anim.fade_in));
                    } else {
                        characterLiked = "You don't like " + characterNameText + " anymore";
                        Toast.makeText(LocationActivity.this , characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setImageAlpha(0);
                        likedIcon.startAnimation(AnimationUtils.loadAnimation(LocationActivity.this, android.R.anim.fade_out));
                    }
                }
            }
        });
    }
}
