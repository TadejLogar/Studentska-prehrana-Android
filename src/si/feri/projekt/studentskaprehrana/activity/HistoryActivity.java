package si.feri.projekt.studentskaprehrana.activity;

import com.sciget.studentmeals.database.model.RestaurantModel;

import android.app.Activity;
import android.os.Bundle;

public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        RestaurantModel restaurantModel = null;
        try {
            restaurantModel = new RestaurantModel(this);
            restaurantModel.getHistory();
        } catch (Exception e) {
            // V primeru, da zgodovina še ni bila posodobljena
            finish();
        } finally {
            if (restaurantModel != null) {
                try {
                    restaurantModel.close();
                } catch (Exception e) {}
            }
        }
    }
    
}
