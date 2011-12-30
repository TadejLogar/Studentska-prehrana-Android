package com.sciget.studentmeals.camera;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PreviewView extends SurfaceView implements SurfaceHolder.Callback {
	public SurfaceHolder surfaceHolder;
	public Camera camera;
	public CameraActivity cameraActivity;
	private boolean isPreviewRunning;
	private int done;
	
	public PreviewView(Context context) {
		super(context);
		cameraActivity = (CameraActivity) context;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(holder);
			Parameters parameters = camera.getParameters();
			Camera.Size size = parameters.getSupportedPreviewSizes().get(0);
			parameters.setPreviewSize(size.width, size.height);
			parameters.setPictureFormat(PixelFormat.JPEG);
			parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
			parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
			camera.setParameters(parameters);
	
			camera.setPreviewCallback(new PreviewCallback() {
				public void onPreviewFrame(byte[] data, Camera camera) {
					PreviewView.this.invalidate();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isPreviewRunning = true;
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (camera != null) {
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
		isPreviewRunning = false;
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { // <15>
		if (isPreviewRunning) {
			camera.stopPreview();
		}
		camera.startPreview();
		isPreviewRunning = true;
	}
	
	public AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
		
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (done == 0) {
				cameraActivity.shootButton.setEnabled(true);
				camera.takePicture(cameraActivity.shutterCallback, cameraActivity.rawCallback, cameraActivity.jpegCallback);
			}
			done++;
		}
	};
}