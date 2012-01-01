package si.feri.projekt.studentskaprehrana.activity;

import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.R;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
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
        TableRow layout = new TableRow(this);
        
        TextView tv = new TextView(this);
        tv.setText(name);
        tv.setTypeface(null, Typeface.BOLD);
        
        TextView tv2 = new TextView(this);
        tv2.setText(value);
        tv2.setTypeface(null, Typeface.BOLD);
        
        layout.addView(tv);
        layout.addView(tv2);
        
        mainLayout.addView(layout);
    }
}
