package si.feri.projekt.studentskaprehrana;

import si.feri.projekt.studentskaprehrana.activity.HistoryListActivity;
import si.feri.projekt.studentskaprehrana.activity.PersonalInfoActivity;
import si.feri.projekt.studentskaprehrana.activity.UpdateActivity;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.client.StudentMealsWebServiceClientActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class Preferences extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	    perferences();
	}
	
    private void perferences() {
        {
            Preference myPref = (Preference) findPreference("createAccountKey");
            myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                 public boolean onPreferenceClick(Preference preference) {
                     Intent intent = new Intent(Preferences.this, StudentMealsWebServiceClientActivity.class);
                     startActivity(intent);
                     return true;
                 }
             });
        }
        
        {
            Preference myPref = (Preference) findPreference("historyKey");
            myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                 public boolean onPreferenceClick(Preference preference) {
                     Intent intent = new Intent(Preferences.this, HistoryListActivity.class);
                     startActivity(intent);
                     return true;
                 }
             });
             myPref.setEnabled(MyPerferences.getInstance().getUserId() > 0);
        }
        
        {
            Preference myPref = (Preference) findPreference("personalInfoKey");
            myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                 public boolean onPreferenceClick(Preference preference) {
                     Intent intent = new Intent(Preferences.this, PersonalInfoActivity.class);
                     startActivity(intent);
                     return true;
                 }
             });
             myPref.setEnabled(MyPerferences.getInstance().getUserId() > 0);
        }
        
        {
            Preference myPref = (Preference) findPreference("updateKey");
            myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                 public boolean onPreferenceClick(Preference preference) {
                     Intent intent = new Intent(Preferences.this, UpdateActivity.class);
                     startActivity(intent);
                     return true;
                 }
             });
             myPref.setEnabled(MyPerferences.getInstance().getUserId() > 0);
        }
    }

}