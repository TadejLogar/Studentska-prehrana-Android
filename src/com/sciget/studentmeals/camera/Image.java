package com.sciget.studentmeals.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.FileData;
import com.sciget.studentmeals.database.data.StudentMealFileData;

public class Image {
    private String userKey;
    private boolean done;
    public String sha1;
    public String smallSha1;
    private StudentMealFileData fileData;
    private File image;
    private File smallImage;

    public Image(StudentMealFileData fileData) {
        this.fileData = fileData;
        try {
            image();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void image() throws InterruptedException {
        String dir = MyPerferences.getInstance().getExternalStoragePath();
        
        if (fileData.hash == null) {
            try {
                File imageTemp = new File(dir + fileData.fileKey);
                File smallImageTemp = new File(dir + MVC.random(40));
                resize(imageTemp, smallImageTemp, 512);
                
                sha1 = Security.fileSha1(imageTemp);
                smallSha1 = Security.fileSha1(smallImageTemp);
                
                image = new File(dir + sha1);
                smallImage = new File(dir + smallSha1);
                
                imageTemp.renameTo(image);
                smallImageTemp.renameTo(smallImage);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            sha1 = fileData.hash;
            smallSha1 = fileData.smallHash;
            
            image = new File(dir + sha1);
            smallImage = new File(dir + smallSha1);
        }
        
        userKey = MyPerferences.getInstance().getUserKey();
        
        upload(MyPerferences.getInstance().getServer(), smallSha1, sha1, smallImage, image);
    }

    private void upload(String ip, String smallSha1, String sha1, File smallImage, File image) throws InterruptedException {
        StudentMealsService meals = new StudentMealsService();
        meals.uploadRestaurantFile(userKey, fileData.restaurantId, smallSha1, FileData.FileType.IMAGE, FileData.Size.SMALL, fileData.fileKey);
        meals.uploadRestaurantFile(userKey, fileData.restaurantId, sha1, FileData.FileType.IMAGE, FileData.Size.ORIGINAL, fileData.fileKey);

        UploadFileThread uploadSmall = new UploadFileThread(ip, smallImage, smallSha1);
        uploadSmall.start();
        uploadSmall.join();
        if (!uploadSmall.isDone()) {
            return;
        }
        
        UploadFileThread upload = new UploadFileThread(ip, image, sha1);
        upload.start();
        upload.join();
        if (!upload.isDone()) {
            return;
        }
        
        done = true;
    }
    
    public boolean isDone() {
        return done;
    }

    private void resize(File file, File small, int maxSize) {
        Bitmap bitmap = decodeFile(file, maxSize);
        try {
            FileOutputStream out = new FileOutputStream(small);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
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
                scale = (int) Math.pow(2, (int) Math.round(Math.log(maxSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
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
