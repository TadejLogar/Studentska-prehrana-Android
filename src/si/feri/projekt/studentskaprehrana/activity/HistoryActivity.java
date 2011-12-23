package si.feri.projekt.studentskaprehrana.activity;

import com.sciget.studentmeals.database.model.RestaurantModel;

import android.app.Activity;
import android.os.Bundle;

public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        RestaurantModel restaurantModel = new RestaurantModel(this);
        restaurantModel.getHistory();
    }
    
}
