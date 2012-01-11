package si.feri.projekt.studentskaprehrana.activity;

import si.feri.projekt.studentskaprehrana.Main;
import si.feri.projekt.studentskaprehrana.R;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.UserData;
import com.sciget.studentmeals.database.data.Data;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;
import com.sciget.studentmeals.service.UpdateDataTask;
import com.sciget.studentmeals.service.UpdateService;
import com.sciget.studentmeals.widget.UpdateWidgetService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TableLayout;

public class UpdateActivity extends Activity {

    private ProgressDialog pd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pd = ProgressDialog.show(this,"Posodabljam","Posodabljam ponudnike, menije, zgodovino, osebne podatke in vrednost subvancije.",true,false,null);

        new Thread() {
            public void run() {
                //displayNotification("UPDATING", "UPDATING", "UPDATING", UpdateActivity.class, 0);
                UpdateDataTask updateDataTask = new UpdateDataTask(UpdateActivity.this);
                updateDataTask.all();
                updateDataTask.updateUserHistory();
                updateDataTask.closeModel();
                MyPerferences.getInstance().setLastRestaurantsUpdate(Data.time());

                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
                ComponentName thisWidget = new ComponentName(getApplicationContext(), UpdateWidgetService.class);
                AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(thisWidget, remoteViews);
                
                
                int userId = MyPerferences.getInstance().getUserId();
                if (userId > 0) {
                    StudentMealsService service = new StudentMealsService();
                    String key = MyPerferences.getInstance().getUserKey();
                    UserData user = service.userData(key);
                    if (user != null) {
                        new StudentMealUserModel(UpdateActivity.this).add(new StudentMealUserData(0, user.userId, user.email, user.password, user.firstName, user.lastName, user.pin, user.getAddress(), "", "", user.getTempAddress(), "", "", user.university, user.facility, user.length, user.currentYear, user.studyMethod, user.statusValidation, user.enrollmentNumber, user.phone, key, "", user.remainingSubsidies));
                    }
                }
                displayNotification("Posodobljeno", "Posodobljeno", "Posodobljeno", "Posodobljeno", UpdateActivity.class, 1);
                done();
            }
        }.start();
    }
    
    public void done() {
        pd.cancel();
        
        finish();
        finish();
        finish();
        
        Intent intent = new Intent(this, RestaurantsListActivity2.class);
        startActivity(intent);
    }
    
    public void displayNotification(String title, String extra, String contentTitle, String contentText, Class<?> cls, int id) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notifyDetails = new Notification(R.drawable.logo, title, System.currentTimeMillis());
        Intent intent = new Intent(this, cls);
        intent.putExtra("extra", extra);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), id, intent, PendingIntent.FLAG_ONE_SHOT);
        notifyDetails.setLatestEventInfo(getApplicationContext(), contentTitle, contentText, contentIntent);
        mNotificationManager.notify(id, notifyDetails);
    }
}
