package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;
import com.sciget.studentmeals.database.model.RestaurantModel;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Menu;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.layout;
import si.feri.projekt.studentskaprehrana.db.HistoryArrayAdapter;
import si.feri.projekt.studentskaprehrana.db.MenusArrayAdapter;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;

import android.app.ListActivity;
import android.os.Bundle;

public class HistoryListActivity extends ListActivity {
	private ListApplication app;
	
	public HistoryArrayAdapter menusAdapter;
	public ArrayList<StudentMealHistoryData> menuList;
	
	private String hash;
	private RestaurantModel menusDB;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ListApplication) getApplication();
		Bundle extras = getIntent().getExtras(); 
		setData();
		setListAdapter(menusAdapter);
		setContentView(R.layout.menus_list_activity);
	}

    public void setData() {
        menusDB = new RestaurantModel(this);
        menuList = new ArrayList<StudentMealHistoryData>();
        Vector<StudentMealHistoryData> menus = menusDB.getHistory();
        for (StudentMealHistoryData menu : menus) {
            menuList.add(menu);
        }
        menusDB.close();
        
        menusAdapter = new HistoryArrayAdapter(this, R.layout.menu_layout, menuList);
    }
}
