package blake.com.gameofthronesmap.activities;

import android.content.Intent;
import android.os.Bundle;
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

import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.otherFiles.SongService;
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

    public static final String USER_COMMENT_ID_KEY = "userCommentID";

    TextView locationTitleText;
    TextView locationDescription;
    ImageView locationImage;
    EditText reviewEditText;
    String characterNameText;
    FloatingActionButton isLikedButton;
    Button reviewButton;
    ImageView likedIcon;
    MusicStateSingleton musicState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        instantiateItems();
        getCharacterDetails();
        setIcon();
        setIsLikedButton();
        setReviewButton();
        musicState = MusicStateSingleton.getInstance(); //Creates instance of the music state
    }

    /**
     * Creates menu at the top
     *
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
     *
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
     *
     * @return
     */
    private DatabaseHelper databaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(CharacterActivity.this);
        return databaseHelper;
    }

    /**
     * Gets character details and name from the database and shows them in the activity
     */
    private void getCharacterDetails() {
        int id = getIntent().getIntExtra("id", -1);
        if (id >= 0) {
            characterNameText = databaseHelper().getCharacterNameAndDescription(id)[0];
            TextView nameText = (TextView) findViewById(R.id.locationTitleText);
            if (!characterNameText.isEmpty()) {
                nameText.setText(characterNameText);
            }
            String characterDescriptionText = databaseHelper().getCharacterNameAndDescription(id)[1];
            TextView descriptionText = (TextView) findViewById(R.id.locationDescription);
            if (!characterDescriptionText.isEmpty()) {
                descriptionText.setText(characterDescriptionText);
            }
            int characterPicture = databaseHelper().getCharacterImage(id);
            ImageView characterImage = (ImageView) findViewById(R.id.imageViewLocation);
            if (characterPicture != -1) {
                characterImage.setBackgroundResource(characterPicture);
            }
        }
    }

    /**
     * Shows the favorited icon if the character is favorited
     */
    private void setIcon() {
        final int id = getIntent().getIntExtra("id", -1);
        if (id >= 0) {
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
        final int id = getIntent().getIntExtra(SearchResultsActivity.ID_INTENT_KEY, -1);
        isLikedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id >= 0) {
                    databaseHelper().changeIsLikedColumn(id);
                    boolean isLiked = databaseHelper().getCharacterIsLikedBoolean(id);
                    String characterLiked;
                    if (isLiked) {
                        characterLiked = "You liked " + characterNameText;
                        Toast.makeText(CharacterActivity.this, characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setVisibility(View.VISIBLE);
                    } else {
                        characterLiked = characterNameText + " is now swine to you!";
                        Toast.makeText(CharacterActivity.this, characterLiked, Toast.LENGTH_SHORT).show();
                        likedIcon.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    /**
     * Gets the users comments from the edit text and puts them in a string.
     * @return
     */
    private String userComment() {
        String inputText = reviewEditText.getText().toString();
        return inputText;
    }

    /**
     * Grabs the comments left by a user and adds them to the database. Then sends the user to the ReviewActivity
     */
    private void setReviewButton() {
        final int id = getIntent().getIntExtra(SearchResultsActivity.ID_INTENT_KEY, -1);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id >= 0) {
                    if (!(userComment().isEmpty())) {
                        databaseHelper().addReviewOfCharacter(id, userComment());
                    }
                    Intent goToReviewActivityIntent = new Intent(CharacterActivity.this, ReviewActivity.class);
                    goToReviewActivityIntent.putExtra(USER_COMMENT_ID_KEY, id);
                    startActivity(goToReviewActivityIntent);
                }
            }
        });

    }

}
