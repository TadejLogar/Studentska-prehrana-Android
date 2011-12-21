package si.feri.projekt.studentskaprehrana.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.sciget.studentmeals.database.data.RestaurantData;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.drawable;
import si.feri.projekt.studentskaprehrana.R.id;
import si.feri.projekt.studentskaprehrana.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends MainActivity {
	private ListApplication app;

	TextView name;
	TextView address;
	TextView fee;
	TextView time;
	ImageButton fav;
	Button menuButton;
	
	private RestaurantData currentProvider;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ListApplication) getApplication();
		setContentView(R.layout.provider);
		
		name = (TextView) findViewById(R.id.textViewProviderName);
		address = (TextView) findViewById(R.id.textViewAddress);
		fee = (TextView) findViewById(R.id.textViewFee);
		time = (TextView) findViewById(R.id.textViewOpenTime);
		fav = (ImageButton) findViewById(R.id.imageButtonFav);
		menuButton = (Button) findViewById(R.id.buttonMenu);
		
		currentProvider = app.getProviderById(app.getCurrentProvider());
		
		setData(app.getCurrentProvider());
		
	}
	
	public void onStart() {
		super.onStart();
	}
	
	public void onResume() {
		super.onResume();
	}
	
	public void backClick(View v) {
		finish();
	}
	
	public void menuClick(View v) {
		Intent i = new Intent(this, MenuListActivity.class);
		i.putExtra("hash", currentProvider.getHash());
		startActivity(i);
	}
	
	public void mapLocationClick(View v) {
		Intent i = new Intent(this, si.feri.projekt.studentskaprehrana.activity.ProviderMapActivity.class);
		i.putExtra("latitude", currentProvider.getLocationLatitude());
		i.putExtra("longitude", currentProvider.getLocationLongitude());
		startActivity(i);
	}
	
	public void addToFavoritesListClick(View v) {
		boolean favorited = currentProvider.getFavorited();
		
		// TODO
		/*if (favorited) {
			currentProvider.setFavorited(false);
			// TODO spremeni app.providersDB.setFavorited(currentProvider.getHash(), false);
			fav.setBackgroundResource(R.drawable.unstarred48);
		} else {
			currentProvider.setFavorited(true);
			// TODO spremeni app.providersDB.setFavorited(currentProvider.getHash(), true);
			fav.setBackgroundResource(R.drawable.starred48);
		}*/
	}
	
	public void setData(int id) {
		final RestaurantData provider = app.getProviderById(id);
		
		name.setText(provider.getName());
		address.setText(provider.getFullAddress());
		fee.setText("Doplačilo: " + provider.getEuroFee());
		// TODO time.setText(provider.getOpenTime());
		// TODO popravi blok
		/*Boolean menu = provider.getMenu();
		if (menu == null) {
			provider.setMenu(app.menusDB.menuExists(provider.getHash()));
			menu = provider.getMenu();
		}*/
		
		// TODO
		/*if (!menu) {
			menuButton.setVisibility(View.INVISIBLE);
		}*/
		
		if (provider.getFavorited()) {
			fav.setBackgroundResource(R.drawable.starred48);
		}

		new DownloadImageTask().execute(provider.getFullAddress());
	}
	
	private class DownloadImageTask extends AsyncTask<String, Bitmap, Void> {
		
		@Override
		protected Void doInBackground(String... url) {
			loadMapImage(url[0]);
			return null;
		}
		
		
		protected void onProgressUpdate(Bitmap... img) {
			ImageView i = (ImageView) findViewById(R.id.mapImage);
			i.setImageBitmap(img[0]);
		}
		
		public void loadMapImage(String address) {
			String query = address;
			try {
				query = URLEncoder.encode(address, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String url = "http://maps.google.com/maps/api/staticmap?center=" + query + "&markers=color:blue|" + query + "&zoom=15&size=470x220&maptype=roadmap&sensor=false";
			try {
				Bitmap img = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
				publishProgress(img);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
