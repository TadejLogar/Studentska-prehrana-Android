/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.feri.projekt.studentskaprehrana.db;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.helper.Helper;

import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.id;
import si.feri.projekt.studentskaprehrana.R.layout;
import si.feri.projekt.studentskaprehrana.activity.RestaurantsListActivity2;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 * @author Neo101L
 */
public class ProvidersArrayAdapter extends ArrayAdapter<RestaurantData> {
	LayoutInflater mInflater;
	private static final int NICE_BLUE = Color.rgb(0, 148, 255);
    private ArrayList<RestaurantData> list;
    private RestaurantsListActivity2 activity;
	
	public ProvidersArrayAdapter(Context context, int textViewResourceId, ArrayList<RestaurantData> objects, RestaurantsListActivity2 activity) {
        super(context, textViewResourceId, objects);
	    this.mInflater = LayoutInflater.from(context);
	    this.activity = activity;
	    this.list = objects;
	}
	
	public ArrayList<RestaurantData> getList() {
	    return list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    RestaurantData tmp = getItem(position);
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.provider_layout2, null);
			// Creates a ViewHolder and store references to the two children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			// holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.fee = (TextView) convertView.findViewById(R.id.fee);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.distance = (TextView) convertView.findViewById(R.id.distance);
			if (activity.getType() != RestaurantsListActivity2.Type.NEAR) {
			    holder.distance.setVisibility(View.GONE);
			}
			holder.icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
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
		
		String time = tmp.getOpenTime();
		if (time == null) {
		    time = "";
		} else {
		    time = " (" + time + ")";
		}
		
		String distanceStr = null;
		if (activity.getType() == RestaurantsListActivity2.Type.NEAR) {
    		distanceStr = Helper.distanceInMeters(tmp.getDistance());
		}
		
        if (activity.getType() == RestaurantsListActivity2.Type.NEAR) {
            holder.distance.setVisibility(View.VISIBLE);
        } else {
            holder.distance.setVisibility(View.GONE);
        }
		
		if (!tmp.isOpen()) {
		    holder.name.setTextColor(Color.GRAY);
		} else {
		    holder.name.setTextColor(NICE_BLUE);
		}
		
		// Bind the data efficiently with the holder.
		holder.name.setText(tmp.getName());
		holder.fee.setText(tmp.getEuroFee());
		holder.address.setText(tmp.getPost() + time);
		if (activity.getType() == RestaurantsListActivity2.Type.NEAR) {
		    holder.distance.setText(distanceStr);
		}
		if (tmp.imageSha1 == null) {
		    holder.icon.setVisibility(View.GONE);
		} else {
		    File file = new File(Environment.getExternalStorageDirectory() + "/StudentMeals/" + tmp.imageSha1);
		    if (file.exists()) {
		        holder.icon.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
		        holder.icon.setVisibility(View.VISIBLE);
		    } else {
		        holder.icon.setVisibility(View.GONE);
		    }
		}
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	static class ViewHolder {
		TextView name;
		TextView fee;
		TextView address;
		TextView distance;
		ImageView icon;
	}
}