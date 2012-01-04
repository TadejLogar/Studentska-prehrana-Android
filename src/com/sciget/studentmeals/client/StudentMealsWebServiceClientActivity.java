package com.sciget.studentmeals.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import si.feri.projekt.studentskaprehrana.Main;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.activity.DetailsActivity;
import si.feri.projekt.studentskaprehrana.activity.RestaurantsListActivity;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.FileData;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.client.service.data.StudentMealsStateData;
import com.sciget.studentmeals.client.service.data.UserData;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;
import com.sciget.studentmeals.service.UpdateDataTask;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentMealsWebServiceClientActivity extends Activity {
	private StudentMealsStateData state;
	public static int loginDataResponse;
	private EditText studentMealsEmail;
	
	public String getEmail(){
	    AccountManager manager = AccountManager.get(this); 
	    Account[] accounts = manager.getAccountsByType("com.google"); 

	    String email = null;
	    for (Account account : accounts) {
	        String email0 = account.name;
	        if (email0 != null && email0.contains("@")) {
	            email = email0;
	        }
	    }
	    
	    if (email == null) {
	        return "@gmail.com";
	    } else {
	        return email;
	    }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		
		TextView status = (TextView) findViewById(R.id.textViewStatus);
		
		studentMealsEmail = (EditText) findViewById(R.id.editTextStudentMealsEmail);
		studentMealsEmail.setText(getEmail());
        studentMealsEmail.setSelection(0);
        studentMealsEmail.setFocusable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
		/*StudentMealsService service = new StudentMealsService();
		Vector<MenuData> list = service.allRestaurantsPermanentMenus();
		status.setText(list.toString());*/
		
		/*StudentMealsService service = new StudentMealsService();
		UserData user = service.userData("tadej.logar.101@gmail.com", "studentskaprehrana.si");
		System.out.println(user.getFirstName());*/

		StudentMealsService service = new StudentMealsService();
		state = service.captchaImageUrl();
		
		status.setText(state.imageUrl);
		setImage(state.imageUrl);
		
		status.setText(loginDataResponse + "");
		
		Log.i("A", state.imageUrl);
	}
	
    private class DownloadRecaptchaCode extends AsyncTask<String, Bitmap, Void> {

        @Override
        protected Void doInBackground(String... url) {
            try {
                Bitmap img = BitmapFactory.decodeStream(new URL(url[0]).openStream());
                publishProgress(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Bitmap... imgs) {
            ImageView iv = (ImageView) findViewById(R.id.imageViewRecaptcha);
            if (iv != null && imgs != null && imgs.length > 0) {
                iv.setImageBitmap(imgs[0]);
            }
        }
    }

	public void setImage(String url1) {
		new DownloadRecaptchaCode().execute(url1);
	}
	
	private ProgressDialog pd;
	public void createAccount(final View view) {
	    pd = ProgressDialog.show(this,"Ustvarjanje računa","Račun bo ustvarjen čez nekaj trenutkov. Zgodovina bo posodobljena ko bodo podatki na voljo.",true,false,null);

        final StudentMealsService service = new StudentMealsService();
        //final EditText appEmail = (EditText) findViewById(R.id.editTextAppEmail);
        //EditText appPassword = (EditText) findViewById(R.id.editTextAppPassword);
        //final EditText studentMealsEmail = (EditText) findViewById(R.id.editTextStudentMealsEmail);
        EditText studentMealsPassword = (EditText) findViewById(R.id.editTextTextStudentMealsPassword);
        EditText recaptchaCode = (EditText) findViewById(R.id.editTextRecaptchaCode);
        final String email = studentMealsEmail.getText().toString();
        final String password = studentMealsPassword.getText().toString();
        final String recaptcha = recaptchaCode.getText().toString();
	    
	    new Thread() {
	        public void run() {
	            loginDataResponse = service.addLoginData(email, password, email, password, state.stateHash, recaptcha);
	            if (loginDataResponse == 1) {
	                String key = service.getUserKey(email, password);
	                MyPerferences.getInstance().setUserKey(key);
	                UserData user = service.userData(key);
	                if (user != null) {
	                    MyPerferences.getInstance().setUserId(user.getUserId());
	                    new StudentMealUserModel(StudentMealsWebServiceClientActivity.this).add(new StudentMealUserData(0, user.userId, user.email, user.password, user.firstName, user.lastName, user.pin, user.getAddress(), "", "", user.getTempAddress(), "", "", user.university, user.facility, user.length, user.currentYear, user.studyMethod, user.statusValidation, user.enrollmentNumber, user.phone, key, "", user.remainingSubsidies));
	                    new Thread() {
	                        public void run() {
	                            try {
	                                Thread.sleep(60 * 1000);
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();
	                            }
	                            new UpdateDataTask(StudentMealsWebServiceClientActivity.this).updateUserHistory();
	                        }
	                    }.start();
	                }
	                
	                pd.cancel();
	                
	                finish();
	                Intent myIntent = new Intent(view.getContext(), Main.class);
	                startActivityForResult(myIntent, 0);
	            } else {
	                pd.cancel();
	                
	                finish();
	                Intent myIntent = new Intent(view.getContext(), StudentMealsWebServiceClientActivity.class);
	                startActivityForResult(myIntent, 0);
	            }
	        }
	    }.start();
	}
}