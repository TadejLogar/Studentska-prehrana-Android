package si.feri.projekt.studentskaprehrana.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.camera.CameraActivity;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.CommentData;
import com.sciget.studentmeals.client.service.data.FileData;
import com.sciget.studentmeals.database.Database;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.StudentMealCommentData;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.R.drawable;
import si.feri.projekt.studentskaprehrana.R.id;
import si.feri.projekt.studentskaprehrana.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsActivity extends MainActivity {
    public static final String FILE_URL = "http://" + Perferences.SERVER + ":8080/StudentMealsWebService/restaurantFiles?hash=";
    
    private ListApplication app;

    TextView name;
    TextView address;
    TextView phone;
    TextView fee;
    TextView time;
    ImageButton fav;
    Button menuButton;
    TextView comments;
    LinearLayout imagesLinearLayout;

    private RestaurantData currentProvider;

    private Button addButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ListApplication) getApplication();
        setContentView(R.layout.provider);

        name = (TextView) findViewById(R.id.textViewProviderName);
        address = (TextView) findViewById(R.id.textViewAddress);
        phone = (TextView) findViewById(R.id.textViewPhone);
        fee = (TextView) findViewById(R.id.textViewFee);
        time = (TextView) findViewById(R.id.textViewOpenTime);
        fav = (ImageButton) findViewById(R.id.imageButtonFav);
        menuButton = (Button) findViewById(R.id.buttonMenu);
        comments = (TextView) findViewById(R.id.textViewComments);
        addButton = (Button) findViewById(R.id.buttonAdd);
        imagesLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutImages);
        
        registerForContextMenu(addButton);
        
        currentProvider = app.getProviderById(app.getCurrentProvider());
        

        setData(app.getCurrentProvider());

    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    public void backClick(View v) {
        finish();
    }

    public void menuClick(View v) {
        Intent i = new Intent(this, MenuListActivity.class);
        i.putExtra("hash", currentProvider.getHash());
        startActivity(i);
    }

    public void mapLocationClick(View v) {
        Intent i = new Intent(
                this,
                si.feri.projekt.studentskaprehrana.activity.ProviderMapActivity.class);
        i.putExtra("latitude", currentProvider.getLocationLatitude());
        i.putExtra("longitude", currentProvider.getLocationLongitude());
        startActivity(i);
    }

    public void addToFavoritesListClick(View v) {
        setFavorite(currentProvider.getId(), MyPerferences.getInstance().getUserId());
    }
    
    private void setFavorite(int restaurantId, int userId) {
        boolean favorited = app.favoritesDB.isFavorited(restaurantId);
        if (favorited) {
            app.favoritesDB.removeFavorite(restaurantId);
            currentProvider.setFavorited(false);
            fav.setBackgroundResource(R.drawable.unstarred48);
        } else {
            app.favoritesDB.addFavorite(restaurantId);
            currentProvider.setFavorited(true);
            fav.setBackgroundResource(R.drawable.starred48);
        }
    }

    public void setData(int id) {
        final RestaurantData provider = app.getProviderById(id);
        provider.setFavorited(app.favoritesDB.isFavorited(currentProvider.getId()));
        
        int restaurantId = currentProvider.getId();
        /*Vector<StudentMealCommentData> commentsList = getComments(currentProvider.getId());
        String comm = "";
        for (StudentMealCommentData comment : commentsList) {
            comm += comment.toString();
        }
        comments.setText(comm);
        */

        name.setText(provider.getName());
        address.setText(provider.getFullAddress());
        String providerStr = provider.getFullPhone();
        if (providerStr == null) {
            phone.setText(providerStr);
        } else {
            phone.setVisibility(View.GONE); 
        }
        
        fee.setText("Doplačilo: " + provider.getEuroFee());
        
        time.setText(provider.getOpenTimes());
        // TODO popravi blok
        /*
         * Boolean menu = provider.getMenu(); if (menu == null) {
         * provider.setMenu(app.menusDB.menuExists(provider.getHash())); menu =
         * provider.getMenu(); }
         */

        // TODO
        /*
         * if (!menu) { menuButton.setVisibility(View.INVISIBLE); }
         */

        if (provider.getFavorited()) {
            fav.setBackgroundResource(R.drawable.starred48);
        }

        new DownloadImageTask().execute(provider.getFullAddress());
        
        new DownloadRestaurantImagesTask().execute();
        
        new DownloadRestaurantCommentsTask().execute(restaurantId);
    }

    public void addClick(View view) {
        openContextMenu(view);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Dodaj");
        menu.add(0, 1, 0, "Dodaj komentar");
        menu.add(0, 2, 0, "Dodaj zvočni posnetek");
        menu.add(0, 3, 0, "Dodaj sliko");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            Intent i = new Intent(this, CommentActivity.class);
            i.putExtra("restaurantId", currentProvider.getId());
            startActivity(i);
        } else if (item.getItemId() == 2) {
            //function2(item.getItemId());
        } else if (item.getItemId() == 3) {
            Intent i = new Intent(this, CameraActivity.class);
            i.putExtra("restaurantId", currentProvider.getId());
            startActivity(i);
        } else {
            return false;
        }
        return true;
    }

    private Vector<StudentMealCommentData> getComments(int restaurantId) {
        StudentMealsService meals = new StudentMealsService();
        Vector<CommentData> list = meals.getComments(restaurantId);
        Vector<StudentMealCommentData> comments = new Vector<StudentMealCommentData>();
        for (CommentData comment : list) {
            comments.add(new StudentMealCommentData(comment.userId, comment.restaurantId, comment.comment, Database.toTimestamp(comment.time)));
        }
        return comments;
    }
    
    private Vector<FileData> getFiles(int restaurantId) {
        StudentMealsService meals = new StudentMealsService();
        Vector<FileData> list = meals.restaurantFiles(restaurantId);
        return list;
    }
    
    private class DownloadRestaurantCommentsTask extends AsyncTask<Integer, StudentMealCommentData, Void> {

        @Override
        protected Void doInBackground(Integer... restaurantId) {
            Vector<StudentMealCommentData> commentsList = getComments(currentProvider.getId());
            StudentMealCommentData[] comments = new StudentMealCommentData[commentsList.size()];
            commentsList.toArray(comments);
            publishProgress(comments);
            return null;
        }

        protected void onProgressUpdate(StudentMealCommentData... commentsList) {
            String comm = "";
            for (StudentMealCommentData comment : commentsList) {
                comm += comment.toString();
            }
            comments.setText(comm);
        }
    }
    
    private class DownloadRestaurantImagesTask extends AsyncTask<String, Bitmap, Void> {

        @Override
        protected Void doInBackground(String... url) {
            Vector<FileData> files = getFiles(currentProvider.getId());
            Bitmap[] imgs = new Bitmap[files.size()];
            for (int i = 0; i < files.size(); i++) {
                try {
                    String url0 = FILE_URL + files.get(i).getSmallHash();
                    Bitmap img = BitmapFactory.decodeStream(new URL(url0).openStream());
                    imgs[i] = img;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(imgs);
            return null;
        }

        protected void onProgressUpdate(Bitmap... imgs) {
            int i = 0;
            for (Bitmap img : imgs) {
                ImageView image = new ImageView(DetailsActivity.this);
                image.setImageBitmap(img);
                imagesLinearLayout.addView(image, i);
                i++;
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Bitmap, Void> {

        @Override
        protected Void doInBackground(String... url) {
            loadMapImage(url[0]);
            return null;
        }

        protected void onProgressUpdate(Bitmap... img) {
            ImageView i = (ImageView) findViewById(R.id.mapImage);
            i.setImageBitmap(img[0]);
        }

        public void loadMapImage(String address) {
            String query = address;
            try {
                query = URLEncoder.encode(address, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String url = "http://maps.google.com/maps/api/staticmap?center=" + query + "&markers=color:blue|" + query
                    + "&zoom=15&size=470x220&maptype=roadmap&sensor=false";
            try {
                Bitmap img = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                publishProgress(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
