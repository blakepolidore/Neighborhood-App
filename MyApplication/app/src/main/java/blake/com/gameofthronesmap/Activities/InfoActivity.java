package blake.com.gameofthronesmap.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/13/16.
 * <h1>Info Activity</h1>
 * Explains to user how to use the app
 */
public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
    }
}
