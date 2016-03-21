package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import blake.com.gameofthronesmap.OtherFiles.SongService;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/12/16.
 * <h1>Character's page</h1>
 * This activity is for each character. It gives a description of the character and a picture.
 * It also allows the user to favorite the character and leave a comment about the character.
 *
 * @author Blake
 * @version 1.0
 */
public class CharacterActivity extends AppCompatActivity {

    TextView locationTitleText;
    TextView locationDescription;
    ImageView locationImage;
    EditText reviewEditText;
    String characterNameText;
    boolean playIsOn;
    FloatingActionButton isLikedButton;
    Button reviewButton;
    ImageView likedIcon;
    public static final String REQUEST_CODE_FOR_TITLE = "characterNameForReviewActivity";
    public static final String REQUEST_CODE_FOR_COMMENT = "characterReviewForReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        instantiateItems();
        getCharacterDetails();
        setIcon();
        setIsLikedButton();
        setReviewButton();
        playIsOn= SongService.isPlayOn;

        locationTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(CharacterActivity.this, ReviewActivity.class);
                startActivity(reviewIntent);
            }
        });
    }

    /**
     * Creates menu at the top
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
                Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(infoIntent);
                return true;
            case R.id.musicActivity:
                if (playIsOn) {
                    stopService(new Intent(this, SongService.class));
                    playIsOn = false;
                } else {
                    startService(new Intent(this, SongService.class));
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

    /**
     * Creates instance of the database helper in this class
     * @return
     */
    private DatabaseHelper databaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(CharacterActivity.this);
        return databaseHelper;
    }

    /**
     * Gets character details and name and shows them in the activity
     */
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

    /**
     * Shows the favorited icon if the character is favorited
     */
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

    /**
     * Allows you to click on the fab and like or unlike a character. Liking a character stores them in your favorites
     */
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
                        Toast.makeText(CharacterActivity.this , characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setVisibility(View.VISIBLE);
                    } else {
                        characterLiked = "You don't like " + characterNameText + " anymore";
                        Toast.makeText(CharacterActivity.this , characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    /**
     * Grabs the comments left by a user and sends them to the ReviewActivity
     */
    private void setReviewButton() {
        if ((reviewEditText.getText().toString().isEmpty())) {
            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createSharedPreferences();
                    Intent reviewIntent = new Intent(CharacterActivity.this, ReviewActivity.class);
                    startActivity(reviewIntent);
                }
            });
        }
    }

    /**
     * Creates shared preferences so the user's comments can be left on the ReviewActivity
     */
    private void createSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CharacterActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REQUEST_CODE_FOR_TITLE, characterNameText);
        String characterReview = reviewEditText.getText().toString();
        editor.putString(REQUEST_CODE_FOR_COMMENT, characterReview);
        editor.apply();
    }
}
