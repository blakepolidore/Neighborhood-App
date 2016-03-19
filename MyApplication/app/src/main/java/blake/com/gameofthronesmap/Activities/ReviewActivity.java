package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/19/16.
 */
public class ReviewActivity extends AppCompatActivity {

    TextView reviewTitleText;
    ListView reviewListView;
    ArrayList<String> reviewsArrayList;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        instantiateItems();
        setTitleString();
        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        reviewsArrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reviewsArrayList);
        if (getReviewPreferences() != null) {
            reviewsArrayList.add(getReviewPreferences());
            arrayAdapter.notifyDataSetChanged();
        }
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
        reviewListView = (ListView) findViewById(R.id.reviewsListView);
        reviewTitleText = (TextView) findViewById(R.id.reviewTitleText);
    }

    private String getTitlePreferences() {
        SharedPreferences getPreferences = PreferenceManager.getDefaultSharedPreferences(ReviewActivity.this);
        return getPreferences.getString(LocationActivity.REQUEST_CODE_FOR_TITLE, null);
    }

    private void setTitleString() {
        if (getTitlePreferences() != null) {
            reviewTitleText.setText(getTitlePreferences());
        }
    }

    private String getReviewPreferences() {
        SharedPreferences getPreferences = PreferenceManager.getDefaultSharedPreferences(ReviewActivity.this);
        return getPreferences.getString(LocationActivity.REQUEST_CODE_FOR_COMMENT, null);
    }

}
