package com.sciget.studentmeals.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateService extends Service {
    public static boolean running;
    public static boolean updated;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onCreate() {
        //if (!running) {
            running = true;
            System.out.println("service studentmeals create");
            
            new Thread() {
                public void run() {
                    UpdateDataTask updateDataTask = new UpdateDataTask(UpdateService.this);
                    updateDataTask.all();
                    updated = true;
                }
            }.start();
        //}
    }

    @Override
    public void onDestroy() {
        System.out.println("service studentmeals destroy");
    }
    
    @Override
    public void onStart(Intent intent, int startid) {
        System.out.println("service studentmeals start");
    }

}
