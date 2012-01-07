package com.sciget.studentmeals.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.camera.Security;
import com.sciget.studentmeals.database.model.RestaurantModel;

import si.feri.projekt.studentskaprehrana.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class RestaurantMapImageTask extends AsyncTask<Void, Bitmap, Void> {
    private MainApplication application;
    private int restaurantId;
    private ImageView imageView;
    private String hash;
    private String address;
    private String newHash;
    
    public RestaurantMapImageTask(MainApplication application, int restaurantId, ImageView imageView, String hash, String address) {
        super();
        this.application = application;
        this.restaurantId = restaurantId;
        this.imageView = imageView;
        this.hash = hash;
        this.address = address;
    }

    @Override
    protected Void doInBackground(Void... params) {
        File file = new File(MyPerferences.getInstance().getExternalStoragePath() + hash);
        if (hash != null && file.exists()) {
            Bitmap img = BitmapFactory.decodeFile(file.getAbsolutePath());
            publishProgress(img);
        } else {
            String query = address;
            try {
                query = URLEncoder.encode(address, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            String url = "http://maps.google.com/maps/api/staticmap?center=" + query + "&markers=color:blue|" + query + "&zoom=15&size=470x220&maptype=roadmap&sensor=false";
            downloadImage(url);
        }
        return null;
    }

    protected void onProgressUpdate(Bitmap... img) {
        imageView.setImageBitmap(img[0]);
        if (newHash != null) {
            application.setRestaurantMapImageHash(restaurantId, newHash);
        }
    }

    protected void downloadImage(String url) {
        try {
            File file = new File(MyPerferences.getInstance().getExternalStoragePath() + MVC.random(40));
            MVC.downloadToFile(url, file);
            newHash = Security.fileSha1(file);
            File newFile = new File(MyPerferences.getInstance().getExternalStoragePath() + newHash);
            file.renameTo(newFile);
            Bitmap img = BitmapFactory.decodeFile(newFile.getAbsolutePath());
            publishProgress(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}