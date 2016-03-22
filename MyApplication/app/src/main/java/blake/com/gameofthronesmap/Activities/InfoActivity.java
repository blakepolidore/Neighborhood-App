package blake.com.gameofthronesmap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import blake.com.gameofthronesmap.OtherFiles.MusicStateSingleton;
import blake.com.gameofthronesmap.OtherFiles.SongService;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/13/16.
 * <h1>Info Activity</h1>
 * Explains to user how to use the app
 */
public class InfoActivity extends AppCompatActivity {

    MusicStateSingleton musicState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        musicState = MusicStateSingleton.getInstance();
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

}
