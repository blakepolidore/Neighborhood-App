package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.OtherFiles.SongService;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/18/16.
 * <h1>Favorite Character Page</h1>
 * Same as Character Activity but without ability to favorite the character since it has already been favorited.
 */
public class FavoriteCharacterActivity extends AppCompatActivity {

    TextView locationTitleTextFavorite;
    TextView locationDescriptionFavorite;
    ImageView locationImageFavorite;
    EditText reviewEditTextFavorite;
    String characterNameText;
    boolean playIsOn;
    ImageView likedIcon;
    Button enterCommentFromFavoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_item);

        playIsOn = SongService.isPlayOn;
        instantiateItems();
        getCharacterDetails();
        setIcon();
        setReviewButton();
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
        locationTitleTextFavorite = (TextView) findViewById(R.id.locationTitleTextFavorite);
        locationDescriptionFavorite = (TextView) findViewById(R.id.locationDescriptionFavorite);
        locationImageFavorite = (ImageView) findViewById(R.id.imageViewLocationFavorite);
        reviewEditTextFavorite = (EditText) findViewById(R.id.reviewEditTextFavorite);
        likedIcon = (ImageView) findViewById(R.id.likedImageFavorite);
        enterCommentFromFavoritesButton = (Button) findViewById(R.id.enterReviewButtonFavorite);
    }

    /**
     * Creates instance of the database helper in this class
     * @return
     */
    private DatabaseHelper databaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(FavoriteCharacterActivity.this);
        return databaseHelper;
    }

    /**
     * Gets character details and shows them in the activity
     */
    private void getCharacterDetails() {
        int id = getIntent().getIntExtra("idFavorite", -1);
        if(id >= 0){
            characterNameText = databaseHelper().getCharacterStringDetails(id)[0];
            TextView nameText = (TextView)findViewById(R.id.locationTitleTextFavorite);
            nameText.setText(characterNameText);
            String characterDescriptionText = databaseHelper().getCharacterStringDetails(id)[1];
            TextView descriptionText = (TextView)findViewById(R.id.locationDescriptionFavorite);
            descriptionText.setText(characterDescriptionText);
            int characterPicture = databaseHelper().getCharacterImage(id);
            ImageView characterImage = (ImageView)findViewById(R.id.imageViewLocationFavorite);
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

    private String userComment() {
        String inputText = reviewEditTextFavorite.getText().toString();
        return inputText;
    }

    /**
     * Grabs the comments left by a user and adds them to the database. Then sends the user to the ReviewActivity
     */
    private void setReviewButton() {
        final int id = getIntent().getIntExtra("idFavorite", -1);
        enterCommentFromFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id >= 0) {
                    if (!(userComment().isEmpty())) {
                        databaseHelper().addReviewOfCharacter(id, userComment());
                    }
                    Intent goToReviewActivityIntent = new Intent(FavoriteCharacterActivity.this, ReviewActivity.class);
                    goToReviewActivityIntent.putExtra(CharacterActivity.REQUEST_CODE_FOR_TITLE, characterNameText);
                    goToReviewActivityIntent.putExtra("id2", id);
                    startActivity(goToReviewActivityIntent);
                }
            }
        });

    }
}
