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
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

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
                    GeoPoint moveTo = new GeoPoint(moveToLat, moveToLong);

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
            paint.setColor(Color.RED);
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
    }
}