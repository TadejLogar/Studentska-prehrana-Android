package si.feri.projekt.studentskaprehrana.db;

import java.util.List;

import com.sciget.studentmeals.database.data.Data;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;

import si.feri.projekt.studentskaprehrana.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 *
 * @author Neo101L
 */
@Deprecated
public class HistoryArrayAdapter extends ArrayAdapter<StudentMealHistoryData> {
	LayoutInflater mInflater;
	
	public HistoryArrayAdapter(Context context, int textViewResourceId, List<StudentMealHistoryData> objects) {
        super(context, textViewResourceId, objects);
	    mInflater = LayoutInflater.from(context);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    StudentMealHistoryData tmp = getItem(position);
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.menu_layout, null);
			// Creates a ViewHolder and store references to the two children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.food1 = (TextView) convertView.findViewById(R.id.food1);
			holder.food2 = (TextView) convertView.findViewById(R.id.food2);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (position % 2 == 0) {
			convertView.setBackgroundColor(Color.rgb(40, 40, 41));
		} else {
			convertView.setBackgroundColor(Color.rgb(30, 30, 31));
		}
		
		// Bind the data efficiently with the holder.
		holder.food1.setText(Data.toString(tmp.time) + ": " + tmp.provider);
		holder.food2.setText(tmp.fee + " €");
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	static class ViewHolder {
		TextView food1;
		TextView food2;
	}
}