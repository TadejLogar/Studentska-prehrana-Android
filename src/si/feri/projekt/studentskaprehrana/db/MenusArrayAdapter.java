package si.feri.projekt.studentskaprehrana.db;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sciget.studentmeals.database.data.RestaurantMenuData;

import si.feri.projekt.studentskaprehrana.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author Neo101L
 */
@Deprecated
public class MenusArrayAdapter extends ArrayAdapter<RestaurantMenuData> {
	LayoutInflater mInflater;
    private int blueColor = Color.parseColor("#008BCC");
    private int greenColor = Color.parseColor("#6FBF44");
    private Date todayDate;
	
	public MenusArrayAdapter(Context context, int textViewResourceId, List<RestaurantMenuData> objects) {
        super(context, textViewResourceId, objects);
	    mInflater = LayoutInflater.from(context);
	    todayDate = new Date(System.currentTimeMillis());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    RestaurantMenuData tmp = getItem(position);
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.menu_layout, null);
			// Creates a ViewHolder and store references to the two children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.colorTag = (LinearLayout) convertView.findViewById(R.id.linearLayoutColorTag);
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
		
		if (tmp.date == null) {
		    holder.colorTag.setBackgroundColor(blueColor);
		} else if (areEqual(tmp.date, todayDate)) {
		    holder.colorTag.setBackgroundColor(greenColor );
		} else {
		    holder.colorTag.setBackgroundColor(Color.GRAY);
		}
		
		// Bind the data efficiently with the holder.
		holder.food1.setText(tmp.getFood1());
		holder.food2.setText(tmp.getFood2().replace("[", "").replace("\",\"", ", ").replace("]", "").replace("\"", ""));
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	static class ViewHolder {
		TextView food1;
		TextView food2;
		LinearLayout colorTag; // linearLayoutColorTag
	}
	
	public static boolean areEqual(Date date1, Date date2) {
	    Calendar d1 = Calendar.getInstance();
	    d1.setTimeInMillis(date1.getTime());
	    
        Calendar d2 = Calendar.getInstance();
        d2.setTimeInMillis(date2.getTime());
        
        return d1.get(Calendar.DAY_OF_MONTH) == d2.get(Calendar.DAY_OF_MONTH) && d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH) && d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR);        
        //return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.get() == date2.getDay();
	}
}