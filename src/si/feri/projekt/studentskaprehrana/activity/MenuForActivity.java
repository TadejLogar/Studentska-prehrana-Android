package si.feri.projekt.studentskaprehrana.activity;

import si.feri.projekt.studentskaprehrana.Main;
import si.feri.projekt.studentskaprehrana.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuForActivity {
    public static boolean onOptionsItemSelected(MenuItem item, Activity activity) {
        switch (item.getItemId()) {
            case R.id.foodMenu:
    	        showFood(activity);
    	        return true;
            case R.id.settingsMenu:
    	        showSettings(activity);
    	        return true;
            case R.id.gameMenu:
    	        showGame(activity);
    	        return true;
            case R.id.exitMenu:
    	        showExit(activity);
    	        return true;
        }
        return false;
  }
    
    public static boolean onCreateOptionsMenu(Menu mMenu, Activity activity) {
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.menu, mMenu);
        return true;
    }

	private static void showExit(final Activity activity) {
	     AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	     builder.setMessage("Želiš zapustiti aplikacijo?")
	     .setCancelable(false)
	     .setPositiveButton("Da", new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int id) {
		    	 activity.moveTaskToBack(true);
		     }
	     })
	     .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int id) {
		    	 dialog.cancel();
		     }
	     });
	     AlertDialog alert = builder.create();
	     alert.show();
	}

	private static void showGame(Activity activity) {
		Intent i = new Intent(activity, si.feri.mui.igra.connectfour.ConnectFourActivity.class);
		activity.startActivity(i);
	}

	private static void showSettings(Activity activity) {
		Intent i = new Intent(activity, si.feri.projekt.studentskaprehrana.Preferences.class);
		activity.startActivity(i);
	}

	private static void showFood(Activity activity) {
	    activity.finish();
		Intent i = new Intent(activity, RestaurantsListActivity2.class);
		activity.startActivity(i);
	}
}
