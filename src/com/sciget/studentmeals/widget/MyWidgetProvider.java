package com.sciget.studentmeals.widget;

import si.feri.projekt.studentskaprehrana.Main;
import si.feri.projekt.studentskaprehrana.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Build the intent to call the service
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context.getApplicationContext(), UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        // To react to a click we have to use a pending intent as the onClickListener is excecuted by the homescreen application
        //PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //remoteViews.setOnClickPendingIntent(R.id.buttonWidgetUpdate, pendingIntent);
        
        Intent intent2 = new Intent(context, Main.class);
        intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);  // Identifies the particular widget...
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        // Make the pending intent unique...
        intent2.setData(Uri.parse(intent2.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.textViewWidgetNumber, pendIntent);
        views.setOnClickPendingIntent(R.id.textViewWidgetRestaurant, pendIntent);
        appWidgetManager.updateAppWidget(appWidgetIds,views);
        
        // Finally update all widgets with the information about the click listener
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        // Update the widgets via the service
        context.startService(intent);
    }
}