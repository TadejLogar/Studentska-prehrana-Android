package si.feri.projekt.studentskaprehrana.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainListActivity extends ListActivity {
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuForActivity.onOptionsItemSelected(item, this);
	}
	
	public boolean onCreateOptionsMenu(Menu mMenu) {
		return MenuForActivity.onCreateOptionsMenu(mMenu, this);
	}
}
