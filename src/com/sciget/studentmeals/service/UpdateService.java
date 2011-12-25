package com.sciget.studentmeals.service;

import java.sql.Timestamp;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.database.data.Data;

import si.feri.projekt.studentskaprehrana.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class UpdateService extends Service {
    public static boolean running;
    public static boolean updated;

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler handler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), msg.getData().getString("msg"), Toast.LENGTH_LONG).show();
        }
    };
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        new MyPerferences(this);
        
        if (!running) {
            running = true;
            System.out.println("service studentmeals create");
            
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    
            new Thread() {
                public void sendMessage(String msg) {
                    Message myMessage = new Message();
                    Bundle resBundle = new Bundle();
                    resBundle.putString("status", "SUCCESS");
                    resBundle.putString("msg", msg);
                    myMessage.obj = resBundle;
                    handler.sendMessage(myMessage);
                }
                
                public void run() {
                    Timestamp last = MyPerferences.getInstance().getLastRestaurantsUpdate();
                    if (last != null) {
                        long last1 = last.getTime() + (24 * 3600 * 1000);
                        long now = Data.time().getTime();
                        if (last1 > now) {
                            return;
                        }
                    }
                    
                    while (!isOnline()) {
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    sendMessage("UPDATING");                
                    displayNotification("UPDATING", "UPDATING", "UPDATING", UpdateService.class, 0);
    
    
                    UpdateDataTask updateDataTask = new UpdateDataTask(UpdateService.this);
                    updateDataTask.all();
                    updated = true;
                    MyPerferences.getInstance().setLastRestaurantsUpdate(Data.time());
                    
                    sendMessage("DONE");
                    displayNotification("DONE", "DONE", "DONE", UpdateService.class, 1);
                }
            }.start();
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("service studentmeals destroy");
    }

    @Override
    public void onStart(Intent intent, int startid) {
        System.out.println("service studentmeals start");
    }
    
    private void displayNotification(String extra, String contentTitle, String contentText, Class<?> cls, int id) {     
        Notification notifyDetails = new Notification(R.drawable.icon, "New Alert!", System.currentTimeMillis());
        Intent intent = new Intent(this, cls);
        intent.putExtra("extra", extra);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), id, intent, PendingIntent.FLAG_ONE_SHOT);
        notifyDetails.setLatestEventInfo(getApplicationContext(), contentTitle, contentText, contentIntent);
        mNotificationManager.notify(id, notifyDetails);
    }


}
