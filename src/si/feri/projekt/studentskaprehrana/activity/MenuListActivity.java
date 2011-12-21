package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Menu;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.layout;
import si.feri.projekt.studentskaprehrana.db.MenusArrayAdapter;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;

import android.app.ListActivity;
import android.os.Bundle;

public class MenuListActivity extends ListActivity {
	private ListApplication app;
	
	public MenusArrayAdapter menusAdapter;
	public ArrayList<RestaurantMenuData> menuList;
	
	private String hash;
	private RestaurantMenuModel menusDB;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ListApplication) getApplication();
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			this.hash = extras.getString("hash");
			setData();
			setListAdapter(menusAdapter);
			setContentView(R.layout.menus_list_activity);
		}
	}
	
	public void setData1() throws Exception {
	    if (true) throw new Exception("Deprecated");
		/*menusDB = new MenusDB(this);
		menuList = new ArrayList<Menu>();
		menusDB.open();
		Vector<Menu> menus = menusDB.getMenusByHash(hash);
		for (Menu menu : menus) {
			menuList.add(menu);
		}
		menusDB.close();
		
		menusAdapter = new MenusArrayAdapter(this, R.layout.menu_layout, menuList);*/
	}
	
    public void setData() {
        menusDB = new RestaurantMenuModel(this);
        menuList = new ArrayList<RestaurantMenuData>();
        Vector<RestaurantMenuData> menus = menusDB.getMenusByHash(hash);
        for (RestaurantMenuData menu : menus) {
            menuList.add(menu);
        }
        menusDB.close();
        
        menusAdapter = new MenusArrayAdapter(this, R.layout.menu_layout, menuList);
    }
}
