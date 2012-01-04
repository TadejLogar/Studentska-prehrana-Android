package com.sciget.studentmeals.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.client.StudentMealsWebServiceClientActivity;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.FileData;

import si.feri.projekt.studentskaprehrana.R;

import android.app.Activity;
import android.content.Context;
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
            this.restaurantId = extras.getInt("restaurantId");
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
		private File image;
		private File smallImage;
		private String randomFileKey;
		private String smallSha1;
		private String sha1;
		private String userKey;
		
		public void onPictureTaken(byte[] data, Camera camera) {
		    muteMusicAudio(false);
		    
			File dir = new File(Environment.getExternalStorageDirectory() + "/StudentMeals/");
			dir.mkdirs();
			
			String name = "img" + System.currentTimeMillis();
			image = new File(dir.getAbsolutePath() + "/" + name + ".jpg");
			smallImage = new File(dir.getAbsolutePath() + "/" + name + ".small.jpg");
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

			resize(image, smallImage, 512);
			
			randomFileKey = MVC.random(40);
            smallSha1 = Security.fileSha1(smallImage);
            sha1 = Security.fileSha1(image);
            userKey = MyPerferences.getInstance().getUserKey();
			
			upload(MyPerferences.getInstance().getServer());
			//upload(Perferences.SERVER2);

			destroyPreview();
			createPreview();
			
			finish();
		}
		
		private void upload(String ip) {
		    StudentMealsService meals = new StudentMealsService();
		    meals.uploadRestaurantFile(userKey, restaurantId, smallSha1, FileData.FileType.IMAGE, FileData.Size.SMALL, randomFileKey);
		    meals.uploadRestaurantFile(userKey, restaurantId, sha1, FileData.FileType.IMAGE, FileData.Size.ORIGINAL, randomFileKey);
		    
			new UploadFileThread(ip, smallImage, smallSha1).start();
			new UploadFileThread(ip, image, sha1).start();
		}
	};
	
	private void resize(File file, File small, int maxSize) {
		Bitmap bitmap = decodeFile(file, maxSize);
		try {
			FileOutputStream out = new FileOutputStream(small);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.recycle();
		bitmap = null;
	}
	
	private Bitmap decodeFile(File f, int maxSize) {
	    Bitmap b = null;
	    try {
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;

	        FileInputStream fis = new FileInputStream(f);
	        BitmapFactory.decodeStream(fis, null, o);
	        fis.close();

	        int scale = 1;
	        if (o.outHeight > maxSize || o.outWidth > maxSize) {
	            scale = (int)Math.pow(2, (int) Math.round(Math.log(maxSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
	        }

	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        fis = new FileInputStream(f);
	        b = BitmapFactory.decodeStream(fis, null, o2);
	        fis.close();
	    } catch (IOException e) {
	    }
	    return b;
	}
}