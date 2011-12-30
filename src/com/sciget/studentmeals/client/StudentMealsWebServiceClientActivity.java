package com.sciget.studentmeals.client;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import si.feri.projekt.studentskaprehrana.Main;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.activity.RestaurantsListActivity;

import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.client.service.data.StudentMealsStateData;
import com.sciget.studentmeals.client.service.data.UserData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentMealsWebServiceClientActivity extends Activity {
	private StudentMealsStateData state;
	public static int loginDataResponse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		
		TextView status = (TextView) findViewById(R.id.textViewStatus);
		
		
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

	public void setImage(String url1) {
		ImageView iv = (ImageView) findViewById(R.id.imageViewRecaptcha);

		try {
			URL ulrn = new URL(url1);
			HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
			InputStream is = con.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(is);
			if (null != bmp) {
				iv.setImageBitmap(bmp);
			} else {
				System.out.println("The Bitmap is NULL");
			}

		} catch (Exception e) {
		}
	}
	
	public void createAccount(View view) {
		StudentMealsService service = new StudentMealsService();
		EditText appEmail = (EditText) findViewById(R.id.editTextAppEmail);
		EditText appPassword = (EditText) findViewById(R.id.editTextAppPassword);
		EditText studentMealsEmail = (EditText) findViewById(R.id.editTextStudentMealsEmail);
		EditText studentMealsPassword = (EditText) findViewById(R.id.editTextTextStudentMealsPassword);
		EditText recaptchaCode = (EditText) findViewById(R.id.editTextRecaptchaCode);
		loginDataResponse = service.addLoginData(appEmail.getText().toString(), appPassword.getText().toString(), studentMealsEmail.getText().toString(), studentMealsPassword.getText().toString(), state.stateHash, recaptchaCode.getText().toString());
		if (loginDataResponse == 1) {
		    finish();
	        Intent myIntent = new Intent(view.getContext(), Main.class);
	        startActivityForResult(myIntent, 0);
		} else {
			finish();
	        Intent myIntent = new Intent(view.getContext(), StudentMealsWebServiceClientActivity.class);
	        startActivityForResult(myIntent, 0);
		}
	}
}