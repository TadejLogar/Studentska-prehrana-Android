/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.feri.projekt.studentskaprehrana.db;

import java.util.List;

import com.sciget.studentmeals.database.data.RestaurantData;

import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.id;
import si.feri.projekt.studentskaprehrana.R.layout;
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
public class ProvidersArrayAdapter extends ArrayAdapter<RestaurantData> {
	LayoutInflater mInflater;
	
	public ProvidersArrayAdapter(Context context, int textViewResourceId, List<RestaurantData> objects) {
        super(context, textViewResourceId, objects);
	    mInflater = LayoutInflater.from(context);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    RestaurantData tmp = getItem(position);
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.provider_layout, null);
			// Creates a ViewHolder and store references to the two children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			// holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.fee = (TextView) convertView.findViewById(R.id.fee);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.openTime = (TextView) convertView.findViewById(R.id.openTime);			
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
		holder.name.setText(tmp.getName());
		holder.fee.setText(tmp.getEuroFee());
		holder.address.setText(tmp.getFullAddress());
		holder.openTime.setText(""); // tmp.getOpenTime()
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	static class ViewHolder {
		TextView name;
		TextView fee;
		TextView address;
		TextView openTime;
	}
}