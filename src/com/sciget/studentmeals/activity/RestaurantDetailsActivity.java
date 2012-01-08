package com.sciget.studentmeals.activity;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.activity.CommentActivity;
import si.feri.projekt.studentskaprehrana.activity.DetailsActivity;
import si.feri.projekt.studentskaprehrana.activity.MainActivity;
import si.feri.projekt.studentskaprehrana.activity.MenuForActivity;
import si.feri.projekt.studentskaprehrana.activity.MenuListActivity;
import si.feri.projekt.studentskaprehrana.activity.ProviderMapActivity;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.camera.CameraActivity;
import com.sciget.studentmeals.camera.Security;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.CommentData;
import com.sciget.studentmeals.client.service.data.FileData;
import com.sciget.studentmeals.database.Database;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.StudentMealCommentData;
import com.sciget.studentmeals.helper.Helper;
import com.sciget.studentmeals.task.RestaurantMapImageTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RestaurantDetailsActivity extends MainActivity {
    public static final String RESTAURANT_ID_KEY = "restaurantId";
    
    private MainApplication application;
    private RestaurantData provider;
    
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView feeTextView;
    private TextView timeTextView;
    private ImageButton favoriteImageButton;
    private Button menuButton;
    private TextView commentsTextView;
    private LinearLayout imagesLinearLayout;
    private Button addButton;
    private TextView propertiesTextView;
    private ImageView mapImageView;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider);
        application = (MainApplication) getApplication();
        setViews();
        Bundle extras = getIntent().getExtras();
        setRestaurant(extras.getInt(RESTAURANT_ID_KEY));
        setData();
    }

    private void setData() {
        nameTextView.setText(provider.getName());
        addressTextView.setText(provider.getFullAddress());
        String providerStr = provider.getFullPhone();
        if (providerStr == null) {
            phoneTextView.setText(providerStr);
        } else {
            phoneTextView.setVisibility(View.GONE); 
        }
        feeTextView.setText("Doplačilo: " + provider.getEuroFee());
        timeTextView.setText(provider.getOpenTimes());
        String distance = Helper.distanceInMeters(provider.getDistance());
        distance = Helper.dataInNewLine("Razdalja", distance);
        propertiesTextView.setText(provider.getFeatures().toString() + distance);
        setMapImage();
        new DownloadRestaurantCommentsTask().execute();
        new DownloadRestaurantImagesTask().execute();
    }

    private void setMapImage() {
        RestaurantMapImageTask task = new RestaurantMapImageTask(application, provider.id, mapImageView, provider.mapImageSha1, provider.address);
        task.execute();
    }

    private void setRestaurant(int restaurantId) {
        provider = application.getRestaurantById(restaurantId);
    }

    private void setViews() {
        nameTextView = (TextView) findViewById(R.id.textViewProviderName);
        addressTextView = (TextView) findViewById(R.id.textViewAddress);
        phoneTextView = (TextView) findViewById(R.id.textViewPhone);
        feeTextView = (TextView) findViewById(R.id.textViewFee);
        timeTextView = (TextView) findViewById(R.id.textViewOpenTime);
        favoriteImageButton = (ImageButton) findViewById(R.id.imageButtonFav);
        menuButton = (Button) findViewById(R.id.buttonMenu);
        commentsTextView = (TextView) findViewById(R.id.textViewComments);
        addButton = (Button) findViewById(R.id.buttonAdd);
        imagesLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutImages);
        propertiesTextView = (TextView) findViewById(R.id.textViewProperties);
        mapImageView = (ImageView) findViewById(R.id.mapImage);
        
        registerForContextMenu(addButton);
        
        favoriteImageButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setFavorite(provider.id);
            }
        });
        
        addButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View view) {
                openContextMenu(view);
            }
        });
        
        menuButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantMenuListActivity.class);
                intent.putExtra(RESTAURANT_ID_KEY, provider.getId());
                startActivity(intent);
            }
        });
        
        mapImageView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantMapActivity.class);
                intent.putExtra(RestaurantMapActivity.LATITUDE_KEY, provider.locationLatitude);
                intent.putExtra(RestaurantMapActivity.LONGITUDE_KEY, provider.locationLongitude);
                startActivity(intent);
            }
        });
    }

    public static String getFileDownloadUrl() {
        return "http://" + MyPerferences.getInstance().getServer() + ":8080/StudentMealsWebService/restaurantFiles?hash=";
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Dodaj");
        menu.add(0, 1, 0, "Dodaj komentar");
        //menu.add(0, 2, 0, "Dodaj zvočni posnetek");
        menu.add(0, 3, 0, "Dodaj sliko");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            Intent i = new Intent(this, CommentActivity.class);
            i.putExtra(RESTAURANT_ID_KEY, provider.getId());
            startActivity(i);
        } else if (item.getItemId() == 2) {
            
        } else if (item.getItemId() == 3) {
            Intent i = new Intent(this, CameraActivity.class);
            i.putExtra(RESTAURANT_ID_KEY, provider.getId());
            startActivity(i);
        } else {
            return false;
        }
        return true;
    }
    
    private void setFavorite(int restaurantId) {
        boolean favorited = application.isRestaurantFavorited(restaurantId);
        if (favorited) {
            favoriteImageButton.setBackgroundResource(R.drawable.unstarred48);
        } else {
            favoriteImageButton.setBackgroundResource(R.drawable.starred48);
        }
        application.setRestaurantFavorited(restaurantId, !favorited);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuForActivity.onOptionsItemSelected(item, this);
    }
    
    public boolean onCreateOptionsMenu(Menu mMenu) {
        return MenuForActivity.onCreateOptionsMenu(mMenu, this);
    }
    
    private class DownloadRestaurantCommentsTask extends AsyncTask<Void, StudentMealCommentData, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Vector<StudentMealCommentData> commentsList = getComments(provider.getId());
            StudentMealCommentData[] comments = new StudentMealCommentData[commentsList.size()];
            commentsList.toArray(comments);
            publishProgress(comments);
            return null;
        }

        protected void onProgressUpdate(StudentMealCommentData... commentsList) {
            StringBuilder str = new StringBuilder();
            for (StudentMealCommentData comment : commentsList) {
                if (str.length() > 0) str.append("\n");
                if (comment.name != null && comment.name.length() > 0) {
                    str.append(comment.name + " (" + Helper.toSloTime(comment.time) + "): ");
                } else {
                    str.append(Helper.toSloTime(comment.time) + ": ");
                }
                str.append(comment.comment);
                if (comment.rate > 0) {
                    str.append(" ");
                    for (int i = 0; i < comment.rate; i++) {
                        str.append("★");
                    }
                }
            }
            commentsTextView.setText(str.toString());
        }
        
        private Vector<StudentMealCommentData> getComments(int restaurantId) {
            StudentMealsService meals = new StudentMealsService();
            Vector<CommentData> list = meals.getComments(restaurantId);
            Vector<StudentMealCommentData> comments = new Vector<StudentMealCommentData>();
            for (CommentData comment : list) {
                comments.add(new StudentMealCommentData(comment.userId, comment.restaurantId, comment.name, comment.rate, comment.comment, Database.toTimestamp(comment.time)));
            }
            return comments;
        }
    }
    
    private class DownloadRestaurantImagesTask extends AsyncTask<String, Bitmap, Void> {

        @Override
        protected Void doInBackground(String... url) {
            Vector<FileData> files = getFiles(provider.getId());
            Bitmap[] imgs = new Bitmap[files.size()];
            for (int i = 0; i < files.size(); i++) {
                imgs[i] = getBitmap(files.get(i).getSmallHash());
            }
            publishProgress(imgs);
            return null;
        }

        private Bitmap getBitmap(String smallHash) {
            File file = new File(MyPerferences.getInstance().getExternalStoragePath() + smallHash);
            if (!file.exists()) {
                try {
                    MVC.downloadToFile(getFileDownloadUrl() + smallHash, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        protected void onProgressUpdate(Bitmap... imgs) {
            int i = 0;
            for (Bitmap img : imgs) {
                ImageView image = new ImageView(RestaurantDetailsActivity.this);
                image.setImageBitmap(img);
                imagesLinearLayout.addView(image, i);
                i++;
            }
        }
        
        private Vector<FileData> getFiles(int restaurantId) {
            StudentMealsService meals = new StudentMealsService();
            Vector<FileData> list = meals.restaurantFiles(restaurantId);
            return list;
        }
    }
}
