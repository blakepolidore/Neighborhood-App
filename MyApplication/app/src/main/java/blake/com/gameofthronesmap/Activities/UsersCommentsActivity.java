package blake.com.gameofthronesmap.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.otherFiles.SongService;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/19/16.
 * <h1>User Comments</h1>
 * Shows the users comments and when the user made the comments for the selected character.
 */
public class UsersCommentsActivity extends AppCompatActivity {

    //region Private Variables
    private TextView reviewTitleText;
    private ListView reviewListView;
    private ArrayList<String> reviewsArrayList;
    private MusicStateSingleton musicState;
    //endregion Private Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        instantiateItems();
        setFontText();
        musicState = MusicStateSingleton.getInstance(); //Creates instance of the music state
        reviewsArrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reviewsArrayList);
        getCharacterReviews();
        arrayAdapter.notifyDataSetChanged();
        reviewListView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Creates menu bar for the activity
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
        reviewListView = (ListView) findViewById(R.id.reviewsListView);
        reviewTitleText = (TextView) findViewById(R.id.reviewTitleText);
    }

    /**
     * Sets the character's name text to a special font
     */
    private void setFontText() {
        Typeface gotFont = Typeface.createFromAsset(getAssets(), "got_font.ttf");
        reviewTitleText.setTypeface(gotFont);
    }

    /**
     * Creates instance of the databasehelper
     * @return
     */
    private DatabaseHelper databaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(UsersCommentsActivity.this);
        return databaseHelper;
    }

    /**
     * Gets the comments left by users of the characters and displays them
     */
    private void getCharacterReviews() {
        final int id = getIntent().getIntExtra(CharacterActivity.USER_COMMENT_ID_KEY, -1);
        if (id >= 0) {
            String userComments = databaseHelper().getUsersReview(id);
            String[] arrayOfComments = userComments.split("aintNuthingButAGThang297"); //Unique string to split user comments string by. The array holds all the user comments
            reviewTitleText.setText(databaseHelper().getCharacterNameAndDescription(id)[0]);
            if (userComments != null) {
                for (int i = 0; i < arrayOfComments.length; i++) {
                    reviewsArrayList.add(arrayOfComments[i]); //Puts all comments into the list to be displayed
                }
            }
        }
    }

}
