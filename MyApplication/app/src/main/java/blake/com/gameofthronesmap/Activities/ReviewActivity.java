package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.OtherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.OtherFiles.SongService;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/19/16.
 */
public class ReviewActivity extends AppCompatActivity {

    TextView reviewTitleText;
    ListView reviewListView;
    ArrayList<String> reviewsArrayList;
    MusicStateSingleton musicState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        instantiateItems();
        musicState = MusicStateSingleton.getInstance();
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

    private DatabaseHelper databaseHelper() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ReviewActivity.this);
        return databaseHelper;
    }

    /**
     * Gets the reviews left by users of the characters and displays them
     */
    private void getCharacterReviews() {
        final int id = getIntent().getIntExtra("id2", -1);
        if (id >= 0) {
            String userComments = databaseHelper().getUsersReview(id);
            String[] arrayOfComments = userComments.split("aintNuthingButAGThang297");
            reviewTitleText.setText(databaseHelper().getCharacterNameAndDescription(id)[0]);
            if (userComments != null) {
                for (int i = 0; i < arrayOfComments.length; i++) {
                    reviewsArrayList.add(arrayOfComments[i]);
                }
            }
        }
    }

}
