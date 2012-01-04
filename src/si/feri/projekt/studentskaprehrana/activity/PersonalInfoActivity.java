package si.feri.projekt.studentskaprehrana.activity;

import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.R;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PersonalInfoActivity extends Activity {
    private TableLayout mainLayout;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        mainLayout = (TableLayout) findViewById(R.id.linearLayoutMain);
        
        StudentMealUserModel userModel = new StudentMealUserModel(this);
        StudentMealUserData user = userModel.getUserAll();
        userModel.close();
        addElementToMainLayout("Ime", user.firstName + " " + user.lastName);
        addElementToMainLayout("Naslov", user.address.toString());
        addElementToMainLayout("Elektronski naslov", user.email);
        addElementToMainLayout("EMŠO", user.pin);
        addElementToMainLayout("Vpisna številka", user.enrollmentNumber);
        addElementToMainLayout("Univerza", user.university);
        addElementToMainLayout("Fakulteta", user.facility);
        addElementToMainLayout("Trajanje", user.length);
        addElementToMainLayout("Trenutno leto", user.currentYear);
        addElementToMainLayout("Način študija", user.studyMethod);
        addElementToMainLayout("Status", user.statusValidation);
        addElementToMainLayout("Telefon", user.phone);
        addElementToMainLayout("Število subvencij", user.remainingSubsidies + "");
    }
    
    private void addElementToMainLayout(String name, String value) {
        TextView tvv = (TextView) findViewById(R.id.textView2);
        TableRow tr = (TableRow) findViewById(R.id.tableRow2);
        
        //android.view.ViewGroup.LayoutParams param = new android.view.ViewGroup.LayoutParams(this, 
        
        TableRow layout = new TableRow(this);
        layout.setLayoutParams(tr.getLayoutParams());
        layout.setBackgroundColor(Color.BLACK);
        
        TextView tv = new TextView(this);
        tv.setText(name);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setLayoutParams(tvv.getLayoutParams());
        tv.setMaxLines(1);
        
        TextView tv2 = new TextView(this);
        tv2.setText(value);
        tv2.setLayoutParams(tvv.getLayoutParams());
        tv2.setMaxLines(1);
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        
        layout.addView(tv);
        layout.addView(tv2);
        
        mainLayout.addView(layout);
    }
}
