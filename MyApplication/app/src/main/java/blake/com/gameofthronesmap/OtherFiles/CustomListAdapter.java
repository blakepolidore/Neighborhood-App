package blake.com.gameofthronesmap.OtherFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/14/16.
 */
public class CustomListAdapter extends ArrayAdapter<GOTCharacter> {

    List<GOTCharacter> charactersList;

    public CustomListAdapter(Context context, ArrayList<GOTCharacter> objects) {
        super(context, -1, objects);
        this.charactersList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        TextView nameText = (TextView) rowItem.findViewById(R.id.nameText);
        TextView houseText = (TextView) rowItem.findViewById(R.id.houseText);
        ImageView imageView = (ImageView) rowItem.findViewById(R.id.imageView);
        GOTCharacter gotCharacter = charactersList.get(position);
        nameText.setText(gotCharacter.name);
        houseText.setText(gotCharacter.house);
        return rowItem;
    }
}
