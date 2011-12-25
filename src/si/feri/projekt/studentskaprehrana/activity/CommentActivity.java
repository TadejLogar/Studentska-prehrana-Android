package si.feri.projekt.studentskaprehrana.activity;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.client.service.StudentMealsService;

import si.feri.projekt.studentskaprehrana.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CommentActivity extends Activity {
    private int restaurantId;
    private EditText editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        editTextComment = (EditText) findViewById(R.id.editTextComment);

        Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
            this.restaurantId = extras.getInt("restaurantId");
        }
    }
    
    public void addComment(View view) {
        String key = MyPerferences.getInstance().getUserKey();
        String comment = editTextComment.getText().toString();
        
        StudentMealsService meals = new StudentMealsService();
        meals.addComment(key, restaurantId, comment);
        
        finish();
        finish();
        
        Intent detailsActivity = new Intent(this, DetailsActivity.class);
        startActivityForResult(detailsActivity, 1);
    }

}
