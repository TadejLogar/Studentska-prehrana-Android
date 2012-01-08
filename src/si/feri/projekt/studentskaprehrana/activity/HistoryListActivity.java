package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;
import com.sciget.studentmeals.database.model.RestaurantModel;
import com.sciget.studentmeals.database.model.StudentMealUserModel;
import com.sciget.studentmeals.service.UpdateDataTask;
import com.sciget.studentmeals.service.UpdateDataTaskTest;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Menu;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.layout;
import si.feri.projekt.studentskaprehrana.db.HistoryArrayAdapter;
import si.feri.projekt.studentskaprehrana.db.MenusArrayAdapter;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HistoryListActivity extends ListActivity {
    private MainApplication app;

    public HistoryArrayAdapter menusAdapter;
    public ArrayList<StudentMealHistoryData> menuList;

    private String hash;
    private RestaurantModel menusDB;
    
    private ProgressDialog pd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MainApplication) getApplication();
        Bundle extras = getIntent().getExtras();
        setData();
        setListAdapter(menusAdapter);
        setContentView(R.layout.menus_list_activity);
    }

    public void setData() {
        menusDB = new RestaurantModel(this);
        menuList = new ArrayList<StudentMealHistoryData>();
        Vector<StudentMealHistoryData> menus = menusDB.getHistory();
        if (menus != null) {
            for (StudentMealHistoryData menu : menus) {
                menuList.add(menu);
            }
        }
        menusDB.close();

        menusAdapter = new HistoryArrayAdapter(this, R.layout.menu_layout, menuList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.historyMenu:
                updateHistory();
                return true;
        }
        return false;
    }
    
    public boolean onCreateOptionsMenu(android.view.Menu mMenu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, mMenu);
        return true;
    }

    private void updateHistory() {
        pd = ProgressDialog.show(this,"Posodabljam","Posodabljam zgodovino.",true,false,null);
        
        new Thread() {
            public void run() {
                UpdateDataTask task = new UpdateDataTask(HistoryListActivity.this);
                task.updateUserHistory();
                task.closeModel();
                pd.cancel();
                finish();
                Intent intent = new Intent(HistoryListActivity.this, HistoryListActivity.class);
                startActivityForResult(intent, 1);
            }
        }.start();
    }
}
