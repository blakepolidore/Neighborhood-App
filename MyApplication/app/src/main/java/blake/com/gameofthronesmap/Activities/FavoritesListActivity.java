package blake.com.gameofthronesmap.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import blake.com.gameofthronesmap.OtherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/18/16.
 */
public class FavoritesListActivity extends AppCompatActivity{

    ImageButton musicButtonFavorite1;
    ImageButton infoButtonFavorite1;
    TextView searchResultsTextView;
    ListView searchResultsListView;
    MediaPlayer themeMediaPlayer;
    boolean playIsOn = false;
    private Intent infoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        instantiateItems();
        playAudio();
        goToInfoActivity();
        createCursorAdapterForSearchList(cursorForFavorites());
    }

    private void instantiateItems() {
        musicButtonFavorite1 = (ImageButton) findViewById(R.id.musicButtonFavorite1);
        infoButtonFavorite1 = (ImageButton) findViewById(R.id.infoButtonFavorite1);
        searchResultsTextView = (TextView) findViewById(R.id.searchResultsTextFavorite1);
        searchResultsListView = (ListView) findViewById(R.id.listViewFavorite);
    }

    private void playAudio() {
        musicButtonFavorite1.setOnClickListener(new View.OnClickListener() {
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
        infoButtonFavorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(infoIntent);
            }
        });
    }

    private SQLiteDatabase getDatabaseForFavorites() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(FavoritesListActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db;
    }

    private Cursor cursorForFavorites() {
        String queryString =  "SELECT * FROM "+DatabaseHelper.CHARACTERS_TABLE_NAME+
                " WHERE "+DatabaseHelper.COL_ISLIKED+" LIKE "+1+";";
        Cursor cursor = getDatabaseForFavorites().rawQuery(queryString, null);
        return cursor;
    }

    private void createCursorAdapterForSearchList(Cursor cursor) {
        CursorAdapter cursorAdapterForSearchList = new CursorAdapter(FavoritesListActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nameText = (TextView) view.findViewById(R.id.characterNameText);
                TextView badassText = (TextView) view.findViewById(R.id.badassText);
                ImageView iconImage = (ImageView) view.findViewById(R.id.iconImageView);

                nameText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                badassText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BADASS)));
                int drawableID= getDrawableValue(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOUSE)));
                //int drawableID= cursor.getColumnIndex(DatabaseHelper.COL_ICON_IMAGE);
                iconImage.setBackgroundResource(drawableID);
            }
        };
        ListView listView = (ListView) findViewById(R.id.listViewFavorite);
        listView.setAdapter(cursorAdapterForSearchList);
    }
    //MAKE BETTER PICTURES, EXACT SQUARES!!!!
    private int getDrawableValue(String house){
        switch(house){
            case "Targaryen":
                return R.drawable.targaryen;
            case "Stark":
                return R.drawable.stark;
            case "Lannister":
                return R.drawable.lannister;
            case "Martell":
                return R.drawable.martell;
            case "Mormont":
                return R.drawable.mormont;
            case "Baratheon":
                return R.drawable.baratheon;
            case "None":
                return R.drawable.none;
            default:
                return 0;
        }
    }

}
