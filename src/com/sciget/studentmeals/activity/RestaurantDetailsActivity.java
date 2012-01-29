package com.sciget.studentmeals.activity;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

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
import com.sciget.studentmeals.client.service.data.RestaurantData.Features;
import com.sciget.studentmeals.database.Database;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.StudentMealCommentData;
import com.sciget.studentmeals.helper.Helper;
import com.sciget.studentmeals.task.RestaurantMapImageTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
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
    
    protected static final int ID_COMMENT = 1;
    protected static final int ID_IMAGE = 2;
    
    private class Icon {
        public ImageView celiac; // celiakiji prijazni obroki
        public ImageView delivery; // dostava
        public ImageView mealAssembly; // možnost sestavljanja obrokov
        public ImageView salad; // solatni bar
        public ImageView invalid; // dostop za invalide
        public ImageView invalidWC; // dostop za invalide (WC)
        public ImageView vegetarian; // vegetarijanska prehrana
        public ImageView weekend; // odprto ob vikendih
    }
    
    private MainApplication application;
    private RestaurantData provider;
    
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView messageTextView;
    private TextView feeTextView;
    private TextView timeWorkdayTextView;
    private TextView timeSaturdayTextView;
    private TextView timeSundayTextView;
    private LinearLayout iconsPropertiesLinearLayout; // linearLayoutPropertiesIcons
    private LinearLayout openTimeslinearLayout;
    private ImageButton favoriteImageButton;
    private Button menuButton;
    private TextView commentsTextView;
    private TextView distanceTextView;
    private LinearLayout imagesLinearLayout;
    private Button addButton;
    //private TextView propertiesTextView;
    private ImageView mapImageView;
    private Icon icon;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider3);
        application = (MainApplication) getApplication();
        setViews();
        Bundle extras = getIntent().getExtras();
        setRestaurant(extras.getInt(RESTAURANT_ID_KEY));
        setData();
        addMenu();
    }

    private void setData() {
        nameTextView.setText(provider.getName());
        addressTextView.setText(provider.getFullAddress());
        String providerStr = provider.getFullPhone();
        if (providerStr == null) {
            phoneTextView.setVisibility(View.GONE);
        } else {
            phoneTextView.setText(providerStr);
        }
        String message = provider.message;
        if (message != null && message.length() > 0 && !message.equals("anyType{}")) {
            messageTextView.setText(Helper.data("Obvestilo", message));
        } else {
            messageTextView.setVisibility(View.GONE); 
        }
        feeTextView.setText(provider.getEuroFee());
        int day = MyPerferences.getInstance().getOpenTimeType();
        if (day == MyPerferences.Day.WORKDAY) {
            timeWorkdayTextView.setTypeface(null, Typeface.BOLD);
        } else if (day == MyPerferences.Day.WORKDAY) {
            timeSaturdayTextView.setTypeface(null, Typeface.BOLD);
        } else if (day == MyPerferences.Day.WORKDAY) {
            timeSundayTextView.setTypeface(null, Typeface.BOLD);
        }
        timeWorkdayTextView.setText(provider.getOpenTimeWorkday());
        timeSaturdayTextView.setText(provider.getOpenTimeSaturday());
        timeSundayTextView.setText(provider.getOpenTimeSunday());
        
        String distance = Helper.dataInNewLine("Razdalja", Helper.distanceInMeters(provider.getDistance()));
        distanceTextView.setText(Helper.data("Razdalja", Helper.distanceInMeters(provider.getDistance())));
        //String message = Helper.dataInNewLine("Obvestilo", provider.message);
        //propertiesTextView.setText(provider.getFeatures().toString() + distance + message);
        setIconsData();
        setMapImage();
        setFavoriteIcon(application.isRestaurantFavorited(provider.getId()));
        if (!application.hasMenus(provider.getId())) {
            menuButton.setVisibility(View.GONE);
        }
        new DownloadRestaurantCommentsTask().execute();
        new DownloadRestaurantImagesTask().execute();
    }

    private void setMapImage() {
        RestaurantMapImageTask task = new RestaurantMapImageTask(application, provider.id, mapImageView, provider.mapImageSha1, provider.address + ", " + provider.post);
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
        openTimeslinearLayout = (LinearLayout) findViewById(R.id.linearLayoutOpenTimes);
        timeWorkdayTextView = (TextView) findViewById(R.id.textViewOpenTimeWorkday);
        timeSaturdayTextView= (TextView) findViewById(R.id.textViewOpenTimeSaturday);
        timeSundayTextView = (TextView) findViewById(R.id.textViewOpenTimeSunday);
        favoriteImageButton = (ImageButton) findViewById(R.id.imageButtonFavorite);
        menuButton = (Button) findViewById(R.id.buttonMenu);
        addButton = (Button) findViewById(R.id.buttonAdd);
        commentsTextView = (TextView) findViewById(R.id.textViewComments);
        imagesLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutImages);
        //propertiesTextView = (TextView) findViewById(R.id.textViewProperties);
        distanceTextView = (TextView) findViewById(R.id.textViewDistance);
        mapImageView = (ImageView) findViewById(R.id.mapImageView);
        messageTextView = (TextView) findViewById(R.id.textViewMessage);
        setIcons();
        
        //registerForContextMenu(addButton);
        
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
        
        OnClickListener mapClickListener = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantMapActivity.class);
                intent.putExtra(RestaurantMapActivity.LATITUDE_KEY, provider.locationLatitude);
                intent.putExtra(RestaurantMapActivity.LONGITUDE_KEY, provider.locationLongitude);
                startActivity(intent);
            }
        };
        
        openTimeslinearLayout.setOnClickListener(mapClickListener);
        mapImageView.setOnClickListener(mapClickListener);
        
        phoneTextView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                String phone = provider.getPhone();
                if (phone != null && phone.length() > 6) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantDetailsActivity.this);
                    builder.setMessage("Želiš poklicati?").setPositiveButton("Da", dialogPhoneClickListener).setNegativeButton("Ne", dialogPhoneClickListener).show();
                }
            }
        });
    }
    
    DialogInterface.OnClickListener dialogPhoneClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    
                    String phone = provider.getPhone();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phone.replace(" ", "")));
                    startActivity(intent);
                    
                    break;
    
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };


    private void setIcons() {
        icon = new Icon();
        icon.celiac = (ImageView) findViewById(R.id.icncoeliac);
        icon.delivery = (ImageView) findViewById(R.id.icndelivery);
        icon.salad = (ImageView) findViewById(R.id.icnsaladbar);
        icon.vegetarian = (ImageView) findViewById(R.id.icnvegetarian);
        icon.weekend = (ImageView) findViewById(R.id.icnweekend);
        icon.invalidWC = (ImageView) findViewById(R.id.icnwheelchairwc);
    }
    
    private void setIconsData() {
        Features features = provider.getFeatures();
        if (features.celiac) {
            icon.celiac.setVisibility(View.VISIBLE);
        }
        if (features.delivery) {
            icon.delivery.setVisibility(View.VISIBLE);
        }
        if (features.salad) {
            icon.salad.setVisibility(View.VISIBLE);
        }
        if (features.vegetarian) {
            icon.vegetarian.setVisibility(View.VISIBLE);
        }
        if (features.weekend) {
            icon.weekend.setVisibility(View.VISIBLE);
        }
        if (features.invalidWC) {
            icon.invalidWC.setVisibility(View.VISIBLE);
        }
    }

    public static String getFileDownloadUrl() {
        return "http://" + MyPerferences.getInstance().getServer() + "/StudentMealsWebService/restaurantFiles?hash=";
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Dodaj");
        menu.add(0, 1, 0, "Dodaj komentar");
        //menu.add(0, 2, 0, "Dodaj zvočni posnetek");
        menu.add(0, 3, 0, "Dodaj sliko");
    }
    
    private void addMenu() {
        ActionItem addItem      = new ActionItem(ID_COMMENT, "Dodaj komentar", getResources().getDrawable(R.drawable.ic_add));
        ActionItem acceptItem   = new ActionItem(ID_IMAGE, "Dodaj sliko", getResources().getDrawable(R.drawable.ic_accept));
        
        final QuickAction mQuickAction  = new QuickAction(this);
        
        mQuickAction.addActionItem(addItem);
        mQuickAction.addActionItem(acceptItem);
        
        //setup the action item click listener
        mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction quickAction, int pos, int actionId) {
                ActionItem actionItem = quickAction.getActionItem(pos);
                
                if (actionId == ID_COMMENT) {
                    Intent i = new Intent(RestaurantDetailsActivity.this, CommentActivity.class);
                    i.putExtra(RESTAURANT_ID_KEY, provider.getId());
                    startActivity(i);
                } else if (actionId == ID_IMAGE) {
                    Intent i = new Intent(RestaurantDetailsActivity.this, CameraActivity.class);
                    i.putExtra(RESTAURANT_ID_KEY, provider.getId());
                    startActivity(i);
                }
            }
        });
        
        mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
                //Toast.makeText(getApplicationContext(), "Ups..dismissed", Toast.LENGTH_SHORT).show();
            }
        });
        
        /*Button btn1 = (Button) this.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mQuickAction.show(v);
            }
        });

        Button btn2 = (Button) this.findViewById(R.id.btn2);*/
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuickAction.show(v);
                mQuickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
            }
        });
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
        boolean favorited = !application.isRestaurantFavorited(restaurantId);
        setFavoriteIcon(favorited);
        application.setRestaurantFavorited(restaurantId, favorited);
    }
    
    private void setFavoriteIcon(boolean favorited) {
        if (favorited) {
            favoriteImageButton.setBackgroundResource(R.drawable.starred48);
        } else {
            favoriteImageButton.setBackgroundResource(R.drawable.unstarred48);
        }
    }
    
    public void drawDetails() {
        
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
