package si.feri.projekt.studentskaprehrana;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapOverlay extends com.google.android.maps.Overlay {
    Road mRoad;
    ArrayList<GeoPoint> mPoints;

    public MapOverlay(Road road, MapView mv) {
            mRoad = road;
            mPoints = new ArrayList<GeoPoint>();
            if (road.mRoute.length > 0) {
                    for (int i = 0; i < road.mRoute.length; i++) {
                            mPoints.add(new GeoPoint((int) (road.mRoute[i][1] * 1000000),
                                            (int) (road.mRoute[i][0] * 1000000)));
                    }
                    int moveToLat = (mPoints.get(0).getLatitudeE6() + (mPoints.get(
                                    mPoints.size() - 1).getLatitudeE6() - mPoints.get(0)
                                    .getLatitudeE6()) / 2);
                    int moveToLong = (mPoints.get(0).getLongitudeE6() + (mPoints.get(
                                    mPoints.size() - 1).getLongitudeE6() - mPoints.get(0)
                                    .getLongitudeE6()) / 2);
                    GeoPoint moveTo = new GeoPoint(mPoints.get(0).getLatitudeE6(), mPoints.get(0).getLongitudeE6());

                    MapController mapController = mv.getController();
                    mapController.animateTo(moveTo);
                    mapController.setZoom(15);
            }
    }

    @Override
    public boolean draw(Canvas canvas, MapView mv, boolean shadow, long when) {
            super.draw(canvas, mv, shadow);
            drawPath(mv, canvas);
            return true;
    }

    public void drawPath(MapView mv, Canvas canvas) {
            int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.argb(255, 194, 12, 43));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            for (int i = 0; i < mPoints.size(); i++) {
                Point point = new Point();
                mv.getProjection().toPixels(mPoints.get(i), point);
                x2 = point.x;
                y2 = point.y;
                if (i > 0) {
                    canvas.drawLine(x1, y1, x2, y2, paint);
                }
                x1 = x2;
                y1 = y2;
            }
            
            int color = Color.rgb(0, 79, 119);
            
            {
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                Point point = new Point();
                mv.getProjection().toPixels(mPoints.get(0), point);
                paint.setColor(color);
                RectF oval = new RectF(point.x - 5, point.y - 5, point.x + 5, point.y + 5);
                canvas.drawOval(oval, paint);
            }
            
            {
                paint.setAntiAlias(true);
                Point point = new Point();
                mv.getProjection().toPixels(mPoints.get(mPoints.size() -1), point);
                paint.setColor(color);
                RectF oval = new RectF(point.x - 5, point.y - 5, point.x + 5, point.y + 5);
                canvas.drawOval(oval, paint);
            }
        
        /*
        MapView mapView = mv;
        int defaultColor = 999;
        int mode = 1;
        int mRadius = 7;
        boolean shadow = true;
        Projection projection = mapView.getProjection();
        if (shadow == false) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Point point = new Point();
            projection.toPixels(gp1, point);
            if (mode == 1) {
                if (defaultColor == 999) {
                    paint.setColor(Color.BLUE);
                } else {
                    paint.setColor(defaultColor);
                }
                RectF oval = new RectF(point.x - mRadius, point.y - mRadius, point.x + mRadius, point.y + mRadius);
                canvas.drawOval(oval, paint);
            } else if (mode == 2) {
                if (defaultColor == 999) {
                    paint.setColor(Color.RED);
                } else {
                    paint.setColor(defaultColor);
                }

                Point point2 = new Point();
                projection.toPixels(gp2, point2);
                paint.setStrokeWidth(5);
                paint.setAlpha(120);
                canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
            } else if (mode == 3) {
                if (defaultColor == 999) {
                    paint.setColor(Color.MAGENTA);
                } else {
                    paint.setColor(defaultColor);
                }
                Point point2 = new Point();
                projection.toPixels(gp2, point2);
                paint.setStrokeWidth(5);
                paint.setAlpha(120);
                canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
                RectF oval = new RectF(point2.x - mRadius, point2.y - mRadius, point2.x + mRadius, point2.y + mRadius);
                
                paint.setAlpha(255);
                canvas.drawOval(oval, paint);

                RectF rec = new RectF(5, 10, 390, 35);
                paint.setColor(Color.TRANSPARENT);
                canvas.drawRect(rec, paint);

                Paint strokePaint = new Paint();
                strokePaint.setARGB(255, 0, 0, 0);
                strokePaint.setTextAlign(Paint.Align.CENTER);
                strokePaint.setTextSize(20);
                strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setStrokeWidth(2);

                Paint textPaint = new Paint();
                textPaint.setARGB(255, 255, 0, 0);
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(20);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                canvas.drawColor(Color.TRANSPARENT);

                //canvas.drawText(data, 195, 30, strokePaint);
                //canvas.drawText(data, 195, 30, textPaint);

            }
        }*/
    }
}