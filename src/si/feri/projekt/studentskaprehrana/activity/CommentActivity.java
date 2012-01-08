package si.feri.projekt.studentskaprehrana.activity;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.activity.RestaurantDetailsActivity;
import com.sciget.studentmeals.client.service.StudentMealsService;

import si.feri.projekt.studentskaprehrana.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class CommentActivity extends Activity {
    private int restaurantId;
    private EditText commentEditText;
    private EditText nameEditText;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        nameEditText = (EditText) findViewById(R.id.editTextName);
        commentEditText = (EditText) findViewById(R.id.editTextComment);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
            this.restaurantId = extras.getInt(RestaurantDetailsActivity.RESTAURANT_ID_KEY);
        }
    }
    
    public void addComment(View view) {
        String key = MyPerferences.getInstance().getUserKey();
        String name = nameEditText.getText().toString();
        String comment = commentEditText.getText().toString();
        int rate = (int) ratingBar.getRating();
        
        StudentMealsService meals = new StudentMealsService();
        meals.addComment(key, restaurantId, name, rate, comment);
        
        finish();
        finish();
        
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantDetailsActivity.RESTAURANT_ID_KEY, restaurantId);
        startActivity(intent);
    }

}
