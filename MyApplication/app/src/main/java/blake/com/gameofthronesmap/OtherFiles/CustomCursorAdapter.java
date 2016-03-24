package blake.com.gameofthronesmap.otherFiles;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/24/16.
 * Creates a custom cursor adapter for both the SearchResultsActivity and the FavoritesListActivity.
 *
 */
public class CustomCursorAdapter {

    /**
     * Custom cursor adapter for list view.
     * Takes in cursor and sets the appropriate fields in the list based off the cursor
     * @param context
     * @param cursor
     * @return custom cursor adapter
     */
    public static CursorAdapter getCustomCursorAdapter(Context context, Cursor cursor) {
        CursorAdapter cursorAdapter = new CursorAdapter(context, cursor, 0) {
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
                iconImage.setBackgroundResource(drawableID);
            }
        };

        return cursorAdapter;
    }

    /**
     * Gets icon for listview depending on the house of the favorited characters
     * @param house
     * @return
     */
    private static int getDrawableValue(String house) {
        switch (house) {
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
            case "Tyrell":
                return R.drawable.tyrell;
            case "Bolton":
                return R.drawable.bolton;
            case "Greyjoy":
                return R.drawable.greyjoy;
            case "None":
                return R.drawable.none;
            default:
                return 0;
        }
    }
}
