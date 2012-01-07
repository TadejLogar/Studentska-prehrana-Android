package com.sciget.studentmeals.widget;

import com.sciget.studentmeals.MainApplication;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.R;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {

    MainApplication app;

    @Override
    public void onStart(Intent intent, int startId) {
        if (app == null)
            app = (MainApplication) getApplication();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        if (appWidgetIds.length > 0) {
            for (int widgetId : appWidgetIds) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
                String provider = app.getLastProvider();
                String number = app.getSubsidiesNumber() + "";
                if (provider == null) {
                    provider = "Nimaš dodanega računa";
                    number = "";
                }
                remoteViews.setTextViewText(R.id.textViewWidgetRestaurant, provider);
                remoteViews.setTextViewText(R.id.textViewWidgetNumber, number);
                //app.updateLastVisit();
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
            stopSelf();
        }
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}