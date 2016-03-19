package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/12/16.
 */
public class LocationActivity extends AppCompatActivity {

    TextView locationTitleText;
    TextView locationDescription;
    ImageView locationImage;
    EditText reviewEditText;
    MediaPlayer themeMediaPlayer;
    String characterNameText;
    boolean playIsOn = false;
    FloatingActionButton isLikedButton;
    Button reviewButton;
    ImageView likedIcon;
    public static final String REQUEST_CODE_FOR_TITLE = "characterNameForReviewActivity";
    public static final String REQUEST_CODE_FOR_COMMENT = "characterReviewForReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        instantiateItems();
        getCharacterDetails();
        setIcon();
        setIsLikedButton();
        setReviewButton();
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
        locationTitleText = (TextView) findViewById(R.id.locationTitleText);
        locationDescription = (TextView) findViewById(R.id.locationDescription);
        locationImage = (ImageView) findViewById(R.id.imageViewLocation);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        isLikedButton = (FloatingActionButton) findViewById(R.id.fabButton);
        likedIcon = (ImageView) findViewById(R.id.likedImage);
        reviewButton = (Button) findViewById(R.id.enterReviewButton);
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

    private void setIcon() {
        final int id = getIntent().getIntExtra("id", -1);
        if(id >= 0) {
            boolean isLiked = databaseHelper().getCharacterIsLikedBoolean(id);
            if (isLiked) {
                likedIcon.setVisibility(View.VISIBLE);
            } else {
                likedIcon.setVisibility(View.INVISIBLE);
            }
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
                        likedIcon.setVisibility(View.VISIBLE);
                    } else {
                        characterLiked = "You don't like " + characterNameText + " anymore";
                        Toast.makeText(LocationActivity.this , characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void setReviewButton() {
        if (reviewEditText.getText().toString() != null) {
            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createSharedPreferences();
                    Intent reviewIntent = new Intent(LocationActivity.this, ReviewActivity.class);
                    startActivity(reviewIntent);
                }
            });
        }
    }

    private void createSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LocationActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REQUEST_CODE_FOR_TITLE, characterNameText);
        String characterReview = reviewEditText.getText().toString();
        editor.putString(REQUEST_CODE_FOR_COMMENT, characterReview);
        editor.commit();
    }
}
