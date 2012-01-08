package com.sciget.studentmeals.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.activity.RestaurantDetailsActivity;
import com.sciget.studentmeals.client.StudentMealsWebServiceClientActivity;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.FileData;
import com.sciget.studentmeals.database.data.StudentMealFileData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;
import com.sciget.studentmeals.service.UpdateService;

import si.feri.projekt.studentskaprehrana.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
	public PreviewView preview;
	public Button shootButton;
	public FrameLayout previewLayout;
	
	private int restaurantId;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_preview);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		previewLayout = (FrameLayout) findViewById(R.id.preview);
		createPreview();
		
		shootButton = (Button) findViewById(R.id.buttonClick);
		shootButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				muteMusicAudio(true);
				preview.camera.autoFocus(preview.autoFocusCallback);
			}
		});
		
        Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
            this.restaurantId = extras.getInt(RestaurantDetailsActivity.RESTAURANT_ID_KEY);
        }
	}
	
	public void createPreview() {
		if (preview == null) {
			preview = new PreviewView(this);
			previewLayout.addView(preview);
		}
	}
	
	public void destroyPreview() {
		if (preview != null && preview.camera != null) {
			preview.surfaceDestroyed(null);
			previewLayout.removeView(preview);
			preview = null;
		}
	}
	
	public void muteMusicAudio(boolean mute) {
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, mute);
	}
	
	public void onResume() {
		super.onResume();
		createPreview();
	}
	
	public void onPause() {
		super.onPause();
		destroyPreview();
	}
	
	public void onStop() {
		super.onPause();
		destroyPreview();
	}
	
	public ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};
	
	public PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};
	
	public PictureCallback jpegCallback = new PictureCallback() {
		
		public void onPictureTaken(byte[] data, Camera camera) {
		    muteMusicAudio(false);
		    
		    File dir = new File(MyPerferences.getInstance().getExternalStoragePath());
		    dir.mkdirs();

		    String fileKey = MVC.random(40);
		    File image = new File(dir.getAbsolutePath() + "/" + fileKey);
		    FileOutputStream outStream = null;
		    try {
		        outStream = new FileOutputStream(image);
		        outStream.write(data);
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            outStream.close();
		        } catch (IOException e) {}
		    }
		    
            StudentMealUserModel userModel = new StudentMealUserModel(CameraActivity.this);
            userModel.addImageFileData(restaurantId, fileKey);
            userModel.getFilesData();
            userModel.close();
            
            destroyPreview();
            createPreview();
            
            Intent startServiceIntent = new Intent(getBaseContext(), UpdateService.class);
            getBaseContext().startService(startServiceIntent);
            
            finish();
		}
	};
}